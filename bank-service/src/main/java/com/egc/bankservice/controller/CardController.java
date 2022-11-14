package com.egc.bankservice.controller;

import com.egc.bankservice.model.entity.card.AuthType;
import com.egc.bankservice.model.exception.BankServiceException;
import com.egc.bankservice.service.card.CardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;


@Tag(name = "Card Validation")
@RestController
@RequestMapping("card")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping({"{cardNumber}"})
    AuthType getCardAuthenticationType(@NotBlank @PathVariable  String cardNumber) throws BankServiceException {
        return cardService.getCardAuthenticationType(cardNumber);
    }
}
