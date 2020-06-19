package com.safetynet.safetynetalerts.dao;

import java.util.List;

public interface IDao<T> {

	
	List<T> getAll();
	boolean save(T t);
	boolean update(T t);
	boolean delete (T t);
	
}