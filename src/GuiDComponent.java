//package JGUID.Session;
//import JGUID.*;
//import Util.*;
//import UI.GuiDUIPropertiesPanel;
// LINE 650

import java.util.Random;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.text.JTextComponent;
public class GuiDComponent
{
    private String props[][];
    private String componentType;
    private int x;
    private int y;
    GuiDUIPropertiesPanel uiPropertiesPanel;
    JComponent newComponent;
    
    MouseEvent pressed;
    Point location;
    private int width;
    private int height;
    boolean set;
    iGuiDPropMessenger igpm;
    private int re_cur;
    
    String compName;
    String compText;
    
    Robot robot; //Used for keypress after attribute change
    
    public final int PROPERTYNAME=0;
    public final int PROPERTYTYPE=1;
    public final int PROPERTYVALUE=2;
    
    public final int VARNAME=0;
    public final int XLOCATION=1;
    public final int YLOCATION=2;
    public final int WIDTH=3;
    public final int HEIGHT=4;
    public final int FOREGROUND=5;
    public final int BACKGROUND=6;
    public final int VISIBLE=7;
    public final int ALLCONTENT=8;
    
    //PROPS 8-10 VARY BY COMPONENT
    public final int JLBLLABEL=8;
    
    public final int JTXTFDTEXT=8;
    public final int JTXTFDCOLS=9;
    
    public final int JBTNLABEL=8;
    
    public final int JCHECKLABEL=8;
    public final int JCHECKSELECTED=9;
    
    public final int JRADTEXT=8;
    public final int JRADSELECTED=9;
    
    public final int JPASSTEXT=8;
    public final int JPASSCOLS=9;
    public final int JPASSECHO=10;
    
    public final int JTXTARTEXT=8;
    public final int JTXTARROWS=9;
    public final int JTXTARCOLS=10;
    
    public final int JLISTCONTENT=8;    
    
    public final int JCMBCONTENT=8;
    public final int JCMBEDITABLE=9;
    
    //END OF PROPS 8-10
    
    public final int TOOLTIPTEXT=11;  
    public final int USECENTERFUNCTIONX=12;
    public final int CENTERFUNCTIONXOFFSET=13;
    public final int USECENTERFUNCTIONY=14;
    public final int CENTERFUNCTIONYOFFSET=15;
    
    
    public GuiDComponent(String componentType,int no,int x,int y,iGuiDPropMessenger gpm)
    {
        System.out.println("--------Inside GUIDComponent Constructor - NO PROPS ARRAY");
        System.out.println("--------New component?");
        this.componentType = componentType;
        System.out.println("COMPONENT TYPE: "+this.componentType);
        this.x = x;
        this.y = y;
        igpm=gpm;
        set=false;

        props = GuiDFileReaderProperties.getProperties(componentType,no,x,y);
        if(componentType.contains("Text")||componentType.equals("JPasswordField")||componentType.contains("List"))
        {
            props[BACKGROUND][PROPERTYVALUE]="-1";
        }
        enterComponentDetails();
        createComponent();
        createPropertiesPanel();
        makeMovable();
    }
    
    public GuiDComponent(String componentType,String tempprops[][],int no,int x,int y,iGuiDPropMessenger gpm)
    {
        System.out.println("--------Inside GUIDComponent Constructor with props array");
        System.out.println("--------Loading a previously saved project?");
        this.componentType = componentType;
        System.out.println("COMPONENT TYPE: "+this.componentType);
        this.x = x;
        this.y = y;
        //System.out.println("DEBUG: GUIDComponent Constructor with props array2 ");
        igpm=gpm;
        this.props = tempprops;
        //System.out.println("DEBUG: GUIDComponent Constructor with props array3 ");
        if(no!=-1)
        {
          props[VARNAME][PROPERTYVALUE] += "_"+String.valueOf(no);
          props[XLOCATION][PROPERTYVALUE] = String.valueOf(x);
          props[YLOCATION][PROPERTYVALUE] = String.valueOf(y);
        }
        set=false;
        System.out.println("DEBUG: GUIDComponent Constructor with props array4 ");
        createComponent();
        System.out.println("DEBUG: GUIDComponent Constructor with props array5 ");
        createPropertiesPanel();
        System.out.println("DEBUG: GUIDComponent Constructor with props array6 ");
        makeMovable();
        System.out.println("DEBUG: GUIDComponent Constructor with props array7 ");
    }
    
        
    public void finalize()
    {
        try
        {
           super.finalize();    
        }
        catch (Throwable e)
        {
            System.out.println(e);
        }
        
    }
    
