package com.Atos.Webaccess.Domain.Groupes;



import java.util.List;

public interface GroupeService {

    GroupeResponse findById(Integer id);

    List<GroupeResponse> findAllActive();

    GroupeResponse create(GroupeRequest request);

    GroupeResponse update(Integer id, GroupeRequest request);

    GroupeResponse changeStatus(Integer id, Integer statut);
}
