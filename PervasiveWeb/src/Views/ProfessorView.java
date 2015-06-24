package Views;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import org.parse4j.ParseException;
import org.parse4j.ParseFile;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.GetCallback;
import org.parse4j.callback.SaveCallback;

import com.google.gwt.thirdparty.guava.common.base.Charsets;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class ProfessorView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6480557686072383903L;
	
	Button testW= new Button("TestWrite");
	Button testR = new Button("TestRead");
	Label lab = new Label();
	
	public ProfessorView()
		{
			
			testW.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					testWrite();
					
				}
			});
			
			testR.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					testRead();
					
				}
			});
			
			this.addComponents(testW,testR,lab);
		
		}
	private void testRead()
		{
		 ParseQuery<ParseObject> query = ParseQuery.getQuery("new_schedule");
		    query.getInBackground("Ir7Nzd51TL", new GetCallback<ParseObject>(){

				@Override
				public void done(ParseObject t, ParseException parseException) {
					if(parseException==null)
					{
					System.out.println("Sono nel done");
					String result =t.getString("topics");
					String noPar=result.replaceAll("[{}]", "");
					System.out.println(noPar);
					}else System.out.println("orcamadò "+parseException.getMessage());
					
				}});
		
		
		
		}
	
	private void testWrite()
		{
		final HashMap<Integer,String> test = new HashMap<Integer,String>();
		test.put(1, "portanna");
		test.put(2, "bubù");
		test.put(3, "blablabla");
		ParseQuery<ParseObject> query = ParseQuery.getQuery("new_schedule");
	    query.getInBackground("Ir7Nzd51TL", new GetCallback<ParseObject>(){

			@Override
			public void done(ParseObject t, ParseException parseException) {
				if(parseException==null)
				{
				System.out.println("Sono nel done");
				Iterator<Entry<Integer, String>> it = test.entrySet().iterator();
				String result = new String();
				while(it.hasNext())
					{
					Entry temp=it.next();
					String tempString="{"+temp.getKey().toString()+":"+temp.getValue().toString()+"}";
					result=result+tempString+"|";
					it.remove();
					}
				//t.put("topics", test.toString());
				t.put("topics", result);
				t.saveInBackground();
				}else System.out.println("orcamadò "+parseException.getMessage());
				
			}});
		}
	
	/*private void testWrite() throws IOException, ParseException
		{
		HashMap<Integer,String> test = new HashMap<Integer,String>();
		test.put(1, "portanna");
		test.put(2, "bubù");
		test.put(3, "blablabla");
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
	    ObjectOutputStream out = new ObjectOutputStream(byteOut);
	    out.writeObject(test);
	    final byte[] data = byteOut.toByteArray();
	    final String stringData = new String(data,Charsets.UTF_8);
	    //final ParseFile file = new ParseFile("java.Hashmap<Integer,String>",stringData);
	    ParseQuery<ParseObject> query = ParseQuery.getQuery("new_schedule");
	    query.getInBackground("Ir7Nzd51TL", new GetCallback<ParseObject>(){

			@Override
			public void done(ParseObject t, ParseException parseException) {
				if(parseException==null)
				{
				System.out.println("Sono nel done");
				t.put("topics", data);
				t.saveInBackground();
				}else System.out.println("orcamadò "+parseException.getMessage());
				
			}});
	    /*file.save(new SaveCallback(){

			@Override
			public void done(ParseException parseException) {
				if(parseException==null)
					{
					System.out.println("file salvato chissadove");
					ParseQuery<ParseObject> query = ParseQuery.getQuery("new_schedule");
				    query.getInBackground("Ir7Nzd51TL", new GetCallback<ParseObject>(){

						@Override
						public void done(ParseObject t,
								ParseException parseException) {
							if(parseException==null)
								{
								System.out.println("file puttato");
								t.put("topics", file);
								t.saveInBackground();
								}else System.out.println("orcamadò "+parseException.getMessage());
							
						}});
					}else System.out.println("Porcaddiooo "+parseException.getMessage());
				
			}});*/
	    
				 /*ParseQuery<ParseObject> query = ParseQuery.getQuery("new_schedule");
				    query.getInBackground("Ir7Nzd51TL", new GetCallback<ParseObject>(){

						@Override
						public void done(final ParseObject t, ParseException parseException) {
							if(parseException==null)
								{
								 file.saveInBackground(new SaveCallback(){

									@Override
									public void done(ParseException parseException) {
										if(parseException==null)
											{
											t.put("topics", file);
											 t.saveInBackground();
											 UI.getCurrent().access(new Runnable(){

												@Override
												public void run() {
													lab.setValue("map hopefully written to parse");
													
												}});
											}else System.out.println(parseException.getMessage());
										
									}});
								}else System.out.println(parseException.getMessage());
							
						}});
				
	    
	   
		}*/
}