    private void makeMovable()
    {
        newComponent.addMouseListener(new MouseAdapter()
        {
                public void mousePressed(MouseEvent me)
                {
                    pressed = me;
                    width = newComponent.getWidth();
                    height = newComponent.getHeight();

                }
            });
        newComponent.addMouseMotionListener(new MouseMotionAdapter(){
            
            public void mouseMoved(MouseEvent e) 
            {
                    int xh= newComponent.getX()+newComponent.getWidth();
                    int yh = newComponent.getY()+newComponent.getHeight();
                    set=false;
                    if(e.getX()<=(xh-re_cur)&&(e.getX()>=newComponent.getWidth()-(10+re_cur))&&e.getY()<=yh&&(e.getY()>=newComponent.getHeight()-10))
                    {
                        newComponent.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
                        set=true;
                    }
                    else
                    {
                        newComponent.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }

            }
            public void mouseDragged(MouseEvent me)
            {

                if(!set)
                {
                    location = newComponent.getLocation(location);
                    int x = location.x - pressed.getX() + me.getX();
                    int y = location.y - pressed.getY() + me.getY();
                    if(x<0)
                        x=0;
                    if(y<0)
                        y=0;
                    if(x>(newComponent.getParent().getWidth()-newComponent.getWidth()))
                        x=newComponent.getParent().getWidth()-newComponent.getWidth();
                    if(y>(newComponent.getParent().getHeight()-newComponent.getHeight()))
                        y=newComponent.getParent().getHeight()-newComponent.getHeight();
                    newComponent.setLocation(x, y);
                    if(!(newComponent instanceof JTextComponent))
                         ((JComponent)newComponent).updateUI();
                    Component tmp[] = uiPropertiesPanel.getPropComponents();
                    ((JTextField)tmp[3]).setText(String.valueOf(x));
                    ((JTextField)tmp[5]).setText(String.valueOf(y));
                    props[XLOCATION][PROPERTYVALUE]=String.valueOf(x);
                    props[YLOCATION][PROPERTYVALUE]=String.valueOf(y);
                }
                else
                {
                    int w = width+me.getX()-pressed.getX();
                    int h = height+me.getY()-pressed.getY();
                    if(w<25)
                    {
                        w=25;
                    }
                    if(h<25)
                    {
                        h=25;
                    }
                    newComponent.setSize(w,h);
                    if(!(newComponent instanceof JTextComponent))
                     ((JComponent)newComponent).updateUI();
                    Component tmp[] = uiPropertiesPanel.getPropComponents();
                    ((JTextField)tmp[7]).setText(String.valueOf(w));
                    ((JTextField)tmp[9]).setText(String.valueOf(h));
                    props[WIDTH][PROPERTYVALUE]=String.valueOf(w);
                    props[HEIGHT][PROPERTYVALUE]=String.valueOf(h);
                }
             }
        });
    }
    
    public String[][] getPropertiesArray()
    {
        return props;
    }
    
    public String getCast()
    {
        return componentType;
    }
    
    public String getVariableName()
    {
        return props[VARNAME][PROPERTYVALUE];
    }
    
    public String getSelString()
    {
        return props[VARNAME][PROPERTYVALUE]+"( "+componentType+" )";
    }
    
    public GuiDUIPropertiesPanel getPropertiesPanel()
    {
        return uiPropertiesPanel;
    }
    
