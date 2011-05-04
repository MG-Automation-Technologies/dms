package com.openkm.jaas;

import java.security.Principal;

public class Role implements Principal {
	private final String name;
	
	public Role(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return "Role: " + name;
	}
}
