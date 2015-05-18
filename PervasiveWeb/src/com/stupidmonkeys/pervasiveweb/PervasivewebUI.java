package com.stupidmonkeys.pervasiveweb;

import javax.servlet.annotation.WebServlet;

import Views.LoginView;
import Views.WelcomeView;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("pervasiveweb")
@Widgetset("com.stupidmonkeys.pervasiveweb.widgetset.PervasivewebWidgetset")
@Push(PushMode.MANUAL)
public class PervasivewebUI extends UI {
	private Navigator navi;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = PervasivewebUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
	/*	final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		Button button = new Button("Click Me");
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				layout.addComponent(new Label("Thank you for clicking"));
			}
		});
		layout.addComponent(button);*/
		navi = new Navigator(this,this);
		navi.addView("", (Class<? extends View>) this.getClass());
		navi.addView("Login", new LoginView(navi));
		navi.addView("Welcome", new WelcomeView(navi));
		navi.navigateTo("Login");
		
	}

}