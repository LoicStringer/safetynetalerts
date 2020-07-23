package com.safetynet.safetynetalerts.dao;

import java.util.List;

import org.springframework.stereotype.Component;



/**
 * 
 * @author newbie
 *
 * @param <T>
 */

@Component
public interface IDao<T> {

	List<T> getAll();
	T insert(T t);
	T update(T t);
	T delete (T t);
	T getOne(String s);
}