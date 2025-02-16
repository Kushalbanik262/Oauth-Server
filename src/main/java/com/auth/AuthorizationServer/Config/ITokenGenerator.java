package com.auth.AuthorizationServer.Config;

import com.auth.AuthorizationServer.Models.ReferenceTokenDTO;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;

import javax.naming.Context;

public interface ITokenGenerator {
    /***
     *
     * @param context Current Context
     * @return The Reference Access Token
     */
    public String generateReferenceAccessToken(OAuth2TokenContext context);


    /**
     * @param context Current Context
     */
    public String generateReferenceRefreshToken(OAuth2TokenContext context);


    /**
     * Id token generator
     */
    public String generateIdToken(OAuth2TokenContext context);

    /***
     *
     * @param token The token I want to lookup
     * @return the user present with the token
     */
    public String getUserFromtoken(ReferenceTokenDTO token);
}
