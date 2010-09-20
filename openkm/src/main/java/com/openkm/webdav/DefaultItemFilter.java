package com.openkm.webdav;

import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.webdav.simple.ItemFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultItemFilter implements ItemFilter {
	private static Logger log = LoggerFactory.getLogger(DefaultItemFilter.class);
	
	@Override
	public void setFilteredURIs(String[] uris) {
	}

	@Override
	public void setFilteredPrefixes(String[] prefixes) {
	}

	@Override
	public void setFilteredNodetypes(String[] nodetypeNames) {
	}

	@Override
	public boolean isFilteredItem(Item item) {
		log.debug("isFilteredItem({})", item);
		
		try {
			if (item.isNode() && ((Node) item).isNodeType("okm:notes")) {
				return true;
			}
		} catch (RepositoryException e) {
			// Silent
		}
		
		return false;
	}

	@Override
	public boolean isFilteredItem(String name, Session session) {
		//log.info("isFilteredItem({}, {})", name, session);
		return false;
	}
}
