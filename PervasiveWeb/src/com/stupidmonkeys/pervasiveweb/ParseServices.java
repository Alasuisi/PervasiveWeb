package com.stupidmonkeys.pervasiveweb;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parse4j.ParseCloud;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.FunctionCallback;
import org.parse4j.callback.GetCallback;

import com.stupidmonkeys.pervasiveweb.PervasivewebUI;
import com.vaadin.ui.UI;

import domainEntities.Board;
import domainEntities.Classroom;
import domainEntities.Course;
import domainEntities.Lecture;
import domainEntities.Noise;
import domainEntities.Professor;

public class ParseServices {
	
	private static ParseServices instance = null;
	//private PervasivewebUI thisUI = (PervasivewebUI) UI.getCurrent();
	
	private  LinkedList<Lecture>[] totalList;
	private  boolean lecListRetrieved;
	private  boolean lecListPending=false;
	private  boolean lectureListDirty=false;
	private	 boolean studentsNumberPending=false;
	private  long lecListRetrievedTime;
	
	private  LinkedList<Classroom> classroomList;
	private  boolean classListRetrieved;
	private  boolean classListPending=false;
	private  long classListRetrievedTime;
	private  long attendingStudentsRetrievedTime;
	
	private LinkedList<Course> courseList;
	
	private  HashMap<String,Noise> classesNoiseMap= new HashMap<String,Noise>();
	private  HashMap<String,Boolean> classesNoiseMapRetrieved=new HashMap<String,Boolean>();
	private  HashMap<String,Boolean> classNoiseMapPending = new HashMap<String,Boolean>();
	private HashMap<String,Integer> classStudentMap = new HashMap<String,Integer>();
	
	private final Semaphore updatePermitClassList = new Semaphore(1);
	private final Semaphore updatePermitNoiseList = new Semaphore(1);
	private final Semaphore updatePermitStudentsNumber = new Semaphore(1);
	
	private LinkedList<Long> noiseList= new LinkedList<Long>();
	
	private String test;
	
	String topicsForLecture=null;
	
	private Calendar cal = Calendar.getInstance();
	
	   protected ParseServices() {
	      // Exists only to defeat instantiation.
	   }
	   public static ParseServices getInstance() {
	      if(instance == null) {
	         instance = new ParseServices();
	       //  System.out.println("NUOOOOVA ISTANZAAAAAAAA");
	      }
	     // System.out.println("VECCHIAAAA ISTANZAAAAAAAA");
	      return instance;
	   }
	
	   
	   
	/*
	 * Questo metodo e i successivi vanno corretti e riscritti per correggere i problemi di sincronizzazione
	 */
	public LinkedList<Lecture>[] getLectures()
		{
		if(lecListPending==true) 
			{
			System.out.println("list pending");
			return null;
			}
		long thirtyMin=1800000;
		long thisTime=cal.getTimeInMillis();
		if(totalList==null)
			{
			System.out.println("Total list null");
			retrieveLectureList();
			}else if(!lecListPending)
				{
				long listAge=thisTime-lecListRetrievedTime;
				if(listAge>thirtyMin) 
					{
					System.out.println("Total list old");
					lecListPending=true;
					retrieveLectureList();
					}
				}
		if(lectureListDirty && !lecListPending)
			{
			System.out.println("Total list is dirty");
			//lecListPending=true;
			retrieveLectureList();
			}
		if(classListRetrievedTime-thisTime<300000 && !lectureListDirty)
			{
			System.out.println("class list retrieved");
			lecListPending=false;
			return totalList;
			}
		else return null;
		}
	
	public void markLectureListAsDirty()
		{
		lectureListDirty=true;
		}
	
	
	public synchronized Integer getAttendingStudents(String classroom)
		{
		if(updatePermitStudentsNumber.tryAcquire())
			{
			 if(studentsNumberPending==true)
			 	{
				 System.out.println("Students number update pending, releasing the lock");
				 updatePermitStudentsNumber.release();
				 return null;
			 	}
			 long tenSeconds=10000;
			 long thisTime=cal.getTimeInMillis();
			 if(classStudentMap.get(classroom)==null)
			 	{
				 System.out.println("calling for retrieving students number");
				 studentsNumberPending=true;
				 retrieveAttendings(classroom);
			 	}else
			 		{
			 		long valueAge =thisTime-attendingStudentsRetrievedTime;
			 		if(valueAge>tenSeconds)
			 			{
			 			studentsNumberPending=true;
			 			retrieveAttendings(classroom);
			 			System.out.println("old value for attendings, updating...");
			 			}
			 		}
			 if(thisTime-attendingStudentsRetrievedTime<tenSeconds)
			 	{
				 studentsNumberPending=false;
				 updatePermitStudentsNumber.release();
				 return classStudentMap.get(classroom);
			 	}
			}
		return null;
		}
	
