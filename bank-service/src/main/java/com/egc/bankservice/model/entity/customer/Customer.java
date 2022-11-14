package com.egc.bankservice.model.entity.customer;

import com.egc.bankservice.model.entity.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(generator = "CustomerSequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerSequence", allocationSize = 5)
    private Long id;

    @Column(length = 15)
    private String personalNumber;

    @Column(length = 100)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    @ToString.Exclude
    @OneToMany(mappedBy = "customer")
    private List<Account> accounts = new ArrayList<>();
}
