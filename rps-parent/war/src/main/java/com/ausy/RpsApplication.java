package com.ausy;

import org.apache.wicket.Page;
import org.apache.wicket.cdi.CdiConfiguration;
import org.apache.wicket.protocol.http.WebApplication;

import com.ausy.pages.RpsGamePage;

/**
 * Represents the application of the game
 * 
 * @author dmaldonado
 *
 */
public class RpsApplication extends WebApplication {

	public Class<? extends Page> getHomePage() {
		return RpsGamePage.class;
	}

	public void init() {
		super.init();

		// Configure CDI, disabling Conversations as we aren't using them
		new CdiConfiguration().configure(this);
	}
}
