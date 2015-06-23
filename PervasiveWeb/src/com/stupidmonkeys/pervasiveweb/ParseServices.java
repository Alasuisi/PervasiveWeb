package com.stupidmonkeys.pervasiveweb;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parse4j.ParseCloud;
import org.parse4j.ParseException;
import org.parse4j.callback.FunctionCallback;

import com.stupidmonkeys.pervasiveweb.PervasivewebUI;
import com.vaadin.ui.UI;

import domainEntities.Lecture;
import domainEntities.Noise;

public class ParseServices {
	
	private static ParseServices instance = null;
	//private PervasivewebUI thisUI = (PervasivewebUI) UI.getCurrent();
	
	private static LinkedList<Lecture>[] totalList;
	private static boolean lecListRetrieved;
	private static boolean lecListPending=false;
	private static long lecListRetrievedTime;
	
	private static LinkedList<String> classroomList;
	private static boolean classListRetrieved;
	private static boolean classListPending=false;
	private static long classListRetrievedTime;
	
	private static HashMap<String,Noise> classesNoiseMap;
	private static HashMap<String,Boolean> classesNoiseMapRetrieved;
	
	private Calendar cal = Calendar.getInstance();
	
	   protected ParseServices() {
	      // Exists only to defeat instantiation.
	   }
	   public static ParseServices getInstance() {
	      if(instance == null) {
	         instance = new ParseServices();
	         System.out.println("NUOOOOVA ISTANZAAAAAAAA");
	      }
	      System.out.println("VECCHIAAAA ISTANZAAAAAAAA");
	      return instance;
	   }
	   
	public LinkedList<Lecture>[] getLectures()
		{
		if(lecListPending==true) return null;
		long thirtyMin=1800000;
		long thisTime=cal.getTimeInMillis();
		if(totalList==null)
			{
			System.out.println("Total list null");
			retrieveLectureList();
			}else
				{
				long listAge=thisTime-lecListRetrievedTime;
				if(listAge>thirtyMin) 
					{
					System.out.println("Total list old");
					lecListPending=true;
					retrieveLectureList();
					}
				}
		if(classListRetrievedTime-thisTime<300000)
			{
			System.out.println("class list retrieved");
			lecListPending=false;
			return totalList;
			}
		else return null;
		}
	
	public LinkedList<String> getClassroomList()
		{
		if(classListPending==true) return null;
		long twentyforHours=86400000;
		long thisTime=cal.getTimeInMillis();
		if(classroomList==null)
			{
			retrieveClassroomList();
			}else
				{
				 long listAge=thisTime-classListRetrievedTime;
				 if(listAge>twentyforHours)
				 	{
					 classListPending=true;
					 retrieveClassroomList();
				 	}
				}
		if(classListRetrievedTime-thisTime<twentyforHours)
			{
			classListPending=false;
			return classroomList;
			}else return null;
		}
	
	/*
	 * This method calls a remote procedure on parse.com asking for the list of all the lectures
	 * ad returns an array linkedlist of objects "Lecture" properly formatted in this manner:
	 * array[0]= lectures already ended
	 * array[1]= ongoing lectures
	 * array[2]= lectures not started yet
	 */
	private  void retrieveLectureList()
		{
		System.out.println("called retrieveLectureList");
		lecListRetrieved=false;
		final LinkedList<Lecture> pastList = new LinkedList<Lecture>();
		final LinkedList<Lecture> nowList = new LinkedList<Lecture>();
		final LinkedList<Lecture> nextList = new LinkedList<Lecture>();
		final SimpleDateFormat hours = new SimpleDateFormat("HH:mm",Locale.ITALIAN);
		final SimpleDateFormat days = new SimpleDateFormat("EEEE dd MMM YYYY",Locale.ENGLISH);
		final long now=Calendar.getInstance().getTimeInMillis();	
		final LinkedList<Lecture>[] total = new LinkedList[3];
				ParseCloud.callFunctionInBackground("getNewWeeklySchedule", null, new FunctionCallback<JSONArray>(){

					@Override
					public void done(JSONArray result, ParseException parseException) {
						if(parseException==null)
							{
							for(int i=0;i<result.length();i++)
								{
								 
								 JSONObject row=result.getJSONObject(i);
								 long baseDate = row.getLong("start_date");
								 long startHour = row.getLong("starttime");
								 long endHour = row.getLong("endtime");
								 String className = row.getString("classroom_name");
								 String profName = row.getString("prof_name");
								 String summary = row.getString("summary");
								 long lecStart=baseDate+(startHour*60);
								 long lecEnd=baseDate+(endHour*60);
								 /*if(java.util.Locale.getDefault().toString().equals("en_US"))
								 	{
									 lecStart=lecStart+21600;
									 lecEnd=lecEnd+21600;
									// now=now+21600000;
								 	}*/
								 Date startDate = new Date(Long.parseLong(String.valueOf(lecStart))*1000);
								 Date endDate = new Date(Long.parseLong(String.valueOf(lecEnd))*1000);
								 
								 Lecture toAdd = new Lecture();
								 toAdd.setClassroom(className);
								 toAdd.setDayOfTheWeek(days.format(startDate));
								 toAdd.setFrom(hours.format(startDate));
								 toAdd.setTo(hours.format(endDate));
								 toAdd.setProf(profName);
								 toAdd.setTitle(summary);
								 toAdd.setTopics("---");
								 if((lecStart*1000)<now && (lecEnd*1000)<now)
								 	{
									 pastList.add(toAdd);
								 	}else if((lecStart*1000)<=now && (lecEnd*1000)>now)
								 		{
								 		nowList.add(toAdd);
								 		}else if((lecStart*1000)>now) nextList.add(toAdd);
								total[0]=pastList;
								total[1]=nowList;
								total[2]=nextList;

								}
							}else
								{
								System.out.println(parseException.getMessage());
								}
						totalList=total;
						lecListRetrieved=true;
						lecListRetrievedTime=Calendar.getInstance().getTimeInMillis();
						/*thisUI.setLecList(total);
						thisUI.setLecListRetrievedTrue();
						thisUI.setLecListRetrievedTime();
						System.out.println("Lecture list retrieved");*/
						}
					});
				
		}
	
