package com.openkm.jaas;

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

import com.openkm.core.Config;
import com.openkm.jaas.HttpAuthCallbackHandler;
import com.openkm.util.ServerDetector;

public class ContextSubjectFilter implements Filter {
	private static Logger log = LoggerFactory.getLogger(ContextSubjectFilter.class);
	private static final String SESSION_AUTH_SUBJECT = "session.auth.subject";
	
	@Override
	public void init(FilterConfig cfg) throws ServletException {
		log.info("Init filter");
	}
	
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		try {
			if (ServerDetector.isTomcat() && httpRequest.getRemoteUser() != null) {
				HttpSession hs = (HttpSession)(httpRequest).getSession(false);
				Subject sub = (Subject) hs.getAttribute(SESSION_AUTH_SUBJECT);
				
				if (sub == null) {
					log.info("Login and put Subject in session");
					HttpAuthCallbackHandler hach = new HttpAuthCallbackHandler(httpRequest);
					LoginContext lc = new LoginContext(Config.CONTEXT, new Subject(), hach);
					lc.login();
					sub = lc.getSubject();
					hs.setAttribute(SESSION_AUTH_SUBJECT, sub);
					//LoginContextHolder.set(lc);
				}
				
				Subject.doAs(sub, new PrivilegedAction<Object>() {
					public Object run() {
						try {
							log.debug("AccessController: {}", AccessController.getContext());
							log.debug("Subject: {}", Subject.getSubject(AccessController.getContext()));
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
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (ServletException e) {
			log.error(e.getMessage(), e);
		} finally {
			//LoginContextHolder.set(null);
		}
	}

	@Override
	public void destroy() {
		log.info("Destroy filter");
	}
}
