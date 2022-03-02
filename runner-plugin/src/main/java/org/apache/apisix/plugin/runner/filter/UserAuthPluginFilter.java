package org.apache.apisix.plugin.runner.filter;

import com.bnyte.xuni.http.reactive.web.R;
import com.bnyte.xuni.http.reactive.web.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.apisix.plugin.runner.HttpRequest;
import org.apache.apisix.plugin.runner.HttpResponse;
import org.apache.apisix.plugin.runner.entity.User;
import org.apache.apisix.plugin.runner.exception.BasicException;
import org.apache.apisix.plugin.runner.forest.SsoServiceClient;
import org.apache.apisix.plugin.runner.pojo.RequestRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class UserAuthPluginFilter implements PluginFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthPluginFilter.class);

    @Autowired
    SsoServiceClient userServiceClient;

    @Override
    public String name() {
        return "UserAuthPluginFilter";
    }

    @Override
    public void filter(HttpRequest request, HttpResponse response, PluginFilterChain chain) {
        String path = request.getPath();
        LOGGER.info("[AuthPluginFilter] request path is '" + path + "'");

        try {
            RequestRoute requestRoute = RequestRoute.initialize(path);
            LOGGER.info("[AuthPluginFilter] current request route parsing result > " + requestRoute);

            if (requestRoute.isAuthentication()) {
                switch (requestRoute.getVersion()) {
                    case v1:
                        v1Auth(requestRoute, request, response, chain);

                        break;
                    default:
                        throw new BasicException("request rule not found");
                }
            }
            // rewrite request uri
            rewriteUri(request, 2);

        } catch (BasicException e) {
            Gson gson = new GsonBuilder().serializeNulls().create();
            R<Object> r = R.error();
            r.setCode(e.getCode());
            r.setMessage(e.getMessage());
            response.setStatusCode(e.getCode());
            response.setBody(gson.toJson(r));
        } catch (Exception e) {
            Gson gson = new GsonBuilder().serializeNulls().create();
            R<Object> error = R.error();
            error.setCode(403);
            error.setMessage(e.getMessage());
            response.setStatusCode(error.getCode());
            response.setBody(gson.toJson(error));
        }

        chain.filter(request, response);
    }

    /**
     * rewrite request path
     * @param request apisix request object
     * @param rewriteCount rewrite count
     */
    private void rewriteUri(HttpRequest request, int rewriteCount) {
        String path = request.getPath();
        if (path.startsWith("/")) path = path.replaceFirst("/", "");
        for (int i = 0; i < rewriteCount; i++) {
            int currentPathSeparatorIndex = path.indexOf("/") + 1;
            path = path.substring(currentPathSeparatorIndex);
        }
        path = "/" + path;
        request.setPath(path);

        LOGGER.info("[AuthPluginFilter] rewrite path is '" + path + "'");
    }

    private void v2Auth(RequestRoute requestRoute, HttpRequest request, HttpResponse response) {

    }

    /**
     *
     * @param requestRoute request route object
     * @param request apisix plugin request entity
     * @param response apisix plugin response entity
     */
    private void v1Auth(RequestRoute requestRoute, HttpRequest request, HttpResponse response, PluginFilterChain chain) {
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            LOGGER.info("[AuthPluginFilter] current request header not find");
            throw new BasicException(401, "auth error");
        } else {
            R<User> authorize = userServiceClient.authorize(token);
            LOGGER.info("[AuthPluginFilter] current request token : {} , authorize result {}", token, authorize);
            if (!authorize.getCode().equals(Status.ok.getCode())) {
                throw new BasicException(401, authorize.getMessage());
            } else {
                request.setHeader("id", authorize.getData().getId());
                LOGGER.info("[AuthPluginFilter] rewrite heater is '{}'", new GsonBuilder().serializeNulls().create().toJson(request.getHeader()));
            }
        }
    }

    @Override
    public List<String> requiredVars() {
        return null;
    }

    @Override
    public Boolean requiredBody() {
        return null;
    }
}
