//package JGUID.UI;

import javax.swing.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
//import Util.ChangeAdapter;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

//import UI.*;
//import Session.*;

public class GuiDUIManager extends JFrame implements ChangeListener
{
	GuiDUIPropertiesPanel currentComponentProps=null;
	GuiDUIPropertiesPanel frameProperties=null;
	GuiDUIMainPanel mainDesignPanel;
	
	JTabbedPane propertiesTabs;
	
	

	public GuiDUIManager(GuiDUIMenuTabs tabs,GuiDUIComponentList toolbox,GuiDUIMainPanel gp)
	{
		    
		
		super("JGuiDv32 (2022)");
		System.out.println("DEBUG: Running GuiDUIManager-constuctor"); 	
		this.mainDesignPanel= gp;
		
		this.setLayout(new BorderLayout());
	
		try
		{
			//Image img = getToolkit().createImage(getClass().getResource("Images/icons/GuiD_icon.png"));
			//this.setIconImage(img);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		setDefaultLookAndFeelDecorated(true);
		
		
		this.add(tabs,BorderLayout.NORTH);
		this.add(toolbox,BorderLayout.WEST);
		this.add(new JScrollPane(mainDesignPanel),BorderLayout.CENTER);
		propertiesTabs = new JTabbedPane();
		this.add(propertiesTabs,BorderLayout.EAST);
		
		
		propertiesTabs.addChangeListener(this);
		
		this.setSize(1024,768);
		
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //this.setMaximizedBounds(env.getMaximumWindowBounds());
        
		
		
		setResizable(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.pack();
		this.setVisible(true);
		
		this.setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
		
		
	}
	public void removePropertiesPanel()
	{
		System.out.println("DEBUG: Running GuiDUIManager-remove properties"); 
		
		if(currentComponentProps!=null)
		{
			propertiesTabs.removeTabAt(1);
			currentComponentProps=null;
		}
	
		if(mainDesignPanel.type.contains("Applet")==false)
		{
			mainDesignPanel.setUsrPanelTitle(((JTextField)frameProperties.getPropComponent(0)).getText());
		}


	}
	public void addParentPropPanel(GuiDUIPropertiesPanel theFrameProps)
	{
		System.out.println("DEBUG: Running GuiDUIManager-addParentPropPanel"); 
		frameProperties=theFrameProps;
		propertiesTabs.addTab(theFrameProps.getPropTitle(),theFrameProps);
	
	}
	
	public void updatePropertiesPanel(GuiDUIPropertiesPanel theComponentProps)
	{
		System.out.println("DEBUG: Running GuiDUIManager-updatePropertiesPanel"); 
		
		if(currentComponentProps!=null)
		{
			propertiesTabs.removeTabAt(1);
		}
		currentComponentProps=theComponentProps;
		propertiesTabs.addTab(currentComponentProps.getPropTitle(),currentComponentProps);
	
		propertiesTabs.setSelectedIndex(1);
		propertiesTabs.updateUI();
		currentComponentProps.updatePanel();
	}
	
	
	public void stateChanged(ChangeEvent ce)
	{
		System.out.println("DEBUG: Running GuiDUIManager-stateChanged"); 
		if(propertiesTabs.getSelectedIndex()==0)
		{
			mainDesignPanel.setUsrPanelTitle(((JTextField)frameProperties.getPropComponent(0)).getText());
		}
		else
		{
			mainDesignPanel.setCmpSelection(((JTextField)currentComponentProps.getPropComponent(0)).getText());
		}
	}
	

}
