package com.auth.AuthorizationServer.Db;


import com.auth.AuthorizationServer.Config.OAuthClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthClientRepository extends JpaRepository<OAuthClient,String> {
}
