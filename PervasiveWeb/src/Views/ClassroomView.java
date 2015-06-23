package Views;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import org.json.JSONArray;
import org.json.JSONObject;
import org.parse4j.ParseCloud;
import org.parse4j.ParseException;
import org.parse4j.callback.FunctionCallback;

import com.stupidmonkeys.pervasiveweb.PervasivewebUI;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.LegendItemClickEvent;
import com.vaadin.addon.charts.LegendItemClickListener;
import com.vaadin.addon.charts.model.Background;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotBand;
import com.vaadin.addon.charts.model.PlotOptionsGauge;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.TickPosition;
import com.vaadin.addon.charts.model.Title;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.GradientColor;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
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
	private Chart distChart;
	private Chart noiseChart;
	private Chart noiseGauge;
	private ListSeries noiseSeries;
	private String genString1 = "Averages of classroom: ";
	private String genString2 = "Mean percentage of room occupation: ";
	private String genString3 = "Mean temperature of room: ";
	private String genString4 = "Mean number of lessons per day: ";
	private String actString1 = "Actual stats for classroom: ";
	private String actString2 = "Total seats available in classroom: ";
	private String actString3 = "Actual room occupation: ";
	private String actString4 = "Actual room temperature: ";
	private String actString5 = "Actual lesson: ";
	private String genValue2= new String();
	private String genValue3=new String();
	private String genValue4=new String();
	private String actValue2=new String();
	private String actValue3=new String();
	private String actValue4=new String();
	private String actValue5=new String();
	
	private Label genLabel1= new Label();
	private Label genLabel2 = new Label();
	private Label genLabel3 = new Label();
	private Label genLabel4 = new Label();
	private Label actLabel1 = new Label();
	private Label actLabel2 = new Label();
	private Label actLabel3 = new Label();
	private Label actLabel4 = new Label();
	private Label actLabel5 = new Label();
	
	private PervasivewebUI thisUI;
	
	public ClassroomView()
		{
		 thisUI=(PervasivewebUI) UI.getCurrent();
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
		 comboLabel.setReadOnly(true);
		 thisUI.access(new Runnable(){

			@Override
			public void run() {
				new Timer().schedule(new TimerTask(){

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
			       		thisUI.push();
					}}, 1000);
				
			}});
		 
		/* new Timer().schedule( 
			        new TimerTask() {
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
			);*/
		 
		
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
		 populateComboBox();
		 
		 //combo.addItem(new String("A2"));
		 //combo.addItem(new String("B2"));
		 //combo.addItem(new String("A3"));
		 
		 combo.addValueChangeListener(new Property.ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -688781700373068852L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				final String classValue = combo.getValue().toString();
				getActualStudentNumber(classValue);
				getClassroomSeats(classValue);
				getActualLecture(classValue);
				getNoiseForRoom(classValue);
				genValue2="43%";
				genValue3="28°";
				genValue4="6";
				actValue4="value_here (4)";
				setDistanceChart(100,78,20);
				
				
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
				actLabel5.addStyleName(ValoTheme.LABEL_LARGE);
				actLabel2.setValue(actString2+actValue2);
				actLabel4.setValue(actString4+actValue4);
				actLabel5.setValue(actString5+actValue5);
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
				actLabel5.addStyleName("animated");
				actLabel5.addStyleName("delay13");
				actLabel5.addStyleName("fadeInRight");
				vert1.addComponents(genLabel1,genLabel2,genLabel3,genLabel4,actLabel1,actLabel2,actLabel3,actLabel4,actLabel5);
				setDistanceChart(100,78,20);
				setNoiseChart();
				setNoiseGauge();
				hori2.addComponents(distChart,vert1,noiseChart,noiseGauge);
				
				
			}
		});
		 this.setComponentAlignment(hori1, Alignment.TOP_CENTER); 
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
	
	private void setDistanceChart(double near,double intermediate,double far)
		{
		 distChart = new Chart(ChartType.COLUMN);
		 Configuration conf = distChart.getConfiguration();
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
	        
	        distChart.addLegendItemClickListener(new LegendItemClickListener() {
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

	        distChart.drawChart(conf);
		}
	private void setNoiseChart()
	{
	noiseChart = new Chart();
	noiseChart.setHeight("450px");
	noiseChart.setWidth("100%");
	noiseSeries = new ListSeries();
    final Configuration configuration = noiseChart.getConfiguration();
    configuration.setTitle("Noise Levels");

    configuration.getChart().setType(ChartType.SPLINE);

    /*final ListSeries series = new ListSeries(29.9, 71.5, 106.4, 129.2,
            144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4);*/
    configuration.setSeries(noiseSeries);

    noiseChart.drawChart(configuration);

    
	}
	
	private void setNoiseGauge() {
        noiseGauge = new Chart();
        noiseGauge.setWidth("500px");
        noiseGauge.setHeight("450px");

        final Configuration configuration = noiseGauge.getConfiguration();
        configuration.getChart().setType(ChartType.GAUGE);
        configuration.getChart().setPlotBackgroundColor(null);
        configuration.getChart().setPlotBackgroundImage(null);
        configuration.getChart().setPlotBorderWidth(0);
        configuration.getChart().setPlotShadow(false);
        configuration.setTitle("Actual Noise (Demo)");

        GradientColor gradient1 = GradientColor.createLinear(0, 0, 0, 1);
        gradient1.addColorStop(0, new SolidColor("#FFF"));
        gradient1.addColorStop(1, new SolidColor("#333"));

        GradientColor gradient2 = GradientColor.createLinear(0, 0, 0, 1);
        gradient2.addColorStop(0, new SolidColor("#333"));
        gradient2.addColorStop(1, new SolidColor("#FFF"));

        Background[] background = new Background[3];
        background[0] = new Background();
        background[0].setBackgroundColor(gradient1);
        background[0].setBorderWidth(0);
        background[0].setOuterRadius("109%");

        background[1] = new Background();
        background[1].setBackgroundColor(gradient2);
        background[1].setBorderWidth(1);
        background[1].setOuterRadius("107%");

        background[2] = new Background();
        background[2].setBackgroundColor(new SolidColor("#DDD"));
        background[2].setBorderWidth(0);
        background[2].setInnerRadius("103%");
        background[2].setOuterRadius("105%");

        configuration.getPane().setStartAngle(-150);
        configuration.getPane().setEndAngle(150);
        configuration.getPane().setBackground(background);

        YAxis yAxis = configuration.getyAxis();
        yAxis.setTitle(new Title("dB"));
        yAxis.setMin(0);
        yAxis.setMax(150);
        yAxis.setMinorTickInterval("auto");
        yAxis.setMinorTickWidth(1);
        yAxis.setMinorTickLength(10);
        yAxis.setMinorTickPosition(TickPosition.INSIDE);
        yAxis.setMinorTickColor(new SolidColor("#666"));
        yAxis.setGridLineWidth(0);
        yAxis.setTickPixelInterval(30);
        yAxis.setTickWidth(2);
        yAxis.setTickPosition(TickPosition.INSIDE);
        yAxis.setTickLength(10);
        yAxis.setTickColor(new SolidColor("#666"));

        yAxis.getLabels().setStep(2);
        yAxis.getLabels().setRotationPerpendicular();

        PlotBand[] plotBands = new PlotBand[3];
        plotBands[0] = new PlotBand(0, 50, new SolidColor("#55BF3B"));
        plotBands[1] = new PlotBand(50, 100, new SolidColor("#DDDF0D"));
        plotBands[2] = new PlotBand(100, 150, new SolidColor("#DF5353"));
        yAxis.setPlotBands(plotBands);

        final ListSeries series = new ListSeries("db level", 80);
        PlotOptionsGauge plotOptions = new PlotOptionsGauge();
        plotOptions.getTooltip().setValueSuffix(" dB");
        series.setPlotOptions(plotOptions);
        configuration.setSeries(series);
        final Random r = new Random(0);
        runWhileAttached(noiseGauge, new Runnable() {
            Random r = new Random(0);

            @Override
            public void run() {
                Integer oldValue = series.getData()[0].intValue();
                Integer newValue = (int) (oldValue + (r.nextDouble() - 0.5) * 20.0);
                series.updatePoint(0, newValue);
            }
        }, 1000, 1000);
       

        noiseGauge.drawChart(configuration);
     
    }
	public static void runWhileAttached(final Component component,
            final Runnable task, final int interval, final int initialPause) {
        // Until reliable push available in our demo servers
        UI.getCurrent().setPollInterval(interval);

        final Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(initialPause);
                    while (component.getUI() != null) {
                        Future<Void> future = component.getUI().access(task);
                        future.get();
                        Thread.sleep(interval);
                    }
                } catch (InterruptedException e) {
                } catch (ExecutionException e) {
                    Logger.getLogger(this.getClass().getName())
                            .log(Level.WARNING,
                                    "Stopping repeating command due to an exception",
                                    e);
                } catch (com.vaadin.ui.UIDetachedException e) {
                } catch (Exception e) {
                    Logger.getLogger(this.getClass().getName())
                            .log(Level.WARNING,
                                    "Unexpected exception while running scheduled update",
                                    e);
                }
                Logger.getLogger(this.getClass().getName()).log(Level.INFO,
                        "Thread stopped");
            };
        };
        thread.start();
    }

	
	
	private void getActualStudentNumber(final String classLabel)
		{
		thisUI.access(new Runnable(){

			@Override
			public void run() {
				actValue3="Waiting value..";
				actLabel3.setValue(actString3+actValue3);
				thisUI.push();
				
			}});

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("getClassroom", classLabel);
		ParseCloud.callFunctionInBackground("getStudentsNumber", map, new FunctionCallback<Integer>() {
			
			@Override
			public void done(final Integer result, ParseException parseException) {
				if (parseException == null) {
					thisUI.access(new Runnable(){

						@Override
						public void run() {
							actValue3=result.toString();
							actLabel3.setValue(actString3+actValue3);
							System.out.println("Number of students from classroom: " + classLabel);
							System.out.println("Number of students from utils: " + result);
							thisUI.push();
							
						}});

				} else {
					System.err.println("Error in ClassroomView.getActualStudentNumber() "+parseException.getMessage());
				}
			}
		});
		}
	
	private void getClassroomSeats(final String classLabel)
		{
		thisUI.access(new Runnable(){

			@Override
			public void run() {
				actValue2="Waiting value..";
				actLabel2.setValue(actString2+actValue2);
				thisUI.push();
				
			}});
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("getSeats", classLabel);
			ParseCloud.callFunctionInBackground("getSeatsNumber", map, new FunctionCallback<Integer>(){

				@Override
				public void done(final Integer result, ParseException parseException) {
					if(parseException == null)
						{
						thisUI.access(new Runnable(){

							@Override
							public void run() {
								actValue2=result.toString();
								actLabel2.setValue(actString2+actValue2);
								thisUI.push();
								
							}});
							System.out.println("Dio bubu, number of seats= "+result.toString());
						}else
							{
							System.err.println("Error in ClassroomView.getClassroomSeats "+parseException.getMessage());
							}
					
				}});
		}
	
	private void getActualLecture(String classLabel)
		{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("getLabel", classLabel);
		thisUI.access(new Runnable(){

			@Override
			public void run() {
				actValue5="Waiting value..";
				actLabel5.setValue(actString5+actValue5);
				thisUI.push();
				
			}});
		ParseCloud.callFunctionInBackground("getCurrentLesson", map, new FunctionCallback<String>(){

			@Override
			public void done(final String result, ParseException parseException) {
				if(parseException==null){
					thisUI.access(new Runnable(){

						@Override
						public void run() {
							actValue5=result.toString();
							actLabel5.setValue(actString5+actValue5);
							thisUI.push();
							
						}});
					System.out.println("fucking lecture= "+result.toString());
				}else{
					System.err.println(parseException.getMessage());
				}
				
			}});
		}
	
	
	private void getNoiseForRoom(final String classLabel)
		{
		final Timer timer = new Timer();
		TimerTask task = new TimerTask(){

			@Override
			public void run() {
				final LinkedList<Long> noiseList = thisUI.getNoiseForRoom(classLabel);
				if(noiseList!=null)
					{
					thisUI.access(new Runnable(){

						@Override
						public void run() {
							Iterator<Long> iter =noiseList.iterator();
							while(iter.hasNext())
								{
								long point=iter.next().longValue();
								noiseSeries.addData(point);
								thisUI.push();
								try {
									Thread.sleep(500);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								}
							
						}});
					
					timer.cancel();
					}
			}};
			timer.scheduleAtFixedRate(task, 0, 500);
		}
	/*private void getNoiseForRoom(String classLabel)
		{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("room", classLabel);
		ParseCloud.callFunctionInBackground("getNoiseForRoomRecursive", map, new FunctionCallback<JSONArray>(){

			@Override
			public void done(final JSONArray result, ParseException parseException) {
				if(parseException==null)
					{
					thisUI.access(new Runnable(){

						@Override
						public void run() {
							int length=result.length();
							for(int i=0;i<length;i++)
								{
								JSONObject obj = result.getJSONObject(i);
								long noise = obj.getLong("Decibel");
								noiseSeries.addData(noise);
								UI.getCurrent().push();
								 try {
									Thread.sleep(500);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								}
							
						}});
					
					}else
						{
						System.err.println(parseException.getMessage());
						}
				
			}});
		}*/
	
	private void populateComboBox()
		{
		 System.out.println("Called ClassroomView.populateComboBox()");
		 final Timer timer = new Timer();
		 TimerTask task = new TimerTask(){

			@Override
			public void run() {
				if(thisUI.isClassListRetrieved())
					{
					BeanItemContainer<String> container = new BeanItemContainer<String>(String.class,thisUI.getClassroomList());
					combo.setContainerDataSource(container);
					timer.cancel();
					}else
					{
					System.out.println("List wasn't there or is being updated, rescheduling populateComboBox()");
					}
			}};
			timer.scheduleAtFixedRate(task, 0, 1500);
		}

			
		
}
