package Views;

import java.util.HashMap;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.base.elements.Trendline;
import org.dussan.vaadin.dcharts.base.elements.XYaxis;
import org.dussan.vaadin.dcharts.metadata.XYaxes;
import org.dussan.vaadin.dcharts.metadata.locations.TooltipLocations;
import org.dussan.vaadin.dcharts.metadata.renderers.AxisRenderers;
import org.dussan.vaadin.dcharts.options.Axes;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.renderers.tick.AxisTickRenderer;
import org.parse4j.ParseCloud;
import org.parse4j.ParseException;
import org.parse4j.callback.FunctionCallback;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.LegendItemClickEvent;
import com.vaadin.addon.charts.LegendItemClickListener;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ClassroomView extends VerticalLayout{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1107553222948642399L;
	private Label desc = new Label("Here you will find informations related to the classrooms");
	private Label subDesc = new Label("Select from the dropdown menu the classroom you want to inquiry");
	private Label comboLabel = new Label("Room:");
	private HorizontalLayout hori1 = new HorizontalLayout();
	private HorizontalLayout hori2 = new HorizontalLayout();
	private VerticalLayout vert1 = new VerticalLayout();
	private ComboBox combo = new ComboBox();
	private VerticalLayout thisLayout = this;
	private Chart chart;
	private DCharts dchart;
	
	private String genString1 = "Averages of classroom: ";
	private String genString2 = "Mean percentage of room occupation: ";
	private String genString3 = "Mean temperature of room: ";
	private String genString4 = "Mean number of lessons per day: ";
	private String actString1 = "Actual stats for classroom: ";
	private String actString2 = "Actual room occupation: ";
	private String actString3 = "Actual room temperature: ";
	private String actString4 = "Actual lesson: ";
	String genValue2= new String();
	String genValue3=new String();
	String genValue4=new String();
	String actValue2=new String();
	String actValue3=new String();
	String actValue4=new String();
	private Label genLabel1= new Label();
	private Label genLabel2 = new Label();
	private Label genLabel3 = new Label();
	private Label genLabel4 = new Label();
	private Label actLabel1 = new Label();
	private Label actLabel2 = new Label();
	private Label actLabel3 = new Label();
	private Label actLabel4 = new Label();
	
	public ClassroomView()
		{
		 desc.addStyleName(ValoTheme.LABEL_BOLD);
		 desc.addStyleName(ValoTheme.LABEL_LARGE);
		 desc.setSizeUndefined();
		 subDesc.addStyleName(ValoTheme.LABEL_BOLD);
		 subDesc.addStyleName(ValoTheme.LABEL_LIGHT);
		 subDesc.setSizeUndefined();
		 comboLabel.setSizeUndefined();
		 comboLabel.addStyleName(ValoTheme.LABEL_COLORED);
		 comboLabel.addStyleName(ValoTheme.LABEL_H3);
		 desc.addStyleName("animated");
		 desc.addStyleName("bounceInUp");
		 desc.addStyleName("delay05");
		 subDesc.addStyleName("animated");
		 subDesc.addStyleName("bounceInUp");
		 subDesc.addStyleName("delay06");
		 combo.setVisible(false);
		 comboLabel.setVisible(false);
		 new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            comboLabel.setVisible(true);
			            combo.setVisible(true);
			            comboLabel.addStyleName("animated");
			       		comboLabel.addStyleName("rubberBand");
			       		comboLabel.addStyleName("delay07");
			       		combo.addStyleName("animated");
			       		combo.addStyleName("rubberBand");
			       		combo.addStyleName("delay07");
			            combo.markAsDirty();
			            comboLabel.markAsDirty();
			            UI.getCurrent().push();
			            }
			        }, 
			        1000 
			);
		 
		
		 this.setWidth("100%");
		 this.addComponent(desc);
		 this.addComponent(subDesc);
		 this.addComponent(hori1);
		 hori1.setSizeUndefined();
		 hori1.setSpacing(true);
		 hori1.addComponents(comboLabel,combo);
		 hori1.setComponentAlignment(comboLabel, Alignment.MIDDLE_CENTER);
		 hori1.setComponentAlignment(combo, Alignment.MIDDLE_CENTER);
		 //this.addComponent(combo);
		 this.setComponentAlignment(desc, Alignment.TOP_CENTER);
		 this.setComponentAlignment(subDesc, Alignment.TOP_CENTER);
		 combo.addStyleName(ValoTheme.COMBOBOX_ALIGN_CENTER);
		 combo.addStyleName(ValoTheme.COMBOBOX_SMALL);
		 combo.setSizeUndefined();
		 
		 combo.addItem(new String("A2"));
		 combo.addItem(new String("B2"));
		 combo.addItem(new String("A3"));
		 
		 combo.addValueChangeListener(new Property.ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -688781700373068852L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				String classValue = combo.getValue().toString();
				if(classValue.equals("A2"))
					{
					getActualStudentNumber(classValue);
					genValue2="43%";
					genValue3="28°";
					genValue4="6";
					actValue3=actValue4="value_here";
					setChart(100,78,20);
					}else if(classValue.equals("A3"))
						{
						getActualStudentNumber(classValue);
						genValue2="60%";
						genValue3="31°";
						genValue4="4";
						actValue3=actValue4="value_here";
						setChart(80,20,20);
						
						}else if (classValue.equals("B2"))
							{
							getActualStudentNumber(classValue);
							genValue2="84%";
							genValue3="22°";
							genValue4="8";
							actValue3=actValue4="value_here";
							setChart(50,80,23);
							}
				
				vert1.removeAllComponents();
				hori2.removeAllComponents();
				hori2.removeStyleName("fadeInLeft");
				genLabel1.setValue(genString1+classValue);
				genLabel1.addStyleName(ValoTheme.LABEL_COLORED);
				genLabel1.addStyleName(ValoTheme.LABEL_BOLD);
				genLabel1.addStyleName(ValoTheme.LABEL_LARGE);
				genLabel2.addStyleName(ValoTheme.LABEL_LARGE);
				genLabel3.addStyleName(ValoTheme.LABEL_LARGE);
				genLabel4.addStyleName(ValoTheme.LABEL_LARGE);
				genLabel2.setValue(genString2+genValue2);
				genLabel3.setValue(genString3+genValue3);
				genLabel4.setValue(genString4+genValue4);
				actLabel1.setValue(actString1+classValue);
				actLabel1.addStyleName(ValoTheme.LABEL_COLORED);
				actLabel1.addStyleName(ValoTheme.LABEL_BOLD);
				actLabel1.addStyleName(ValoTheme.LABEL_LARGE);
				actLabel2.addStyleName(ValoTheme.LABEL_LARGE);
				actLabel3.addStyleName(ValoTheme.LABEL_LARGE);
				actLabel4.addStyleName(ValoTheme.LABEL_LARGE);
				//actLabel2.setValue(actString2+actValue2);
				actLabel3.setValue(actString3+actValue3);
				actLabel4.setValue(actString4+actValue4);
				hori2.addStyleName("animated");
				hori2.addStyleName("fadeInLeft");
				genLabel1.addStyleName("animated");
				genLabel1.addStyleName("fadeInRight");
				genLabel1.addStyleName("delay05");
				genLabel2.addStyleName("animated");
				genLabel2.addStyleName("fadeInRight");
				genLabel2.addStyleName("delay06");
				genLabel3.addStyleName("animated");
				genLabel3.addStyleName("fadeInRight");
				genLabel3.addStyleName("delay07");
				genLabel4.addStyleName("animated");
				genLabel4.addStyleName("fadeInRight");
				genLabel4.addStyleName("delay08");
				actLabel1.addStyleName("animated");
				actLabel1.addStyleName("delay09");
				actLabel1.addStyleName("fadeInRight");
				actLabel2.addStyleName("animated");
				actLabel2.addStyleName("delay1");
				actLabel2.addStyleName("fadeInRight");
				actLabel3.addStyleName("animated");
				actLabel3.addStyleName("delay11");
				actLabel3.addStyleName("fadeInRight");
				actLabel4.addStyleName("animated");
				actLabel4.addStyleName("delay12");
				actLabel4.addStyleName("fadeInRight");
				vert1.addComponents(genLabel1,genLabel2,genLabel3,genLabel4,actLabel1,actLabel2,actLabel3,actLabel4);
				setChart(100,78,20);
				hori2.addComponents(chart,vert1);
				//hori2.markAsDirty();
				//UI.getCurrent().push();
				
				
			}
		});
		 this.setComponentAlignment(hori1, Alignment.TOP_CENTER);
		 
		 Button test = new Button("test");
		 test.addClickListener(new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -3617252521939255485L;

			@Override
			public void buttonClick(ClickEvent event) {
				thisLayout.addComponent(new Label("TESTESTESTESTESTESTESTESTE"));
				
			}
		});
		 this.addComponent(test);
		 
		 this.addComponent(hori2);
		 this.setComponentAlignment(hori1, Alignment.TOP_CENTER);
		 this.setComponentAlignment(hori2, Alignment.TOP_CENTER);
		 
		 vert1.setSpacing(true);
		 
		 
		 //setChart();
		 //setDcharts();
		 hori2.setSizeUndefined();
		 hori2.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		 hori2.setSpacing(true);
		 
		}
	
	private void setChart(double near,double intermediate,double far)
		{
		 chart = new Chart(ChartType.COLUMN);
		 Configuration conf = chart.getConfiguration();
		 conf.setTitle("Seats capacity occupation");
		 conf.setSubTitle("(Occupied seats at near, medium and far distance from professor's desk)");
		 
		 Tooltip tooltip = new Tooltip();
	        tooltip.setValueDecimals(1);
	        tooltip.setPointFormat("{series.name}: {point.percentage}%");
	        conf.setTooltip(tooltip);
	        
	        PlotOptionsPie plotOptions = new PlotOptionsPie();
	        plotOptions.setAllowPointSelect(true);
	        plotOptions.setCursor(Cursor.POINTER);
	        plotOptions.setShowInLegend(true);
	        Labels dataLabels = new Labels();
	        dataLabels.setEnabled(true);
	        conf.setPlotOptions(plotOptions);

	        DataSeries series = new DataSeries();
	        series.add(new DataSeriesItem("Near", near));
	        series.add(new DataSeriesItem("Intermediate", intermediate));
	        DataSeriesItem chrome = new DataSeriesItem("Far", far);
	        chrome.setSliced(true);
	        chrome.setSelected(true);
	        series.add(chrome);
	        //series.add(new DataSeriesItem("Safari", 8.5));
	        //series.add(new DataSeriesItem("Opera", 6.2));
	        //series.add(new DataSeriesItem("Others", 0.7));
	        conf.setSeries(series);
	        
	        chart.addLegendItemClickListener(new LegendItemClickListener() {
	            /**
				 * 
				 */
				private static final long serialVersionUID = -1373032233192618190L;

				@Override
	            public void onClick(LegendItemClickEvent event) {
	                Notification.show("Legend item click"
	                        + " : "
	                        + event.getSeriesItemIndex()
	                        + " : "
	                        + ((DataSeries) event.getSeries())
	                                .get(event.getSeriesItemIndex()).getName());
	            }
	        });

	        chart.drawChart(conf);
		}
	private void setDcharts()
		{
		org.dussan.vaadin.dcharts.data.DataSeries dataSeries = new org.dussan.vaadin.dcharts.data.DataSeries();
		dataSeries.newSeries();
		dataSeries.add("2","boh");
		dataSeries.add("3","mmmah");
		dataSeries.add("33","Coddio");
		
		Highlighter highlighter = new Highlighter();
		
		SeriesDefaults seriesDefaults = new SeriesDefaults()
		.setTrendline(
			new Trendline()
				.setShow(true));

	Axes axes = new Axes()
		.addAxis(
			new XYaxis()
				.setRenderer(AxisRenderers.DATE)
				.setTickOptions(
					new AxisTickRenderer()
						.setFormatString("%#d/%#m/%y")));
						//.setNumberTicks(8))   ///cambiato qui
		axes.addAxis(
			new XYaxis(XYaxes.Y)
				.setTickOptions(
					new AxisTickRenderer()
						.setFormatString("%d €")));

	
	highlighter.setShow(true);
	highlighter.setSizeAdjust(10);
	highlighter.setTooltipLocation(TooltipLocations.NORTH);
	highlighter.setUseAxesFormatters(true);
	highlighter.setShowTooltip(true);
	highlighter.setTooltipAlwaysVisible(true);
	
	

Options options = new Options()
	.addOption(seriesDefaults)
	.addOption(axes)
	.addOption(highlighter)
	.setAnimate(true);
	dchart= new DCharts();
	dchart.setOptions(options).setDataSeries(dataSeries).show();
		}
	
	private void getActualStudentNumber(final String classLabel)
		{
		actValue2="Waiting value..";
		actLabel2.setValue(actString2+actValue2);
		actLabel2.markAsDirty();
		UI.getCurrent().push();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("getClassroom", classLabel);
		ParseCloud.callFunctionInBackground("getStudentsNumber", map, new FunctionCallback<Integer>() {
			
			@Override
			public void done(Integer result, ParseException parseException) {
				if (parseException == null) {
					actValue2=result.toString();
					actLabel2.setValue(actString2+actValue2);
					System.out.println("Number of students from classroom: " + classLabel);
					System.out.println("Number of students from utils: " + result);
					actLabel2.markAsDirty();
					hori2.markAsDirty();
					UI.getCurrent().push();
				} else {
					System.err.println("dioboia"+parseException.getMessage());
				}
			}
		});
		}

}
