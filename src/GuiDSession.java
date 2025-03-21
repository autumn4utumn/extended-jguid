//ACTION LISTENERS IN HERE

//package JGUID.Session;
//import JGUID.*;
//import JGUID.UI.*;
//import JGUID.Util.*;
//import JGUID.Session.*;

import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Vector;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileWriter;
import javax.swing.UIManager.*;
import java.awt.Image;

public class GuiDSession implements ActionListener,iGuiDPropMessenger
{
    GuiDUIMainPanel mainPanel;    

    GuiDUIMenuTabs menuTabs;

    GuiDUIComponentList componentList;

    GuiDUIManager guiManager;

    GuiDComponent curr_sel;
    GuiDParentPropertiesPanel parentPropsPanel;
    String toolbox_sel;
    String type;
    int curr_no;
    Vector<GuiDComponent> comp_vec;
    Vector<GuiDComponent> buf_vec;
    File f=null;
    File jf=null;
    iJGuiD itg;  //Interface passed from JGuiD
    
    String[][] tempParentProperties;
    
    
    //ExportWindow////////////////////////////////////////
    JFrame exportWindow;
    JPanel exportPanel = new JPanel(null); //layout
    
     JTextField jtfPath = new JTextField();
    JLabel lblPath = new JLabel();
    JButton btnPath = new JButton();
    JCheckBox cbBatch = new JCheckBox();
    JButton btnExport = new JButton();
    JLabel lblPropTitle = new JLabel();
    JTextField tfFrameTitle = new JTextField();
    boolean makeBatch=false;
    String tempBatchFilename = "GuiDFrame";;
    JFileChooser jfc;
    
    JLabel lblClassName = new JLabel();
    JTextField tfClassName = new JTextField();
    
    String path="";
    
    JLabel lblPanelName = new JLabel();
    JTextField jtfPanelName = new JTextField();
    JLabel lblMethodName = new JLabel();
    JTextField jtfMethodName = new JTextField();
    JLabel lblObjectName = new JLabel();
    JTextField jtfObjectName = new JTextField();
    //JCheckBox cbAddTabbedPane = new JCheckBox();

    String tempClassName;
    String tempPanel; 
    String tempMethod;
    String tempObject;
    
    //boolean makeTabs=false;
    public String val = "OR DONT";
    
    ///////////////////////////////////////////////////////
    boolean allowShutdown=true;
    //Constructor to being new session
    public GuiDSession(String type,iJGuiD tg)
    {
        try
        {
            selectLookAndFeel(); //Start with Nimbus
            
            System.out.println("DEBUG: Running GuiDSession- Constructor TYPE TAG");
            itg=tg;
            
            doInit(type);

            comp_vec = new Vector<GuiDComponent>();
            guiManager = new GuiDUIManager(menuTabs,componentList,mainPanel);
        
            parentPropsPanel = new GuiDParentPropertiesPanel(mainPanel);

            addListeners();
            guiManager.addParentPropPanel(parentPropsPanel.getPropPanel());

            
            
            
        }
        catch(Exception ee)
        {
            System.out.println("EXCEPTION: GuiDSession-constructor TYPE TAG");
            JOptionPane.showMessageDialog(guiManager," Exception Occured in GuiDSession constructor "+ee.getMessage(),"Error",0);
        }
        
    }
    
    //Constructor to open existing file
    public GuiDSession(File f,iJGuiD tg)
    {
        try
        {        
            System.out.println("DEBUG: Running GuiDSession WITH FILE PARAM -Constructor FILE TAG TEST1");
            System.out.println("DEBUG: Running GuiDSession WITH FILE PARAM -Constructor FILE TAG1");
            System.out.println("DEBUG: Running GuiDSession WITH FILE PARAM -Constructor FILE TAGZ1");
            itg =tg;
            System.out.println("DEBUG: Running GuiDSession WITH FILE PARAM -Constructor FILE TAGZ2");
            this.f = f;
            System.out.println("DEBUG: Running GuiDSession WITH FILE PARAM -Constructor FILE TAG2");
            GuiDFileProjectHandler gpfh = new GuiDFileProjectHandler(f,null,null,null);
            System.out.println("DEBUG: Running GuiDSession WITH FILE PARAM -Constructor FILE TAG3");
            gpfh.parseProjectFile(this);

            doInit(gpfh.getType());

            comp_vec = gpfh.getComponentVector();

            int i;
            for(i=0;i<comp_vec.size();i++)
            {
                
                GuiDComponent gc = comp_vec.elementAt(i);
                mainPanel.addCmp(gc.getDispComponent());    
            }
            curr_no=i;
            mainPanel.invalidate();

            guiManager = new GuiDUIManager(menuTabs,componentList,mainPanel);

            parentPropsPanel = new GuiDParentPropertiesPanel(mainPanel,gpfh.getParentProps());
            
            mainPanel.getUserPanel().updateUI();
            addListeners();
            guiManager.addParentPropPanel(parentPropsPanel.getPropPanel());
            guiManager.invalidate();
            
        }
        
        catch(Exception ee)
        {
            System.out.println("EXCEPTION: GuiDSession WITH FILE PARAM -constructor FILE TAG 4");
            System.out.println("**********************");
            System.out.println("**********************");
            System.out.println(ee);
            System.out.println("**********************");
            System.out.println("**********************");
        }
    }
    
    
    
