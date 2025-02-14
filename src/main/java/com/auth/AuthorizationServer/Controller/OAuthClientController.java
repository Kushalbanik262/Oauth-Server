package com.auth.AuthorizationServer.Controller;

import com.auth.AuthorizationServer.Config.DbClientRegistry;
import com.auth.AuthorizationServer.Config.OAuthClient;
import com.auth.AuthorizationServer.Db.OAuthClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/client")
@Slf4j
public class OAuthClientController {

    @Autowired
    private DbClientRegistry clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register new Client
    @PostMapping("/register")
    public ResponseEntity<OAuthClient> registerOAuthClient(@RequestBody OAuthClient client){
        clientRepository.save(client.registerThisClient(false,passwordEncoder)); // save to db
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<Map> updateOAuthClient(@RequestBody OAuthClient client){
        log.info("Client Updation Request:"+client);
        clientRepository.save(client.registerThisClient(false,passwordEncoder));
        Map<String,Object> mp  = new HashMap<>();
        mp.put("updatedClient",client);
        mp.put("message","Client Updated Successfully");
        return new ResponseEntity<>(mp,HttpStatus.CREATED);
    }
}
