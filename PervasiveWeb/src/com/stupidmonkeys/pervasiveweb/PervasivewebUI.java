package com.stupidmonkeys.pervasiveweb;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;

import javax.servlet.annotation.WebServlet;

import org.parse4j.Parse;

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
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import domainEntities.Lecture;

@SuppressWarnings("serial")
@Theme("pervasiveweb")
@Widgetset("com.stupidmonkeys.pervasiveweb.widgetset.PervasivewebWidgetset")
@Push(PushMode.MANUAL)
public class PervasivewebUI extends UI {
	private Navigator navi;
	private String parseAppId;
	private String parseRestKey;
	
	private LinkedList<Lecture>[] totalList;
	private boolean lecListRetrieved;
	private long lecListRetrievedTime;
	
	int retry =0;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = PervasivewebUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		//UI.getCurrent().setLocale(new Locale("it"));
		lecListRetrievedTime=0;
		lecListRetrieved=false;
		totalList=new LinkedList[3];
		parseAppId = "gjDmHU8kCWGxlmcJP97iCfDWXrH5zxtBZRC8kDMM";
		parseRestKey = "MKEJ4APJFnq7srnzpjPFvRW3vdkP3g1IOwbO53Yl";
		
		Parse.initialize(parseAppId, parseRestKey);
		
		navi = new Navigator(this,this);
		navi.addView("", (Class<? extends View>) this.getClass());
		navi.addView("Login", new LoginView(navi));
		navi.addView("Welcome", new WelcomeView(navi));
		navi.navigateTo("Login");
		
		ParseServices.getInstance().retrieveLectureList();
		ParseServices.getInstance().periodicallyRetrieveLectureList(1800000);
		
		
		
	}
	public void setLecListRetrievedFalse()
		{
			lecListRetrieved=false;
		}
	public void setLecListRetrievedTrue()
	{
		lecListRetrieved=true;
	}
	public boolean isLecListRetrieved()
		{
		return lecListRetrieved;
		}
	public void setLecListRetrievedTime()
		{
		lecListRetrievedTime=Calendar.getInstance().getTimeInMillis();
		}
	public long getLectListRetrievedTime()
		{
		return lecListRetrievedTime;
		}
	public void setLecList(LinkedList<Lecture>[] toSet)
		{
		totalList=toSet;
		}
	public LinkedList<Lecture>[] getLecList()
		{
		return totalList;
		}

}