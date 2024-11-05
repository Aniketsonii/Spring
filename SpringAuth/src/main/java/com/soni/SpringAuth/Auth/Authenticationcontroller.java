package com.soni.SpringAuth.Auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping({ "/api/auth", "/api/auth/" })
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class Authenticationcontroller {

    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping({ "/signup", "/signup/" })
    public ResponseEntity<Object> signUp(@RequestBody UserSignupDTO userSignupDTO) {

        return authenticationService.register(userSignupDTO);
    }
    
    @PostMapping({ "/signin", "/signin/" })
    public ResponseEntity<Object> signIn(@RequestBody UserSigninDTO userSigninDTO) {

        return authenticationService.authenticate(userSigninDTO);
    }

}
