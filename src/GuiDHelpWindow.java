//package JGUID.UI;
import java.net.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;
import java.io.File;
import java.awt.Image;
import java.awt.*;

public class GuiDHelpWindow extends JFrame implements HyperlinkListener
{
    JTabbedPane infoTabs = new JTabbedPane();
    JEditorPane theHelp;
    String type="html";
    
    
    public GuiDHelpWindow(String thetitle, int theInfoType)
    {
        super(thetitle);
        System.out.println("DEBUG: Running GuiDHelpWindow");
        
        try
        {
           // URL url = new URL("images/GuiD_icon.png");
           //Image img = getToolkit().createImage(url);
           // this.setIconImage(img);
        }
        catch(Exception e)
        {
            System.out.println("DEBUG: Exception GuiDHelpWindow");
        }
        
        
        if(theInfoType==0)
        {
            String helpPage="Help/howToUse.html";
            theHelp = createPane(helpPage,type);
        }
        else if(theInfoType==1)
        {
            String originalDocPage="Help/batchfiles.html";
            theHelp = createPane(originalDocPage,type);
        }
        else if(theInfoType==2)
        {
            String newDocPage="Help/multiplescreens.html";
            theHelp = createPane(newDocPage,type);
        }
        else if(theInfoType==3)
        {
            String newDocPage="Help/history.html";
            theHelp = createPane(newDocPage,type);
        }
        
        JScrollPane theHelpScroller = new JScrollPane(theHelp);
        
        
        this.setLayout(new GridLayout(1,1));
        this.add(theHelpScroller);
        
        this.setSize(600,300);
        this.setLocation(150,150); 
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        
    }
    
    public JEditorPane createPane(String originalDocPage,String type)
    {
        JEditorPane localPane = new JEditorPane();
        localPane.setEditable(false);
        localPane.setContentType("text/"+type);
        if(type.equals("html"))
        {
            try
            {
               localPane.setPage(new File(originalDocPage).toURI().toURL());    
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(this," Sorry Cannot Load Page . . .\n Page may be deleted ","Error",0);
            }
        }
        else
        {
            localPane.setText(originalDocPage);
        }
        localPane.addHyperlinkListener(this);

        return localPane;
    }
    
        
    // This part code is taken from Sun Microsystems java 1.4.2 Documentation page of JEditorPane
    public void hyperlinkUpdate(HyperlinkEvent e) 
    {
        System.out.println("DEBUG: Running GuiDUIPopupInfo-hyperlinkUpdate");       

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