	/*
	 * This method simply invokes periodically retrieveLectureList(),
	 * this is to be used in the main UI class, in order to create-update
	 * a global shared variable for all the instances of the web-app
	 * 
	 * ---PROBABLY DEPRECATED---
	 */
	private void periodicallyRetrieveLectureList(final long intervalMillis)
		{
			final Timer timer = new Timer();
			TimerTask task = new TimerTask(){
			
						@Override
						public void run() {
							//thisUI.setLecListRetrievedFalse();
							lecListRetrieved=false;
							System.out.println("Called lecture retrieve periodically every "+intervalMillis/(60*1000)+" minutes");
							retrieveLectureList();

						}};
						
						timer.scheduleAtFixedRate(task, intervalMillis, intervalMillis);
						
		}
	
	/*
	 * This method simply retrieve from parse, the list of the classrooms
	 * defined in the system
	 */
	private void retrieveClassroomList()
		{
			System.out.println("called retrieveClassroomList");
			classListRetrieved =false;
			final LinkedList<String> lista=new LinkedList<String>();
			ParseCloud.callFunctionInBackground("getClassroomList", null, new FunctionCallback<JSONArray>(){

			@Override
			public void done(final JSONArray result, ParseException parseException) {
				
				for(int i=0;i<result.length();i++)
				{
				
				JSONObject obj =result.getJSONObject(i);
				lista.add(new String(obj.getString("Label")));
				}
				classroomList=lista;
				classListRetrieved = true;
				classListRetrievedTime=Calendar.getInstance().getTimeInMillis();
				/*thisUI.setClassList(lista);
				thisUI.setClassListRetrievedTrue();
				thisUI.setClassListRetrievedTime();*/
				System.out.println("ClassroomList retrieved");
				
			}});
		}
	/*
	 * Even if its very unlikely that classrooms changes, still a method
	 * has been defined for polling at regular intervals for update the cached
	 * data about che classroom list, it executes every 24 hour at 20:00
	 * 
	 * ---PROBABLY DEPRECATED---
	 */
	private void periodicallyRetrieveClassroomList(final int hours)
		{
			Timer timer = new Timer();
			TimerTask task = new TimerTask(){

				@Override
				public void run() {
					//thisUI.setClassListRetrievedFalse();
					classListRetrieved=false;
					System.out.println("Called lecture retrieve periodically every "+hours+" hours");
					retrieveClassroomList();
				}};
				long timeMillis=hours*60*60*1000;
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.HOUR_OF_DAY, 20);
				Date startDate = cal.getTime();
				timer.scheduleAtFixedRate(task, startDate, timeMillis);
			
		}
	
	private void retrieveNoiseForRoom(final String classRoom)
		{
		System.out.println("called retrieveNoiseForRoom("+classRoom+")");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("room", classRoom);
		//thisUI.setNoiseForRoomRetrievedFalse(classRoom);
		classesNoiseMapRetrieved.put(classRoom, false);
		ParseCloud.callFunctionInBackground("getNoiseForRoomRecursive", map, new FunctionCallback<JSONArray>(){

			@Override
			public void done(final JSONArray result, ParseException parseException) {
				if(parseException==null)
					{
					int length = result.length();
					LinkedList<Long> noiseList=new LinkedList<Long>();
					for(int i=0;i<length;i++)
						{
						JSONObject obj = result.getJSONObject(i);
						Long temp = new Long(obj.getLong("Decibel"));
						noiseList.add(temp);
						}
					Noise toSet = new Noise(Calendar.getInstance().getTimeInMillis(), noiseList);
					//thisUI.setNoiseForRoom(classRoom,toSet);
					classesNoiseMap.put(classRoom, toSet);
					classesNoiseMapRetrieved.put(classRoom, true);
					//thisUI.setNoiseForRoomRetrievedTrue(classRoom);
					System.out.println(" retrieved noise for room "+classRoom);
					
					}else
						{
						System.err.println(parseException.getMessage());
						}
				
			}});
		}
}
