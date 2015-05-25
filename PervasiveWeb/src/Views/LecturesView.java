package Views;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parse4j.Parse;
import org.parse4j.ParseCloud;
import org.parse4j.ParseException;
import org.parse4j.callback.FunctionCallback;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import domainEntities.Lecture;
import domainEntities.Professor;

public class LecturesView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2570793839851203621L;
	
	private VerticalLayout tableSpace = new VerticalLayout();
	private VerticalLayout prevLayout = new VerticalLayout();
	private VerticalLayout nowLayout = new VerticalLayout();
	private VerticalLayout nextLayout = new VerticalLayout();
	
	private Label title = new Label("Today's lectures");
	private Label prevLabel = new Label("Previous lectures");
	private Label nowLabel = new Label("Ongoing lectures");
	private Label nextLabel = new Label("Next lectures");
	
	private Table prevTable = new Table();
	private Table nowTable = new Table();
	private Table nextTable = new Table();
	private HashMap<String,Integer> dayMap = new HashMap<String,Integer>();
	private JSONArray tempResult = new JSONArray();
	private boolean more=true;
	private boolean done=false;
	private int index=0;
	
	
	public LecturesView()
		{
			dayMap.put("Monday", 1);
			dayMap.put("Tuesday", 2);
			dayMap.put("Wednesday", 3);
			dayMap.put("Thursday", 4);
			dayMap.put("Friday", 5);
			dayMap.put("Saturday", 6);
			dayMap.put("Sunday", 7);
			//getWeeklySchedule();
			title.addStyleName(ValoTheme.LABEL_BOLD);
			title.addStyleName(ValoTheme.LABEL_LARGE);
			title.setSizeUndefined();
			title.addStyleName("animated");
			title.addStyleName("flipInX");
			title.addStyleName("delay05");
			prevLabel.setSizeUndefined();
			prevLabel.addStyleName(ValoTheme.LABEL_BOLD);
			nowLabel.setSizeUndefined();
			nowLabel.addStyleName("animated");
			nowLabel.addStyleName("tada");
			nowLabel.addStyleName("delay09");
			nowLabel.addStyleName(ValoTheme.LABEL_BOLD);
			nextLabel.setSizeUndefined();
			nextLabel.addStyleName("animated");
			nextLabel.addStyleName("tada");
			nextLabel.addStyleName("delay1");
			nextLabel.addStyleName(ValoTheme.LABEL_BOLD);
			prevTable.setImmediate(true);
			nowTable.setImmediate(true);
			nextTable.setImmediate(true);
			prevLabel.setVisible(false);
			nowLabel.setVisible(false);
			nextLabel.setVisible(false);
			prevTable.setVisible(false);
			nowTable.setVisible(false);
			nextTable.setVisible(false);
			new Timer().schedule(new TimerTask(){

				@Override
				public void run() {
					prevLabel.setVisible(true);
					prevLabel.addStyleName("animated");
					prevLabel.addStyleName("tada");
					//prevLabel.addStyleName("delay08");
					prevLabel.markAsDirty();
					UI.getCurrent().push();
				}}, 1000);
			new Timer().schedule(new TimerTask(){

				@Override
				public void run() {
					prevTable.setVisible(true);
					prevTable.addStyleName("animated");
		            prevTable.addStyleName("zoomInUp");
		            prevTable.markAsDirty();
		            UI.getCurrent().push();
				}}, 1500);
			new Timer().schedule(new TimerTask(){

				@Override
				public void run() {
					nowLabel.setVisible(true);
					nowLabel.addStyleName("animated");
					nowLabel.addStyleName("tada");
					nowLabel.markAsDirty();
					UI.getCurrent().push();
					
				}}, 2000);
			new Timer().schedule(new TimerTask(){

				@Override
				public void run() {
					nowTable.setVisible(true);
					nowTable.addStyleName("animated");
					nowTable.addStyleName("zoomInUp");
					nowTable.markAsDirty();
		            UI.getCurrent().push();
					
				}}, 2500);
			new Timer().schedule(new TimerTask(){

				@Override
				public void run() {
					nextLabel.setVisible(true);
					nextLabel.addStyleName("animated");
					nextLabel.addStyleName("tada");
					nextLabel.markAsDirty();
					UI.getCurrent().push();
					
				}}, 3000);
			new Timer().schedule(new TimerTask(){

				@Override
				public void run() {
					nextTable.setVisible(true);
					nextTable.addStyleName("animated");
					nextTable.addStyleName("zoomInUp");
					nextTable.markAsDirty();
		            UI.getCurrent().push();
					
				}}, 3500);
			
			prevLayout.addComponents(prevLabel,prevTable);
			prevLayout.setComponentAlignment(prevLabel, Alignment.TOP_CENTER);
			prevLayout.setComponentAlignment(prevTable, Alignment.TOP_CENTER);
			nowLayout.addComponents(nowLabel,nowTable);
			nowLayout.setComponentAlignment(nowLabel, Alignment.TOP_CENTER);
			nowLayout.setComponentAlignment(nowTable, Alignment.TOP_CENTER);
			nextLayout.addComponents(nextLabel,nextTable);
			nextLayout.setComponentAlignment(nextLabel, Alignment.TOP_CENTER);
			nextLayout.setComponentAlignment(nextTable, Alignment.TOP_CENTER);

			//getWeeklySchedule();
			getNewWeeklySchedule();
			
			tableSpace.setWidth("100%");
			tableSpace.setSpacing(true);
			tableSpace.setMargin(true);
			tableSpace.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
			tableSpace.addComponents(prevLayout,nowLayout,nextLayout);
			tableSpace.setComponentAlignment(prevLayout, Alignment.TOP_CENTER);
			tableSpace.setComponentAlignment(nowLayout, Alignment.TOP_CENTER);
			tableSpace.setComponentAlignment(nextLayout, Alignment.TOP_CENTER);
			
			this.setWidth("100%");
			this.addComponents(title,tableSpace);
			this.setSpacing(true);
			this.setComponentAlignment(title, Alignment.TOP_CENTER);
			this.setComponentAlignment(tableSpace, Alignment.TOP_CENTER);
		}

	private void setNextTable(LinkedList<Lecture> next) {
		BeanItemContainer<Lecture> container = new BeanItemContainer<Lecture>(Lecture.class,next);
		nextTable.setContainerDataSource(container);
		nextTable.setHeight("167px");
		nextTable.setWidth("100%");
		if(next.size()<=5)
			{
			nextTable.setPageLength(next.size());
			}else nextTable.setPageLength(5);
		nextTable.addStyleName(ValoTheme.TABLE_COMPACT);
		nextTable.addStyleName(ValoTheme.TABLE_SMALL);
		prevTable.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		nextTable.setColumnAlignments(Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER);
		nextTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -6373787444551133713L;

			@Override
			public void itemClick(ItemClickEvent event) {
				BeanItem<?> selected =(BeanItem<?>) event.getItem();
				Lecture lecture = (Lecture) selected.getBean();
				Notification.show("Hey ya!", "You selected lesson of professor "+lecture.getProf()+" which talks about "+lecture.getTopics(), Notification.Type.TRAY_NOTIFICATION);
			}
		});
		
	}

	private void setNowTable(LinkedList<Lecture> now) {
		BeanItemContainer<Lecture> container = new BeanItemContainer<Lecture>(Lecture.class,now);
		nowTable.setContainerDataSource(container);
		nowTable.setHeight("167px");
		nowTable.setWidth("100%");
		if(now.size()<=5)
		{
			nowTable.setPageLength(now.size());
		}else nowTable.setPageLength(5);
		nowTable.addStyleName(ValoTheme.TABLE_COMPACT);
		nowTable.addStyleName(ValoTheme.TABLE_SMALL);
		prevTable.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		nowTable.setColumnAlignments(Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER);
		nowTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -6373787444551133713L;

			@Override
			public void itemClick(ItemClickEvent event) {
				BeanItem<?> selected =(BeanItem<?>) event.getItem();
				Lecture lecture = (Lecture) selected.getBean();
				Notification.show("Hey ya!", "You selected lesson of professor "+lecture.getProf()+" which talks about "+lecture.getTopics(), Notification.Type.WARNING_MESSAGE);
			}
		});
		
	}

	private void setPrevTable(LinkedList<Lecture> prev) {

		BeanItemContainer<Lecture> container = new BeanItemContainer<Lecture>(Lecture.class,prev);
		prevTable.setContainerDataSource(container);
		prevTable.setHeight("167px");
		prevTable.setWidth("100%");
		if(prev.size()<=5)
		{
			prevTable.setPageLength(prev.size());
		}else prevTable.setPageLength(5);
		prevTable.addStyleName(ValoTheme.TABLE_COMPACT);
		prevTable.addStyleName(ValoTheme.TABLE_SMALL);
		prevTable.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		prevTable.setColumnAlignments(Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER);
		prevTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 8643717639430433327L;

			@Override
			public void itemClick(ItemClickEvent event) {
				BeanItem<?> selected =(BeanItem<?>) event.getItem();
				Lecture lecture = (Lecture) selected.getBean();
				Notification.show("Hey ya!", "You selected lesson of professor "+lecture.getProf()+" which talks about "+lecture.getTopics(), Notification.Type.HUMANIZED_MESSAGE);
			}
		});
	}
	
	private void getWeeklySchedule()
		{
		final LinkedList<Lecture> pastList = new LinkedList<Lecture>();
		final LinkedList<Lecture> nowList = new LinkedList<Lecture>();
		final LinkedList<Lecture> nextList = new LinkedList<Lecture>();
		
		final Calendar currentCal =Calendar.getInstance();
		final Date now =currentCal.getTime();
		currentCal.setTime(now);
		SimpleDateFormat dayName = new SimpleDateFormat("EEEE",Locale.ENGLISH);
		final SimpleDateFormat nowName = new SimpleDateFormat("HH:mm",Locale.ITALIAN);
		//final String hourTime = nowName.format(now);
		final String dayTime = dayName.format(now);
		
		ParseCloud.callFunctionInBackground("getWeeklySchedule", null, new FunctionCallback<JSONArray>(){
			

			@Override
			public void done(JSONArray result, ParseException parseException) {
				
				for(int i=0;i<result.length();i++)
					{
					Date startDate = new Date();
					Date endDate = new Date();
					JSONObject jsn = result.getJSONObject(i);
					String professor= jsn.getString("Professor");
					String start = jsn.getString("Start_Time");
					String end = jsn.getString("End_Time");
					String day = jsn.getString("Day");
					String lesson = jsn.getString("Lesson");
					
					try {
						
						startDate =  nowName.parse(start);
						endDate = nowName.parse(end);
						Calendar tempCal = Calendar.getInstance();
						tempCal.setTime(startDate);
						tempCal.set(Calendar.DAY_OF_MONTH, currentCal.get(Calendar.DAY_OF_MONTH));
						tempCal.set(Calendar.MONTH, currentCal.get(Calendar.MONTH));
						tempCal.set(Calendar.YEAR, currentCal.get(Calendar.YEAR));
						startDate=tempCal.getTime();
						tempCal.setTime(endDate);
						tempCal.set(Calendar.DAY_OF_MONTH, currentCal.get(Calendar.DAY_OF_MONTH));
						tempCal.set(Calendar.MONTH, currentCal.get(Calendar.MONTH));
						tempCal.set(Calendar.YEAR, currentCal.get(Calendar.YEAR));
						endDate=tempCal.getTime();
						
						//System.out.println("PORCADDIOOOOOOOOOOOO    "+startDate.toString()+" original time string: "+start+"             "+now+"   compare= "+startDate.compareTo(now));
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Lecture toAdd = new Lecture();
					toAdd.setProf(professor);
					toAdd.setFrom(start);
					toAdd.setTo(end);
					toAdd.setTitle(lesson);
					toAdd.setDayOfTheWeek(day);
					toAdd.setTopics("---");
					if(dayMap.get(day)<dayMap.get(dayTime))
						{
						 pastList.add(toAdd);
						}else if(dayMap.get(day)==dayMap.get(dayTime))
							{
								if(startDate.before(now) && endDate.before(now))
									{
									pastList.add(toAdd);
									}else if (startDate.before(now)&& endDate.after(now))
										{
										nowList.add(toAdd);
										}else if (startDate.after(now) && endDate.after(now))
											{
											nextList.add(toAdd);
											}
							}else
								{
								nextList.add(toAdd);
								}
					//System.out.println(professor+" "+lesson+" "+start+" "+end+" "+day+" day+time= "+dayTime+" "+hourTime+" compareToStart "+start.compareTo(hourTime)+" compareToEnd "+end.compareTo(hourTime));
					}
				setPrevTable(pastList);
				setNowTable(nowList);
				setNextTable(nextList);
				//System.out.println(dayTime+" "+hourTime);
			}});
		}

	/*private void getNewWeeklySchedule(final int fromResult)
		{
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("skip", Integer.toString(fromResult));
			ParseCloud.callFunctionInBackground("getNewWeeklySchedule", map, new FunctionCallback<JSONArray>(){
				
				@Override
				public void done(JSONArray result, ParseException parseException) {
					if(parseException==null)
						{
							while(!done){
										System.out.println(result.length());
										if(result.length()!=1000) 
											{
											more=false;
											System.out.println("MORE E' FALSOOOOOOOOOOOOOOOOOOO!   "+result.length());
											}
										for(int i=0;i<result.length();i++)
											{
											tempResult.put(result.get(i));
											}
										if(more)
											{
												int newFrom = fromResult+1000;
												System.out.println("sto per invocarmi di nuovo, il valore di index è "+newFrom);
												getNewWeeklySchedule(newFrom);
											}else{done=true;}
										}
							System.out.println("fine del porcoddio "+tempResult.length());
						}else
							{
							System.err.println(parseException.getMessage());
							}
				}});
			//System.out.println("Fuori dal done "+result.length());
		}*/
	private void getNewWeeklySchedule()
		{
		final LinkedList<Lecture> pastList = new LinkedList<Lecture>();
		final LinkedList<Lecture> nowList = new LinkedList<Lecture>();
		final LinkedList<Lecture> nextList = new LinkedList<Lecture>();
		final Calendar cal = Calendar.getInstance();
		final SimpleDateFormat hours = new SimpleDateFormat("HH:mm",Locale.ITALIAN);
		
			ParseCloud.callFunctionInBackground("getNewWeeklySchedule", null, new FunctionCallback<JSONArray>(){

				@Override
				public void done(JSONArray result, ParseException parseException) {
					if(parseException==null)
						{
						long now = cal.getTimeInMillis();
						for(int i=0;i<result.length();i++)
							{
							 
							 JSONObject row=result.getJSONObject(i);
							 long baseDate = row.getLong("start_date");
							 long startHour = row.getLong("starttime");
							 long endHour = row.getLong("endtime");
							 String className = row.getString("classroom_name");
							 //String profName = row.getString("prof_name");
							 String profName = "stoca";
							 String summary = row.getString("summary");
							 long lecStart=baseDate+startHour;
							 long lecEnd=baseDate+endHour;
							 
							 cal.setTimeInMillis(baseDate);
							 String dayName=cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
							 cal.setTimeInMillis(lecStart);
							 Date startDate = cal.getTime();
							 hours.format(startDate);
							 cal.setTimeInMillis(lecEnd);
							 Date endDate = cal.getTime();
							 hours.format(endDate);
							 
							 Lecture toAdd = new Lecture();
							 toAdd.setClassroom(className);
							 toAdd.setDayOfTheWeek(dayName);
							 toAdd.setFrom(startDate.toString());
							 toAdd.setTo(endDate.toString());
							 toAdd.setProf(profName);
							 toAdd.setTitle(summary);
							 toAdd.setTopics("---");
							 
							 if(lecStart<now && lecEnd<now)
							 	{
								 pastList.add(toAdd);
							 	}else if(lecStart<=now && lecEnd>now)
							 		{
							 		nowList.add(toAdd);
							 		}else if(lecStart>now) nextList.add(toAdd);
							}
						setPrevTable(pastList);
						setNowTable(nowList);
						setNextTable(nextList);
						 System.out.println(result.length());
						}else
							{
							System.err.println("PIG GOD "+parseException.getMessage());
							}
					
				}});
		}

}
