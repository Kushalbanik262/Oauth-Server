package com.auth.AuthorizationServer.Config;

import com.auth.AuthorizationServer.Db.OAuthClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

@Service
public class DbClientRegistry implements RegisteredClientRepository {

    @Autowired
    private OAuthClientRepository oAuthClientRepository;


    private OAuthClient getClientFromRegisteredClient(RegisteredClient registeredClient){
        return new OAuthClient.OAuthClientBuilder()
                .clientId(registeredClient.getClientId())
                .clientSecret(registeredClient.getClientSecret())
                .redirectUri(registeredClient.getRedirectUris().stream().reduce((cur,ac)->ac+","+cur).toString())
                .grantTypes(registeredClient.getAuthorizationGrantTypes().stream().reduce((ac,cur)->new AuthorizationGrantType(ac+","+cur)).toString())
                .scopes(registeredClient.getScopes().stream().reduce((cur,ac)->ac+","+cur).toString())
                .build();
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        oAuthClientRepository.save(getClientFromRegisteredClient(registeredClient));
    }

    @Override
    public RegisteredClient findById(String id) {
        throw new RuntimeException("Feature not implemented");
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return oAuthClientRepository.findById(clientId).get().registerThisClient();
    }
}
