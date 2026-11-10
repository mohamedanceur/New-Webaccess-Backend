
package com.Atos.Webaccess.Domain.ChargeBack;

import com.Atos.Webaccess.Domain.ChargeBack.DTO.ChargeBackResponse;
import com.Atos.Webaccess.Domain.ChargeBack.DTO.ChargeBackSearchRequest;

import java.util.List;
import java.util.Optional;

public interface ChargeBackService {

	Optional<ChargeBack> findById(Integer id);
    List<ChargeBackResponse> search(ChargeBackSearchRequest request);
}
