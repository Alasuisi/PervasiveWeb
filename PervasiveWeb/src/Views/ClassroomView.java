package Views;

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
import org.vaadin.jouni.animator.Animator;

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
	private Animator anim;
	private Chart chart;
	private DCharts dchart;
	
	private String stocaString = "Here some meaningful stuff about room: ";
	private String stoca2String = "Mean percentage of room occupation: ";
	private String stoca3String = "Mean temperature of room: ";
	private String stoca4String = "Mean number of lessons per day:";
	private Label stoca = new Label();
	private Label stoca2 = new Label();
	private Label stoca3 = new Label();
	private Label stoca4 = new Label();
	
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
				String value = combo.getValue().toString();
				if(value.equals("A2"))
					{
					vert1.removeAllComponents();
					hori2.removeAllComponents();
					stoca.setValue(stocaString+value);
					stoca2.setValue(stoca2String+"43%");
					stoca3.setValue(stoca3String+"28° C");
					stoca4.setValue(stoca4String+"6");
					vert1.addComponents(stoca,stoca2,stoca3,stoca4);
					setChart(100,78,20);
					hori2.addComponent(chart);
					hori2.addComponents(vert1);
					}else if(value.equals("A3"))
						{
						vert1.removeAllComponents();
						hori2.removeAllComponents();
						stoca.setValue(stocaString+value);
						stoca2.setValue(stoca2String+"60%");
						stoca3.setValue(stoca3String+"31° C");
						stoca4.setValue(stoca4String+"4");
						vert1.addComponents(stoca,stoca2,stoca3,stoca4);
						setChart(80,20,20);
						hori2.addComponent(chart);
						hori2.addComponents(vert1);
						}else if(value.equals("B2"))
							{
							vert1.removeAllComponents();
							hori2.removeAllComponents();
							stoca.setValue(stocaString+value);
							stoca2.setValue(stoca2String+"84%");
							stoca3.setValue(stoca3String+"22° C");
							stoca4.setValue(stoca4String+"8");
							vert1.addComponents(stoca,stoca2,stoca3,stoca4);
							setChart(50,80,23);
							hori2.addComponent(chart);
							hori2.addComponents(vert1);
							}
				
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
		 stoca.addStyleName(ValoTheme.LABEL_LARGE);
		 stoca2.addStyleName(ValoTheme.LABEL_LARGE);
		 stoca3.addStyleName(ValoTheme.LABEL_LARGE);
		 stoca4.addStyleName(ValoTheme.LABEL_LARGE);
		 
		 
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

}
