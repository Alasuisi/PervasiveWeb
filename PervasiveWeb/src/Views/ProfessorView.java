package Views;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.ParseRelation;
import org.parse4j.ParseUser;
import org.parse4j.callback.FindCallback;
import org.parse4j.callback.GetCallback;
import org.parse4j.callback.ParseCallback;

import com.stupidmonkeys.pervasiveweb.ParseServices;
import com.vaadin.data.Item;
import com.vaadin.data.Item.PropertySetChangeEvent;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;

import domainEntities.Classroom;
import domainEntities.Lecture;

public class ProfessorView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6480557686072383903L;
	
	private Label title=new Label();
	private HorizontalLayout lecSelLayout=new HorizontalLayout();
	private ComboBox lecTitle = new ComboBox();
	private ComboBox lecDay = new ComboBox();
	private Button editBtn = new Button("Edit");
	private Button doneBtn = new Button("Done");
	private RichTextArea topics = new RichTextArea();
	
	private HashMap<String,HashSet<String>> lecDayMap = new HashMap<String,HashSet<String>>();
	private LinkedList<Lecture>[] lecList;
	private LinkedList<String> selObjectID=new LinkedList<String>();
	
	private VerticalLayout thisLayout=this;
	private UI thisUI=UI.getCurrent();
	
	
	
	public ProfessorView()
		{
		title.setValue("Select a lecture for which you want to add topics..");	
		title.addStyleName(ValoTheme.LABEL_BOLD);
		title.addStyleName(ValoTheme.LABEL_LARGE);
		title.setSizeUndefined();
		title.addStyleName("animated");
		title.addStyleName("flipInX");
		title.addStyleName("delay05");
		
		lecTitle.setCaption("Choose the lecture from here");
		lecTitle.setWidth("350px");
		lecTitle.addStyleName("animated");
		lecTitle.addStyleName("flipInX");
		lecTitle.addStyleName("delay06");
		lecTitle.setImmediate(true);
		
		lecDay.setCaption("Then choose the day of that lecture");
		lecDay.setWidth("250px");
		lecDay.addStyleName("animated");
		lecDay.addStyleName("flipInX");
		lecDay.addStyleName("delay07");
		lecDay.setImmediate(true);
		
		editBtn.addStyleName("animated");
		editBtn.addStyleName("flipInX");
		editBtn.addStyleName("delay08");
		editBtn.setImmediate(true);
		
		
		topics.setCaption("Write here the lecture topics, one per line");
		
		lecTitle.addValueChangeListener(new ValueChangeListener(){

			/**
			 * 
			 */
			private static final long serialVersionUID = -2466006651985369081L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				String text =event.getProperty().getValue().toString();
				HashSet<String> set =lecDayMap.get(text);
				BeanItemContainer<String> container = new BeanItemContainer<String>(String.class,set);
				lecDay.setContainerDataSource(container);
				System.out.println(text);
				
			}});
		
		
		
		setLecListCombo();
		
		editBtn.addClickListener(new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 8048012603537293255L;

			@Override
			public void buttonClick(ClickEvent event) {
				String lecTitleString = (String) lecTitle.getValue();
				String lecDayString = (String) lecDay.getValue();
				if(lecTitleString==null || lecDayString==null)
					{
					Notification.show("Ehm..are you shure?", "It seems that you have not selected one or both of the required fileds, please check your selection", Type.ERROR_MESSAGE);
					}else
						{
							thisLayout.addComponents(topics,doneBtn);
							thisLayout.setComponentAlignment(topics, Alignment.TOP_CENTER);
							thisLayout.setComponentAlignment(doneBtn, Alignment.TOP_CENTER);
							topics.addStyleName("animated");
							topics.addStyleName("flipInX");
							topics.addStyleName("delay06");
							doneBtn.addStyleName("animated");
							doneBtn.addStyleName("flipInX");
							doneBtn.addStyleName("delay07");
							Iterator<Lecture> iter0 =lecList[0].iterator();
							while(iter0.hasNext())
								{
									Lecture temp=iter0.next();
									//System.out.println(temp.toString());
									if(temp.getCourse().getName().equals(lecTitleString) && temp.getDayOfTheWeek().equals(lecDayString))
										{
										selObjectID.add(temp.getObjectId());
										System.out.println("past lec"+temp.toString());
										}
								}
							Iterator<Lecture> iter1 =lecList[1].iterator();
							while(iter1.hasNext())
								{
								Lecture temp=iter1.next();
								//System.out.println(temp.toString());
								if(temp.getCourse().getName().equals(lecTitleString) && temp.getDayOfTheWeek().equals(lecDayString))
									{
									selObjectID.add(temp.getObjectId());
									System.out.println("now lec"+temp.toString());
									}
								}
							Iterator<Lecture> iter2 =lecList[2].iterator();
							while(iter2.hasNext())
								{
								Lecture temp=iter2.next();
								//System.out.println(temp.toString());
								if(temp.getCourse().getName().equals(lecTitleString) && temp.getDayOfTheWeek().equals(lecDayString))
									{
									selObjectID.add(temp.getObjectId());
									System.out.println("prox lec"+temp.toString());
									}
								}
						}
		
				
			}
		});
		
		doneBtn.addClickListener(new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -3502151488441853418L;

			@Override
			public void buttonClick(ClickEvent event) {
				String topicsText =topics.getValue();
				if(topicsText.equals(""))
					{
					Notification.show("What?", "It seems that you're trying to save an empty list of topics, please add some topics to the lecture before clicking on Done", Type.WARNING_MESSAGE);
					}else
						{
						System.out.println("OBJID NEL DONEBTN "+selObjectID);
						Iterator<String> lecListIter = selObjectID.iterator();
						while(lecListIter.hasNext())
							{
							 String temp = lecListIter.next();
							 ParseServices.getInstance().saveTopicsListForLecture(temp, topicsText);
							}
						//ParseServices.getInstance().saveTopicsListForLecture(selObjectID, topicsText);
						ParseServices.getInstance().markLectureListAsDirty();
						selObjectID = new LinkedList<String>();
						System.out.println(topicsText);
						}
			}
		});
		//////////TEST CODE//////////
		Button addLecBtn = new Button();
		addLecBtn.setCaption("Add a new Lecture");
		addLecBtn.addStyleName("animated");
		addLecBtn.addStyleName("flipInX");
		addLecBtn.addStyleName("delay09");
		addLecBtn.setImmediate(true);
		
		addLecBtn.addClickListener(new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -9011740697052582649L;

			@Override
			public void buttonClick(ClickEvent event) {
				ParseUser user = (ParseUser) UI.getCurrent().getSession().getAttribute("ParseUser");
				addLecturesForProf(user);
				
			}
		});
		/////////////////////////////
		lecSelLayout.addComponents(lecTitle,lecDay,editBtn);
		lecSelLayout.setComponentAlignment(editBtn, Alignment.BOTTOM_LEFT);
		lecSelLayout.setSpacing(true);
		this.addComponents(title,lecSelLayout,addLecBtn);
		this.setComponentAlignment(title, Alignment.TOP_CENTER);
		this.setComponentAlignment(lecSelLayout, Alignment.TOP_CENTER);
		this.setComponentAlignment(addLecBtn, Alignment.TOP_CENTER);
		this.setWidth("100%");
		this.setSpacing(true);
		this.setMargin(true);
		
		}
	

	private void setLecListCombo()
		{
			lecList=ParseServices.getInstance().getLectures();
			final Timer timer = new Timer();
			TimerTask task = new TimerTask(){

				@Override
				public void run() {
					if(lecList!=null)
						{
						timer.cancel();
						UI.getCurrent().access(new Runnable(){

							@Override
							public void run() {
								Iterator<Lecture> iter =lecList[0].iterator();
								ParseUser user = (ParseUser) UI.getCurrent().getSession().getAttribute("ParseUser");
								System.out.println(user.getString("surname"));
								while(iter.hasNext())
									{
									Lecture temp = iter.next();
									if(user.getString("surname").equals(temp.getProf().getName())){
									HashSet<String> dates=lecDayMap.get(temp.getCourse().getName()); //old value temp.getTitle()
									if(dates==null)
										{
	
										HashSet<String> set =new HashSet<String>();
										set.add(temp.getDayOfTheWeek());
										lecDayMap.put(temp.getCourse().getName(),set); //old value temp.getTitle()
										}else
										{
											HashSet<String> set =lecDayMap.get(temp.getCourse().getName()); //old value temp.getTitle()
											set.add(temp.getDayOfTheWeek());
											lecDayMap.put(temp.getCourse().getName(), set); //old value temp.getTitle()
										}
									}}
								
								Iterator<Lecture> iter1 =lecList[1].iterator();
								while(iter1.hasNext())
									{
									Lecture temp = iter1.next();
									if(user.getString("surname").equals(temp.getProf().getName())){
									HashSet<String> dates=lecDayMap.get(temp.getCourse().getName());//old value temp.getTitle()
									if(dates==null)
										{
										HashSet<String> set =new HashSet<String>();
										set.add(temp.getDayOfTheWeek());
										lecDayMap.put(temp.getCourse().getName(),set); //old value temp.getTitle()
										}else
										{
											HashSet<String> set =lecDayMap.get(temp.getCourse().getName());//old value temp.getTitle()
											set.add(temp.getDayOfTheWeek());
											lecDayMap.put(temp.getCourse().getName(), set); //old value temp.getTitle()
										}
									}}
								
								Iterator<Lecture> iter2 =lecList[2].iterator();
								while(iter2.hasNext())
									{
									Lecture temp = iter2.next();
									if(user.getString("surname").equals(temp.getProf().getName())){
									HashSet<String> dates=lecDayMap.get(temp.getCourse().getName());//old value temp.getTitle()
									if(dates==null)
										{
										HashSet<String> set =new HashSet<String>();
										set.add(temp.getDayOfTheWeek());
										lecDayMap.put(temp.getCourse().getName(),set); //old value temp.getTitle()
										}else
										{
											HashSet<String> set =lecDayMap.get(temp.getCourse().getName());//old value temp.getTitle()
											set.add(temp.getDayOfTheWeek());
											lecDayMap.put(temp.getCourse().getName(), set); //old value temp.getTitle()
										}
									}}
								Iterator<Entry<String, HashSet<String>>> mapIter =lecDayMap.entrySet().iterator();
								LinkedList<String> lecTitleList = new LinkedList<String>();
								while(mapIter.hasNext())
									{
										Entry<String, HashSet<String>> temp = mapIter.next();
										String lecTitleString=temp.getKey();
										lecTitleList.add(lecTitleString);
									}
								BeanItemContainer<String> container = new BeanItemContainer<String>(String.class,lecTitleList);
								lecTitle.setContainerDataSource(container);
								UI.getCurrent().push();
								
							}});
						}
					
				}};
			
			timer.scheduleAtFixedRate(task, 0, 1500);
		}
	
	private void addLecturesForProf(ParseUser prof)
	{
		
		
		
	
	    ParseQuery<ParseObject> query =ParseQuery.getQuery("_User");
	    System.out.println(prof.getObjectId());
	    query.getInBackground(prof.getObjectId(), new GetCallback<ParseObject>(){

			@Override
			public void done(ParseObject t, ParseException parseException) {
				if(parseException==null)
					{
					 ParseRelation<ParseObject> courses= t.getRelation("Courses");
					 ParseQuery<ParseObject> courseQuery = courses.getQuery();
					 courseQuery.findInBackground(new FindCallback<ParseObject>(){

						@Override
						public void done(List<ParseObject> list, ParseException parseException) {
							if(parseException==null)
								{
								
								 Iterator<ParseObject> iter = list.iterator();
								 while(iter.hasNext())
								 	{
									 ParseObject tempCourse =iter.next();
									 System.out.println(tempCourse.getString("name")+" "+tempCourse.getObjectId()+" classroom_name: ");
									 LinkedList<Classroom> clasList =ParseServices.getInstance().getClassroomList();
									 Iterator<Classroom> clasIter =clasList.iterator();
									 while(clasIter.hasNext())
									 	{
										 Classroom temp =clasIter.next();
										 System.out.println("class name: "+temp.getClassName()+" objectid: "+temp.getObjectId());
									 	}
								 	}
								 
								}else
									{
									System.out.println("Erorr in getLecturesForProf (inner query): "+parseException.getMessage());
									}
							
						}});
					// courseQuery.getInBackground(objectId, callback);
					//ParseObject courses = t.getParseObject("Courses");
					 System.out.println(courses.toString());
					}else
						{
						System.out.println("Erorr in getLecturesForProf (outer query): "+parseException.getMessage());
						}
				
				VerticalLayout subContainer = new VerticalLayout();
				subContainer.setSpacing(true);
				subContainer.setSizeFull();
				
				HorizontalLayout line1 = new HorizontalLayout();
				line1.setSpacing(true);

				
				ComboBox courses = new ComboBox("Choose course");
				ComboBox room = new ComboBox("Choose room");
				
				DateField dayStart = new DateField("Select lecture start");
				Date now = Calendar.getInstance().getTime();
				dayStart.setRangeStart(now);
				dayStart.setResolution(Resolution.MINUTE);
				dayStart.setImmediate(true);
				
				DateField dayEnd = new DateField("Select Lecture end");
				dayEnd.setRangeStart(now);
				dayEnd.setResolution(Resolution.MINUTE);
				dayEnd.setImmediate(true);
				
				line1.addComponents(courses,room,dayStart,dayEnd);
				subContainer.addComponent(line1);
				subContainer.setComponentAlignment(line1, Alignment.TOP_CENTER);
				
				Window subWindow = new Window("Add lectures pop-up");
				subWindow.setContent(subContainer);
				subWindow.setModal(true);
				subWindow.setResizable(false);
				subWindow.setDraggable(false);
				subWindow.setClosable(true);
				subWindow.setWidth("1000px");
				subWindow.setHeight("400px");
				subWindow.addStyleName("animated");
				subWindow.addStyleName("tada");
				subWindow.markAsDirtyRecursive();
				thisUI.addWindow(subWindow);
				thisUI.push();
				
			}});
	
	
	}
	
	
	
	
	
}
