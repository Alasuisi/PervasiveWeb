package Views;

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

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import domainEntities.Lecture;

public class LecturesView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2570793839851203621L;
	
	private VerticalLayout tableSpace = new VerticalLayout();
	private VerticalLayout prevLayout = new VerticalLayout();
	private VerticalLayout nowLayout = new VerticalLayout();
	private VerticalLayout nextLayout = new VerticalLayout();
	private VerticalLayout thisLayout = this;
	
	private Label title = new Label("Today's lectures");
	private Label prevLabel = new Label("Previous lectures");
	private Label nowLabel = new Label("Ongoing lectures");
	private Label nextLabel = new Label("Next lectures");
	private Label waitingLabel = new Label("Fetching results...");
	
	private Table prevTable = new Table();
	private Table nowTable = new Table();
	private Table nextTable = new Table();
	private HashMap<String,Integer> dayMap = new HashMap<String,Integer>();
	
	
	public LecturesView()
		{
			dayMap.put("Monday", 1);
			dayMap.put("Tuesday", 2);
			dayMap.put("Wednesday", 3);
			dayMap.put("Thursday", 4);
			dayMap.put("Friday", 5);
			dayMap.put("Saturday", 6);
			dayMap.put("Sunday", 7);
			title.addStyleName(ValoTheme.LABEL_BOLD);
			title.addStyleName(ValoTheme.LABEL_LARGE);
			title.setSizeUndefined();
			title.addStyleName("animated");
			title.addStyleName("flipInX");
			title.addStyleName("delay05");
			waitingLabel.setSizeUndefined();
			waitingLabel.addStyleName(ValoTheme.LABEL_HUGE);
			waitingLabel.addStyleName(ValoTheme.LABEL_BOLD);
			waitingLabel.addStyleName(ValoTheme.LABEL_COLORED);
			waitingLabel.addStyleName("animated");
			waitingLabel.addStyleName("flipInX");
			waitingLabel.addStyleName("delay05");
			prevLabel.setSizeUndefined();
			prevLabel.addStyleName(ValoTheme.LABEL_BOLD);
			prevLabel.addStyleName(ValoTheme.LABEL_COLORED);
			nowLabel.setSizeUndefined();
			nowLabel.addStyleName("animated");
			nowLabel.addStyleName("tada");
			nowLabel.addStyleName("delay09");
			nowLabel.addStyleName(ValoTheme.LABEL_BOLD);
			nowLabel.addStyleName(ValoTheme.LABEL_COLORED);
			nextLabel.setSizeUndefined();
			nextLabel.addStyleName("animated");
			nextLabel.addStyleName("tada");
			nextLabel.addStyleName("delay1");
			nextLabel.addStyleName(ValoTheme.LABEL_BOLD);
			nextLabel.addStyleName(ValoTheme.LABEL_COLORED);
			prevTable.setImmediate(true);
			nowTable.setImmediate(true);
			nextTable.setImmediate(true);
			prevLabel.setVisible(false);
			nowLabel.setVisible(false);
			nextLabel.setVisible(false);
			prevTable.setVisible(false);
			nowTable.setVisible(false);
			nextTable.setVisible(false);
						
			prevLayout.addComponents(prevLabel,prevTable);
			prevLayout.setComponentAlignment(prevLabel, Alignment.TOP_CENTER);
			prevLayout.setComponentAlignment(prevTable, Alignment.TOP_CENTER);
			prevLayout.setSpacing(true);
			nowLayout.addComponents(nowLabel,nowTable);
			nowLayout.setComponentAlignment(nowLabel, Alignment.TOP_CENTER);
			nowLayout.setComponentAlignment(nowTable, Alignment.TOP_CENTER);
			nowLayout.setSpacing(true);
			nextLayout.addComponents(nextLabel,nextTable);
			nextLayout.setComponentAlignment(nextLabel, Alignment.TOP_CENTER);
			nextLayout.setComponentAlignment(nextTable, Alignment.TOP_CENTER);
			nextLayout.setSpacing(true);

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
			this.addComponents(title,waitingLabel,tableSpace);
			this.setSpacing(true);
			this.setComponentAlignment(title, Alignment.TOP_CENTER);
			this.setComponentAlignment(waitingLabel, Alignment.MIDDLE_CENTER);
			this.setComponentAlignment(tableSpace, Alignment.TOP_CENTER);
		}

	private void setNextTable(LinkedList<Lecture> next) {
		BeanItemContainer<Lecture> container = new BeanItemContainer<Lecture>(Lecture.class,next);
		nextTable.setContainerDataSource(container);
		nextTable.setWidth("100%");
		if(next.size()<10) nextTable.setPageLength(next.size());
		else nextTable.setPageLength(10);
		nextTable.addStyleName(ValoTheme.TABLE_COMPACT);
		nextTable.addStyleName(ValoTheme.TABLE_SMALL);
		nextTable.setColumnAlignments(Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER);
		nextTable.setVisibleColumns("classroom","dayOfTheWeek","from","to","prof","title","topics");
		nextTable.setColumnHeaders("Classroom","Day","From","To","Professor","Lecture","Topics");
		nextTable.setColumnWidth("classroom", 170);
		nextTable.setColumnWidth("from", 50);
		nextTable.setColumnWidth("to", 50);
		nextTable.setColumnWidth("prof", 230);
		nextTable.setColumnExpandRatio("title", 50);
		nextTable.setColumnExpandRatio("topics", 50);
		nextTable.setColumnWidth("dayOfTheWeek", 170);
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
		nowTable.setWidth("100%");
		if(now.size()<10) nowTable.setPageLength(now.size());
		else nowTable.setPageLength(10);
		nowTable.addStyleName(ValoTheme.TABLE_COMPACT);
		nowTable.addStyleName(ValoTheme.TABLE_SMALL);
		//nowTable.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		nowTable.setColumnAlignments(Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER);
		nowTable.setVisibleColumns("classroom","dayOfTheWeek","from","to","prof","title","topics");
		nowTable.setColumnHeaders("Classroom","Day","From","To","Professor","Lecture","Topics");
		nowTable.setColumnWidth("classroom", 170);
		nowTable.setColumnWidth("from", 50);
		nowTable.setColumnWidth("to", 50);
		nowTable.setColumnWidth("prof", 230);
		nowTable.setColumnExpandRatio("title", 50);
		nowTable.setColumnExpandRatio("topics", 50);
		nowTable.setColumnWidth("dayOfTheWeek", 170);
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
		prevTable.setWidth("100%");
		if(prev.size()<10)prevTable.setPageLength(prev.size());
		else prevTable.setPageLength(10);
		prevTable.addStyleName(ValoTheme.TABLE_COMPACT);
		prevTable.addStyleName(ValoTheme.TABLE_SMALL);
		prevTable.setColumnAlignments(Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER);
		prevTable.setVisibleColumns("classroom","dayOfTheWeek","from","to","prof","title","topics");
		prevTable.setColumnHeaders("Classroom","Day","From","To","Professor","Lecture","Topics");
		prevTable.setColumnWidth("classroom", 170);
		prevTable.setColumnWidth("from", 50);
		prevTable.setColumnWidth("to", 50);
		prevTable.setColumnWidth("prof", 230);
		prevTable.setColumnExpandRatio("title", 50);
		prevTable.setColumnExpandRatio("topics", 50);
		prevTable.setColumnWidth("dayOfTheWeek", 170);
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
	
	
	private void getNewWeeklySchedule()
		{
		final LinkedList<Lecture> pastList = new LinkedList<Lecture>();
		final LinkedList<Lecture> nowList = new LinkedList<Lecture>();
		final LinkedList<Lecture> nextList = new LinkedList<Lecture>();
		final Calendar cal = Calendar.getInstance();
		final SimpleDateFormat hours = new SimpleDateFormat("HH:mm",Locale.ITALIAN);
		final SimpleDateFormat days = new SimpleDateFormat("EEEE dd MMM YYYY",Locale.ENGLISH);
		
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
							 String profName = row.getString("prof_name");
							 String summary = row.getString("summary");
							 long lecStart=baseDate+(startHour*60);
							 long lecEnd=baseDate+(endHour*60);
							 if(java.util.Locale.getDefault().toString().equals("en_US"))
							 	{
								 lecStart=lecStart+21600;
								 lecEnd=lecEnd+21600;
								// now=now+21600000;
							 	}
							 
							 
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
							}
						new Timer().schedule(new TimerTask(){

							@Override
							public void run() {
								prevLabel.setVisible(true);
								prevLabel.addStyleName("animated");
								prevLabel.addStyleName("tada");
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
						thisLayout.removeComponent(waitingLabel);
						setPrevTable(pastList);
						setNowTable(nowList);
						setNextTable(nextList);
						}else
							{
							System.err.println("FUUUUUUUU "+parseException.getMessage());
							}
					
				}});
		}

}
