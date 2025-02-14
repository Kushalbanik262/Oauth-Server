package com.auth.AuthorizationServer.generator;

import com.auth.AuthorizationServer.Config.ITokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Instant;
import java.util.Map;

@Service
public class TokenGenerator implements OAuth2TokenGenerator<OAuth2Token> {

    @Autowired
    private ITokenGenerator tokenGenerator;

    @Override
    public OAuth2Token generate(OAuth2TokenContext context) {
        if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())){
            return new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,tokenGenerator.generateReferenceAccessToken(context),Instant.now(),Instant.now().plusSeconds(3600));
        }

        if (OAuth2TokenType.REFRESH_TOKEN.equals(context.getTokenType())){
            return new OAuth2RefreshToken(tokenGenerator.generateReferenceRefreshToken(context), Instant.now(),Instant.now().plusSeconds(86400));
        }


        return new OidcIdToken(
                    tokenGenerator.generateIdToken(context),
                    Instant.now(),
                    Instant.now().plusSeconds(3600),
                    Map.of(
                            "sub", context.getPrincipal().getName(),
                            "iss", ServletUriComponentsBuilder.fromCurrentRequest().toUriString(),
                            "aud", context.getRegisteredClient().getClientId(),
                            "iat", Instant.now().getEpochSecond(),
                            "exp", Instant.now().plusSeconds(3600).getEpochSecond()
                    ));
   }

}
