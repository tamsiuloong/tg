package com.jeecms.cms.entity.main.base;

import com.jeecms.cms.entity.main.IndustryCategory;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * This is an object that contains data related to the jc_api_account table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_api_account"
 */

public abstract class BaseTree<T> implements Serializable {

	private Integer id;
	private String name;
	private T parent;
	private Set<T> chirdren=new HashSet<T>();
	private boolean hasChildren;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public T getParent() {
		return parent;
	}

	public void setParent(T parent) {
		this.parent = parent;
	}

	public Set<T> getChirdren() {
		return chirdren;
	}

	public void setChirdren(Set<T> chirdren) {
		this.chirdren = chirdren;
	}

	public boolean getHasChildren() {
		if(getChirdren()!=null && getChirdren().size()>0){
			hasChildren = true;
		}
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
}