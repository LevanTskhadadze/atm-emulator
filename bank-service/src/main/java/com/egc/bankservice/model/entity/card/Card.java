package com.egc.bankservice.model.entity.card;

import com.egc.bankservice.model.entity.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Card {

    @Id
    @GeneratedValue(generator = "CardSequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CardSequence", allocationSize = 5)
    private Long id;

    private String number;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private AuthenticationDetails authenticationDetails;
}