    public JComponent getDispComponent()
    {
        return newComponent;
    }
    
    
    public void enterComponentDetails()
    {
        compName="";
        compText="";
        
        boolean compNameAccepted=false;
        
        while(compNameAccepted==false)
        {
            compName = JOptionPane.showInputDialog(null, componentType + " name? e.g. lblName (No Spaces)", "Create "+componentType, JOptionPane.QUESTION_MESSAGE);
                
            if(compName.equals(""))
            {
                compNameAccepted=false;
                JOptionPane.showMessageDialog(null, "None Entered", "Naming Error", JOptionPane.WARNING_MESSAGE);
            }
            
            else if(compName.isEmpty()==false)
            {
                char tempFirstChar = compName.charAt(0);
                
                            
                if(compName.contains(" "))
                {
                    compNameAccepted=false;
                    JOptionPane.showMessageDialog(null, "Spaces not allowed", "Naming Error", JOptionPane.WARNING_MESSAGE);
                }
                
                                
                else if(Character.isLowerCase(tempFirstChar)==false && tempFirstChar!='_')
                {
                    compNameAccepted=false;
                    JOptionPane.showMessageDialog(null, "Must begin with lowercase letter or underscore", "Naming Error", JOptionPane.WARNING_MESSAGE);
                }
                                    
                else 
                {
                    compNameAccepted=true;
                    props[VARNAME][PROPERTYVALUE] = compName; //setName
                }
            }
        }
        
            
        
        if(componentType.equals("JList"))
        {
            JOptionPane.showMessageDialog(null, "Remember to setup a list model later", "Create "+componentType, JOptionPane.WARNING_MESSAGE);
            compText = "Item1,Item2,Item3";
        }

        else if(componentType.equals("JComboBox"))
        {
            //Bypass Combo
            //compText = (String)JOptionPane.showInputDialog(null, componentType + " options? e.g. Orange,Green,Blue", "Create "+componentType, JOptionPane.QUESTION_MESSAGE,null,null,"Orange,Green,Blue");
            compText="Option1,Option2,Option3";
            //if(compText.equals(""))
            //{
            //    compText = "NOTENTERED";
            //}
        }
        
        else if(componentType.equals("JTextArea")||componentType.equals("JTextField"))
        {
            //JOptionPane.showMessageDialog(null, "Default Value Disabled"+
            //"\r\n\r\n Please add manually later", "Create "+componentType, JOptionPane.WARNING_MESSAGE);
            compText = "";
        }
        
        //text
        else
        {
            
            compText = JOptionPane.showInputDialog(null, componentType + " text? e.g. Customer Name:", "Create "+componentType, JOptionPane.QUESTION_MESSAGE);
                    
            if(compText.equals(""))
            {
                compText="NOTSET";
            }
        }
        
        props[ALLCONTENT][PROPERTYVALUE] = compText;
        
    }
    
    
    //Set Type for Component
    private void createComponent()
    {
        System.out.println("DEBUG: Creating Component in GuiDComponent.java 344");
        System.out.println("DEBUG: Component type is "+componentType);    
        props[USECENTERFUNCTIONX][PROPERTYVALUE] = "false";
        props[USECENTERFUNCTIONY][PROPERTYVALUE] = "false";
        
        if(componentType.equals("JButton"))
        {
                newComponent = new JButton(props[JBTNLABEL][PROPERTYVALUE]);
        }
        else if(componentType.equals("JLabel"))
        {
                newComponent = new JLabel(props[JLBLLABEL][PROPERTYVALUE]);
        }
        else if(componentType.equals("JCheckBox")) //MSO:CheckBox->Checkbox
        {
                newComponent = new JCheckBox(props[JCHECKLABEL][PROPERTYVALUE]);
                ((JCheckBox)newComponent).setBorderPainted(true);
        }
        else if(componentType.equals("JTextField"))
        {
            //if(props[JTXTFDTEXT][PROPERTYVALUE].equals("$")==false)
            props[JTXTFDTEXT][PROPERTYVALUE]="TEST";
            //{
            //    newComponent = new JTextField(props[JTXTFDTEXT][PROPERTYVALUE],Integer.parseInt(props[JTXTFDCOLS][PROPERTYVALUE])); //cols?
            //}
            //else
            //{
                //newComponent = new JTextField(Integer.parseInt(props[JTXTFDCOLS][PROPERTYVALUE])); //cols?
            //}
            System.out.println("339 in GUIDComponent - crash fix");
            //if(props[JTXTFDTEXT][PROPERTYVALUE]==null)
            //{
            //    System.out.println("340 in GUIDComponent - crash fix 2");
            //}
            //System.out.println("342 in GUIDComponent - crash fixnew");
            //newComponent = new JTextField(Integer.parseInt(props[JTXTFDTEXT][PROPERTYVALUE])); 
            //String temp= props[JTXTFDTEXT][PROPERTYVALUE];
            //newComponent = new JTextField(temp); 
            
            System.out.println("342 in GUIDComponent - crash fix3");
            newComponent = new JTextField(""); 
        }
        else if(componentType.equals("JTextArea"))
        {
            props[JTXTARTEXT][PROPERTYVALUE]="";
            newComponent = new JTextArea(props[JTXTARTEXT][PROPERTYVALUE],Integer.parseInt(props[JTXTARROWS][PROPERTYVALUE]),Integer.parseInt(props[JTXTARCOLS][PROPERTYVALUE]));
        }
        else if(componentType.equals("JList"))
        {
            System.out.println("DEBUG 390 : Data not imported for a JList");
            String[] tempListData = {"Imported1","Imported2","Imported3"};
            newComponent = new JList(tempListData);
        }
        else if(componentType.equals("JComboBox"))
        {
            System.out.println("DEBUG 395 : Component type is "+componentType);
            System.out.println("DEBUG 396 : Comptext is "+compText);
            System.out.println("DEBUG 397 : Content is "+props[JCMBCONTENT][PROPERTYVALUE]);
            String tempContent = props[JCMBCONTENT][PROPERTYVALUE];
            String[] tempData =tempContent.split(",");
            
            
            System.out.println("**************** 402 Trial : + "+tempData[0]+"-"+tempData[1]);
            newComponent = new JComboBox<>(tempData);
        }
        else if(componentType.equals("JRadioButton"))
        {
            newComponent = new JRadioButton(props[JRADTEXT][PROPERTYVALUE]);
            ((JRadioButton)newComponent).setBorderPainted(true);
        }
        else if(componentType.equals("JPasswordField"))
        {
                newComponent = new JPasswordField(props[JPASSTEXT][PROPERTYVALUE],Integer.parseInt(props[JPASSCOLS][PROPERTYVALUE]));

                ((JPasswordField)newComponent).setEchoChar(props[JPASSECHO][PROPERTYVALUE].charAt(0));
                ((JPasswordField)newComponent).setEchoChar('#');
        }
    
        if(newComponent instanceof JComboBox)
        {
            re_cur =20;
        }
        else
        {
            re_cur = 0;
        }
        newComponent.setLocation(Integer.parseInt(props[XLOCATION][PROPERTYVALUE]),Integer.parseInt(props[YLOCATION][PROPERTYVALUE]));
        newComponent.setSize(Integer.parseInt(props[WIDTH][PROPERTYVALUE]),Integer.parseInt(props[HEIGHT][PROPERTYVALUE]));
        ((JComponent)newComponent).setOpaque(true);
        if(componentType.startsWith("J"))
          ((JComponent)newComponent).setBorder(BorderFactory.createLineBorder(Color.black));
        else
          ((JComponent)newComponent).setBorder(BorderFactory.createLineBorder(Color.yellow));    

        newComponent.setForeground(new Color(Integer.parseInt(props[FOREGROUND][PROPERTYVALUE])));
        newComponent.setBackground(new Color(Integer.parseInt(props[BACKGROUND][PROPERTYVALUE])));

        newComponent.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent cme)
            {
                requestPanelChange();
            }
            public void mouseExited(MouseEvent me)
            {
                if(me.getSource() instanceof JToggleButton)
                    mouseClicked(me);
            }
            public void mouseClicked(MouseEvent cme)
            {
                Component tmp = (Component)cme.getSource();
                if(tmp instanceof JToggleButton)
                {
                    JComboBox tm=(JComboBox)uiPropertiesPanel.getPropComponent(9);
                    tm.setSelectedItem(String.valueOf(((JToggleButton)newComponent).isSelected()));
                }

            }
        });
        if((newComponent instanceof JTextArea)||(newComponent instanceof JTextField))
            ((JTextComponent)newComponent).setEditable(false);
    }
    
    public void requestPanelChange()
    {
        igpm.changePropPanel(this);
    }
    
    
    private void createPropertiesPanel()
    {
        try
        {
            robot = new Robot(); 
        }
        catch (Exception e)
        {
            System.out.println("#RobotProbs");
        }
        
        uiPropertiesPanel = new GuiDUIPropertiesPanel();
        uiPropertiesPanel.setPropTitle(componentType);

        printProps();
        
        

//VARNAME///////////////////////////////////////////////////////////////
        JTextField p1 = new JTextField(props[VARNAME][PROPERTYVALUE]);
        
        if(p1.getText().equals("NOTSET") || p1.getText().equals(""))
        {
            p1.setBackground(Color.RED);
        }
        
        else
        {
            p1.setBackground(Color.WHITE);
        }
        
        
        p1.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent ke)
            {
            
                JTextField tm = (JTextField)ke.getSource();
                String tempVarName = tm.getText();

                boolean nameOK=false;
                                
                
                robot.keyPress(KeyEvent.VK_ENTER); //Enter to update property
                    
                
                if(tempVarName.isEmpty()==true)
                {
                    tm.setBackground(Color.RED);
                    nameOK=false;
                }

                else if(tempVarName.isEmpty()==false)
                {
                    char tempFirstChar = tempVarName.charAt(0);
                    
                    if(tempVarName.equals("NOTSET"))
                    {
                        tm.setBackground(Color.RED);
                        nameOK=false;
                    }
                                    
                    else if(tempVarName.contains(" "))
                    {
                        tm.setBackground(Color.RED);
                        nameOK=false;
                    }
                    
                                    
                    else if(Character.isLowerCase(tempFirstChar)==false && tempFirstChar!='_')
                    {
                        tm.setBackground(Color.RED);
                        nameOK=false;
                    }
                                        
                    else 
                    {
                        tm.setBackground(Color.WHITE);
                        nameOK=true;
                    }
                }
                
                //new IF - final check
                    
                if(nameOK==true)
                {
                    props[VARNAME][PROPERTYVALUE] = tm.getText();
                    igpm.updateCompSel(props[VARNAME][PROPERTYVALUE]+"( "+componentType+" )");
                    
                }    
                
            }
        });
        uiPropertiesPanel.addProperty(new JLabel("Variable Name"),p1);
