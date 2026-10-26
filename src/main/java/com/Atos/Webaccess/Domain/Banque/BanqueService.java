package com.Atos.Webaccess.Domain.Banque;




import java.util.List;

public interface BanqueService {

    List<BanqueResponse> findAll();

    BanqueResponse findById(Integer id);

    BanqueResponse create(BanqueRequest request);

    BanqueResponse update(Integer id, BanqueRequest request);

    BanqueResponse changeStatus(Integer id, Integer statut);

    String getBanqueName(Integer id);

    List<BanqueResponse> findByGroupe(Integer idGroupe);

    Integer getBanqueMereId();
}
