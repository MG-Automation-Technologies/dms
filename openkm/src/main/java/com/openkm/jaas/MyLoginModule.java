package com.openkm.jaas;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.CredentialNotFoundException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyLoginModule implements LoginModule {
	private static Logger log = LoggerFactory.getLogger(MyLoginModule.class);
	private Subject subject;
	private CallbackHandler callbackHandler;
	private String password;
	private String name;
	
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
	}
	
	@Override
	public boolean commit() throws LoginException {
		authenticate();
		populateRoles();
		return true;
	}
	
	@Override
	public boolean login() throws LoginException {
		NameCallback name = new NameCallback("User name");
		PasswordCallback password = new PasswordCallback("Password", true);
		
		try {
			this.callbackHandler.handle(new Callback[] { name, password });
			this.name = name.getName();
			this.password = new String(password.getPassword());
		} catch (Exception e) {
			throw new LoginException(e.getMessage());
		}
		
		if (this.name == null || this.name.equals("")) {
			throw new CredentialNotFoundException("User name is required");
		}
		if (this.password == null || this.password.equals("")) {
			throw new CredentialNotFoundException("Password is required");
		}
		
		return true;
	}
	
	@Override
	public boolean abort() throws LoginException {
		return true;
	}
	
	@Override
	public boolean logout() throws LoginException {
		return true;
	}
	
	private void populateRoles() {
		// add user's roles to subject being authenticated
		subject.getPrincipals().add(new Role("AdminRole"));
		log.info("Roles: {}", subject.getPrincipals());
	}

	private void authenticate() {
		// TODO check if user and password are valid
		subject.getPrincipals().add(new User(name));
		log.info("Users: {}", subject.getPrincipals());
	}
}
