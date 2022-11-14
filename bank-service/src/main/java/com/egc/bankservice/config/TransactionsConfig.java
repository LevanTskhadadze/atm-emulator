package com.egc.bankservice.config;

import com.egc.bankservice.model.exception.BankServiceException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration;
import org.springframework.transaction.interceptor.DelegatingTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

import java.lang.reflect.AnnotatedElement;

@Configuration
public class TransactionsConfig extends ProxyTransactionManagementConfiguration {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Override
    public TransactionAttributeSource transactionAttributeSource() {
        return new AnnotationTransactionAttributeSource() {
            @Nullable
            protected TransactionAttribute determineTransactionAttribute(AnnotatedElement element) {
                TransactionAttribute ta = super.determineTransactionAttribute(element);
                if (ta == null) {
                    return null;
                }
                return new DelegatingTransactionAttribute(ta) {
                    @Override
                    public boolean rollbackOn(Throwable ex) {
                        return super.rollbackOn(ex) || ex instanceof BankServiceException;
                    }
                };
            }
        };
    }
}
