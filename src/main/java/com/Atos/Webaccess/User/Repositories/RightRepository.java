package com.Atos.Webaccess.User.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Atos.Webaccess.User.Entities.Right;

public interface RightRepository extends JpaRepository<Right, Long> {

    // Get root rights (source IS NULL)
    List<Right> findByParentIsNull();

    // Get children of a specific right
    List<Right> findByParentId(Long parentId);

    // Get active rights
    List<Right> findByStatus(Integer status);
}