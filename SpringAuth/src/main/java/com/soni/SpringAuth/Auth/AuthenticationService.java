package com.soni.SpringAuth.Auth;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.soni.SpringAuth.common.CONSTANT;
import com.soni.SpringAuth.config.JwtService;
import com.soni.SpringAuth.role.Role;
import com.soni.SpringAuth.role.RoleRepository;
import com.soni.SpringAuth.user.User;
import com.soni.SpringAuth.user.UserRepository;
import com.soni.SpringAuth.utils.CookieUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CookieUtils cookieUtils;
    
    public ResponseEntity<Object> register(UserSignupDTO userSignupDTO) {
        Map<String, Object> body = new HashMap<String, Object>();
        try {
            Boolean isUsernameExists = userRepository.findByUsername(userSignupDTO.getUsername()).isPresent();
            if (isUsernameExists) {
              throw new Exception("Username already exists.");
            }

            Boolean isEmailExists = userRepository.findByEmail(userSignupDTO.getEmail()).isPresent();
            if (isEmailExists) {
              throw new Exception("Email already exists.");
            }

            Role userRole = roleRepository.findByAuthority(CONSTANT.ROLE_USER).get();
            Set<Role> authorities = new HashSet<Role>();
            authorities.add(userRole);

            User user = User.builder()
                                .firstname(userSignupDTO.getFirstname())
                                .lastname(userSignupDTO.getLastname())
                                .email(userSignupDTO.getEmail())
                                .username(userSignupDTO.getUsername())
                                .password(passwordEncoder.encode(userSignupDTO.getPassword()))
                                .authorities(authorities)
                                .build();

            User newlyCreatedUser = userRepository.save(user);

            body.put("status", "success");
            body.put("message", "New user created successfully");
            body.put("user", newlyCreatedUser);
            return new ResponseEntity<Object>(body, HttpStatus.OK);

        } catch (Exception e) {
            body.put("status", "failure");
            body.put("message", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<Object>(body, HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<Object> authenticate(UserSigninDTO userSigninDTO) {
        Map<String, Object> body = new HashMap<String, Object>();
        try {

            Boolean isUserExists = userRepository.findByUsername(userSigninDTO.getUsername()).isPresent();

            if (!isUserExists) {
                body.put("status", false);
                body.put("message", String.format("User with username \"%s\" does not exist", userSigninDTO.getUsername()));
                return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
            }

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userSigninDTO.getUsername(), userSigninDTO.getPassword()));

            User user = userRepository.findByUsername(userSigninDTO.getUsername()).get();
            String jwtToken = jwtService.generateToken(user);

            body.put("status", "success");
            body.put("message", "User signed in successfully");
            body.put("user", user);
            body.put(CONSTANT.ACCESS_TOKEN, jwtToken);
            cookieUtils.setCookieValue(CONSTANT.ACCESS_TOKEN, jwtToken);
            return new ResponseEntity<Object>(body, HttpStatus.OK);

        }catch (AuthenticationException e) {

            e.printStackTrace();
            body.put("status", "failure");
            body.put("message", "Invalid username or password");
            return new ResponseEntity<Object>(body, HttpStatus.FORBIDDEN);
        }
        
    }

}