    public void selectLookAndFeel()
    {
        try
        {
            System.out.println("DEBUG: Running GuiDSession-selectLookAndFeel");
            
            String look ="javax.swing.plaf.nimbus.NimbusLookAndFeel";
            
            UIManager.setLookAndFeel(look);
            
            //SwingUtilities.updateComponentTreeUI(guiManager);    //THROWS AN EXCEPTION
            //NOT NEEDED?
            
        }
        catch(Exception ee)
        {
            System.out.println("EXCEPTION: GuiDSession-LookandFeel (Random Nimbus Exc)");
            ee.printStackTrace();
        }
    }
    
    
    private void doInit(String type)
    {
        this.type = type;

        curr_sel=null;
        toolbox_sel=null;
        curr_no=0;
    
                menuTabs = new GuiDUIMenuTabs();
    
        componentList = new GuiDUIComponentList();
        mainPanel = new GuiDUIMainPanel(type);

        buf_vec = new Vector<GuiDComponent>();
    }

    private void addListeners()
    {
        try
        {
            guiManager.addWindowListener(new WindowAdapter() 
            {
                @Override
                public void windowClosing(WindowEvent we)
                { 
                                        
                    int promptResult = JOptionPane.showConfirmDialog(null,"Close JGuiD? \n\n You will lose all unsaved work","Close JGuiD",JOptionPane.YES_NO_OPTION);
                    
                    if(promptResult==JOptionPane.YES_OPTION)
                    {
                        allowShutdown=true;
                        //saveNewProject();
                        if(allowShutdown==true)
                        {
                            System.exit(0);
                        }
                    }
                    else if(promptResult==JOptionPane.NO_OPTION)
                    {
                        System.out.println("GuiDSession: New Project Cancelled");
                    }
                    
            }
                });
            
            
            
            menuTabs.addListeners(this);
    
        
        componentList.addListeners(new ActionAdapter()
        {
            public void actionPerformed(ActionEvent ae)
            {
                JButton src = (JButton)ae.getSource();
                toolbox_sel= src.getActionCommand();
                componentList.setToolboxSelection(toolbox_sel);
            }
        });
        
        mainPanel.getUserPanel().addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent me)
            {
                if(toolbox_sel!=null)
                {
                    createCmp(me.getX(),me.getY()-25);
                    toolbox_sel=null;
                    componentList.clearToolboxSelection();
                }
            }
        });
        }
        catch(Exception e)
        {
            System.out.println("ERRRRROR "  +e.getMessage());
        }
    }

    public void createCmp(int x,int y)
    {
        System.out.println("DEBUG: Running GuiDSession- createCmp");
        curr_no++;
        GuiDComponent gc = new GuiDComponent(toolbox_sel,curr_no,x,y,this);
        mainPanel.addCmp(gc.getDispComponent());
        curr_sel=gc;
        comp_vec.add(gc);
        guiManager.updatePropertiesPanel(gc.getPropertiesPanel());
        updateCompSel(gc.getSelString());
        guiManager.invalidate();
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        System.out.println("DEBUG: Running GuiDSession- actionPerformed");
        
        String action_str= ((AbstractButton)ae.getSource()).getActionCommand();
        
        if(action_str.equalsIgnoreCase("helptouse"))
        {
            GuiDHelpWindow giv= new GuiDHelpWindow("How to Use",0);
        }
        else if(action_str.equalsIgnoreCase("helpbatchfile"))
        {
            GuiDHelpWindow giv= new GuiDHelpWindow("Creating Batch Files",1);
        }
        else if(action_str.equalsIgnoreCase("helpmultiplewindows"))
        {
            GuiDHelpWindow giv= new GuiDHelpWindow("Multiple Windows & TabbedPanes",2);
        }
        else if(action_str.equalsIgnoreCase("helphistory"))
        {
            GuiDHelpWindow giv= new GuiDHelpWindow("History of JGuiD",3);
        }
        
        else if(action_str.equals("code"))
        {
            System.out.println("Running GuiDSession: actionPerformed 'code':");
            String allcode=GuiDCodeGenerator.getCode(parentPropsPanel.getPropertiesArray(),mainPanel.type,comp_vec);
            
            
            GuiDUIPopupCode giv= new GuiDUIPopupCode("Your Code",allcode,"text");
            
        }
        else if(action_str.contains("look"))
        {
            String look="javax.swing.plaf.metal.MetalLookAndFeel";
                if(action_str.equals("look_java"))
                {
                      look="javax.swing.plaf.metal.MetalLookAndFeel";
                }
                else if(action_str.equals("look_windows"))
                {
                        look="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
                }
                else if (action_str.equals("look_motif"))
                {
                        look="com.sun.java.swing.plaf.motif.MotifLookAndFeel";
                }
                else if(action_str.equals("look_nimbus"))
                {
                    look ="com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
                }                
                try
                {
                    UIManager.setLookAndFeel(look);
                    SwingUtilities.updateComponentTreeUI(guiManager);
                }
                catch(Exception ee)
                {
                    JOptionPane.showMessageDialog(guiManager," Exception Occured when setting Look and Feel "+ee,"Error",0);
                }
        }
        else if(action_str.equals("copy"))
        {
            if(curr_sel!=null)
            {
                curr_no++;
                copyCurrComp(curr_no);
            }
        }
        else if(action_str.equals("paste"))
        {
            pasteComp();
        }
        else if(action_str.equals("delete"))
        {
            deleteCurrComp();
            if(curr_sel!=null)
            {
                comp_vec.remove(curr_sel);
                curr_sel.finalize();
                curr_sel=null;
            }
        }
        else if(action_str.equals("cut"))
        {
            copyCurrComp(-1);
            deleteCurrComp();
            comp_vec.remove(curr_sel);
        }
        else if(action_str.equals("new"))
        {
            //closeProject("Would you like to save existing project before starting new one ?");
            
            
            int promptResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to start a new project? \n\n You will lose all unsaved work","Start New Project",JOptionPane.YES_NO_OPTION);
            
                
            if(promptResult==JOptionPane.YES_OPTION)
            {
                allowShutdown=true;
                //saveNewProject();
                if(allowShutdown==true)
                {
                    guiManager.dispose();
                    itg.showProjectDialog(); //Calls method from JGuiD to create new project
                    System.out.println("Line 359");
                    finalize();
                }
            }
            else if(promptResult==JOptionPane.NO_OPTION)
            {
                guiManager.dispose();
                itg.showProjectDialog(); //Calls method from JGuiD to create new project
                System.out.println("Line 381");
                System.out.println("GuiDSession 388: New Project Cancelled");
            }
        }
        else if(action_str.equals("open"))
        {
            int promptResult = JOptionPane.showConfirmDialog(null,"Want to continue to open another project? \n\n You will lose all unsaved work","Open Another Project",JOptionPane.YES_NO_OPTION);
                    
            if(promptResult==JOptionPane.YES_OPTION)
            {
                allowShutdown=true;
                //saveNewProject();
                if(allowShutdown==true)
                {
                    jfc = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("GuiD Project File", "GuiD");
                    jfc.setFileFilter(filter);
                    if(jfc.showOpenDialog(guiManager)==JFileChooser.APPROVE_OPTION)
                    {
                            guiManager.dispose();
                            itg.openExistingProject(jfc.getSelectedFile());
                            System.out.println("Line 402");
                            finalize();
                    }
                }
            }
            else if(promptResult==JOptionPane.NO_OPTION)
            {
                //guiManager.dispose();
                //itg.showProjectDialog(); //Calls method from JGuiD to create new project
                //itg.openExistingProject(jfc.getSelectedFile());
                //System.out.println("Line 400");
                //finalize();
                
                /* 10/04/19
                jfc = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("GuiD Project File", "GuiD");
                jfc.setFileFilter(filter);
                if(jfc.showOpenDialog(guiManager)==JFileChooser.APPROVE_OPTION)
                {
                        guiManager.dispose();
                        itg.openExistingProject(jfc.getSelectedFile());
                        System.out.println("Line 327");
                        finalize();
                }*/
            }
            //else if(promptResult==JOptionPane.CANCEL_OPTION)
            //{
                //
            //}
                    
            //closeProject("Would you like to save existing project before opening new one ?");
            //jfc = new JFileChooser();
            //FileNameExtensionFilter filter = new FileNameExtensionFilter("GuiD Project File", "GuiD");
            //jfc.setFileFilter(filter);
            /*if(jfc.showOpenDialog(guiManager)==JFileChooser.APPROVE_OPTION)
            {
                    guiManager.dispose();
                    itg.openExistingProject(jfc.getSelectedFile());
                    System.out.println("Line 327");
                      finalize();
            }*/            
        }
        else if(action_str.equals("save"))
        {
            if(f==null)
            {
                saveNewProject();
            }
            else
            {
                GuiDFileProjectHandler gpfg = new GuiDFileProjectHandler(f,type,parentPropsPanel.getPropertiesArray(),comp_vec);
                gpfg.generateProjectFile();
            }
        }
        else if(action_str.equals("scode")) //export
        {
            if(exportWindow==null)
            {    
                createExportWindow();
                System.out.println("NULL - createExportWindow");        
            }
            tempParentProperties = parentPropsPanel.getPropertiesArray();
            showExportWindow(tempParentProperties[0][2]);
        }
        else if(action_str.equals("close"))
        {
            closeProject("Would you like to save project before closing?");
             guiManager.dispose();
            itg.showProjectDialog();
              System.out.println("Line 363");
            finalize();
        }
        else if(action_str.equals("exit"))
        {
            closeProject("Would you like to save project before leaving?");
            guiManager.dispose();
            System.exit(0);
        }
        else if(action_str.equals("settings"))
        {
            JOptionPane.showMessageDialog(null,"Not implemented");
         }
         
        else if(ae.getSource()==btnPath) //Export Window
        {
            tempParentProperties = parentPropsPanel.getPropertiesArray();
            runBatchScreen(tempParentProperties[0][2]);
            System.out.println("btnPath button has been pressed ");
            jtfPath.setText(path);
            
        }
        else if(ae.getSource()==btnExport) //Export Window
        {
            System.out.println("btnExport button has been pressed ");
            boolean exportValid=false;
            exportValid = exportValidation();
            
                    
            if(exportValid==true)
            {            
                tempParentProperties = parentPropsPanel.getPropertiesArray();
                String code=GuiDCodeGenerator.getCode(tempParentProperties,mainPanel.type,comp_vec);
                                
                //makeTabs = cbAddTabbedPane.isSelected();
                
                code = GuiDCodeGenerator.replaceMarkers(code);
                
                /*if(makeTabs==true)
                {
                    code = GuiDCodeGenerator.addTabbedPane(code);
                }*/
                
                                
                if(code.contains("MYPANEL"))
                {
                    code = code.replace("MYPANEL",tempPanel);
                }
                
                if(code.contains("genericBoringMethodName"))
                {
                    code = code.replace("genericBoringMethodName",tempMethod);
                }
                
                if(code.contains("myguidobject"))
                {
                    code = code.replace("myguidobject",tempObject);
                }                
                
                GuiDFileHandler.writeToFile(jf,code);
                //JOptionPane.showMessageDialog(null, "Code Exported without markers");
                                
                makeBatch = cbBatch.isSelected();
                
                
            
                if(makeBatch == true)
                {
                    createBatchFile(tempBatchFilename,jfc.getCurrentDirectory());
                    //JOptionPane.showMessageDialog(null, "GuiDSession 408: Make Batch Disabled");
                }
                else if (makeBatch == false)
                {
                    System.out.println("DEBUG: NO BATCH");
                }
                
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Export Refused");
            }
        }
    }
    
    
     
        
    private void closeProject(String str)
    {
        System.out.println("DEBUG: Running GuiDSession- closeProject");
        
        int  answer = JOptionPane.showConfirmDialog( guiManager,str,"GuiD Save Dialog", JOptionPane.YES_NO_OPTION ); 

        if( answer == JOptionPane.YES_OPTION)
        {
            boolean old=false;
            if(f!=null)
             {
                old =true;
             }
             if(!old)
             {
                saveNewProject();    
             }
             else
             {
                GuiDFileProjectHandler gpfg = new GuiDFileProjectHandler(f,type,parentPropsPanel.getPropertiesArray(),comp_vec);
                gpfg.generateProjectFile();
             }
        }
    
    }
    private void saveNewProject()
    {
        System.out.println("DEBUG: Running GuiDSession- saveNewProject");
        
        JFileChooser jfc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("GuiD Project File", "GuiD");
        jfc.setFileFilter(filter);
        int result = jfc.showSaveDialog(null);
        if(result==JFileChooser.APPROVE_OPTION)
        {
             System.out.println("Save was clicked");
             f = jfc.getSelectedFile();
            GuiDFileProjectHandler gpfg = new GuiDFileProjectHandler(f,type,parentPropsPanel.getPropertiesArray(),comp_vec);
            gpfg.generateProjectFile();
            allowShutdown=true;
        }
        if(result==JFileChooser.CANCEL_OPTION)
        {
            System.out.println("Cancel was clicked");
            allowShutdown=false;
        }

    }
    
    private void deleteCurrComp()
    {
        System.out.println("DEBUG: Running GuiDSession- deleteCurrComp");
        
        if(curr_sel!=null)
        {
           mainPanel.removeCmp(curr_sel.getDispComponent());
           guiManager.removePropertiesPanel();
        }
    }
    
    private void copyCurrComp(int no)
    {
        System.out.println("DEBUG: Running GuiDSession- copyCurrComp");
        
        if(curr_sel!=null)
        {
            String props[][] = curr_sel.getPropertiesArray();
            String tmp[][] = new String[props.length][4];
            for(int i=0;i<props.length;i++)
            {
                for(int j=0;j<3;j++)
                    tmp[i][j] = props[i][j];
            }
            GuiDComponent gc = new GuiDComponent(curr_sel.getCast(),tmp,no,0,0,this);
            buf_vec.removeAllElements() ;
            buf_vec.add(gc);
        }
    }
    
    private void pasteComp()
    {
        System.out.println("DEBUG: Running GuiDSession- pasteComp");
        
        if(buf_vec.size()>0)
        {
            GuiDComponent gc=buf_vec.firstElement();
            mainPanel.addCmp(gc.getDispComponent());
            curr_sel=gc;
            comp_vec.add(gc);
            guiManager.updatePropertiesPanel(gc.getPropertiesPanel());
            updateCompSel(gc.getSelString());
            guiManager.invalidate();
        }
    }
    
    public void updateCompSel(String s)
    {
        System.out.println("DEBUG: Running GuiDSession- updateCompSel");
        
        mainPanel.setCmpSelection(s);
        mainPanel.invalidate();
    }
    
    public void changePropPanel(GuiDComponent gcmp)
    {
        System.out.println("DEBUG: Running GuiDSession- changePropPanel");
        
        boolean  flag=false;
        if(curr_sel==null)
        {
            flag=true;
        }
        else if(!curr_sel.equals(gcmp))
        {
            flag=true;
        }
        if(flag)
        {
            curr_sel=gcmp;
            guiManager.updatePropertiesPanel(gcmp.getPropertiesPanel());
            updateCompSel(gcmp.getSelString());
            guiManager.invalidate();
        }
    }

    public void finalize()
    {
        System.out.println("DEBUG: Running GuiDSession- finalize");
        
        try
        {
           super.finalize();    
        }
        catch (Throwable e)
        {
            System.out.println(e);
        }
    }
    
    /////////////////////////////////////////
    // EXPORT WINDOW
    ///////////////////////////////////////
    public void createExportWindow()
    {
        exportWindow = new JFrame();
        
        System.out.println("DEBUG: Running GuiDSession-createExportWindow");
                
        //Image img = exportWindow.getToolkit().createImage(getClass().getResource("images/GuiD_icon.png"));
        //exportWindow.setIconImage(img);
        exportWindow.setTitle("Export Project");        
        exportWindow.setLayout(new GridLayout(1,1));
        
        
        //CREATE PANEL//////////////////////
        System.out.println("DEBUG: Running GuiDSession-createExportPanel");
        
        int startY=10;
        int currentY=startY;
        int gap=60;    
        Font helpFont = new Font("Courier New", Font.ITALIC, 12);
    
            
        lblClassName.setBackground(new Color(255,238,150));    
        lblClassName.setLocation(30,startY);
        lblClassName.setSize(100,25);
        lblClassName.setOpaque(true);
        lblClassName.setText("Class Name:");
        lblClassName.setToolTipText("Fixed as JFrame Title");
        exportPanel.add(lblClassName);

        tfClassName.setLocation(150,startY);
        tfClassName.setSize(250,25);
        tfClassName.setText("");
        tfClassName.setEditable(false);
        tfClassName.setToolTipText("Fixed as JFrame Title");
        exportPanel.add(tfClassName);
        
        JLabel lblClassNameHelp = new JLabel();
        lblClassNameHelp.setText("*Fixed as JFrame Title (See Below)");
        lblClassNameHelp.setFont(helpFont);
        lblClassNameHelp.setForeground(Color.GRAY);
        lblClassNameHelp.setLocation(30,currentY+25);
        lblClassNameHelp.setSize(350,25);
        exportPanel.add(lblClassNameHelp);
        
        currentY = currentY+gap;
        
        lblPropTitle.setBackground(new Color(255,238,150));    
        lblPropTitle.setLocation(30,currentY);
        lblPropTitle.setSize(100,25);
        lblPropTitle.setOpaque(true);
        lblPropTitle.setText("JFrame Title:");
        lblPropTitle.setToolTipText("Fixed - Edit in main interface");
        exportPanel.add(lblPropTitle);

        tfFrameTitle.setLocation(150,currentY);
        tfFrameTitle.setSize(250,25);
        tfFrameTitle.setText("");
        lblPropTitle.setToolTipText("Fixed - Edit in main interface");
        tfFrameTitle.setEditable(false);
        exportPanel.add(tfFrameTitle);
        
        JLabel lblFrameTitleHelp = new JLabel();
        lblFrameTitleHelp.setText("Change using JFrame tab in Main Program");
        lblFrameTitleHelp.setFont(helpFont);
        lblFrameTitleHelp.setForeground(Color.GRAY);
        lblFrameTitleHelp.setLocation(30,currentY+25);
        lblFrameTitleHelp.setSize(350,25);
        exportPanel.add(lblFrameTitleHelp);
        
        
        currentY = currentY+gap;
        
        lblPath.setBackground(new Color(255,238,150));    
        lblPath.setLocation(30,currentY);
        lblPath.setSize(100,25);
        lblPath.setOpaque(true);
        lblPath.setText("Path:");
        exportPanel.add(lblPath);
        
        jtfPath.setLocation(150,currentY);
        jtfPath.setSize(250,25);
        jtfPath.setText("<Path>");
        jtfPath.setEditable(false);
        exportPanel.add(jtfPath);
        
        btnPath.setLocation(410,currentY); //next to tf above
        btnPath.setSize(150,25);
        btnPath.addActionListener(this);
        btnPath.setText("Select Path");
        btnPath.setBackground(Color.BLACK);
        btnPath.setForeground(Color.WHITE);
        exportPanel.add(btnPath);
        
        JLabel lblPathHelp = new JLabel();
        lblPathHelp.setText("*Must be set - Where do you want to save?");
        lblPathHelp.setFont(helpFont);
        lblPathHelp.setForeground(Color.GRAY);
        lblPathHelp.setLocation(30,currentY+25);
        lblPathHelp.setSize(350,25);
        exportPanel.add(lblPathHelp);
        
        currentY = currentY+gap;
        
        lblPanelName.setBackground(new Color(255,238,150));    
        lblPanelName.setLocation(30,currentY);
        lblPanelName.setSize(100,25);
        lblPanelName.setOpaque(true);
        lblPanelName.setText("Panel Name:");
        exportPanel.add(lblPanelName);
        
        jtfPanelName.setLocation(150,currentY);
        jtfPanelName.setSize(250,25);
        jtfPanelName.setText("panelOne");
        jtfPanelName.setEnabled(false);
        exportPanel.add(jtfPanelName);
        
        JLabel lblPanelNameHelp = new JLabel();
        lblPanelNameHelp.setText("*Name of JPanel - Start with lowercase?");
        lblPanelNameHelp.setFont(helpFont);
        lblPanelNameHelp.setForeground(Color.GRAY);
        lblPanelNameHelp.setLocation(30,currentY+25);
        lblPanelNameHelp.setSize(350,25);
        exportPanel.add(lblPanelNameHelp);
        
        currentY = currentY+gap;
            
        lblMethodName.setBackground(new Color(255,238,150));    
        lblMethodName.setLocation(30,currentY);
        lblMethodName.setSize(100,25);
        lblMethodName.setOpaque(true);
        lblMethodName.setText("Method Name:");
        exportPanel.add(lblMethodName);
        
        jtfMethodName.setLocation(150,currentY);
        jtfMethodName.setSize(250,25);
        jtfMethodName.setText("initPanelOne");
        jtfMethodName.setEnabled(false);
        exportPanel.add(jtfMethodName);
        
        JLabel lblMethodNameHelp = new JLabel();
        lblMethodNameHelp.setText("*Name of method to build panel - Start with lowercase, Use a verb");
        lblMethodNameHelp.setFont(helpFont);
        lblMethodNameHelp.setForeground(Color.GRAY);
        lblMethodNameHelp.setLocation(30,currentY+25);
        lblMethodNameHelp.setSize(350,25);
        exportPanel.add(lblMethodNameHelp);
        
        currentY = currentY+gap;
        
        lblObjectName.setBackground(new Color(255,238,150));    
        lblObjectName.setLocation(30,currentY);
        lblObjectName.setSize(100,25);
        lblObjectName.setOpaque(true);
        lblObjectName.setText("Object Name:");
        exportPanel.add(lblObjectName);
        
        jtfObjectName.setLocation(150,currentY);
        jtfObjectName.setSize(250,25);
        jtfObjectName.setText("obj");
        jtfObjectName.setEnabled(false);
        exportPanel.add(jtfObjectName);
        
        JLabel lblObjectNameHelp = new JLabel();
        lblObjectNameHelp.setText("*Name of object e.g. ezgui");
        lblObjectNameHelp.setFont(helpFont);
        lblObjectNameHelp.setForeground(Color.GRAY);
        lblObjectNameHelp.setLocation(30,currentY+25);
        lblObjectNameHelp.setSize(350,25);
        exportPanel.add(lblObjectNameHelp);
        
        currentY = currentY+gap;
        
        /*
        cbAddTabbedPane.setBackground(new Color(255,238,150));    
        cbAddTabbedPane.setLocation(30,currentY);
        cbAddTabbedPane.setSize(400,25);
        cbAddTabbedPane.setOpaque(true);
        cbAddTabbedPane.setText("Add JTabbedPane (disabled)");
        cbAddTabbedPane.setEnabled (false);
        exportPanel.add(cbAddTabbedPane);
        
        
        JLabel lblTabHelp = new JLabel();
        lblTabHelp.setText("*Add a JTabbedPane, ready for more screens later");
        lblTabHelp.setFont(helpFont);
        lblTabHelp.setForeground(Color.GRAY);
        lblTabHelp.setLocation(30,currentY+25);
        lblTabHelp.setSize(350,25);
        exportPanel.add(lblTabHelp);
        
        currentY = currentY+gap;
        */
        cbBatch.setBackground(new Color(255,238,150));    
        cbBatch.setLocation(30,currentY);
        cbBatch.setSize(400,25);
        cbBatch.setText("Create Batch File");
        cbBatch.setSelected(true);
        //cbBatch.setEnabled(false);
        exportPanel.add(cbBatch);

        JLabel lblBatchHelp = new JLabel();
        lblBatchHelp.setText("*Create a batch file to easily compile/run");
        lblBatchHelp.setFont(helpFont);
        lblBatchHelp.setForeground(Color.GRAY);
        lblBatchHelp.setLocation(30,currentY+25);
        lblBatchHelp.setSize(350,25);
        exportPanel.add(lblBatchHelp);
        
        currentY = currentY+gap;
        
        btnExport.setLocation(30,currentY);
        btnExport.setSize(530,50);
        btnExport.addActionListener(this);
        btnExport.setText("Export");
        btnExport.setBackground(Color.BLACK);
        btnExport.setForeground(Color.WHITE);
        exportPanel.add(btnExport);
        
        
        
        exportPanel.setBackground(new Color(255,238,150));    

        // END OF CREATE PANEL//////////////////////
        exportWindow.add(exportPanel);

    
        exportWindow.setSize(600,600); //when not maximized
        exportWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        exportWindow.setVisible(false);
    }
    
    public void showExportWindow(String tempTitle)
    {
        tempParentProperties = parentPropsPanel.getPropertiesArray();
        //Removed Local
        tfFrameTitle.setText(tempParentProperties[0][2]);
        tfClassName.setText(tempParentProperties[0][2]+".java"); //Could be changeable later
        exportWindow.setVisible(true);
    }
    
    public void createBatchFile(String filename,File theDirectory)
    {
        System.out.println("DEBUG: Running GuiDSession- createBatchFile");
        String theFile = theDirectory+"\\"+filename+"Batch"+".bat";
        
        try 
         {
                     

             File f = new File(theFile);
             f.createNewFile();

             FileWriter fw = new FileWriter(f);
             fw.write("javac "+filename+".java");
             fw.write("\r\n");
             fw.write("pause");
             fw.write("\r\n");
             fw.write("java "+filename);
             fw.write("\r\n");
             fw.write("pause");
             fw.write("\r\n");
             fw.close();
             
            JOptionPane.showMessageDialog(null,"Batch File Created: " + theFile);
            System.out.println("Batch File Created at: " + theFile);
            
            exportWindow.setVisible(false);
            //JOptionPane.showMessageDialog(null,"BUG FIX: Window hidden but not reset");
         } 
         catch (Exception ex) 
         {
             System.out.println("Error creating batch");
             ex.printStackTrace();
         }

     }

    public boolean exportValidation()
    {
        System.out.println("GuiDSession 974 : exportValidation method");
        //JOptionPane.showMessageDialog(null, "GuiDSession : exportValidation method");
        //Get All Values
        
        tempClassName = tfClassName.getText();
        tempClassName = tempClassName.toLowerCase();
        tempPanel = jtfPanelName.getText();
        tempMethod = jtfMethodName.getText();
        tempObject = jtfObjectName.getText();
        
        tfClassName.setBackground(SystemColor.text);
        jtfPath.setBackground(SystemColor.text);
        jtfPanelName.setBackground(SystemColor.text);
        jtfMethodName.setBackground(SystemColor.text);
        jtfObjectName.setBackground(SystemColor.text);
        
        //////NO CHANGED CLASSNAME    
        if(tempClassName.contains("notitleset"))
        {
            JOptionPane.showMessageDialog(null, "ClassName/JFrame title Not Set");
            tfClassName.setBackground(Color.RED);
            return false;
        }
        //PATH NOT SET
        else if(jf==null)
        {
            JOptionPane.showMessageDialog(null, "Validation Error: File Path Not Set");
            jtfPath.setBackground(Color.RED);
            return false;
        }
        //////NO CHANGED CLASSNAME    
        else if(tempClassName.equals("notitleset"))
        {
            JOptionPane.showMessageDialog(null, "ClassName/JFrame title Not Set");
            tfClassName.setBackground(Color.RED);
            return false;
        }
        //////EMPTY    
        else if(tempPanel.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Validation Error: Panel name is empty");
            jtfPanelName.setBackground(Color.RED);
            return false;
        }
        else if(tempMethod.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Validation Error: Method name is empty");
            jtfMethodName.setBackground(Color.RED);
            return false;
        }
        else if(tempObject.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Validation Error: Object name is empty");
            jtfObjectName.setBackground(Color.RED);
            return false;
        }
        ////////
        
        //CONTAINS SPACES
        else if(tempPanel.contains(" "))
        {
            JOptionPane.showMessageDialog(null, "Validation Error: Panel name contains spaces");
            jtfPanelName.setBackground(Color.RED);
            return false;
        }
        else if(tempMethod.contains(" "))
        {
            JOptionPane.showMessageDialog(null, "Validation Error: Method name contains spaces");
            jtfMethodName.setBackground(Color.RED);
            return false;
        }
        else if(tempObject.contains(" "))
        {
            JOptionPane.showMessageDialog(null, "Validation Error: Object name contains spaces");
            jtfObjectName.setBackground(Color.RED);
            return false;
        }
        ///////
        
        else
        {
            char tempPanelChar = tempPanel.charAt(0);
            char tempMethodChar = tempMethod.charAt(0);
            char tempObjectChar = tempObject.charAt(0);
            
            if(Character.isLowerCase(tempPanelChar)==false)
            {
                JOptionPane.showMessageDialog(null, "Validation Error: Panel name must begin with lowercase letter");
                jtfPanelName.setBackground(Color.RED);
                return false;
            }
            else if(Character.isLowerCase(tempMethodChar)==false)
            {
                JOptionPane.showMessageDialog(null, "Validation Error: Method name must begin with lowercase letter");
                jtfMethodName.setBackground(Color.RED);
                return false;
            }
            else if(Character.isLowerCase(tempObjectChar)==false)
            {
                JOptionPane.showMessageDialog(null, "Validation Error: Object name must begin with lowercase letter");
                jtfObjectName.setBackground(Color.RED);
                return false;
            }
                    
            else
            {
                return true;
            }
        }
    }
    
    
    public void runBatchScreen(String tempTitle)
    {
        System.out.println("DEBUG: Running GuiDSession-runBatchScreen");
        
        jfc = new JFileChooser();
        //jfc.setCurrentDirectory(new File("#UserFiles"));
        
        
        jfc.setCurrentDirectory(jfc.getFileSystemView().getParentDirectory(new File("C:\\")));
        //jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //jfc.showDialog(button, "Select file");
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Java Source File", "java");
        jfc.setFileFilter(filter);
        tempBatchFilename = tempTitle;
        File f = new File(tempBatchFilename+".java");
        jfc.setSelectedFile(f);
                        
        if(jfc.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
        {
            jf = jfc.getSelectedFile();
            path = jf.getAbsolutePath();
                                
            if((path.endsWith(".java")==false))
            {
                path+=".java";
                jf = new File(path);
            }                        
        }
    }
       
    /////////////////////////////////////////
    // EXPORT WINDOW
    ///////////////////////////////////////
    
    
    public void displayParentProps() //Debug to show properties
    {
        System.out.println("DEBUG: Running GuiDSession-displayParentProps");
        
        tempParentProperties = parentPropsPanel.getPropertiesArray();
        
        for(int i=0;i<tempParentProperties.length;i++)
        {
            System.out.println("");
            System.out.print(" "+tempParentProperties[i][0]+" ");
            System.out.print(" "+tempParentProperties[i][1]+" ");
            System.out.print(" "+tempParentProperties[i][2]+" ");
        }
    }
    
    public int[] getWindowSize() {
        return this.parentPropsPanel.getDims();
    }
}
