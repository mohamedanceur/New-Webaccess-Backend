
package com.Atos.Webaccess.Domain.Operations.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApproveSearchRequest extends OperationSearchRequest {

    /**
     * Beneficiary bank filter.
     * -1 means all banks.
     */
    private String approveBeneficiaryBank = "-1";
}
