package com.Atos.Webaccess.Domain.effets;

import java.util.Optional;

public interface EffetService {

    Effet save(Effet effet);

    Optional<Effet> findById(Integer id);

}