	public synchronized LinkedList<Classroom> getClassroomList()
		{
		if (updatePermitClassList.tryAcquire()) {
		if(classListPending==true)
			{ 
			System.out.println("list is NOT pending");
			updatePermitClassList.release(); ///aggiunto questo
			return null;
			}
		long twentyforHours=86400000;
		long thisTime=cal.getTimeInMillis();
		if(classroomList==null)
			{
			retrieveClassroomList();
			System.out.println("Chiamata 1 (nuova lista)");
			}else
				{
				 long listAge=thisTime-classListRetrievedTime;
				 if(listAge>twentyforHours)
				 	{
					 classListPending=true;
					 retrieveClassroomList();
					 System.out.println("Chiamata 2 (lista obsoleta)");
				 	}
				}
		if((thisTime-classListRetrievedTime)<twentyforHours)  ///INVERTITO thisTime con classLIstretrievedtime
			{
			classListPending=false;
			updatePermitClassList.release();   //aggiunto questo
			return classroomList;
			}else return null;
		 } else {
		        System.out.println("Classroom list update already running, wait");
		    	try {
					updatePermitClassList.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		        //release the permit immediately
		    	updatePermitClassList.release();
		    }
		return null;
		}
	
	public synchronized LinkedList<Long> getNoiseForRoom(String classroom)
		{
		Boolean pending =classNoiseMapPending.get(classroom);
		 if (pending==null) 
		 	{
			 System.out.println("pending not initialized, returning null");
			 classNoiseMapPending.put(classroom, false);
			 return null;
			}
		 if(pending.booleanValue()==true) {System.out.println("update pending returning null");return null;}
		 long sixSeconds=6000;
		 long thisTime = Calendar.getInstance().getTimeInMillis();
		 Noise noise =classesNoiseMap.get(classroom);
		 if(noise==null)
		 	{
			 System.out.println("noiselist not present, invoking retrieve");
			 retrieveNoiseForRoom(classroom);
			 return null; //TODO check if needed
		 	}else
		 		{
		 		long noiseAge = thisTime-classesNoiseMap.get(classroom).getTimeStamp();
		 		if(noiseAge>sixSeconds)
		 			{
		 			classNoiseMapPending.put(classroom, true);
		 			retrieveNoiseForRoom(classroom);
		 			}
		 		}
		 if(classesNoiseMap.get(classroom).getTimeStamp()-thisTime<15000)
		 	{
			 classNoiseMapPending.put(classroom, false);
			 System.out.println("noiselist ready and updated");
			 LinkedList<Long> result =classesNoiseMap.get(classroom).getNoiseList();
			 return result;
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
		//final SimpleDateFormat days = new SimpleDateFormat("MMM dd HH:mm:ss 'CEST' YYYY",Locale.ENGLISH);
		//final SimpleDateFormat hours = new SimpleDateFormat("HH:mm",Locale.ITALIAN);
		final Date now=Calendar.getInstance().getTime();	
		final LinkedList<Lecture>[] total = new LinkedList[3];
				ParseCloud.callFunctionInBackground("getNewWeeklySchedule", null, new FunctionCallback<JSONArray>(){

					@Override
					public void done(JSONArray result, ParseException parseException) {
						if(parseException==null)
							{
							for(int i=0;i<result.length();i++)
								{
								 JSONObject row=result.getJSONObject(i);
								 String objId= row.getString("objectId");
								 JSONObject startJSON = row.getJSONObject("start_time");
								 JSONObject endJSON = row.getJSONObject("end_time");
								 String startHour1 =  startJSON.getString("iso");
								 String endHour1 =  endJSON.getString("iso");
								 Instant startInstant = Instant.parse(startHour1);
								 Instant endInstant = Instant.parse(endHour1);
								 System.out.println("inizio "+Date.from(startInstant)+" fine "+Date.from(endInstant));
								 JSONObject courseJSON = row.getJSONObject("Course");
								 
								 
								 JSONObject classJSON = row.getJSONObject("classroom_name");
								 Classroom className = new Classroom();
								 Course actualCourse = new Course();
								 actualCourse.setName(courseJSON.getString("name"));
								 actualCourse.setObjectId(courseJSON.getString("objectId"));
								 className.setActualCourse(actualCourse);
								 try{
								 className.setAssignedBeacon(classJSON.getString("beacon"));
								 }catch(JSONException e)	
								 {
									 System.err.println("Object with key:"+classJSON.getString("objectId")+" has 'beacon' field not initialized, "
												+ "it will be assigned the empty string value to that field in the web app representation "
												+ "(nothing will be touched in the BaaS)");
								 }
								 try{
								 className.setClassNoise(Long.parseLong(classJSON.getString("actual_noise")));
								 }catch(JSONException e )	
								 {
									 System.err.println("Object with key:"+classJSON.getString("objectId")+" has 'actual_noise' field not initialized, "
												+ "it will be assigned the empty string value to that field in the web app representation "
												+ "(nothing will be touched in the BaaS)");
								 }
								 className.setClassName(classJSON.getString("Name"));
								 try{
								 className.setClassTemp(classJSON.getLong("actual_temp"));
								 }catch(JSONException e)
								 	{
									 System.err.println("Object with key:"+classJSON.getString("objectId")+" has 'actual_temp' field not initialized, "
												+ "it will be assigned the empty string value to that field in the web app representation "
												+ "(nothing will be touched in the BaaS)");
								 	}
								 try{
								 className.setSeatsOfclass(classJSON.getInt("seatsNumber"));
								 }catch(JSONException e)
								 {
									 System.err.println("Object with key:"+classJSON.getString("objectId")+" has 'seatsNumber' field not initialized, "
												+ "it will be assigned the empty string value to that field in the web app representation "
												+ "(nothing will be touched in the BaaS)"); 
								 }
								 try{
								 className.setSeatsTaken(classJSON.getInt("seatsOccupied"));
								 }catch(JSONException e)
								 	{
									 System.err.println("Object with key:"+classJSON.getString("objectId")+" has 'seatsOccupied' field not initialized, "
												+ "it will be assigned the empty string value to that field in the web app representation "
												+ "(nothing will be touched in the BaaS)"); 
								 	}
								 
								 JSONObject profName = row.getJSONObject("prof_name");
								 Professor prof = new Professor();
								 prof.setName(profName.getString("username"));
								 String summary ="";
								 try{
								 summary = row.getString("summary");
								 }catch(JSONException e)
								 	{
									 System.err.println("Object with key:"+row.getString("objectId")+" has 'summary' field not initialized, "
												+ "it will be assigned the empty string value to that field in the web app representation "
												+ "(nothing will be touched in the BaaS)");
								 	}
								 String topicsList =new String();
								 try{
								  topicsList=row.getString("topics");
								 }catch(Exception e)
								 	{
									 System.err.println("Object with key:"+row.getString("objectId")+" has 'topics' field not initialized, "
												+ "it will be assigned the empty string value to that field in the web app representation "
												+ "(nothing will be touched in the BaaS)");
								 	}
							//	 long lecStart=baseDate+(startHour*60);
							//	 long lecEnd=baseDate+(endHour*60);
								 /*if(java.util.Locale.getDefault().toString().equals("en_US"))
								 	{
									 lecStart=lecStart+21600;
									 lecEnd=lecEnd+21600;
									// now=now+21600000;
								 	}*/
								 //Date startDate = new Date(Long.parseLong(String.valueOf(lecStart)));
								 //Date endDate = new Date(Long.parseLong(String.valueOf(lecEnd)));
								 Date startDate =Date.from(startInstant);
								 Date endDate = Date.from(endInstant);
								 
								 Lecture toAdd = new Lecture();
								 toAdd.setObjectId(objId);
								 toAdd.setClassroom(className);
								 toAdd.setDayOfTheWeek(days.format(startDate));
								 toAdd.setFrom(hours.format(startDate));
								 toAdd.setTo(hours.format(endDate));
								 toAdd.setProf(prof);
								 toAdd.setTitle(summary);
								 toAdd.setCourse(actualCourse);
								 if(topicsList.length()>0)
								 	{
									 toAdd.setTopics("Topics");
									 toAdd.setTopicsList(topicsList);
								 	}else toAdd.setTopics("---");
								 
								 if(startDate.before(now) && endDate.before(now))
								 	{
									 pastList.add(toAdd);
								 	}else if(startDate.before(now) && endDate.after(now))
								 		{
								 		nowList.add(toAdd);
								 		}else if(startDate.after(now)) nextList.add(toAdd);
								 /*if((lecStart*1000)<now && (lecEnd*1000)<now)
								 	{
									 pastList.add(toAdd);
								 	}else if((lecStart*1000)<=now && (lecEnd*1000)>now)
								 		{
								 		nowList.add(toAdd);
								 		}else if((lecStart*1000)>now) nextList.add(toAdd);*/
								total[0]=pastList;
								total[1]=nowList;
								total[2]=nextList;

								}
							}else
								{
								System.out.println("error in retrievelecturelist "+parseException.getMessage());
								}
						totalList=total;
						lecListRetrieved=true;
						if(lectureListDirty) lectureListDirty=false;
						lecListRetrievedTime=Calendar.getInstance().getTimeInMillis();
						}
					});
				
		}
	
	/*
	 * This method simply retrieve from parse, the list of the classrooms
	 * defined in the system
	 */
	private void retrieveClassroomList()
		{
		
		

			System.out.println("called retrieveClassroomList");
			classListRetrieved =false;
		//	final LinkedList<String> lista=new LinkedList<String>();
			final LinkedList<Classroom> lista = new LinkedList<Classroom>();
			ParseCloud.callFunctionInBackground("getClassroomList", null, new FunctionCallback<JSONArray>(){

			@Override
			public void done(final JSONArray result, ParseException parseException) {
				System.out.println("PORCADDIOOOOOOOOOO    "+result.length());
				for(int i=0;i<result.length();i++)
				{
				
				JSONObject obj =result.getJSONObject(i);
				Classroom toAdd = new Classroom();
				try{
				toAdd.setObjectId(obj.getString("objectId"));
				}catch(JSONException e)
					{
					System.err.println("error in retrieveclassroomlist "+ e.getMessage());
					}
				try{
				toAdd.setClassName(obj.getString("Name"));
				}catch(JSONException e)
					{
					
					System.err.println("Object with key:"+obj.getString("objectId")+" has 'Name' field not initialized, "
							+ "it will be assigned the NULL value to that field in the web app representation "
							+ "(nothing will be touched in the BaaS)");
					toAdd.setClassName(null);
					}
				try{
				toAdd.setSeatsOfclass(obj.getInt("seatsNumber"));
				}catch(JSONException e)
					{
					System.err.println("Object with key:"+obj.getString("objectId")+" has 'SeatsNumber' field not initialized, "
							+ "it will be assigned the 0 value to that field in the web app representation "
							+ "(nothing will be touched in the BaaS)");
					toAdd.setSeatsOfclass(0);
					}
				try{
				toAdd.setSeatsTaken(obj.getInt("seatsOccupied"));
				}catch(JSONException e)
					{
					System.err.println("Object with key:"+obj.getString("objectId")+" has 'SeastOccupied' field not initialized, "
							+ "it will be assigned the 0 value to that field in the web app representation "
							+ "(nothing will be touched in the BaaS)");
					toAdd.setSeatsTaken(0);
					}
				try{
				toAdd.setAssignedBeacon(obj.getString("beacon"));
				}catch(JSONException e)
					{
					System.err.println("Object with key:"+obj.getString("objectId")+" has 'beacon' field not initialized, "
							+ "it will be assigned the NULL value to that field in the web app representation "
							+ "(nothing will be touched in the BaaS)");
					toAdd.setAssignedBeacon(null);
					}
				try{
				toAdd.setClassNoise(obj.getLong("actual_noise"));
				}catch(JSONException e)
					{
					System.err.println("Object with key:"+obj.getString("objectId")+" has 'actual_noise' field not initialized, "
							+ "it will be assigned the 0 value to that field in the web app representation "
							+ "(nothing will be touched in the BaaS)");
					toAdd.setClassNoise(0);
					}
				try{
				toAdd.setClassTemp(obj.getLong("actual_temp"));
				}catch(JSONException e)
				{
					System.err.println("Object with key:"+obj.getString("objectId")+" has 'actual_temp' field not initialized, "
							+ "it will be assigned the 0 value to that field in the web app representation "
							+ "(nothing will be touched in the BaaS)");
					toAdd.setClassTemp(0);
				}
				try{
					JSONObject jcour=obj.getJSONObject("Course");
					Course actual = new Course();
					actual.setObjectId(jcour.getString("objectId"));
					actual.setName(jcour.getString("name"));
					
				toAdd.setActualCourse(actual);
				}catch(JSONException e)
					{
					System.err.println("Object with key:"+obj.getString("objectId")+" has 'Course' field not initialized, "
							+ "it will be assigned the NULL value to that field in the web app representation "
							+ "(nothing will be touched in the BaaS)");
					toAdd.setAssignedBeacon(null);
					}
				
				//lista.add(new String(obj.getString("Name")));
				lista.add(toAdd);
				
				}
				Iterator<Classroom> iter = lista.iterator();
				while (iter.hasNext())
					{
						System.out.println(iter.next().toString());
					}
				classroomList=lista;
				classListRetrieved = true;
				classListRetrievedTime=Calendar.getInstance().getTimeInMillis();
				System.out.println("ClassroomList retrieved");
				updatePermitClassList.release();
			}});
			
	   
		
		
			
		}

	private void retrieveAttendings(final String classRoom)
		{
		HashMap<String,String> map = new HashMap();
		map.put("getClassRoomName", classRoom);
		ParseCloud.callFunctionInBackground("getStudentsNumber", map, new FunctionCallback<Integer>(){

			@Override
			public void done(Integer result, ParseException parseException) {
				if(parseException==null)
					{
					classStudentMap.put(classRoom, result);
					studentsNumberPending=false;
					updatePermitStudentsNumber.release();
					}else
						{
						studentsNumberPending=false;
						updatePermitStudentsNumber.release();
						System.err.println("Error in ParseServices.retrieveAttendings "+parseException.getMessage());
						
						}
				
			}});
		}
	
	private void retrieveNoiseForRoom(final String classRoom)
	{
	
	if(updatePermitNoiseList.tryAcquire()){
	System.out.println("called retrieveNoiseForRoom("+classRoom+")");
	Iterator<Classroom> classIter = classroomList.iterator();
	Classroom room = classIter.next();
	while(!room.getClassName().equals(classRoom))
		{
		room=classIter.next();
		}
	//System.out.println("ECCO LA CLASSE RICERCATA  "+room.getClassName()+" "+room.getObjectId());
	
	ParseQuery<ParseObject> query =ParseQuery.getQuery("Classroom");
	query.getInBackground(room.getObjectId(), new GetCallback<ParseObject>(){

		@Override
		public void done(ParseObject t, ParseException parseException) {
			if(parseException==null)
				{
					if(noiseList.size()>=10) noiseList=new LinkedList<Long>();
					noiseList.addLast(Long.parseLong(t.getString("actual_noise")));
					Noise toSet = new Noise(Calendar.getInstance().getTimeInMillis(), noiseList);
					classesNoiseMap.put(classRoom, toSet);
					classesNoiseMapRetrieved.put(classRoom, true);
					updatePermitNoiseList.release();
					
					//System.out.println("Ecco la temperatura"+t.getString("actual_noise"));
					//System.out.println("Ecco la lunghezza della lista delle temperature "+noiseList.size());
					Iterator<Long> iter = noiseList.iterator();
					while(iter.hasNext())
						{
						System.out.print(" "+iter.next());
						}
				}else System.err.println("Error in method retrieveNoiseForRoom "+parseException.getMessage());
			
			
		}});
	
	/*HashMap<String, String> map = new HashMap<String, String>();
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
				updatePermitNoiseList.release();
				System.out.println(" retrieved noise for room "+classRoom);
				
				}else
					{
					System.err.println("error in retrievenoiseforroom "+parseException.getMessage());
					}
			
		}});*/
	}else {
        System.out.println("Noise list update already running, wait");
    	try {
			updatePermitNoiseList.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        //release the permit immediately
    	updatePermitNoiseList.release();
    }

	}
	
	
	/*
	 * 
	 * OLD DEPRECATED METHOD
	 * 
	 * private void retrieveNoiseForRoom(final String classRoom)
		{
		
		if(updatePermitNoiseList.tryAcquire()){
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
					updatePermitNoiseList.release();
					System.out.println(" retrieved noise for room "+classRoom);
					
					}else
						{
						System.err.println("error in retrievenoiseforroom "+parseException.getMessage());
						}
				
			}});
		}else {
	        System.out.println("Noise list update already running, wait");
	    	try {
				updatePermitNoiseList.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        //release the permit immediately
	    	updatePermitNoiseList.release();
	    }
	
		}*/
	
	
	public void saveTopicsListForLecture(String objectId,final String topicsList)
	{
	ParseQuery<ParseObject> query = ParseQuery.getQuery("new_schedule");
	System.out.println("IN SAVE ON PARSE "+objectId+" "+topicsList);
    query.getInBackground(objectId, new GetCallback<ParseObject>(){

		@Override
		public void done(ParseObject t, ParseException parseException) {
			if(parseException==null)
			{
				System.out.println(t.get("summary"));
			t.put("topics", topicsList);
			t.saveInBackground();
			}else System.out.println("Something bad happened in saveTopicsListForLecture "+parseException.getMessage());
			
		}});
	}
	/*public void saveTopicsListForLecture(String objectId,final HashMap<Integer,String> topicsList)
	{
	ParseQuery<ParseObject> query = ParseQuery.getQuery("new_schedule");
    query.getInBackground("objectId", new GetCallback<ParseObject>(){

		@Override
		public void done(ParseObject t, ParseException parseException) {
			if(parseException==null)
			{
			System.out.println("Sono nel done");
			Iterator<Entry<Integer, String>> it = topicsList.entrySet().iterator();
			String result = new String();
			while(it.hasNext())
				{
				Entry<Integer, String> temp=it.next();
				String tempString="{"+temp.getKey().toString()+":"+temp.getValue().toString()+"}";
				result=result+tempString+"|";
				it.remove();
				}
			//t.put("topics", test.toString());
			t.put("topics", result);
			t.saveInBackground();
			}else System.out.println("orcamadò "+parseException.getMessage());
			
		}});
	}*/
	
	public String getTopicsForLecture(String objectID)
	{
	final HashMap<Integer,String> map=new HashMap<Integer,String>();
	 ParseQuery<ParseObject> query = ParseQuery.getQuery("new_schedule");
	 
	    query.getInBackground(objectID, new GetCallback<ParseObject>(){

			@Override
			public void done(ParseObject t, ParseException parseException) {
				if(parseException==null)
				{
				topicsForLecture =t.getString("topics");
				}else System.out.println("error in method getTopicsForLecture "+parseException.getMessage());
				
				
			}});
	    
	return topicsForLecture;
	
	}
	/*public HashMap<Integer,String> getTopicsForLecture(String objectID)
	{
	final HashMap<Integer,String> map=new HashMap<Integer,String>();
	 ParseQuery<ParseObject> query = ParseQuery.getQuery("new_schedule");
	    query.getInBackground(objectID, new GetCallback<ParseObject>(){

			@Override
			public void done(ParseObject t, ParseException parseException) {
				if(parseException==null)
				{
				System.out.println("Sono nel done");
				String result =t.getString("topics");
				String[] split=result.split("\\|");
				for(int i=0;i<split.length;i++)
				{
					String temp=split[i];
					temp=temp.replaceAll("[{}]", "");
					String[] entry=temp.split(":");
					map.put(new Integer(entry[0]), entry[1]);
				}
				System.out.println(map.toString());
				}else System.out.println("orcamadò "+parseException.getMessage());
				
				
			}});
	    return map;
	
	
	}*/
}
