package Views;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.stupidmonkeys.pervasiveweb.ParseServices;
import com.vaadin.data.Item;
import com.vaadin.data.Item.PropertySetChangeEvent;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;

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
	private String selObjectID=new String();
	
	private VerticalLayout thisLayout=this;
	
	
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
									if(temp.getTitle().equals(lecTitleString) && temp.getDayOfTheWeek().equals(lecDayString))
										{
										selObjectID=temp.getObjectId();
										System.out.println(temp.getObjectId());
										}
								}
							Iterator<Lecture> iter1 =lecList[1].iterator();
							while(iter1.hasNext())
								{
								Lecture temp=iter1.next();
								if(temp.getTitle().equals(lecTitleString) && temp.getDayOfTheWeek().equals(lecDayString))
									{
									selObjectID=temp.getObjectId();
									System.out.println(temp.getObjectId());
									}
								}
							Iterator<Lecture> iter2 =lecList[2].iterator();
							while(iter2.hasNext())
								{
								Lecture temp=iter2.next();
								if(temp.getTitle().equals(lecTitleString) && temp.getDayOfTheWeek().equals(lecDayString))
									{
									selObjectID=temp.getObjectId();
									System.out.println(temp.getObjectId());
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
						ParseServices.getInstance().saveTopicsListForLecture(selObjectID, topicsText);
						ParseServices.getInstance().markLectureListAsDirty();
						System.out.println(topicsText);
						}
			}
		});
		
		lecSelLayout.addComponents(lecTitle,lecDay,editBtn);
		lecSelLayout.setComponentAlignment(editBtn, Alignment.BOTTOM_LEFT);
		lecSelLayout.setSpacing(true);
		this.addComponents(title,lecSelLayout);
		this.setComponentAlignment(title, Alignment.TOP_CENTER);
		this.setComponentAlignment(lecSelLayout, Alignment.TOP_CENTER);
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
								while(iter.hasNext())
									{
									Lecture temp = iter.next();
									HashSet<String> dates=lecDayMap.get(temp.getTitle());
									if(dates==null)
										{
										HashSet<String> set =new HashSet<String>();
										set.add(temp.getDayOfTheWeek());
										lecDayMap.put(temp.getTitle(),set);
										}else
										{
											HashSet<String> set =lecDayMap.get(temp.getTitle());
											set.add(temp.getDayOfTheWeek());
											lecDayMap.put(temp.getTitle(), set);
										}
									}
								
								Iterator<Lecture> iter1 =lecList[1].iterator();
								while(iter1.hasNext())
									{
									Lecture temp = iter1.next();
									HashSet<String> dates=lecDayMap.get(temp.getTitle());
									if(dates==null)
										{
										HashSet<String> set =new HashSet<String>();
										set.add(temp.getDayOfTheWeek());
										lecDayMap.put(temp.getTitle(),set);
										}else
										{
											HashSet<String> set =lecDayMap.get(temp.getTitle());
											set.add(temp.getDayOfTheWeek());
											lecDayMap.put(temp.getTitle(), set);
										}
									}
								
								Iterator<Lecture> iter2 =lecList[2].iterator();
								while(iter2.hasNext())
									{
									Lecture temp = iter2.next();
									HashSet<String> dates=lecDayMap.get(temp.getTitle());
									if(dates==null)
										{
										HashSet<String> set =new HashSet<String>();
										set.add(temp.getDayOfTheWeek());
										lecDayMap.put(temp.getTitle(),set);
										}else
										{
											HashSet<String> set =lecDayMap.get(temp.getTitle());
											set.add(temp.getDayOfTheWeek());
											lecDayMap.put(temp.getTitle(), set);
										}
									}
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
	
	
	
	
	
}