///////////////////////////////////////////////////////////////////
        
        
//X LOCATION///////////////////////////////////////////////////////////////        
        JTextField p2 = new JTextField(props[XLOCATION][PROPERTYVALUE]);
        p2.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent ke)
            {
                            
                //////////////////////////
                JTextField tm = (JTextField)ke.getSource();
                boolean sizeOK=false;
                System.out.println("Keypressed");
                String validLocation = "";
                                
                if(tm.getText().isEmpty()==true)
                {
                    tm.setBackground(Color.RED);
                    sizeOK=false;
                }
                
                else if(tm.getText().isEmpty()==false)
                {
                    try {
                        robot.keyPress(KeyEvent.VK_ENTER); //Enter to update property
                        // Perform new X Validator
                        String internalText = tm.getText();
                        int validateXLocation = -1;
                        if(internalText.startsWith("CENTER")) {
                            System.out.println("Center added");
                            validateXLocation = (igpm.getWindowSize()[0] / 2) - (Integer.parseInt(props[WIDTH][PROPERTYVALUE]) / 2);
                            validLocation = "" + validateXLocation;
                            props[USECENTERFUNCTIONX][PROPERTYVALUE] = "true";
                            props[CENTERFUNCTIONXOFFSET][PROPERTYVALUE] = "0";
                        }
                        else {
                            validateXLocation = Integer.parseInt(tm.getText());
                            validLocation = tm.getText();
                            props[USECENTERFUNCTIONX][PROPERTYVALUE] = "false";
                        }
                        
                        
                        if(validateXLocation<0)
                        {
                            tm.setBackground(Color.RED);
                            //tm.setText("10");
                            sizeOK=false;
                            
                        }
                        else if(validateXLocation>2000)
                        {
                            tm.setBackground(Color.RED);
                            //tm.setText("1000");
                            sizeOK=false;
                        }
                        else 
                        {
                            tm.setBackground(Color.WHITE);
                            sizeOK=true;
                        }
                    } catch(Exception e) {
                        sizeOK=false;
                        tm.setBackground(Color.RED);
                    }
                }
                if(sizeOK==true)
                {
                    props[XLOCATION][PROPERTYVALUE] = validLocation;
                    newComponent.setLocation(Integer.parseInt(props[XLOCATION][PROPERTYVALUE]),Integer.parseInt(props[YLOCATION][PROPERTYVALUE]));
                    newComponent.updateUI();
                    newComponent.validate();
                    
                }
                
                ////////////////
            }
        });
        uiPropertiesPanel.addProperty(new JLabel("Location X"),p2);
        ///////////////////////////////////////////////////////////////////
        
        //Y LOCATION///////////////////////////////////////////////////////////////            
        JTextField p3 = new JTextField(props[YLOCATION][PROPERTYVALUE],10);
        p3.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent ke)
            {
                //////////////////////////
                JTextField tm = (JTextField)ke.getSource();
                boolean sizeOK=false;
		String validLocation = "";
                
                if(tm.getText().isEmpty()==true)
                {
                    tm.setBackground(Color.RED);
                    sizeOK=false;
                }
                
                else if(tm.getText().isEmpty()==false)
                {
                    try {
                        robot.keyPress(KeyEvent.VK_ENTER); //Enter to update property
                        //int validateYLocation = Integer.parseInt(tm.getText());
                        String internalText = tm.getText();
                        int validateYLocation = -1;
                        if(internalText.startsWith("CENTER")) {
                            System.out.println("Center Added");
                            validateYLocation = (igpm.getWindowSize()[1] / 2) - (Integer.parseInt(props[HEIGHT][PROPERTYVALUE]) / 2);
                            validLocation = "" + validateYLocation;
                            props[USECENTERFUNCTIONY][PROPERTYVALUE] = "true";
                            props[CENTERFUNCTIONYOFFSET][PROPERTYVALUE] = "0";
                        } else {
                            validateYLocation = Integer.parseInt(internalText);
                            validLocation = internalText;
                            props[USECENTERFUNCTIONY][PROPERTYVALUE] = "false";
                        }
                        		
                        		
                        if(validateYLocation<0)
                        {
                        	tm.setBackground(Color.RED);
                        	//tm.setText("10");
                        	sizeOK=false;
                        			
                        }
                        else if(validateYLocation>2000)
                        {
                        	tm.setBackground(Color.RED);
                        	//tm.setText("1000");
                        	sizeOK=false;
                        }
                        else 
                        {
                        	tm.setBackground(Color.WHITE);
                        	sizeOK=true;
                        }
                    } catch(Exception e) {
                    	sizeOK=false;
                    	tm.setBackground(Color.RED);
                    }
                }
                if(sizeOK==true)
                {
                    props[YLOCATION][PROPERTYVALUE] = validLocation;
                    newComponent.setLocation(Integer.parseInt(props[XLOCATION][PROPERTYVALUE]),Integer.parseInt(props[YLOCATION][PROPERTYVALUE]));
                    newComponent.updateUI();
                    newComponent.validate();
                    
                }
                
                ////////////////
            }
        });
        uiPropertiesPanel.addProperty(new JLabel("Location Y"),p3);
    ///////////////////////////////////////////////////////////////////
    
