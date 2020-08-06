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

	List<T> getAll() throws Exception;
	T insert(T t) throws Exception ;
	T update(T t) throws Exception ;
	T delete (T t) throws Exception ;
	T getOne(String s) throws Exception ;
	
}