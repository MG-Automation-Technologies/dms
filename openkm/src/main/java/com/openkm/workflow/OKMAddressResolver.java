package com.openkm.workflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.jbpm.mail.AddressResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.core.RepositoryException;
import com.openkm.module.direct.DirectAuthModule;

public class OKMAddressResolver implements AddressResolver {
	private static Logger log = LoggerFactory.getLogger(OKMAddressResolver.class);
	private static final long serialVersionUID = 1L;

	public Object resolveAddress(String actorId) {
		log.info("resolveAddress("+actorId+")");
		String email = null;
		
		try {
			Collection<String> users = new ArrayList<String>();
			users.add(actorId);
			Collection<String> emails = new DirectAuthModule().getMails(null, users);
			
			for (Iterator<String> it = emails.iterator(); it.hasNext(); ) {
				email = it.next();
			}
		} catch (RepositoryException e) {
			log.warn(e.getMessage());
		}
		
		log.info("resolveAddress: "+email);
		return email;
	}
}
