package org.apache.apisix.plugin.runner.forest;

import com.bnyte.xuni.http.reactive.web.R;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.GetRequest;
import com.dtflys.forest.annotation.Var;
import org.apache.apisix.plugin.runner.entity.User;

@BaseRequest(baseURL = "http://120.79.206.111:6601")
public interface SsoServiceClient {

    /**
     * user authorize
     * @param token token
     * @return authorize result
     */
    @GetRequest("/user/authorize/{token}")
    R<User> authorize(@Var("token") String token);

}
