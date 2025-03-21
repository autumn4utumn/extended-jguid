//package JGUID.Util;

//import Util.*;
import java.util.Vector;
//import JGUID.Session.*;
//import JGUID.*;

public class GuiDCodeGenerator 
{
    public static String addTabbedPane(String tempCode)
    {
        /*
        tempCode= tempCode.replace("//[JTabbedPaneDec1]","JTabbedPane NAMEOFTABBEDPANE = new JTabbedPane();");
        tempCode= tempCode.replace("//[JTabbedPaneDec2]","NAMEOFTABBEDPANE.addTab(\"Title\",MYPANEL);");        
        tempCode= tempCode.replace("this.add(MYPANEL)","this.add(NAMEOFTABBEDPANE)");
        */
        return tempCode;
        
    }
    
    public static String replaceMarkers(String tempCode)
    {
        tempCode=tempCode.replace("//[StartVariables]","");
        tempCode=tempCode.replace("//[EndVariables]","");
        tempCode=tempCode.replace("//[StartFrameSettings]","");
        tempCode=tempCode.replace("//[EndFrameSettings]","");
        tempCode=tempCode.replace("//[StartIntialization]","");
        tempCode=tempCode.replace("//[EndIntialization]","");
        tempCode=tempCode.replace("//[StartActions]","");
        tempCode=tempCode.replace("//[EndActions]","");
        
        return tempCode;
    }
        
