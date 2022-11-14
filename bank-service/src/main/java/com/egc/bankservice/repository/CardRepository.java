package com.egc.bankservice.repository;

import com.egc.bankservice.model.entity.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("SELECT c FROM Card c LEFT JOIN FETCH c.account a WHERE c.number = :number")
    Optional<Card> findByNumberWithAccount(String number);

    @Query("SELECT c FROM Card c LEFT JOIN FETCH c.authenticationDetails a WHERE c.number = :number")
    Optional<Card> findByNumberWithAuthentication(String number);
}