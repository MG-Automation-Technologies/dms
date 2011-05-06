package com.openkm.jaas;

import java.security.NoSuchAlgorithmException;
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

import com.openkm.module.base.BaseAuthModule;
import com.openkm.principal.PrincipalAdapter;
import com.openkm.principal.PrincipalAdapterException;
import com.openkm.util.SecureStore;

public class MyLoginModule implements LoginModule {
	private static Logger log = LoggerFactory.getLogger(MyLoginModule.class);
	private Subject subject;
	private CallbackHandler callbackHandler;
	private String password;
	private String name;
	private boolean customCallbackHandler = false;
	
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
	}
	
	@Override
	public boolean commit() throws LoginException {
		try {
			authenticate();
			populateRoles();
			return true;
		} catch (PrincipalAdapterException pae) {
			new LoginException(pae.getMessage());
			return false;
		} catch (NoSuchAlgorithmException nsae) {
			new LoginException(nsae.getMessage());
			return false;
		}
	}
	
	@Override
	public boolean login() throws LoginException {
		NameCallback ncb = new NameCallback("User name");
		PasswordCallback pcb = new PasswordCallback("Password", true);
		
		try {
			callbackHandler.handle(new Callback[] { ncb, pcb });
			name = ncb.getName();
			password = new String(pcb.getPassword());
		} catch (Exception e) {
			try {
				callbackHandler.handle(new Callback[] { ncb });
				name = ncb.getName();
				customCallbackHandler = true;
			} catch (Exception e1) {
				throw new LoginException(e.getMessage());
			}
		}
		
		if (name == null || name.equals("")) {
			throw new CredentialNotFoundException("User name is required");
		}
		
		if (!customCallbackHandler) {
			if (password == null || password.equals("")) {
				throw new CredentialNotFoundException("Password is required");
			}
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
	
	/**
	 * Add user's roles to subject being authenticated
	 */
	private void populateRoles() throws PrincipalAdapterException { 
		PrincipalAdapter pa = BaseAuthModule.getPrincipalAdapter();
		
		for (String role : pa.getRolesByUser(name)) {
			subject.getPrincipals().add(new Role(role));	
		}
		
		log.debug("Roles: {}", subject.getPrincipals());
	}
	
	/**
	 * Check if user and password are valid
	 */
	private void authenticate() throws PrincipalAdapterException, NoSuchAlgorithmException, LoginException {
		PrincipalAdapter pa = BaseAuthModule.getPrincipalAdapter();
		String ppass = pa.getPassword(name);
		log.debug("User: {}, Password: {}, DBPassword: {}", new Object[] { name, password, ppass });
		
		if (customCallbackHandler || ppass.equals(SecureStore.md5Encode(password.getBytes()))) {
			subject.getPrincipals().add(new User(name));
		} else {
			throw new LoginException("Password does not match");
		}
		
		log.debug("Users: {}", subject.getPrincipals());
	}
}
