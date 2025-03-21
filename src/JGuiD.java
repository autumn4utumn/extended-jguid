//package JGUID;
//import JGUID.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

//import UI.*;
//import Util.*;
//import Session.*;

import java.io.File;


/**
* This class instantiates GuiDSession either creating a new project or opening an existing file
* Uses interface: iJGuiD
*/


public class JGuiD implements iJGuiD
{
   /**
    * This Constructor bypasses the old project dialog
	* Showing a splash screen and creating a new project
    */
	
	String theVersion = "JGuiD 2022 v32";
	
	public JGuiD()
	{
		System.out.println("DEBUG: Running JGuiD-Constructor");
		showSplashScreen();
		
		GuiDSession prim = new GuiDSession("JFrame",this); //Start new JFrame Project
	}
	
	/**
	*This creates an instance for the class GuiDUIPopupProject
	*Which show the Project Dialog
	*/
	
	public void showProjectDialog() //Doesn't ask, just starts new JFrame
	{
	   System.out.println("DEBUG: Running JGuiD-showProjectDialog");
	   GuiDSession prim = new GuiDSession("JFrame",this); //Start new JFrame Project
	}
	
	/**
	*function starts a new Project by sending the type of project to GuiDSession Object
	*which Then start a session for new project
	*/
	public void startNewProject(String type)
	{
		System.out.println("DEBUG: Running JGuiD-startNewProject() DISABLED");
	}
	/**
	*function starts a existing Project by sending the project file  to GuiDSession Object
	*which Then start a session for specified project file
	*/
	public void openExistingProject(File f)
	{
		System.out.println("DEBUG: Running JGuiD-openExistingProblem");
		GuiDSession prim = new GuiDSession(f,this);
	}
	
	 public void showSplashScreen()
	{
		
		System.out.println("DEBUG: Running JGuiD-showSplashScreen");
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		try
		{
			JFrame splashFrame = new JFrame(theVersion);
			int splashWidth = 600;
			int splashHeight = 500;
			
			splashFrame.setSize( splashWidth,splashHeight);
			
			splashFrame.setLocation(dim.width/2-splashFrame.getSize().width/2, dim.height/2-splashFrame.getSize().height/2); //Center Centre
			
			splashFrame.setLayout(null);
			
			splashFrame.setResizable(false);

			
			JLabel lblHeader = new JLabel(new ImageIcon(getClass().getResource("images/splashmonkey.jpg")));
			lblHeader.setText(theVersion);
			//lblHeader.setSize(375,(345+50));
			lblHeader.setSize(375,500);
			lblHeader.setHorizontalTextPosition(JLabel.CENTER);
			lblHeader.setVerticalTextPosition(JLabel.BOTTOM);
			lblHeader.setFont(new Font("Consolas", Font.PLAIN, 14));
			lblHeader.setForeground(Color.WHITE);
			
			lblHeader.setLocation(splashWidth/2-(lblHeader.getWidth()/2), splashHeight/2-(lblHeader.getHeight()/2)); //Center Centre
			
			splashFrame.getContentPane().setBackground(Color.BLACK);
			splashFrame.add( lblHeader );
			
			splashFrame.setVisible(true);
			String startHTML="<html>";
			String endHTML="</html>";
			String breakHTML="<br/>";
			String line1=theVersion+breakHTML;
			String line2=theVersion+"....loading...."+breakHTML;
			String line3=theVersion+"....hiding exceptions...."+breakHTML;
			String line4=theVersion+"....hardcoding a fix...."+breakHTML;
			String line5=theVersion+"....reticulating splines...."+breakHTML;
			
			for(int i=0;i<5;i++)
			{
				
				if(i==0)
				{
					lblHeader.setText(startHTML+line1+endHTML);
					splashFrame.setSize( splashWidth,splashHeight);
					splashHeight=splashHeight+20;
				}
				else if(i==1)
				{
					lblHeader.setText(startHTML+line1+line2+endHTML);
					splashFrame.setSize( splashWidth,splashHeight);
					splashHeight=splashHeight+20;
				}
				else if(i==2)
				{
					lblHeader.setText(startHTML+line1+line2+line3+endHTML);
					splashFrame.setSize( splashWidth,splashHeight);
					splashHeight=splashHeight+20;
				}
				else if(i==3)
				{
					lblHeader.setText(startHTML+line1+line2+line3+line4+endHTML);
					splashFrame.setSize( splashWidth,splashHeight);
					splashHeight=splashHeight+20;
				}
				else if(i==4)
				{
					lblHeader.setText(startHTML+line1+line2+line3+line4+line5+endHTML);
					splashFrame.setSize( splashWidth,splashHeight);
					splashHeight=splashHeight+20;
				}
				Thread.sleep(500);
				
			}
			splashFrame.setVisible(false);
			
		}
		catch(Exception e)
		{
			System.out.println("EXCEPTION: Running JGuiD-showSplashScreen" + e);
		}
	}
	
	public static void main(String[] args) 
	{
		System.out.println("DEBUG: Running JGuiD-main");
		JGuiD tg = new JGuiD();
	}
}
