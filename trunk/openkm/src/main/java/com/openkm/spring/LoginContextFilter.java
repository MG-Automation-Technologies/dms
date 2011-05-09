package com.openkm.spring;

import java.io.IOException;

import javax.security.auth.login.LoginContext;
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

public class LoginContextFilter implements Filter {
	private static Logger log = LoggerFactory.getLogger(LoginContextFilter.class);
	
	@Override
	public void init(FilterConfig cfg) throws ServletException {
		log.info("Init filter");
	}
	
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) {
		LoginContext ctx = null;
		HttpSession sess = (HttpSession)((HttpServletRequest) request).getSession(false);
		
		if (sess != null) {
			ctx = (LoginContext) sess.getAttribute("ctx");
			log.info("Context: {}", ctx);
		}
		
		try {
			LoginContextHolder.set(ctx);
			chain.doFilter(request, response);
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
		log.info("Destroy filter");
	}
}
