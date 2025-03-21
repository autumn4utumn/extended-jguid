//package JGUID.UI;

import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.*;

// This class control the entire menu bar
public class GuiDUIMenuTabs extends JTabbedPane
{
    int currentX=0;
    int currentY=0;
    int tempsize; //Used for button sizes
	
	int prefWidth=500;
	int prefHeight=-1; //override later
	int extraHeight=30; 
    
    
    JPanel menufile = new JPanel(null);
	JPanel menucode = new JPanel(null);
    JPanel menuedit = new JPanel(null);
    JPanel menuhelp = new JPanel(null);

    //Project
    GuiDUITabButton optionFilenew;
	GuiDUITabButton optionFileopen;
	GuiDUITabButton optionFilesave;

    //Edit
    GuiDUITabButton optionEditcut;
	GuiDUITabButton optionEditcopy;
	GuiDUITabButton optionEditpaste;
	GuiDUITabButton optionEditdelete;

    //Help
    GuiDUITabButton optionHelpUse;
	GuiDUITabButton optionHelpBatch;
	GuiDUITabButton optionHelpMultiple;
	GuiDUITabButton optionHelpHistory;

    //Code
    GuiDUITabButton optionCodeexport;
    GuiDUITabButton optionCodeviewcode;

    public GuiDUIMenuTabs()
    {
		System.out.println("DEBUG: Running GuiDUIMenuTabs-constructor");        
		createButtons();
		
		makeFileMenu();
		makeCodeMenu();
        makeEditMenu();
        makeHelpMenu();
		
		setMnemonics();
		setTooltips();
		addButtonsToMenu();
		setActionCommands();
		
		this.add("Project (JGuiD)",menufile);
		this.add("Code (.java)",menucode);
		this.add("Edit (Component)",menuedit);
		//this.add("Help",menuhelp);
		
		this.setPreferredSize(new Dimension(prefWidth, prefHeight));
		//Color bg = new Color(0xFFDE03);
		this.setBackground(Color.RED);
    }


	public void createButtons()
	{
		try
		{
			//Project
		
		 optionFilenew = new GuiDUITabButton("New Project",new ImageIcon(getClass().getResource("images/iconNew.png")));
		 optionFileopen = new GuiDUITabButton("Open Project",new ImageIcon(getClass().getResource("images/iconOpen.png")));
		 optionFilesave = new GuiDUITabButton("Save Project",new ImageIcon(getClass().getResource("images/iconSave.png")));

		//Edit
		 optionEditcut = new GuiDUITabButton("Cut",new ImageIcon(getClass().getResource("images/iconCut.png")));
		 optionEditcopy = new GuiDUITabButton("Copy",new ImageIcon(getClass().getResource("images/iconCopy.png")));
		 optionEditpaste = new GuiDUITabButton("Paste",new ImageIcon(getClass().getResource("images/iconPaste.png")));
		 optionEditdelete = new GuiDUITabButton("Delete",new ImageIcon(getClass().getResource("images/iconDelete.png")));

		//Help
		 optionHelpUse = new GuiDUITabButton("Using JGuiD",new ImageIcon(getClass().getResource("images/iconHelp.png")));
		 optionHelpBatch = new GuiDUITabButton("Batch Files",new ImageIcon(getClass().getResource("images/iconBatch.png")));
		 optionHelpMultiple = new GuiDUITabButton("Multiple Screens",new ImageIcon(getClass().getResource("images/iconMulti.png")));
		 optionHelpHistory = new GuiDUITabButton("History of JGuiD",new ImageIcon(getClass().getResource("images/iconHistory.png")));

		//Code
		 optionCodeexport = new GuiDUITabButton("Export Code",new ImageIcon(getClass().getResource("images/iconExport.png")));
		 optionCodeviewcode = new GuiDUITabButton("View Code",new ImageIcon(getClass().getResource("images/iconView.png")));		
		}
		catch(Exception e)
		{
			System.out.println("Resource not found in GUIDUIMenuTabs.java");
			e.printStackTrace();
		}
	}
	
    public void makeFileMenu()
    {
        System.out.println("DEBUG: Running GuiDUIMenuTabs-makeFileMenu");  
        //*********************************************

        resetX();
        
        optionFilenew.setLocation(currentX,currentY);
        incrementPosition();
        optionFileopen.setLocation(currentX,currentY);
        incrementPosition();
        optionFilesave.setLocation(currentX,currentY);
        incrementPosition();
		
		prefWidth=currentX;
		prefHeight=optionFilenew.getButtonHeight()+extraHeight;
        //*********************************************

        
    }

