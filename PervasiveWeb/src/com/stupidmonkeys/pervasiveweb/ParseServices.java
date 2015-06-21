package com.stupidmonkeys.pervasiveweb;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

public class ParseServices {
	
	private static ParseServices instance = null;
	   protected ParseServices() {
	      // Exists only to defeat instantiation.
	   }
	   public static ParseServices getInstance() {
	      if(instance == null) {
	         instance = new ParseServices();
	      }
	      return instance;
	   }
	   
	/*
	 * This method calls a remote procedure on parse.com asking for the list of all the lectures
	 * ad returns an array linkedlist of objects "Lecture" properly formatted in this manner:
	 * array[0]= lectures already ended
	 * array[1]= ongoing lectures
	 * array[2]= lectures not started yet
	 */
	public  void retrieveLectureList()
		{
		System.out.println("called retrieveLectureList");
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
						PervasivewebUI thisUI = (PervasivewebUI) UI.getCurrent();
						thisUI.setLecList(total);
						thisUI.setLecListRetrievedTrue();
						thisUI.setLecListRetrievedTime();
						System.out.println("list retrieved");
						}
					});
				
		}
	
	/*
	 * This method simply invokes periodically retrieveLectureList(),
	 * this is to be used in the main UI class, in order to create-update
	 * a global shared variable for all the instances of the web-app
	 */
	public void periodicallyRetrieveLectureList(long intervalMillis)
		{
			final Timer timer = new Timer();
			TimerTask task = new TimerTask(){
			
						@Override
						public void run() {
							PervasivewebUI thisUI = (PervasivewebUI) UI.getCurrent();
							thisUI.setLecListRetrievedFalse();
							System.out.println("Called periodically");
							retrieveLectureList();

						}};
						
						timer.scheduleAtFixedRate(task, intervalMillis, intervalMillis);
						
		}
							

}
