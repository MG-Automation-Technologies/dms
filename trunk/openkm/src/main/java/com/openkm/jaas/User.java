package com.openkm.jaas;

import java.io.Serializable;
import java.security.Principal;

public class User implements Principal, Serializable {
	private static final long serialVersionUID = 1L;
	private final String name;
	
	public User(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return "{User: " + name + "}";
	}
}
