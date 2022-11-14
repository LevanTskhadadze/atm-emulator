package com.egc.bankservice.controller;

import com.egc.bankservice.domain.auth.AuthenticationRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "authentication")
@RestController
@RequestMapping("/authentication")
public class DummyController {

    @PostMapping
    String authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return "";
    }
}
