package com.ausy.entity;

import java.io.Serializable;

/**
 * @author dmaldonado
 *
 */
public interface IEntity<I extends Serializable> extends Serializable {
	
	String FIELD_ID = "id";
	
	I getId();
	
	void setId(I id);
}
