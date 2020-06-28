package com.safetynet.safetynetalerts.dao;

import java.util.List;



/**
 * 
 * @author newbie
 *
 * @param <T>
 */
public interface IDao<T> {

	List<T> getAll();
	boolean insert(T t);
	boolean update(T t);
	boolean delete (T t);
	T getOne(String s);
}