
package com.Atos.Webaccess.Domain.ChargeBack.DTO;

import com.Atos.Webaccess.Domain.ChargeBack.ChargeBack;
import com.Atos.Webaccess.Domain.Operations.Operation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChargeBackResponse {

    private Operation operation;
    private ChargeBack chargeBack;
}
