package com.openkm.jaas;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpAuthCallbackHandler implements CallbackHandler {
	private static Logger log = LoggerFactory.getLogger(HttpAuthCallbackHandler.class);
	private String userName;
	
	public HttpAuthCallbackHandler(HttpServletRequest request) {
		userName = request.getRemoteUser();
		log.debug("Remote user is: {}", request.getRemoteUser());
	}
	
	@Override
	public void handle(Callback[] cb) throws IOException, UnsupportedCallbackException {
		for (int i = 0; i < cb.length; i++) {
			if (cb[i] instanceof NameCallback) {
				NameCallback nc = (NameCallback) cb[i];
				nc.setName(userName);
			} else {
				throw new UnsupportedCallbackException(cb[i], "HttpAuthCallbackHandler");
			}
		}
	}
}
