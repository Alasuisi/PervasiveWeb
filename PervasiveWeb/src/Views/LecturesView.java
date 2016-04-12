package Views;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import com.stupidmonkeys.pervasiveweb.ParseServices;
import com.stupidmonkeys.pervasiveweb.PervasivewebUI;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
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
	private PervasivewebUI thisUI;

	
	/*
	 * This is the constructor, it prepares, defines and organize the main components
	 * of this view
	 */
	public LecturesView()
		{
			thisUI=(PervasivewebUI) UI.getCurrent();
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
			
			getNewWeeklySchedule();
		}

	
	/*
	 * This method mainly calls the UI method to retrieve the list of the lectures,
	 * it also invoke the methods to prepare the tables and
	 *  access the ui and schedule the animations to render in the proper way
	 */
	private void getNewWeeklySchedule()
		{
		System.out.println("called LecturesView.getNewWeeklySchedule");
		final Timer timer = new Timer();
		TimerTask task = new TimerTask(){

			@Override
			public void run() {
				final LinkedList<Lecture>[] fullList = ParseServices.getInstance().getLectures();
				if(fullList!=null)
					{
					thisUI.access(new Runnable(){

						@Override
						public void run() {
							thisLayout.removeComponent(waitingLabel);
							setPrevTable(fullList[0]);
							setNowTable(fullList[1]);
							setNextTable(fullList[2]);
							thisUI.push();
						}});
					new Timer().schedule(new TimerTask(){

						@Override
						public void run() {
							thisUI.access(new Runnable(){

								@Override
								public void run() {
									prevLabel.setVisible(true);
									prevLabel.addStyleName("animated");
									prevLabel.addStyleName("tada");
									thisUI.push();
								}});
						}}, 1000);
					new Timer().schedule(new TimerTask(){

						@Override
						public void run() {
							thisUI.access(new Runnable(){

								@Override
								public void run() {
									prevTable.setVisible(true);
									prevTable.addStyleName("animated");
						            prevTable.addStyleName("zoomInUp");
									thisUI.push();
								}});
						}}, 1500);
					
					new Timer().schedule(new TimerTask(){

						@Override
						public void run() {
							thisUI.access(new Runnable(){

								@Override
								public void run() {
									nowLabel.setVisible(true);
									nowLabel.addStyleName("animated");
									nowLabel.addStyleName("tada");
									UI.getCurrent().push();
								}});
							
							
						}}, 2000);
					new Timer().schedule(new TimerTask(){

						@Override
						public void run() {
							thisUI.access(new Runnable(){

								@Override
								public void run() {
									nowTable.setVisible(true);
									nowTable.addStyleName("animated");
									nowTable.addStyleName("zoomInUp");
						            UI.getCurrent().push();
									
								}});
							
						}}, 2500);
					new Timer().schedule(new TimerTask(){

						@Override
						public void run() {
							thisUI.access(new Runnable(){

								@Override
								public void run() {
									nextLabel.setVisible(true);
									nextLabel.addStyleName("animated");
									nextLabel.addStyleName("tada");
									UI.getCurrent().push();
								}});
							
						}}, 3000);

					new Timer().schedule(new TimerTask(){

						@Override
						public void run() {
							thisUI.access(new Runnable(){

								@Override
								public void run() {
									nextTable.setVisible(true);
									nextTable.addStyleName("animated");
									nextTable.addStyleName("zoomInUp");
						            UI.getCurrent().push();
								}});
							
						}}, 3500);
					timer.cancel();
					}else
						{
						System.out.println("List wasn't there or is being updated, rescheduling getNewWeeklySchedule");
						}
				
			}};
			timer.scheduleAtFixedRate(task, 0, 3000);
			
		}
	
	/*
	 * This method defines how the tables should be rendered
	 * it's nothing more than the visual setup and the data binding 
	 * for the appropriate table
	 */
	private void setNextTable(LinkedList<Lecture> next) {
		BeanItemContainer<Lecture> container = new BeanItemContainer<Lecture>(Lecture.class,next);
		nextTable.setContainerDataSource(container);
		nextTable.setWidth("100%");
		if(next.size()<10) nextTable.setPageLength(next.size());
		else nextTable.setPageLength(10);
		nextTable.addStyleName(ValoTheme.TABLE_COMPACT);
		nextTable.addStyleName(ValoTheme.TABLE_SMALL);
		nextTable.setVisibleColumns("classroom","dayOfTheWeek","from","to","prof","course","topics");
		nextTable.setColumnAlignments(Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER);
		
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
				String lectureField=lecture.getTopicsList();
				if(lectureField!=null)
				{
				Window subWin = new Window("Lecture Topics List");
				VerticalLayout winLay = new VerticalLayout();

				Label topicsLabel = new Label();
				topicsLabel.setContentMode(ContentMode.HTML);
				topicsLabel.setValue(lecture.getTopicsList());
				winLay.setImmediate(true);
				winLay.setWidth("100%");
				winLay.setHeight("-1");
				topicsLabel.setImmediate(true);
				topicsLabel.setSizeUndefined();
				winLay.addComponent(topicsLabel);
				winLay.setComponentAlignment(topicsLabel, Alignment.TOP_CENTER);
				winLay.setSpacing(true);
	
				subWin.setWidth("800px");
				subWin.setHeight("600px");
				subWin.setModal(true);
				
				subWin.setImmediate(true);
				subWin.setContent(winLay);
				UI.getCurrent().addWindow(subWin);
				}else
					{
					Notification.show("Hey ya!", "You selected lesson of professor "+lecture.getProf()+" which talks about "+lecture.getTopics(), Notification.Type.HUMANIZED_MESSAGE);
					}
			}
		});
		
	}

	
	/*
	 * This method defines how the tables should be rendered
	 * it's nothing more than the visual setup and the data binding 
	 * for the appropriate table
	 */
	private void setNowTable(LinkedList<Lecture> now) {
		BeanItemContainer<Lecture> container = new BeanItemContainer<Lecture>(Lecture.class,now);
		nowTable.setContainerDataSource(container);
		nowTable.setWidth("100%");
		if(now.size()<10) nowTable.setPageLength(now.size());
		else nowTable.setPageLength(10);
		nowTable.addStyleName(ValoTheme.TABLE_COMPACT);
		nowTable.addStyleName(ValoTheme.TABLE_SMALL);
		nowTable.setVisibleColumns("classroom","dayOfTheWeek","from","to","prof","course","topics");
		nowTable.setColumnAlignments(Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER);
		
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
				String lectureField=lecture.getTopicsList();
				if(lectureField!=null)
				{
				Window subWin = new Window("Lecture Topics List");
				VerticalLayout winLay = new VerticalLayout();

				Label topicsLabel = new Label();
				topicsLabel.setContentMode(ContentMode.HTML);
				topicsLabel.setValue(lecture.getTopicsList());
				winLay.setImmediate(true);
				winLay.setWidth("100%");
				winLay.setHeight("-1");
				topicsLabel.setImmediate(true);
				topicsLabel.setSizeUndefined();
				winLay.addComponent(topicsLabel);
				winLay.setComponentAlignment(topicsLabel, Alignment.TOP_CENTER);
				winLay.setSpacing(true);
	
				subWin.setWidth("800px");
				subWin.setHeight("600px");
				subWin.setModal(true);
				
				subWin.setImmediate(true);
				subWin.setContent(winLay);
				UI.getCurrent().addWindow(subWin);
				}else
					{
					Notification.show("Hey ya!", "You selected lesson of professor "+lecture.getProf()+" which talks about "+lecture.getTopics(), Notification.Type.HUMANIZED_MESSAGE);
					}
			}
		});
		
	}
	
	/*
	 * This method defines how the tables should be rendered
	 * it's nothing more than the visual setup and the data binding 
	 * for the appropriate table
	 */
	private void setPrevTable(LinkedList<Lecture> prev) {

		BeanItemContainer<Lecture> container = new BeanItemContainer<Lecture>(Lecture.class,prev);
		prevTable.setContainerDataSource(container);
		prevTable.setWidth("100%");
		if(prev.size()<10)prevTable.setPageLength(prev.size());
		else prevTable.setPageLength(10);
		prevTable.addStyleName(ValoTheme.TABLE_COMPACT);
		prevTable.addStyleName(ValoTheme.TABLE_SMALL);
		prevTable.setVisibleColumns("classroom","dayOfTheWeek","from","to","prof","course","topics");
		prevTable.setColumnAlignments(Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER);
		
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
				String lectureField=lecture.getTopicsList();
				//if(!lecture.getTopicsList().equals("---"))
				if(lectureField!=null)
				{
				Window subWin = new Window("Lecture Topics List");
				VerticalLayout winLay = new VerticalLayout();

				Label topicsLabel = new Label();
				topicsLabel.setContentMode(ContentMode.HTML);
				topicsLabel.setValue(lecture.getTopicsList());
				winLay.setImmediate(true);
				winLay.setWidth("100%");
				winLay.setHeight("-1");
				topicsLabel.setImmediate(true);
				topicsLabel.setSizeUndefined();
				winLay.addComponent(topicsLabel);
				winLay.setComponentAlignment(topicsLabel, Alignment.TOP_CENTER);
				winLay.setSpacing(true);
	
				subWin.setWidth("800px");
				subWin.setHeight("600px");
				subWin.setModal(true);
				
				subWin.setImmediate(true);
				subWin.setContent(winLay);
				UI.getCurrent().addWindow(subWin);
				}else
					{
					Notification.show("Hey ya!", "You selected lesson of professor "+lecture.getProf()+" which talks about "+lecture.getTopics(), Notification.Type.HUMANIZED_MESSAGE);
					}
			}
		});
	}
}
