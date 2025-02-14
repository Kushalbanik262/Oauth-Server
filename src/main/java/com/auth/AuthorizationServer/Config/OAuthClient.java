package com.auth.AuthorizationServer.Config;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


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
    @Convert(converter = StringSetConverter.class)
    private Set<String> redirectUris;

    @Column(nullable = false)
    @Convert(converter = StringSetConverter.class)
    private Set<String> scopes;

    @Column(nullable = false)
    @Convert(converter = StringSetConverter.class)
    private Set<String> authorizationGrantTypes;


    public RegisteredClient registerThisClient(boolean retrive,PasswordEncoder passwordEncoder){
        return RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(clientId)
                .redirectUris(r->{
                    r.addAll(redirectUris);
                })
                .clientSecret(retrive?clientSecret:passwordEncoder.encode(clientSecret))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantTypes((grant)->{
                    grant.addAll(authorizationGrantTypes.stream().map(AuthorizationGrantType::new).collect(Collectors.toSet()));
                })
                .scopes(scope->{
                    scope.addAll(scopes);
                })
                .build();
    }

}
