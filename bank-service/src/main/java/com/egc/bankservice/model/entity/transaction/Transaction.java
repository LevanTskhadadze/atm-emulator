package com.egc.bankservice.model.entity.transaction;

import com.egc.bankservice.model.entity.account.Account;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "AccountTransaction")
public class Transaction {

    @Id
    @GeneratedValue(generator = "AccountTransactionSequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "AccountTransactionSequence", allocationSize = 5)
    private Long id;

    @Column(precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private Date transactionTime;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Account account;
}