//WIDTH///////////////////////////////////////////////////////////////
        JTextField p4 = new JTextField(props[WIDTH][PROPERTYVALUE],10);
        p4.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent ke)
            {
                JTextField tm = (JTextField)ke.getSource();
                boolean sizeOK=false;
                
                if(tm.getText().isEmpty()==true)
                {
                    tm.setBackground(Color.RED);
                    sizeOK=false;
                }
                
                else if(tm.getText().isEmpty()==false)
                {
                    robot.keyPress(KeyEvent.VK_ENTER); //Enter to update property
                    int validateWidth = Integer.parseInt(tm.getText());
                    
                    
                    if(validateWidth<10)
                    {
                        tm.setBackground(Color.RED);
                        //tm.setText("10");
                        sizeOK=false;
                        
                    }
                    else if(validateWidth>2000)
                    {
                        tm.setBackground(Color.RED);
                        //tm.setText("1000");
                        sizeOK=false;
                    }
                    else 
                    {
                        tm.setBackground(Color.WHITE);
                        sizeOK=true;
                    }
                }
                if(sizeOK==true)
                {
                    System.out.println("DEBUG: HAPPENS AT LINE 712 OF GuiDComponent");
                    props[WIDTH][PROPERTYVALUE] = tm.getText();
                    newComponent.setSize(Integer.parseInt(props[WIDTH][PROPERTYVALUE]),Integer.parseInt(props[HEIGHT][PROPERTYVALUE]));
                    newComponent.updateUI();
                    newComponent.validate();
                    
                }
            }
        });
        uiPropertiesPanel.addProperty(new JLabel("Width"),p4);
    ///////////////////////////////////////////////////////////////////
        
