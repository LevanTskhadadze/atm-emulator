package com.egc.bankservice.model.entity.card;

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
public class AuthenticationDetails {

    @Id
    @GeneratedValue(generator = "AuthenticationSequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "AuthenticationSequence", allocationSize = 5)
    private Long id;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private AuthType authType;

    private String pinCode;

    private String fingerPrint;
}
