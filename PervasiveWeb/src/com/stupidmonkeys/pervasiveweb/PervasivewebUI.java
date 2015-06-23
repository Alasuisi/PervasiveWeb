package com.stupidmonkeys.pervasiveweb;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

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
	
	/*private static LinkedList<Lecture>[] totalList;
	private static boolean lecListRetrieved;
	private static long lecListRetrievedTime;
	
	private static LinkedList<String> classroomList;
	private static boolean classListRetrieved;
	private static long classListRetrievedTime;
	
	private static HashMap<String,Noise> classesNoiseMap;
	private static HashMap<String,Boolean> classesNoiseMapRetrieved;*/
	
	int retry =0;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = PervasivewebUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		//UI.getCurrent().setLocale(new Locale("it"));
		ParseServices services = ParseServices.getInstance();
	/*	lecListRetrievedTime=0;
		lecListRetrieved=false;
		
		
		classListRetrieved=false;
		classListRetrievedTime=0;
		classroomList=new LinkedList<String>();
		
		classesNoiseMap= new HashMap<String,Noise>();
		classesNoiseMapRetrieved=new HashMap<String,Boolean>();*/
		
		parseAppId = "gjDmHU8kCWGxlmcJP97iCfDWXrH5zxtBZRC8kDMM";
		parseRestKey = "MKEJ4APJFnq7srnzpjPFvRW3vdkP3g1IOwbO53Yl";
		
		Parse.initialize(parseAppId, parseRestKey);
		
		navi = new Navigator(this,this);
		navi.addView("", (Class<? extends View>) this.getClass());
		navi.addView("Login", new LoginView(navi));
		navi.addView("Welcome", new WelcomeView(navi));
		navi.navigateTo("Login");
		
		/*if(getLecList()==null && getClassroomList()==null){
			services.retrieveLectureList();
			services.retrieveClassroomList();
			services.periodicallyRetrieveLectureList(1800000);
			services.periodicallyRetrieveClassroomList(24);
		}*/
		
		
	}
	/*protected void setLecListRetrievedFalse()
		{
			lecListRetrieved=false;
		}
	protected void setLecListRetrievedTrue()
	{
		lecListRetrieved=true;
	}
	public boolean isLecListRetrieved()
		{
		return lecListRetrieved;
		}
	protected void setLecListRetrievedTime()
		{
		lecListRetrievedTime=Calendar.getInstance().getTimeInMillis();
		}
	protected long getLectListRetrievedTime()
		{
		return lecListRetrievedTime;
		}
	protected void setLecList(LinkedList<Lecture>[] toSet)
		{
		totalList=toSet;
		}
	
	
	public LinkedList<Lecture>[] getLecList()
		{
		return totalList;
		}
	protected void setClassListRetrievedFalse()
		{
		classListRetrieved=false;
		}
	protected void setClassListRetrievedTrue()
		{
		classListRetrieved=true;
		}
	public boolean isClassListRetrieved()
		{
		return classListRetrieved;
		}
	protected void setClassListRetrievedTime()
		{
		classListRetrievedTime=Calendar.getInstance().getTimeInMillis();
		}
	public long getClassListRetrievedTime()
		{
		return classListRetrievedTime;
		}
	protected void setClassList(LinkedList<String> toSet)
		{
		classroomList=toSet;
		}
	public LinkedList<String> getClassroomList()
		{
		return classroomList;
		}
	
	public LinkedList<Long> getNoiseForRoom(final String classRoomName)
		{
		long timeStamp=0;
		 if(classesNoiseMap.get(classRoomName)!=null)
		 	{
			 Noise noise = classesNoiseMap.get(classRoomName);
			 
				 timeStamp=noise.getTimeStamp();
		 	}
		 //long timeStamp=classesNoiseMap.get(classRoomName).getTimeStamp();
		 long actualTime = Calendar.getInstance().getTimeInMillis();
		 long tenMinutes = 600000;
		 if((actualTime-timeStamp)>tenMinutes)
		 	{
			 ParseServices.getInstance().retrieveNoiseForRoom(classRoomName);
		 	}else return classesNoiseMap.get(classRoomName).getNoiseList();
		
		 return null;
		}
	protected void setNoiseForRoomRetrievedFalse(String classRoomName)
		{
		classesNoiseMapRetrieved.put(classRoomName, false);
		}
	protected void setNoiseForRoomRetrievedTrue(String classRoomName)
		{
		classesNoiseMapRetrieved.put(classRoomName, true);
		}
	public boolean isNoiseForRoomRetrieved(String classRoomName)
		{
		if(classesNoiseMapRetrieved.get(classRoomName)!=null)
			{
				return classesNoiseMapRetrieved.get(classRoomName);
			}else return false;
		
		}
	public void setNoiseForRoom(String classRoomName,Noise toSet)
		{
		classesNoiseMap.put(classRoomName, toSet);
		}*/

}