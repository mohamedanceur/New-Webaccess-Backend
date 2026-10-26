package com.Atos.Webaccess.Domain.Agence;




import java.util.List;

public interface AgenceService {

    List<AgenceResponse> findAll();

    AgenceResponse findById(Integer id);

    AgenceResponse create(AgenceRequest request);

    AgenceResponse update(Integer id, AgenceRequest request);

    AgenceResponse changeStatus(Integer id, Integer statut);

    List<AgenceResponse> findByBank(Integer idBanque);

    List<AgenceResponse> findByGroup(Integer idGroupe);

    List<AgenceResponse> findAllValid();

    String getAgenceName(Integer id);

    String getAgenceLibelle(String code);
}
