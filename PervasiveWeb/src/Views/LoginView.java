package Views;

import java.io.Serializable;

import org.apache.catalina.core.ApplicationContext;
//import org.vaadin.jouni.animator.Animator;
//import org.vaadin.jouni.animator.client.CssAnimation;
//import org.vaadin.jouni.dom.client.Css;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class LoginView extends VerticalLayout implements View, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7505612811002464254L;
	private Navigator navi;
	private Button registerBtn;
	private Button loginBtn;
	private Label welcomeLabel;
	private boolean initialized=false;
	private Window subWindow;
	
	public LoginView(Navigator navi)
		{
		this.navi=navi;
		}
	
	@Override
	public void enter(ViewChangeEvent event) {
		if(!initialized)init();
		
	}
	
	private void init()
		{
		this.setSizeFull();
		this.setSpacing(true);
		welcomeLabel=new Label("Welcome to Pervasive Classes");
		welcomeLabel.addStyleName(ValoTheme.LABEL_BOLD);
		welcomeLabel.addStyleName(ValoTheme.LABEL_COLORED);
		welcomeLabel.addStyleName(ValoTheme.LABEL_H1);
		welcomeLabel.addStyleName(ValoTheme.LABEL_HUGE);
		welcomeLabel.addStyleName("titleFont");
		welcomeLabel.setSizeUndefined();
		welcomeLabel.addStyleName("animated");
		welcomeLabel.addStyleName("tada");
		welcomeLabel.addStyleName("delay025");
		this.addComponent(welcomeLabel);
		this.setComponentAlignment(welcomeLabel, Alignment.TOP_CENTER);
		VerticalLayout infoLayout = new VerticalLayout();
		Label info1 = new Label(FontAwesome.CIRCLE_O_NOTCH.getHtml()+" Cool feature");
		Label info2 = new Label(FontAwesome.CIRCLE_O_NOTCH.getHtml()+" Cooler feature");
		Label info3 = new Label(FontAwesome.CIRCLE_O_NOTCH.getHtml()+" Very cool feature");
		//info1.addStyleName(ValoTheme.LABEL_H2);
		//info2.addStyleName(ValoTheme.LABEL_H2);
		//info3.addStyleName(ValoTheme.LABEL_H2);
		
		info1.setContentMode(ContentMode.HTML);
		info2.setContentMode(ContentMode.HTML);
		info3.setContentMode(ContentMode.HTML);
		info1.addStyleName("animated");
		info1.addStyleName("fadeInLeft");
		info2.addStyleName("animated");
		info2.addStyleName("fadeInRight");
		info3.addStyleName("animated");
		info3.addStyleName("fadeInUp");
		infoLayout.addComponents(info1,info2,info3);
		infoLayout.setComponentAlignment(info1, Alignment.MIDDLE_CENTER);
		infoLayout.setComponentAlignment(info2, Alignment.MIDDLE_CENTER);
		infoLayout.setComponentAlignment(info3, Alignment.MIDDLE_CENTER);
		infoLayout.setSizeUndefined();
		//Animator.animate(info1, new Css().translateX("300px")).sendEndEvent();
		//Animator.animate(info1, new Css().translateX("-300px")).delay(500);
		loginBtn = new Button(FontAwesome.SIGN_IN.getHtml()+"     Login");
		loginBtn.setCaptionAsHtml(true);
		loginBtn.setImmediate(true);
		registerBtn = new Button(FontAwesome.PENCIL.getHtml()+"     Register");
		registerBtn.setCaptionAsHtml(true);
		loginBtn.setWidth("120px");
		loginBtn.addStyleName("animated");
		loginBtn.addStyleName("flipInY");
		loginBtn.addStyleName("delay05");
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		                loginBtn.removeStyleName("flipInY");
		                loginBtn.addStyleName("infinite");
		                loginBtn.addStyleName("pulse");
		                loginBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		                loginBtn.markAsDirty();
		                UI.getCurrent().push();
		            }
		        }, 
		        2000 
		);
		registerBtn.setWidth("120px");
		registerBtn.addStyleName("animated");
		registerBtn.addStyleName("flipInX");
		registerBtn.addStyleName("delay05");
		VerticalLayout registration = new VerticalLayout();
		registration.setSizeUndefined();
		registration.setSpacing(true);
		this.addComponents(registration,infoLayout);
		this.setComponentAlignment(registration, Alignment.MIDDLE_CENTER);
		this.setComponentAlignment(infoLayout, Alignment.TOP_CENTER);
		this.setExpandRatio(registration, 50);
		this.setExpandRatio(welcomeLabel, 25);
		this.setExpandRatio(infoLayout, 25);
		
		registration.addComponents(loginBtn,registerBtn);
		//registration.setComponentAlignment(welcomeLabel, Alignment.MIDDLE_CENTER);
		registration.setComponentAlignment(loginBtn, Alignment.MIDDLE_CENTER);
		registration.setComponentAlignment(registerBtn, Alignment.MIDDLE_CENTER);
		
		
		loginBtn.addClickListener(new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -1889315841723391267L;

			@Override
			public void buttonClick(ClickEvent event) {
				subWindow = new Window("Please login with your credential");
				subWindow.setContent(loginLayout());
				subWindow.setModal(true);
				subWindow.setResizable(false);
				subWindow.setDraggable(false);
				subWindow.setClosable(true);
				subWindow.setWidth("400px");
				subWindow.setHeight("300px");
				UI.getCurrent().addWindow(subWindow);
				
				
				
			}
		});
		initialized=true;
		}
	
	private VerticalLayout loginLayout()
		{
		 VerticalLayout thisWindow = new VerticalLayout();
		 thisWindow.setSizeFull();
		 thisWindow.setMargin(true);
		 TextField userField = new TextField("Username");
		 TextField passField = new TextField("Password");
		 Button loginBtn = new Button("Login!");
		 userField.setSizeUndefined();
		 passField.setSizeUndefined();
		 loginBtn.setSizeUndefined();
		 loginBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		 thisWindow.addComponents(userField,passField,loginBtn);
		 thisWindow.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		 loginBtn.addClickListener(new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 5880482295950207097L;

			@Override
			public void buttonClick(ClickEvent event) {
				navi.navigateTo("Welcome");
				subWindow.close();
				
			}
		});
		 return thisWindow;
		}

}
