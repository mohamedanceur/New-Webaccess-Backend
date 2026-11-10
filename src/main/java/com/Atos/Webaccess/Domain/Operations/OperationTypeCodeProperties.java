package com.Atos.Webaccess.Domain.Operations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "webaccess.operation-types")
public class OperationTypeCodeProperties {

    private String cardPayment;
    private String cardWithdrawal;
    private String cheque;
    private String effect;
    private String directDebit;
    private String transfer;
    private String pMobile;
}