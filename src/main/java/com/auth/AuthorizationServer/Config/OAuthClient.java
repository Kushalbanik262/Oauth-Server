package com.auth.AuthorizationServer.Config;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Arrays;
import java.util.UUID;


@Builder
@Data
@NoArgsConstructor@AllArgsConstructor
@Table(name = "OAuthClient")
@Entity
public class OAuthClient {
    @Id
    private String clientId;
    @Column(nullable = false)
    private String clientSecret;
    @Column(nullable = false)
    private String redirectUri;
    @Column(nullable = false)
    private String scopes;
    @Column(nullable = false)
    private String grantTypes;

    public RegisteredClient registerThisClient(){
        return RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(clientId)
                .redirectUri(redirectUri)
                .clientSecret("{noop}"+clientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantTypes((grant)->{
                    for(String cGrant:grantTypes.split(",")) {
                        grant.add(new AuthorizationGrantType(cGrant.trim()));
                    }
                })
                .scopes(scope->{
                    scope.addAll(Arrays.asList(scopes.split(",")));
                }).build();

    }

}
