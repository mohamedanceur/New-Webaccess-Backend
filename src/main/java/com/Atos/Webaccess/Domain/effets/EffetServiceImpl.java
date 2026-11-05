package com.Atos.Webaccess.Domain.effets;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EffetServiceImpl implements EffetService {

    private final EffetRepository effetRepository;

    public EffetServiceImpl(EffetRepository effetRepository) {
        this.effetRepository = effetRepository;
    }

    @Override
    @Transactional
    public Effet save(Effet effet) {
        return effetRepository.save(effet);
    }

    @Override
    public Optional<Effet> findById(Integer id) {
        return effetRepository.findById(id);
    }
}
