package org.apache.apisix.plugin.runner.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.apisix.plugin.runner.enums.APIVersion;
import org.springframework.util.StringUtils;

/**
 * @auther bnyte
 * @date 2022-01-06 14:15
 * @email bnytezz@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestRoute {

    /**
     * current api version
     */
    private APIVersion version;

    /**
     * CURRENT API WHETHER MANDATORY AUTHENTICATION IS REQUIRED, TRUE FOR MANDATORY AUTHENTICATION AND VICE VERSA
     */
    private boolean authentication;

    public void setAuthentication(String authentication) {
        this.authentication = authentication.equals("auth");
    }

    /**
     * Initialize request routing
     * @param uri request path
     * @return Returns the parsed request routing object
     */
    public static RequestRoute initialize(String uri) {

        uri = uri.replaceFirst("/", "");

        // Pre-check
        preCheckUri(uri);

        // Configure the route object
        RequestRoute route = configRequestRoute(uri);

        return route;
    }

    private static RequestRoute configRequestRoute(String uri) {
        RequestRoute route = new RequestRoute();
        // Parse get request path
        String[] uris = uri.split("/");

        String apiVersion = uris[0];
        String authType = uris[1];

        route.setVersion(APIVersion.version(apiVersion));
        route.setAuthentication(authType);

        return route;
    }

    /**
     * Pre-uri check
     */
    private static void preCheckUri(String uri) {
        if (!StringUtils.hasText(uri)) throw new RuntimeException("forbidden");

        if (uri.startsWith("/")) uri = uri.replaceFirst("/", "");

        String[] uris = uri.split("/");

        if (APIVersion.version(uris[0]) == null) throw new RuntimeException("api version not found");

        if (!"open".equals(uris[1]) && !"auth".equals(uris[1])) throw new RuntimeException("request rule not found");
    }
}
