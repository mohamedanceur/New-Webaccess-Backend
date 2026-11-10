
package com.Atos.Webaccess.Domain.ChargeBack;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ChargeBackRepository
        extends JpaRepository<ChargeBack, Integer>,
                JpaSpecificationExecutor<ChargeBack> {

    List<ChargeBack> findByIdoperation(Integer idoperation);
}