//HEIGHT///////////////////////////////////////////////////////////////            
        JTextField p5 = new JTextField(props[HEIGHT][PROPERTYVALUE],10);
        p5.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent ke)
            {
                JTextField tm = (JTextField)ke.getSource();
                boolean sizeOK=false;        
                
                if(tm.getText().isEmpty()==true)
                {
                    tm.setBackground(Color.RED);
                    sizeOK=false;
                }
                
                else if(tm.getText().isEmpty()==false)
                {
                    robot.keyPress(KeyEvent.VK_ENTER); //Enter to update property
                    int validateHeight = Integer.parseInt(tm.getText());
                    
                    
                    
                    if(validateHeight<10)
                    {
                        tm.setBackground(Color.RED);
                        //tm.setText("10");
                        sizeOK=false;
                        
                    }
                    else if(validateHeight>2000)
                    {
                        tm.setBackground(Color.RED);
                        //tm.setText("1000");
                        sizeOK=false;
                    }
                    else 
                    {
                        tm.setBackground(Color.WHITE);
                        sizeOK=true;
                        
                    }
                }
                if(sizeOK==true)
                {
                    props[HEIGHT][PROPERTYVALUE] = tm.getText();
                    newComponent.setSize(Integer.parseInt(props[WIDTH][PROPERTYVALUE]),Integer.parseInt(props[HEIGHT][PROPERTYVALUE]));
                    newComponent.updateUI();
                    newComponent.validate();
                    
                }
                
            }
        });
        uiPropertiesPanel.addProperty(new JLabel("Height"),p5);
        ///////////////////////////////////////////////////////////////////
        
        //FOREGROUND COLOUR///////////////////////////////////////////////////////////////    
        JButton p6 = new JButton("Frg Color");
        p6.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent ke)
            {
                Color col = JColorChooser.showDialog(null,"Foreground Color Chooser",new Color(Integer.parseInt(props[FOREGROUND][PROPERTYVALUE])));
                if(col!=null)
                {
                    props[FOREGROUND][PROPERTYVALUE]=String.valueOf(col.getRGB());
                    newComponent.setForeground(col);
                    newComponent.updateUI();
                    newComponent.validate();
                }
            }
        });
        uiPropertiesPanel.addProperty(new JLabel("Foreground Color"),p6);
        ///////////////////////////////////////////////////////////////////
    
        //BACKGROUND COLOUR///////////////////////////////////////////////////////////////
        JButton p7 = new JButton("Bck Color");
        p7.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent ke)
            {
                Color col = JColorChooser.showDialog(null,"Background Color Chooser",new Color(Integer.parseInt(props[BACKGROUND][PROPERTYVALUE])));
                if(col!=null)
                {
                    props[BACKGROUND][PROPERTYVALUE]=String.valueOf(col.getRGB());
                    newComponent.setBackground(col);
                    newComponent.updateUI();
                    newComponent.validate();
                }
            }
        });
        uiPropertiesPanel.addProperty(new JLabel("Background Color"),p7);
        ///////////////////////////////////////////////////////////////////
    
        //VISIBLE///////////////////////////////////////////////////////////////    
        String bl[]={"true","false"};
        JComboBox p8 = new JComboBox(bl);
        p8.setEnabled(false);
        p8.setToolTipText("Disabled");
        p8.addItemListener(new ItemAdapter()
        {
            public void itemStateChanged(ItemEvent e)
            {
                robot.keyPress(KeyEvent.VK_ENTER); //Enter to update property
                JComboBox tm = (JComboBox)e.getSource();
                props[VISIBLE][PROPERTYVALUE] = String.valueOf(tm.getSelectedItem());
            }
        });
        //uiPropertiesPanel.addProperty(new JLabel("Visible"),p8);
        JLabel temp81 = new JLabel("");
        temp81.setToolTipText("Visibility Removed: Always True");
        JLabel temp82 = new JLabel("");
        temp82.setToolTipText("Visibility Removed: Always True");
        uiPropertiesPanel.addProperty(temp81,temp82);
        //uiPropertiesPanel.addProperty(new JLabel(""),p8);
        ///////////////////////////////////////////////////////////////////

        
        for(int i=8;props[i][PROPERTYNAME]!=null;i++) //FOR LOOP FIXED = LOOPS THROUGH REMAINING ATTRIBUTES
        {
            if((i==8 && componentType.equalsIgnoreCase("JTextField")) || (i==8 && componentType.equalsIgnoreCase("JPasswordField"))|| (i==8 && componentType.equalsIgnoreCase("JTextArea")))
            {
                JTextField pi1 = new JTextField(props[JTXTFDTEXT][PROPERTYVALUE]);
                pi1.setToolTipText("Disabled: Blank Only");
                pi1.setText("WONTSAVE");
                pi1.setEnabled(false);
                
                JLabel temp1 = new JLabel("");
                temp1.setToolTipText("Default Text Removed for JTextField/JPasswordField/JTextArea");
                JLabel temp2 = new JLabel("");
                temp2.setToolTipText("Default Text Removed for JTextField/JPasswordField/JTextArea");
                uiPropertiesPanel.addProperty(temp1,temp2);
                //uiPropertiesPanel.addProperty(new JLabel(props[i][PROPERTYNAME]),pi1);
            }
            
            else if(i==8 && componentType.equalsIgnoreCase("JList"))
            {
                JTextField pi1 = new JTextField(props[JLISTCONTENT][PROPERTYVALUE]);
                pi1.setToolTipText("Disabled: Blank Only");
                pi1.setText("0");
                pi1.setEnabled(false);
                
                JLabel temp1 = new JLabel("");
                temp1.setToolTipText("Editable Removed for JList");
                JLabel temp2 = new JLabel("");
                temp2.setToolTipText("Editable Removed for JList");
                uiPropertiesPanel.addProperty(temp1,temp2);                                
                //uiPropertiesPanel.addProperty(new JLabel(props[i][PROPERTYNAME]),pi1);
            }
            
            else if((i==9 && componentType.equalsIgnoreCase("JTextField")) || (i==9 && componentType.equalsIgnoreCase("JPasswordField"))|| (i==9 && componentType.equalsIgnoreCase("JTextArea")))
            {
                JTextField pi1 = new JTextField(props[JTXTFDCOLS][PROPERTYVALUE]);
                pi1.setToolTipText("Disabled: Blank Only");
                pi1.setText("0");
                pi1.setEnabled(false);
                
                JLabel temp1 = new JLabel("");
                temp1.setToolTipText("Cols Removed for JTextField/JPasswordField/JTextArea");
                JLabel temp2 = new JLabel("");
                temp2.setToolTipText("Cols Removed for JTextField/JPasswordField/JTextArea");
                uiPropertiesPanel.addProperty(temp1,temp2);                                
                //uiPropertiesPanel.addProperty(new JLabel(props[i][PROPERTYNAME]),pi1);
            }
            
            
            else if(i==JCMBEDITABLE && componentType.equals("JComboBox"))
            {
                JTextField pi1 = new JTextField(props[JTXTFDCOLS][PROPERTYVALUE]);
                pi1.setToolTipText("Disabled: Blank Only");
                pi1.setText("0");
                pi1.setEnabled(false);
                
                JLabel temp1 = new JLabel("");
                temp1.setToolTipText("Editable Removed for JComboBox");
                JLabel temp2 = new JLabel("");
                temp2.setToolTipText("Editable Removed for JComboBox");
                uiPropertiesPanel.addProperty(temp1,temp2);                                
                //uiPropertiesPanel.addProperty(new JLabel(props[i][PROPERTYNAME]),pi1);
            }
            
            
            else if((i==10 && componentType.equalsIgnoreCase("JPasswordField"))||(i==10 && componentType.equalsIgnoreCase("JTextArea")))
            {
                JTextField pi1 = new JTextField(props[JPASSECHO][PROPERTYVALUE]);
                pi1.setToolTipText("Disabled: Blank Only");
                pi1.setText("0");
                pi1.setEnabled(false);
                
                JLabel temp1 = new JLabel("");
                temp1.setToolTipText("Removed for JPasswordField/JTextArea");
                JLabel temp2 = new JLabel("");
                temp2.setToolTipText("Removed for JPasswordField/JTextArea");
                uiPropertiesPanel.addProperty(temp1,temp2);                                
                //uiPropertiesPanel.addProperty(new JLabel(props[i][PROPERTYNAME]),pi1);
            }
            
            else if(props[i][PROPERTYTYPE].equals("Boolean")) //JComboBox or RadioButton
            {
                    String bi[]={"false","true"};
                    JComboBox pi = new JComboBox(bi);
                    pi.addItemListener(new ItemAdapter()
                    {
                        public void itemStateChanged(ItemEvent e)
                        {
                            robot.keyPress(KeyEvent.VK_ENTER); //Enter to update property
                            JComboBox tm = (JComboBox)e.getSource();
                            int j=uiPropertiesPanel.getCompIndex(tm);
                            props[j][PROPERTYVALUE] = String.valueOf(tm.getSelectedItem());
                                                        
                            if((newComponent instanceof JCheckBox) || (newComponent instanceof JRadioButton))
                            {
                                ((JToggleButton)newComponent).setSelected(Boolean.parseBoolean(props[j][PROPERTYVALUE]));
                            }
                            else if (componentType.equals("JComboBox"))
                            {
                                ((JComboBox)newComponent).setEditable(Boolean.parseBoolean(props[j][PROPERTYVALUE]));
                                if(!Boolean.parseBoolean(props[j][PROPERTYVALUE]))
                                    if (((JComboBox)newComponent).getItemCount()!=0 )
                                    {
                                       ((JComboBox)newComponent).setSelectedIndex(0);
                                    }
                                    
                            }
                        }
                    });
                    uiPropertiesPanel.addProperty(new JLabel(props[i][PROPERTYNAME]),pi);
            }
            else if(props[i][PROPERTYTYPE].equals("int")||props[i][PROPERTYTYPE].equals("char")||props[i][PROPERTYTYPE].equals("String"))
            {
                    
                
                JTextField pi1 = new JTextField(props[i][PROPERTYVALUE]);
                
                if(pi1.getText().equals("NOTENTERED") || pi1.getText().equals(""))
                {
                    pi1.setBackground(Color.RED);
                }
                
                else
                {
                    pi1.setBackground(Color.WHITE);
                }
                
                
                                    
                
                pi1.addKeyListener(new KeyAdapter()
                {
                    public void keyTyped(KeyEvent ke)
                    {
                        JTextField tm = (JTextField)ke.getSource();
                        robot.keyPress(KeyEvent.VK_ENTER); //Enter to update property
                                                
                        int j=uiPropertiesPanel.getCompIndex(tm);
                        props[j][PROPERTYVALUE] = tm.getText();
                        
                        if(tm.getText().equals("NOTENTERED") || tm.getText().equals(""))
                        {
                            tm.setBackground(Color.RED);
                        }
                        
                        else
                        {
                            tm.setBackground(Color.WHITE);
                        }
                        
                        if(props[j][PROPERTYNAME].equals("Label")||props[j][PROPERTYNAME].equals("Text"))
                        {
                                                
                            
                            if((newComponent instanceof JButton)||(newComponent instanceof JCheckBox) || (newComponent instanceof JRadioButton))
                            {
                                 ((AbstractButton)newComponent).setText(props[j][PROPERTYVALUE]);
                            }
                            else if(newComponent instanceof JLabel)
                            {
                                ((JLabel)newComponent).setText(props[j][PROPERTYVALUE]);
                                
                            }
                            else if((newComponent instanceof JTextArea)||(newComponent instanceof JTextField))
                            {
                                if(!props[j][PROPERTYVALUE].equals(new String("$")))
                                {
                                    ((JTextComponent)newComponent).setText(props[j][PROPERTYVALUE]);
                                    
                                }
                            }
                        }
                        else if (props[j][PROPERTYNAME].equals("EchoChar"))
                        {
                        
                             ((JPasswordField)newComponent).setEchoChar('#');
                            
                                props[j][PROPERTYVALUE]=String.valueOf(((JPasswordField)newComponent).getEchoChar());
                            
                        }
                        else if (props[j][PROPERTYNAME].equals("ToolTipText"))
                        {
                                if(!props[j][PROPERTYVALUE].equals(new String("$")))
                                    ((JComponent)newComponent).setToolTipText(props[j][PROPERTYVALUE]);
                        }
                        else if(props[j][PROPERTYNAME].equals("Contents"))
                        {
                            if(!props[j][PROPERTYVALUE].equals("$")&&!props[j][PROPERTYVALUE].equals(""))
                            {
                                String tmp[]=props[j][PROPERTYVALUE].split(",");
                                if(componentType.contains("List"))
                                {
                                    ((JList)newComponent).setListData(tmp);
                                }                        
                                
                                else
                                {
                                    ((JComboBox)newComponent).removeAllItems();
                                    for(int ti=0;ti<tmp.length;ti++)
                                    {
                                        ((JComboBox)newComponent).addItem(tmp[ti]);
                                    }
                                }
                            }
                        }
                    }
                });
                
                uiPropertiesPanel.addProperty(new JLabel(props[i][PROPERTYNAME]),pi1);
            }
        }
    
        //PADDING TO BUMP BOXES UP IN GRIDLAYOUT
        uiPropertiesPanel.addProperty(new JLabel(""),new JLabel(""));
        uiPropertiesPanel.addProperty(new JLabel(""),new JLabel(""));
        uiPropertiesPanel.addProperty(new JLabel(""),new JLabel(""));
        uiPropertiesPanel.addProperty(new JLabel(""),new JLabel(""));
    }
    
    public void property8()
    {
        
    }

    public void printProps()
    {
        System.out.println("---Starting to Print Properties---");
        for(int i=0;i<props.length;i++)
        {
            if(props[i][PROPERTYNAME]!=null)
            {
                System.out.println("---Starting to Print Properties2---");
                String currentLine="";
                currentLine=currentLine+props[i][PROPERTYNAME]+",";
                currentLine=currentLine+props[i][PROPERTYTYPE]+",";
                currentLine=currentLine+props[i][PROPERTYVALUE];
                System.out.println(currentLine);
            }
        }
        System.out.println("--------End of Properties--------");
    }
}
