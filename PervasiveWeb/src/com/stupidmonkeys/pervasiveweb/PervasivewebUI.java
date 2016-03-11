package com.stupidmonkeys.pervasiveweb;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.annotation.WebServlet;

import org.parse4j.Parse;
import org.parse4j.ParseCloud;
import org.parse4j.ParsePush;

import Views.LoginView;
import Views.WelcomeView;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.UI;

import domainEntities.Lecture;
import domainEntities.Noise;

@SuppressWarnings("serial")
@Theme("pervasiveweb")
@Widgetset("com.stupidmonkeys.pervasiveweb.widgetset.PervasivewebWidgetset")
@Push(PushMode.MANUAL)
public class PervasivewebUI extends UI {
	private Navigator navi;
	private String parseAppId;
	private String parseRestKey;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = PervasivewebUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		//UI.getCurrent().setLocale(new Locale("it"));
	//	OLD_parseAppId = "gjDmHU8kCWGxlmcJP97iCfDWXrH5zxtBZRC8kDMM";
	//	OLD_parseRestKey = "MKEJ4APJFnq7srnzpjPFvRW3vdkP3g1IOwbO53Yl";
		
		parseAppId = "WYN5XzaG0B7TdkyUGVKuW1kKOZICxdLdoK4Xw8G9";
		parseRestKey = "k6h8B8vH0wivUTSumBYrCROu2HtQooih3b3SoXgn";
		
		Parse.initialize(parseAppId, parseRestKey);
		
		
		navi = new Navigator(this,this);
		navi.addView("", (Class<? extends View>) this.getClass());
		navi.addView("Login", new LoginView(navi));
		navi.addView("Welcome", new WelcomeView(navi));
		navi.navigateTo("Login");
	}
}