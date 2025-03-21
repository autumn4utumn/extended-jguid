//package JGUID.UI;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class GuiDUIMainPanel extends JDesktopPane
{
	 private JInternalFrame usr_frm;
	 public String type;
	 private String title;
	

	public GuiDUIMainPanel(String type)
	{
		System.out.println("DEBUG: Running GuiDUIMainPanel-constructor"); 
		
		this.type = type;
		setPreferredSize(new Dimension(1280,1240));
	    setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		usr_frm = new JInternalFrame("",true,false,false,false);
		
		try
		{
			//usr_frm.setFrameIcon(new ImageIcon("images/GuiD_icon1.png"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(type.equals("Frame")||type.equals("JFrame"))
		{
			//Option to request name on creation
			/* String s = (String)JOptionPane.showInputDialog( null,
                    "Please enter a title...","Set Title",
                    JOptionPane.PLAIN_MESSAGE, null,
                    null, "untitled");*/
					
			 //title = "untitled ( "+type+" )";
			 title = "NoTitleSet";
		}
		else
		{
			title = type;
		}
		 usr_frm.setTitle(title);
		usr_frm.setSize(600,600);
		usr_frm.setVisible(true);
		
		usr_frm.addComponentListener(new ComponentAdapter(){
			
		   public void componentMoved(ComponentEvent ce) 
			  {
			          usr_frm.setLocation(new Point(0,0));
			  }
		});
		usr_frm.setLayout(null);
		super.add(usr_frm);
		this.setSelectedFrame(usr_frm);
	}
	public JInternalFrame getUserPanel()
	{
		return usr_frm;
	}
	public String getUsrPanelTitle()
	{
		return title;
	}
	public void setCmpSelection(String s)
	{
		usr_frm.setTitle(title+"- Selection : "+s);
	}
	public void setUsrPanelTitle(String t)
	{
		title =t;
		usr_frm.setTitle(title);
	}
	public void addCmp(Component cmp)
	{
		usr_frm.getContentPane().add(cmp);
		usr_frm.getContentPane().validate();
		((JComponent)cmp).updateUI();
	}
	public void removeCmp(Component cmp)
	{
		usr_frm.getContentPane().remove(cmp);
		usr_frm.updateUI();
		usr_frm.getContentPane().validate();
	}
	public void removeAllCmp()
	{
		usr_frm.getContentPane().removeAll();
		usr_frm.getContentPane().validate();
	}
	
	
}