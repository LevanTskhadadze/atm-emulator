package com.egc.bankservice.model.entity.account;

import com.egc.bankservice.model.entity.card.Card;
import com.egc.bankservice.model.entity.customer.Customer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(generator = "AccountSequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "AccountSequence", allocationSize = 5)
    private Long id;

    private BigDecimal balance;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @ToString.Exclude
    @OneToMany(mappedBy = "account")
    private List<Card> cards = new ArrayList<>();


    @Version
    private int version;
}
