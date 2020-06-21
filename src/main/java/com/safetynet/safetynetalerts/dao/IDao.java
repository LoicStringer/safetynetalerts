package com.safetynet.safetynetalerts.dao;

import java.util.List;

public interface IDao<T> {

	//void setList(List<T> list);
	List<T> getAll();
	boolean insert(T t);
	boolean update(T t);
	boolean delete (T t);
	
}