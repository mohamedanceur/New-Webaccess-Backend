package com.Atos.Webaccess.User.Services;

import java.util.List;

import com.Atos.Webaccess.User.Entities.Right;


public interface RightService {
	void initialize();
	List<Right> findAll();
}
