package org.apache.apisix.plugin.runner.forest;

import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.GetRequest;
import com.dtflys.forest.annotation.Var;
import org.apache.apisix.plugin.runner.entity.User;
import org.apache.apisix.plugin.runner.response.R;

@BaseRequest(baseURL = "https://sso.bnyte.com")
public interface SsoServiceClient {

    /**
     * user authorize
     * @param token token
     * @return authorize result
     */
    @GetRequest("/user/authorize/{token}")
    R<User> authorize(@Var("token") String token);

}
