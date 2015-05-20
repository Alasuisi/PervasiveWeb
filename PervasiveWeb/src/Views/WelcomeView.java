package Views;

import java.io.File;
import java.io.Serializable;


import org.vaadin.jouni.restrain.Restrain;

import com.vaadin.client.ui.FontIcon;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.LayoutEvents;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;

public class WelcomeView extends VerticalLayout implements View, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 254789032207847761L;

	private Navigator navi;
	private VerticalLayout mainLayout = new VerticalLayout();
	private VerticalLayout topLayout= new VerticalLayout();
	private VerticalLayout midLayout= new VerticalLayout();
	private VerticalLayout bottomLayout= new VerticalLayout();
	private String projDesc="This project is a service to evaluate global quality of lectures, professors, and classrooms. Mainly developed"
			+ " by a group of students attending the course of Pervasive Systems at University of Rome La Sapienza (Master degree in computer science and engineering)."
			+ " using sensors available in common smartphones, such as microphones, accelerometer and bluetooth beacons, the main purpose of this project is to estimate"
			+ " noise levels, people distance from professor desk, and common interest in the lecture throgh correlation between al this data; this all done respecting the"
			+ " need of anonimity of the single user, and without recording and or correlating in any way any of the data collected with a specific, knowable person";
	//private Label topText= new Label();
	private Label midText= new Label();
	private Label bottomText= new Label();
	//private Label bottomText2= new Label();
	
	
	private boolean initialized=false;
	
	public WelcomeView(Navigator navi)
		{
		this.navi=navi;
		}
	@Override
	public void enter(ViewChangeEvent event) {
		if(!initialized)
			{
			init();
			
			setTopMenu();
			midText.setCaption("Things to do here...");
			//bottomText.setValue("Here should go some useful information, about the projects, or maybe links to licensing and things like that, i don't know, something cool..");
			bottomText.setValue(projDesc);
			bottomText.setSizeFull();
			bottomText.addStyleName(ValoTheme.LABEL_SMALL);
			bottomText.addStyleName(ValoTheme.LABEL_COLORED);
			bottomText.addStyleName("centeredText");
			//bottomText2.setCaption("For example this is cool..");
			}
		
		
	}
	
	private void init()
		{
		this.setWidth("100%");
		this.setHeight("-1");
		//this.setSizeUndefined();
		topLayout.setHeight("-1");
		topLayout.setWidth("100%");
		topLayout.setStyleName(ValoTheme.LAYOUT_CARD);
		midLayout.setHeight("-1");
		midLayout.setWidth("100%");
		midLayout.setStyleName(ValoTheme.LAYOUT_WELL);
		bottomLayout.setHeight("-1");
		bottomLayout.setWidth("100%");
		bottomLayout.setStyleName(ValoTheme.LAYOUT_CARD);
		
		mainLayout.addComponents(topLayout,midLayout,bottomLayout);
		//mainLayout.setExpandRatio(topLayout, 15);
		//mainLayout.setExpandRatio(midLayout, 70);
		//mainLayout.setExpandRatio(bottomLayout, 15);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("-1");
		//mainLayout.setSizeUndefined();
		this.addComponent(mainLayout);
		//topLayout.addComponent(topText);
		midLayout.addComponent(midText);
		bottomLayout.addComponent(bottomText);
		//bottomLayout.addComponent(bottomText2);
		//bottomLayout.setComponentAlignment(bottomText2, Alignment.TOP_CENTER);
		Restrain mainRestrain = new Restrain(mainLayout);
		mainRestrain.setMinWidth("1024px");
		//mainRestrain.setMaxWidth("1920px");
		//mainRestrain.setMinHeight("600px");
		//mainRestrain.setMaxHeight("1080px");
		Restrain topRestrain = new Restrain(topLayout);
		topRestrain.setMinWidth("1024px");
		//topRestrain.setMaxWidth("1920px");
		topRestrain.setMinHeight("85px");
		//topRestrain.setMaxHeight("1080px");
		Restrain midRestrain = new Restrain(midLayout);
		midRestrain.setMinWidth("1024px");
		//midRestrain.setMaxWidth("1920px");
		midRestrain.setMinHeight("600px");
		//midRestrain.setMaxHeight("1080px");
		Restrain bottomRestrain = new Restrain(bottomLayout);
		bottomRestrain.setMinWidth("1024px");
		//bottomRestrain.setMaxWidth("1920px");
		//bottomRestrain.setMinHeight("600px");
		//bottomRestrain.setMaxHeight("1080px");
		Restrain thisRestrain = new Restrain(this);
		thisRestrain.setMinWidth("1024px");
		//thisRestrain.setMaxWidth("1920px");
		thisRestrain.setMinHeight("769px");
		//thisRestrain.setMaxHeight("1080px");
		
		
		//String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		//FileResource resource = new FileResource(new File(basePath+"/WEB-INF/images/ice-01.jpg"));
		//Image iceImage = new Image("cool thing",resource);
		//iceImage.setWidth("320px");
		//iceImage.setHeight("240px");
		//bottomLayout.addComponent(iceImage);
		//bottomLayout.setComponentAlignment(iceImage, Alignment.TOP_CENTER);
		//topText.setSizeUndefined();
		midText.setSizeUndefined();
		//bottomText.setSizeUndefined();
		//bottomText2.setSizeUndefined();
		
		//topLayout.setComponentAlignment(topText, Alignment.MIDDLE_CENTER);
		midLayout.setComponentAlignment(midText, Alignment.MIDDLE_CENTER);
		bottomLayout.setComponentAlignment(bottomText, Alignment.TOP_CENTER);
		bottomLayout.setSpacing(true);
		bottomLayout.setMargin(true);
		initialized=true;
		}
	
	private void setTopMenu()
		{
		HorizontalLayout menuLayout = new HorizontalLayout();
		menuLayout.setSizeUndefined();
		final CssLayout classLayout = new CssLayout();
		CssLayout lecturesLayout = new CssLayout();
		CssLayout professorLayout = new CssLayout();
		CssLayout adminLayout = new CssLayout();
		
		//topText.setCaption("Pervasive Diagsss");
		//topText.addStyleName(ValoTheme.LABEL_LARGE);
		//topText.setImmediate(true);
		
		Label titleLabel = new Label(FontAwesome.CLOUD.getHtml()+" Pervasive Classess "+FontAwesome.CLOUD.getHtml());
		titleLabel.setContentMode(ContentMode.HTML);
		titleLabel.addStyleName(ValoTheme.LABEL_HUGE);
		titleLabel.addStyleName(ValoTheme.LABEL_LIGHT);
		titleLabel.setSizeUndefined();
		
		final Button classLabel = new Button(FontAwesome.BELL.getHtml()+" Classroom");
		classLabel.setCaptionAsHtml(true);
		classLabel.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		classLabel.addStyleName(ValoTheme.BUTTON_QUIET);
		classLabel.addStyleName("animated");
		classLabel.addStyleName("lightSpeedIn");
		classLabel.addStyleName("delay05");
		classLabel.addClickListener(new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 8350921344462546959L;

			@Override
			public void buttonClick(ClickEvent event) {
				midLayout.removeAllComponents();
				ClassroomView view =new ClassroomView();
				midLayout.addComponent(view);
				midLayout.setComponentAlignment(view, Alignment.TOP_CENTER);
			}
		});
		
		Button lectLabel = new Button(FontAwesome.BOOK.getHtml()+" Lectures");
		lectLabel.setCaptionAsHtml(true);
		lectLabel.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		lectLabel.addStyleName(ValoTheme.BUTTON_QUIET);
		lectLabel.addStyleName("animated");
		lectLabel.addStyleName("lightSpeedIn");
		lectLabel.addStyleName("delay06");
		lectLabel.addClickListener(new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 9026666916068650906L;

			@Override
			public void buttonClick(ClickEvent event) {
				midLayout.removeAllComponents();
				LecturesView view = new LecturesView();
				midLayout.addComponent(view);
				midLayout.setComponentAlignment(view, Alignment.TOP_CENTER);
				
			}
		});
	//	lectLabel.setContentMode(ContentMode.HTML);
	//	lectLabel.addStyleName(ValoTheme.LABEL_HUGE);
	//	lectLabel.addStyleName(ValoTheme.LABEL_COLORED);
		
		Button profLabel = new Button(FontAwesome.MALE.getHtml()+" Professor ");
		profLabel.setCaptionAsHtml(true);
		profLabel.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		profLabel.addStyleName(ValoTheme.BUTTON_QUIET);
		profLabel.addStyleName("animated");
		profLabel.addStyleName("lightSpeedIn");
		profLabel.addStyleName("delay07");
		profLabel.addClickListener(new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -1533262185857460147L;

			@Override
			public void buttonClick(ClickEvent event) {
				midLayout.removeAllComponents();
				Label wip = new Label(FontAwesome.COFFEE.getHtml()+" Work in progress..");
				wip.setContentMode(ContentMode.HTML);
				wip.setSizeUndefined();
				wip.addStyleName(ValoTheme.LABEL_BOLD);
				wip.addStyleName(ValoTheme.LABEL_COLORED);
				wip.addStyleName(ValoTheme.LABEL_HUGE);
				wip.addStyleName(ValoTheme.LABEL_H1);
				midLayout.addComponent(wip);
				midLayout.setComponentAlignment(wip, Alignment.MIDDLE_CENTER);
				
			}
		});
		
	//	profLabel.setContentMode(ContentMode.HTML);
	//	profLabel.addStyleName(ValoTheme.LABEL_HUGE);
	//	profLabel.addStyleName(ValoTheme.LABEL_COLORED);
		
		Button adminLabel = new Button(FontAwesome.GEARS.getHtml()+" Administration ");
		adminLabel.setCaptionAsHtml(true);
		adminLabel.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		adminLabel.addStyleName(ValoTheme.BUTTON_QUIET);
		adminLabel.addStyleName("red");
		adminLabel.addStyleName("animated");
		adminLabel.addStyleName("lightSpeedIn");
		adminLabel.addStyleName("delay08");
		adminLabel.addClickListener(new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 5175622117591467848L;

			@Override
			public void buttonClick(ClickEvent event) {
				midLayout.removeAllComponents();
				Label wip = new Label(FontAwesome.COFFEE.getHtml()+" Work in progress..");
				wip.setContentMode(ContentMode.HTML);
				wip.setSizeUndefined();
				wip.addStyleName(ValoTheme.LABEL_BOLD);
				wip.addStyleName(ValoTheme.LABEL_COLORED);
				wip.addStyleName(ValoTheme.LABEL_HUGE);
				wip.addStyleName(ValoTheme.LABEL_H1);
				midLayout.addComponent(wip);
				midLayout.setComponentAlignment(wip, Alignment.MIDDLE_CENTER);
			}
		});
		
	//	adminLabel.setContentMode(ContentMode.HTML);
	//	adminLabel.addStyleName(ValoTheme.LABEL_HUGE);
	//	adminLabel.addStyleName(ValoTheme.LABEL_COLORED);
		
		classLayout.addComponent(classLabel);
		lecturesLayout.addComponent(lectLabel);
		professorLayout.addComponent(profLabel);
		adminLayout.addComponent(adminLabel);
		menuLayout.addComponents(classLayout,lecturesLayout,professorLayout,adminLayout);
		menuLayout.setSpacing(true);
		topLayout.addComponent(titleLabel);
		topLayout.addComponent(menuLayout);
		topLayout.setComponentAlignment(menuLayout, Alignment.MIDDLE_CENTER);
		topLayout.setComponentAlignment(titleLabel, Alignment.MIDDLE_CENTER);
		
		}

}
