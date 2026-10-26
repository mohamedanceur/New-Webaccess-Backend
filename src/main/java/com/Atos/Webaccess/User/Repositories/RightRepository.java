package com.Atos.Webaccess.User.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Atos.Webaccess.User.Entities.Right;


@Repository
public interface RightRepository extends JpaRepository<Right, Long> {

}
