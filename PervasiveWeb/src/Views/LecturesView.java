package Views;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import domainEntities.Lecture;

public class LecturesView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2570793839851203621L;
	
	private HorizontalLayout tableSpace = new HorizontalLayout();
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
	
	private SimpleDateFormat sdf= new SimpleDateFormat("HH:mm");
	
	public LecturesView()
		{
			
			title.addStyleName(ValoTheme.LABEL_BOLD);
			title.addStyleName(ValoTheme.LABEL_LARGE);
			title.setSizeUndefined();
			prevLabel.setSizeUndefined();
			nowLabel.setSizeUndefined();
			nextLabel.setSizeUndefined();
			
			prevLayout.addComponents(prevLabel,prevTable);
			prevLayout.setComponentAlignment(prevLabel, Alignment.TOP_CENTER);
			prevLayout.setComponentAlignment(prevTable, Alignment.TOP_CENTER);
			nowLayout.addComponents(nowLabel,nowTable);
			nowLayout.setComponentAlignment(nowLabel, Alignment.TOP_CENTER);
			nowLayout.setComponentAlignment(nowTable, Alignment.TOP_CENTER);
			nextLayout.addComponents(nextLabel,nextTable);
			nextLayout.setComponentAlignment(nextLabel, Alignment.TOP_CENTER);
			nextLayout.setComponentAlignment(nextTable, Alignment.TOP_CENTER);
			
			setPrevTable();
			setNowTable();
			setNextTable();
			
			tableSpace.setWidth("100%");
			tableSpace.setSpacing(true);
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

	private void setNextTable() {
		LinkedList<Lecture> list = new LinkedList<Lecture>();
		list.add(new Lecture("Cool prof","Important Stuff","many things","8:30","10:00"));
		list.add(new Lecture("Not Cool prof","Crazy Stuff","too many things","10:15","11:45"));
		list.add(new Lecture("Boring prof","Useless Stuff","random things","10:15","11:45"));
		list.add(new Lecture("Angry profe","STFU","many things","12:00","13:30"));
		BeanItemContainer<Lecture> container = new BeanItemContainer<Lecture>(Lecture.class,list);
		nextTable.setContainerDataSource(container);
		nextTable.setHeight("-1");
		if(list.size()<=5)
			{
			nextTable.setPageLength(list.size());
			}else nextTable.setPageLength(5);
		nextTable.addStyleName(ValoTheme.TABLE_COMPACT);
		nextTable.addStyleName(ValoTheme.TABLE_SMALL);
		prevTable.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		nextTable.setColumnAlignments(Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER);
		nextTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -6373787444551133713L;

			@Override
			public void itemClick(ItemClickEvent event) {
				BeanItem<?> selected =(BeanItem<?>) event.getItem();
				Lecture lecture = (Lecture) selected.getBean();
				//System.out.println(lecture.getTopics());
				//System.out.println(event.getItemId());
				Notification.show("Hey ya!", "You selected lesson of professor "+lecture.getAttending()+" which talks about "+lecture.getTopics(), Notification.Type.TRAY_NOTIFICATION);
			}
		});
		
	}

	private void setNowTable() {
		LinkedList<Lecture> list = new LinkedList<Lecture>();
		list.add(new Lecture("Cool prof","Important Stuff","many things","8:30","10:00"));
		list.add(new Lecture("Not Cool prof","Crazy Stuff","too many things","10:15","11:45"));
		list.add(new Lecture("Boring prof","Useless Stuff","random things","10:15","11:45"));
		list.add(new Lecture("Angry profe","STFU","many things","12:00","13:30"));
		BeanItemContainer<Lecture> container = new BeanItemContainer<Lecture>(Lecture.class,list);
		nowTable.setContainerDataSource(container);
		nowTable.setHeight("-1");
		if(list.size()<=5)
		{
			nowTable.setPageLength(list.size());
		}else nowTable.setPageLength(5);
		nowTable.addStyleName(ValoTheme.TABLE_COMPACT);
		nowTable.addStyleName(ValoTheme.TABLE_SMALL);
		prevTable.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		nowTable.setColumnAlignments(Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER);
		nowTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -6373787444551133713L;

			@Override
			public void itemClick(ItemClickEvent event) {
				BeanItem<?> selected =(BeanItem<?>) event.getItem();
				Lecture lecture = (Lecture) selected.getBean();
				//System.out.println(lecture.getTopics());
				//System.out.println(event.getItemId());
				Notification.show("Hey ya!", "You selected lesson of professor "+lecture.getAttending()+" which talks about "+lecture.getTopics(), Notification.Type.WARNING_MESSAGE);
			}
		});
		
	}

	private void setPrevTable() {
		LinkedList<Lecture> list = new LinkedList<Lecture>();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH,0);
		cal.set(Calendar.MONTH,0);
		cal.set(Calendar.YEAR, 0);
		cal.set(Calendar.SECOND, 0);
	
		
		list.add(new Lecture("Cool prof","Important Stuff","many things","8:30","10:00"));
		list.add(new Lecture("Not Cool prof","Crazy Stuff","too many things","10:15","11:45"));
		list.add(new Lecture("Boring prof","Useless Stuff","random things","10:15","11:45"));
		list.add(new Lecture("Angry profe","STFU","many things","12:00","13:30"));
		BeanItemContainer<Lecture> container = new BeanItemContainer<Lecture>(Lecture.class,list);
		prevTable.setContainerDataSource(container);
		prevTable.setHeight("-1");
		if(list.size()<=5)
		{
			prevTable.setPageLength(list.size());
		}else prevTable.setPageLength(5);
		prevTable.addStyleName(ValoTheme.TABLE_COMPACT);
		prevTable.addStyleName(ValoTheme.TABLE_SMALL);
		prevTable.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		prevTable.setColumnAlignments(Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER);
		prevTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 8643717639430433327L;

			@Override
			public void itemClick(ItemClickEvent event) {
				BeanItem<?> selected =(BeanItem<?>) event.getItem();
				Lecture lecture = (Lecture) selected.getBean();
				//System.out.println(lecture.getTopics());
				//System.out.println(event.getItemId());
				Notification.show("Hey ya!", "You selected lesson of professor "+lecture.getAttending()+" which talks about "+lecture.getTopics(), Notification.Type.HUMANIZED_MESSAGE);
			}
		});
	}
	

}
