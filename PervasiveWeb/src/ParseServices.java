import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parse4j.ParseCloud;
import org.parse4j.ParseException;
import org.parse4j.callback.FunctionCallback;

import domainEntities.Lecture;

public class ParseServices {
	
	public static LinkedList<Lecture> getLectureList()
		{
		 final LinkedList<Lecture> list = new LinkedList<Lecture>();
			final SimpleDateFormat hours = new SimpleDateFormat("HH:mm",Locale.ITALIAN);
			final SimpleDateFormat days = new SimpleDateFormat("EEEE dd MMM YYYY",Locale.ENGLISH);
			
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
								 list.add(toAdd);
								}
							}
						}
					});
				return list;
		}
							

}