	public void setMnemonics() // Keyboard shortcuts with ALT
	{
		optionFilenew.setMnemonic('N');
		optionFileopen.setMnemonic('O');
        optionFilesave.setMnemonic('S');
		
		optionEditcut.setMnemonic('X');
		optionEditcopy.setMnemonic('C');
		optionEditpaste.setMnemonic('V');
		optionEditdelete.setMnemonic('D');

		//optionHelphelp.setMnemonic('H');
		
		optionCodeexport.setMnemonic('E');
	}
	
	public void setTooltips()
	{
		optionFilenew.setToolTipText("New Project");
		optionFileopen.setToolTipText("Open Project");
        optionFilesave.setToolTipText("Save Project");
		
		optionEditcut.setToolTipText("Cut Component");
		optionEditcopy.setToolTipText("Copy Component");
		optionEditpaste.setToolTipText("Past Component");
		optionEditdelete.setToolTipText("Delete Component");
				
		optionHelpUse.setToolTipText("How to Use");
		optionHelpBatch.setToolTipText("Creating Batch Files");
		optionHelpMultiple.setToolTipText("Multiple Windows & TabbedPanes");
		optionHelpHistory.setToolTipText("History of JGuiD");
		
		optionCodeexport.setToolTipText("Export Code");
        optionCodeviewcode.setToolTipText("View Code");
	}
	
	public void addButtonsToMenu()
	{
		menufile.add(optionFilenew);
        menufile.add(optionFileopen);
        menufile.add(optionFilesave);

		menucode.add(optionCodeexport);
		menucode.add(optionCodeviewcode);
		
		menuedit.add(optionEditcut);
        menuedit.add(optionEditcopy);
        menuedit.add(optionEditpaste);
        menuedit.add(optionEditdelete);
		
		menuhelp.add(optionHelpUse);
		menuhelp.add(optionHelpBatch);
		menuhelp.add(optionHelpMultiple);
		menuhelp.add(optionHelpHistory);
	}
	
	public void setActionCommands()
	{
		optionFilenew.setActionCommand("new");
        optionFileopen.setActionCommand("open");
        optionFilesave.setActionCommand("save");
		
		optionEditcut.setActionCommand("cut");
        optionEditcopy.setActionCommand("copy");
        optionEditpaste.setActionCommand("paste");
        optionEditdelete.setActionCommand("delete");
		
		optionHelpUse.setActionCommand("helptouse");
		optionHelpBatch.setActionCommand("helpbatchfile");
		optionHelpMultiple.setActionCommand("helpmultiplewindows");
		optionHelpHistory.setActionCommand("helphistory");
		
		optionCodeexport.setActionCommand("scode");
        optionCodeviewcode.setActionCommand("code");
	}
	
    public void makeEditMenu()
    {
		System.out.println("DEBUG: Running GuiDUIMenuTabs-makeEditMenu"); 
        //*********************************************
        resetX();
        
        optionEditcut.setLocation(currentX,currentY);
        incrementPosition();
        optionEditcopy.setLocation(currentX,currentY);
        incrementPosition();
        optionEditpaste.setLocation(currentX,currentY);
        incrementPosition();
        optionEditdelete.setLocation(currentX,currentY);
        incrementPosition();

        
    }

    public void makeHelpMenu()
    {
        System.out.println("DEBUG: Running GuiDUIMenuTabs-makeHelpMenu"); 

        resetX();
        
		optionHelpUse.setLocation(currentX,currentY);
		incrementPosition();
		optionHelpBatch.setLocation(currentX,currentY);
		incrementPosition();
		optionHelpMultiple.setLocation(currentX,currentY);
		incrementPosition();
		optionHelpHistory.setLocation(currentX,currentY);
    }

    public void makeCodeMenu()
    {
       System.out.println("DEBUG: Running GuiDUIMenuTabs-makeCodeMenu"); 
	
        resetX();
        optionCodeexport.setLocation(currentX,currentY);
        incrementPosition();
        optionCodeviewcode.setLocation(currentX,currentY);
        incrementPosition();
    }

	public void resetX()
	{
		currentX=0;
	}
	
	//setsize and setlocation from GuiDUITabButton
	public void incrementPosition()
	{
		tempsize = optionFilenew.getButtonWidth();
		currentX=currentX+tempsize;
	}
		
    public void addListeners(ActionListener al)
    {
        System.out.println("DEBUG: Running GuiDUIMenuTabs-addListeners"); 
		
		optionFilenew.addActionListener(al);
        optionFileopen.addActionListener(al);
        optionFilesave.addActionListener(al);

        optionEditcut.addActionListener(al);
        optionEditcopy.addActionListener(al);
        optionEditpaste.addActionListener(al);
        optionEditdelete.addActionListener(al);

        optionHelpUse.addActionListener(al);
		optionHelpBatch.addActionListener(al);
		optionHelpMultiple.addActionListener(al);
		optionHelpHistory.addActionListener(al);

        optionCodeexport.addActionListener(al);
        optionCodeviewcode.addActionListener(al);
    }
}

