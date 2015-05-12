package Views;

import java.io.Serializable;

import org.apache.catalina.core.ApplicationContext;
import org.vaadin.jouni.animator.Animator;
import org.vaadin.jouni.animator.client.CssAnimation;
import org.vaadin.jouni.dom.client.Css;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
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
		welcomeLabel.setSizeUndefined();
		loginBtn = new Button("Login");
		registerBtn = new Button("Register");
		VerticalLayout registration = new VerticalLayout();
		registration.setSizeUndefined();
		registration.setSpacing(true);
		this.addComponent(registration);
		this.setComponentAlignment(registration, Alignment.MIDDLE_CENTER);
		
		registration.addComponents(welcomeLabel,loginBtn,registerBtn);
		registration.setComponentAlignment(welcomeLabel, Alignment.MIDDLE_CENTER);
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
