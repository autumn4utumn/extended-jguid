//package JGUID.UI;


import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;
import java.io.File;
import java.awt.Image;
import java.awt.*;


public class GuiDUIPopupCode extends JFrame implements HyperlinkListener
{
	JTabbedPane codeTabs = new JTabbedPane();
	JEditorPane ep;
	JEditorPane ep2;
	JEditorPane ep3;
	JEditorPane ep4;
	JEditorPane ep5;
	
	public GuiDUIPopupCode(String title,String page,String type)
	{
		super(title);
		System.out.println("DEBUG: Running GuiDUIPopupCode-constructor");
		
		try
		{
			Image img = getToolkit().createImage(getClass().getResource("images/GuiD_icon.png"));
			this.setIconImage(img);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		
			
		ep = createCodeTab(page,type);
		String vars = parseVars(page);
		ep2 = createCodeTab(vars,type);
		String frameSettings = parseSettings(page);
		ep3 = createCodeTab(frameSettings,type);
		String init = parseInit(page);
		ep4 = createCodeTab(init,type);
		String actions = parseActions(page);
		ep5 = createCodeTab(actions,type);
		
		
		
		codeTabs.add(new JScrollPane(ep2),"Declarations");
		codeTabs.add(new JScrollPane(ep3),"initFrame()");
		codeTabs.add(new JScrollPane(ep4),"initPanelOne()");
		codeTabs.add(new JScrollPane(ep5),"Events");
		codeTabs.add(new JScrollPane(ep),"All Code");
		
		this.setLayout(new GridLayout(1,1));
		this.add(codeTabs);
		
		this.setSize(1024,800); //when not maximized
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.setVisible(true);
		
		
	}
	
	public String parseVars(String allCode)
	{
	
		String startMarkerCode = "//[StartVariables]";
		String endMarkerCode = "//[EndVariables]";
		
		int startMarker = allCode.indexOf(startMarkerCode);
		int endMarker = allCode.indexOf(endMarkerCode);
		int newStartMarker = startMarker+startMarkerCode.length();
		String parseOnly="";
		System.out.println("SM: "+startMarker);
		System.out.println("EM: "+endMarker);
		System.out.println("NM: "+newStartMarker);
		parseOnly = allCode.substring(newStartMarker,endMarker); 
		System.out.println("Settings Only");
		System.out.println(parseOnly);
		
		return parseOnly;
	}
	
	public String parseSettings(String allCode)
	{

		
		String startMarkerCode = "//[StartFrameSettings]";
		String endMarkerCode = "//[EndFrameSettings]";
		
		int startMarker = allCode.indexOf(startMarkerCode);
		int endMarker = allCode.indexOf(endMarkerCode);
		int newStartMarker = startMarker+startMarkerCode.length();
		String parseOnly="";
		System.out.println("SM: "+startMarker);
		System.out.println("EM: "+endMarker);
		System.out.println("NM: "+newStartMarker);
		parseOnly = allCode.substring(newStartMarker,endMarker); 
		System.out.println("Settings Only");
		System.out.println(parseOnly);


		
		return parseOnly;
	}
	
	public String parseInit(String allCode)
	{
		String startMarkerCode = "//[StartIntialization]";
		String endMarkerCode = "//[EndIntialization]";
		
		int startMarker = allCode.indexOf(startMarkerCode);
		int endMarker = allCode.indexOf(endMarkerCode);
		int newStartMarker = startMarker+startMarkerCode.length();
		String parseOnly="";
		System.out.println("SM: "+startMarker);
		System.out.println("EM: "+endMarker);
		System.out.println("NM: "+newStartMarker);
		parseOnly = allCode.substring(newStartMarker,endMarker); 
		System.out.println("Init Only");
		System.out.println(parseOnly);
			
		return parseOnly;
	}
	
	public String parseActions(String allCode)
	{

		
		String startMarkerCode = "//[StartActions]";
		String endMarkerCode = "//[EndActions]";
		
		int startMarker = allCode.indexOf(startMarkerCode);
		int endMarker = allCode.indexOf(endMarkerCode);
		int newStartMarker = startMarker+startMarkerCode.length();
		String parseOnly="";
		System.out.println("SM: "+startMarker);
		System.out.println("EM: "+endMarker);
		System.out.println("NM: "+newStartMarker);
		parseOnly = allCode.substring(newStartMarker,endMarker); 
		System.out.println("Actions Only");
		System.out.println(parseOnly);
		
		return parseOnly;
	}
	
	public JEditorPane createCodeTab(String page,String type)
	{
		//Remove Markers
		page=page.replace("//[StartVariables]","");
		page=page.replace("//[EndVariables]","");
		page=page.replace("//[StartFrameSettings]","");
		page=page.replace("//[EndFrameSettings]","");
		page=page.replace("//[StartIntialization]","");
		page=page.replace("//[EndIntialization]","");
		page=page.replace("//[StartActions]","");
		page=page.replace("//[EndActions]","");
		//Remove Markers
		
		JEditorPane temp = new JEditorPane();
		temp.setEditable(false);
		temp.setContentType("text/"+type);
		if(type.equals("html"))
		{
			try
			{
			   temp.setPage(new File(page).toURI().toURL());	
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(this," Sorry Cannot Load Page . . .\n Page may be deleted ","Error",0);
			}
		}
		else
		{
			temp.setText(page);
		}
		temp.addHyperlinkListener(this);
		
		return temp;
	}
	
		
	// This part code is taken from Sun Microsystems java 1.4.2 Documentation page of JEditorPane
	public void hyperlinkUpdate(HyperlinkEvent e) 
	{
		System.out.println("DEBUG: Running GuiDUIPopupCode-hyperlinkUpdate"); 	    

		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
		 {
 		      JEditorPane pane = (JEditorPane) e.getSource();
 		      if (e instanceof HTMLFrameHyperlinkEvent) 
			  {
 		          HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)e;
 		          HTMLDocument doc = (HTMLDocument)pane.getDocument();
 		          doc.processHTMLFrameHyperlinkEvent(evt);
 		      } 
			  else 
			  {
 		          try 
				  {
 				      pane.setPage(e.getURL());
 		          } 
				  catch (Exception ex) 
				  {
  	      			JOptionPane.showMessageDialog(this," Sorry Cannot Load Page . . .\n Page may be deleted ","Error",0);
 		          }
 		      }
 	     }
    }
}
