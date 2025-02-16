package com.auth.AuthorizationServer.Controller;

import com.auth.AuthorizationServer.Config.ITokenGenerator;
import com.auth.AuthorizationServer.Models.ReferenceTokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenEndpointsController {

    @Autowired
    private ITokenGenerator tokenGenerator;

    @PostMapping("/introspect")
    public ResponseEntity getUserToken(@RequestBody ReferenceTokenDTO referenceToken){
        return ResponseEntity.ok(tokenGenerator.getUserFromtoken(referenceToken));
    }
}
