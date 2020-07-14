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
	T insert(T t);
	T update(T t);
	T delete (T t);
	T getOne(String s);
}