    public static String getCode(String par_props[][],String par_type,Vector<GuiDComponent> v)
    {
        String code;
        String var_dec=" ";
        String setup_gui="";
        String actions="";
        
        //code = GuiDFileHandler.readFromFile("TemplatesFrame/"+par_type+".template");
        code = GuiDFileHandler.readFromFile(par_type+".template");
        if(v==null)
            return code;
        
        for(int i=0;i<v.size();i++)
        {
            GuiDComponent gc = v.elementAt(i);
            String props[][] = gc.getPropertiesArray();
                        
            if(gc.getCast().equals("JButton"))
            {
                actions=actions+"    /**\n" + "    * The event handler for when the " + gc.getVariableName() + " button is pressed\n" + "    */\n";
                actions=actions+"    public void "+gc.getVariableName()+"_Click()\n";
                actions=actions+"    {\n";
                actions=actions+"    "+"    "+"// Placeholder function for when the " + gc.getVariableName() + " button is pressed\n";
                actions=actions+"    "+"    "+"System.out.println(\""+ gc.getVariableName()+"_Click() has been pressed \");\n";
                actions=actions+"    }\n";
                
                /*
                
                actions=actions+"    if(e.getSource()=="+gc.getVariableName()+")\n";
                actions=actions+"    {\n";
                actions=actions+"         System.out.println(\"button has been pressed \");\n";
                actions=actions+"    }\n";
                */
                System.out.println(actions);
            }
            
            if(gc.getCast().equals("JList")==false&&gc.getCast().equals("JComboBox")==false)
            {
                var_dec += "    "+gc.getCast()+" "+gc.getVariableName()+" = new "+gc.getCast()+"(); // Creates a new " + gc.getCast() + " named " + gc.getVariableName() + "\n";
            }
            //PUT A LINE IN TO DEAL DIFFERENTLY WITH COMBOBOXES AND JLIST
            
            
            if(gc.getCast().equals("JList")||gc.getCast().equals("JComboBox"))
            {
                int ti;
                if(gc.getCast().equals("JList"))
                {
                    ti=9;
                }
                else
                {
                    ti=8;
                }
                if(!props[ti][2].equals("$")&&!(props[ti][2].equals("")))
                {
                    String tmp[]=props[ti][2].split(",");
                    
                    
                    var_dec+="    "+"String[] "+props[0][2]+"_data={";
                    //int k;
                    //for(k=0;k<tmp.length-1;k++)
                    //    var_dec+="\""+tmp[k]+"\",";
                    //var_dec+="\""+tmp[k]+"\"};\n";
                    
                    var_dec+="\"Option1\",\"Option2\",\"Option3\"}; // Defines the options for the " + props[0][2] + " " + gc.getCast() + "\n";
                
                    
                    var_dec += "    "+gc.getCast()+"<String> "+gc.getVariableName()+" = new "+gc.getCast()+"<>("+props[0][2]+"_data"+"); // Creates a new " + gc.getCast() + " called " + gc.getVariableName() + "\n";
                }
                else
                {
                    var_dec += "    "+gc.getCast()+"<String> "+gc.getVariableName()+" = new "+gc.getCast()+"<>(); // Creates a new " + gc.getCast() + " called " + gc.getVariableName() + "\n";
                }
            }
            else
            {
    
            }
            
        }
        var_dec +="\n";
    
        for(int i=0;i<v.size();i++)
        {
            GuiDComponent gc = v.elementAt(i);
            String props[][] = gc.getPropertiesArray();
            
            setup_gui+="        "+props[0][2]+".setLocation("+props[1][2]+","+props[2][2]+"); // Sets the location to be " + props[1][2]+ "px right & " + props[2][2] + "px down\n";

            setup_gui+="        "+props[0][2]+".setSize("+props[3][2]+","+props[4][2]+"); // Sets the size to be " + props[3][2] + "px by " + props[4][2] + "px\n";
            
            if(Integer.parseInt(props[5][2])!=-13421773)
                setup_gui+="        "+props[0][2]+".setForeground( new Color("+props[5][2]+") ); // Sets the colour to be "+ props[5][2] + "\n";
            
            
            if(gc.getCast().equals("JLabel"))
            {
                setup_gui+="        "+props[0][2]+".setOpaque(true); // Swing Performance Optimisation - Prevents painting behind the text label\n";
            }
            if(gc.getCast().equals("JButton"))
            {
                setup_gui+="        "+props[0][2]+".addActionListener(e->"+props[0][2]+"_Click()); // Adds an action listener bound to " + props[0][2] + "\n";
            }
            if(gc.getCast().contains("JText")||gc.getCast().equals("JPasswordField")||gc.getCast().equals("JList"))
            {
                if(!props[6][2].equals("-1"))
                {
                    setup_gui+="        "+props[0][2]+".setBackground( new Color("+props[6][2]+") ); // Sets the background colour to be " + props[6][2] + "\n";
                }
            }
            else if(Integer.parseInt(props[6][2])!=-1118482)
                setup_gui+="        "+props[0][2]+".setBackground( new Color("+props[6][2]+") ); // Sets the background colour to be " + props[6][2] + "\n";

            if(props[7][2].equals("false"))
            {
                setup_gui+="        "+props[0][2]+".setVisible(false); // Sets the element to be invisible\n";
            }
            
            for(int j=8;props[j][0]!=null;j++)
            {
                if(props[j][1].equals("String"))
                {
                    if(props[j][0].equals("ToolTipText"))
                    {
                        if(!(props[j][2].equals("$")))
                          setup_gui+="        "+props[0][2]+".set"+props[j][0]+"(\""+props[j][2]+"\");\n";

                    }
                    else if(props[j][0].equals("Contents"))
                    {
                        if(!props[j][2].equals("$")&&!(props[j][2].equals("")))
                        {
            
                            String tmp[]=props[j][2].split(",");
                            if(!(gc.getCast().startsWith("J")))
                            {
                                for(int k=0;k<tmp.length;k++)
                                    setup_gui+="        "+props[0][2]+".add(\""+tmp[k]+"\");\n";
                            }
                        }
                    }
                    else if(gc.getCast().startsWith("J")&&props[j][0].equals("Label"))
                    {
                        setup_gui+="        "+props[0][2]+".setText(\""+props[j][2]+"\"); // Sets the text to be " + props[j][2] + "\n";
                    }
                    
                    else if(gc.getCast().contains("JTextField")||gc.getCast().contains("JTextArea"))
                    {
                        System.out.println("LINE 142 of CODE GEN - DONT WRITE SET TEXT");
                    }
                    else
                    {
                        setup_gui+="        "+props[0][2]+".set"+props[j][0]+"(\""+props[j][2]+"\"); // Sets the " +  props[j][0].toLowerCase() + " to be" + props[j][2] + "\n";
                    }
                }
                else if(props[j][1].equals("char"))
                {
                    setup_gui+="        "+props[0][2]+".set"+props[j][0]+"('"+props[j][2]+"');\n";    
                }
                else if(props[j][1].equals("Boolean")&&gc.getCast().equals("JCheckBox"))
                {
                    setup_gui+="        "+props[0][2]+".setSelected("+props[j][2]+"); // Sets selected to be " + props[j][2] + "\n";
                }
                else if(gc.getCast().contains("JTextField")&&props[j][0].equals("Columns"))
                {
                    System.out.println("LINE 164 of CODE GEN - DONT WRITE COLUMNS");
                }
                
                else
                {
                    setup_gui+="        "+props[0][2]+".set"+props[j][0]+"("+props[j][2]+"); // Sets the " + props[j][0] + " value to " + props[j][2] + "\n";    
                }
            }
            if(par_type.startsWith("J"))
              setup_gui+="        newPanel.add("+props[0][2]+"); // Adds the component to the panel\n\n";
            else
              setup_gui+="    "+"add("+props[0][2]+");\n\n";    
        }

        String newSettings="";
        
        
        // newSettings+="        "+"this.setTitle(\""+par_props[0][2]+"\");\n";
        
        newSettings+="        "+"window.setSize("+par_props[1][2]+","+par_props[2][2]+"); // Sets the size of the window to have a width of " + par_props[1][2] + "px and a height of " + par_props[2][2] + "px\n";
        if(Integer.parseInt(par_props[3][2])!=-13421773)
        {
            //newSettings+="        "+"this.setForeground( new Color("+par_props[3][2]+") );\n";
          }
        if(Integer.parseInt(par_props[4][2])!=-1118482)
        {
            //newSettings+="        "+"this.setBackground( new Color("+par_props[4][2]+") );\n";
            }
        if(par_props[5][2].equals("true"))
        {
            //newSettings+="        "+"this.setVisible(true);\n";
          }
        
        //newSettings+="        "+"this.setResizable("+par_props[6][2]+");\n";
        
        
        
        
    
        
        int variablesMarker = code.indexOf("[Variables]");
        String beforeVariables = code.substring(0,variablesMarker); //UP TO WHERE VARIABLES IS!
        
        int startOfSettings = code.indexOf("[FRAMESETTINGS]");
        String beforeSettings = code.substring(variablesMarker+12,startOfSettings); //UP TO WHERE SETTING IS!
        
        int startofInit = code.indexOf("[Intialization]");                    //UP TO WHERE INIT IS!
        String beforeInit = code.substring(startOfSettings+16,startofInit);
        
        int startofActions = code.indexOf("[ACTIONLISTENERS]");                    //UP TO WHERE INIT IS!
        String beforeActions = code.substring(startofInit+16,startofActions);
        
        String endCode = code.substring(startofActions+18,code.length()-1);
    
        System.out.println("NOT OUTPUTTING CODE");
        //outputCode(beforeVariables,var_dec,beforeSettings,newSettings,beforeInit,setup_gui,beforeActions,actions,endCode);    
    
        String codeToReturn = beforeVariables+var_dec+beforeSettings+newSettings+beforeInit+setup_gui+beforeActions+actions+endCode;
    
        //if(codeToReturn.contains("MYPANEL"))
        //{
        //    String tempPanelName = par_props[0][2]+"";
        //    char c[] = tempPanelName.toCharArray();
        //    c[0] = Character.toLowerCase(c[0]);
        //    String panelName = new String(c);
        //    panelName = panelName+"Panel";
        //    codeToReturn = codeToReturn.replace("MYPANEL",panelName);
        //}
        
        if(codeToReturn.contains("GuiDFrame"))
        {
            codeToReturn = codeToReturn.replace("GuiDFrame",par_props[0][2]);
        }
    
        
        System.out.println("TEST: Title for frame is "+par_props[0][2] );
        return codeToReturn;
    }
    
    public static void outputCode(String beforeVariables,String var_dec,String beforeSettings,String newSettings,String beforeInit,String setup_gui,String beforeActions,String actions,String endCode)
    {
        System.out.println("******************************************************");
        System.out.println("Running GuiDCodeGenerator-outputCode()               ");
        System.out.println("");
        System.out.println("******************************************************");
        System.out.println(beforeVariables);
        System.out.println("******************************************************");
        System.out.println(var_dec);
        System.out.println("******************************************************");
        System.out.println(beforeSettings);
        System.out.println("******************************************************");
        System.out.println(newSettings);
        System.out.println("******************************************************");
        System.out.println(beforeInit);
        System.out.println("******************************************************");
        System.out.println(setup_gui);
        System.out.println("******************************************************");
        System.out.println(beforeActions);
        System.out.println("******************************************************");
        System.out.println(actions);
        System.out.println("******************************************************");
        System.out.println(endCode);
        System.out.println("******************************************************");
    }
    
    
}
