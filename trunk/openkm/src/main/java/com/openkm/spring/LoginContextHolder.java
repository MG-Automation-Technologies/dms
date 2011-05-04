package com.openkm.spring;

import javax.security.auth.login.LoginContext;

public class LoginContextHolder {
	private static ThreadLocal<LoginContext> ctx = new ThreadLocal<LoginContext>();

	public static void set(LoginContext lc) {
		ctx.set(lc);
	}
	
	public static LoginContext get() {
		return ctx.get();
	}
}
