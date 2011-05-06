package com.openkm.spring;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.jaas.HttpAuthCallbackHandler;

public class LoginContextFilter implements Filter {
	private static Logger log = LoggerFactory.getLogger(LoginContextFilter.class);
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		log.info("Init filter");
	}
	
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) {
		LoginContext ctx = null;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession sess = (HttpSession)(httpRequest).getSession(false);
		
		if (sess != null) {
			ctx = (LoginContext) sess.getAttribute("ctx");
			log.info("Context: {}", ctx);
		}
		
		try {
			if (httpRequest.getRemoteUser() != null) {
				LoginContextHolder.set(ctx);
				Subject s1 = new Subject();
				LoginContext lc = new LoginContext("OpenKM", s1, new HttpAuthCallbackHandler(httpRequest));
				lc.login();
				s1 = lc.getSubject();
				
				Subject.doAs(s1, new PrivilegedAction<Object>() {
					public Object run() {
						try {
							log.info("AccessController: {}", AccessController.getContext());
							log.info("Subject: {}", Subject.getSubject(AccessController.getContext()));
							chain.doFilter(request, response);
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ServletException e) {
							e.printStackTrace();
						}
						
						return null;
					}
				});
			} else {
				chain.doFilter(request, response);
			}
		} catch (LoginException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} finally {
			LoginContextHolder.set(null);
		}
	}

	@Override
	public void destroy() {
	}
}
