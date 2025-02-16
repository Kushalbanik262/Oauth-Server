package com.auth.AuthorizationServer.Config;

import com.auth.AuthorizationServer.Exception.UserNotExistsException;
import com.auth.AuthorizationServer.Models.ReferenceTokenDTO;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryTokenGenerator implements ITokenGenerator{
    private static Map<String,String> inMemoryAccessTokenStore = new ConcurrentHashMap<>();
    private static Map<String,String> inMemoryRefreshTokenStore = new ConcurrentHashMap<>();
    private static Map<String,String> inMemoryIdTokenStore = new ConcurrentHashMap<>();

    @Override
    public String generateReferenceAccessToken(OAuth2TokenContext context) {
        String currentUser = context.getPrincipal().getName();
        String opecToken = "KB_AC" + UUID.randomUUID().toString();
        inMemoryAccessTokenStore.put(opecToken,currentUser);
        return opecToken;
    }

    @Override
    public String generateReferenceRefreshToken(OAuth2TokenContext context) {
        String opecRefreshToken = "KB_RF" + UUID.randomUUID().toString();
        inMemoryRefreshTokenStore.put(opecRefreshToken,"valid");
        return opecRefreshToken;
    }

    @Override
    public String generateIdToken(OAuth2TokenContext context) {
        String opecIdToken = "KB_ID" + UUID.randomUUID();
        inMemoryIdTokenStore.put(opecIdToken,context.getPrincipal().getName());
        return opecIdToken;
    }

    @Override
    public String getUserFromtoken(ReferenceTokenDTO token) {
        if(!inMemoryAccessTokenStore.containsKey(token.getToken())){throw new UserNotExistsException();}
        return inMemoryAccessTokenStore.get(token.getToken());
    }
}
