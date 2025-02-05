package com.auth.AuthorizationServer.Controller;

import com.auth.AuthorizationServer.Config.DbClientRegistry;
import com.auth.AuthorizationServer.Config.OAuthClient;
import com.auth.AuthorizationServer.Db.OAuthClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class OAuthClientController {

    @Autowired
    private OAuthClientRepository clientRepository;

    @PostMapping("/register")
    public ResponseEntity<OAuthClient> registerOAuthClient(@RequestBody OAuthClient client){
        clientRepository.save(client); // save to db
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }
}
