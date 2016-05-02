package Views;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class StudentView extends VerticalLayout{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2958231288957411919L;
	private Label titleLabel= new Label();
	
	public StudentView()
		{
		 titleLabel.setCaption("Student page");
		 
		 titleLabel.addStyleName(ValoTheme.LABEL_BOLD);
		 titleLabel.addStyleName(ValoTheme.LABEL_LARGE);
		 titleLabel.setSizeUndefined();
		 titleLabel.addStyleName("animated");
		 titleLabel.addStyleName("flipInX");
		 titleLabel.addStyleName("delay05");
		 
		 this.addComponents(titleLabel);
			
		}

}
