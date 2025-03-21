//package JGUID.UI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GuiDUIPropertiesPanel extends JDesktopPane 
{
    private JInternalFrame propfrm;
    private JPanel propcont;
    private int x;
    private int y;
    private String title;
    
    
    public GuiDUIPropertiesPanel()
    {
        System.out.println("DEBUG: Running GuiDUIPropertiesPanel-constructor"); 
        setPreferredSize(new Dimension(260,668));
        setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        setAutoscrolls(true);
        propfrm = new JInternalFrame("",false,false,false,false);
        title="";
        
        try
        {
            propfrm.setFrameIcon(new ImageIcon(getClass().getResource("images/GuiD_icon.png")));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        propfrm.setSize(260,668);
        propfrm.setPreferredSize(new Dimension(260,668));
        propfrm.setVisible(true);
        propfrm.addComponentListener(new ComponentAdapter(){
        public void componentMoved(ComponentEvent ce) 
          {
                  propfrm.setLocation(new Point(0,0));
          }
        });
        propcont = new JPanel();
        
        propcont.setLayout(new GridLayout(0,2)); //unlimited rows, 2 cols 
        
        x=1;
        y=1;
        super.add(propfrm);
        propfrm.getContentPane().add(propcont);
        propfrm.toFront();
    }
    
    public void setPropTitle(String title)
    {
        System.out.println("DEBUG: Running GuiDUIPropertiesPanel-setPropTitle"); 
        this.title=title;
        propfrm.setTitle(title);
    }
    
    public String getPropTitle()
    {
        System.out.println("DEBUG: Running GuiDUIPropertiesPanel-getPropTitle"); 
        return title;
    }
    
    public void addProperty(JLabel lbl,JComponent cmp)
    {
        System.out.println("DEBUG: Running GuiDUIPropertiesPanel-addProperty"); 
        System.out.println("PROPERTY: "+lbl.getText()); 
        
        
        propcont.add(lbl);
        
        propcont.add(cmp);
        
    }
    
    public void updatePanel()
    {
        System.out.println("DEBUG: Running GuiDUIPropertiesPanel-updatePanel"); 
        propfrm.toFront();
        propfrm.updateUI();
    }
    
    public int getCompIndex(Component cmp)
    {
        Component c[]=getPropComponents();
        for(int i=16;c[i]!=null;i++)
        {
            if(c[i].equals(cmp))
                return i/2;
        }
        return -1;
    }
    
    public Component getPropComponent(int index)
    {
        System.out.println("DEBUG: Running GuiDUIPropertiesPanel-getPropComponent"); 
        int t=(index+1)*2-1;
        return propcont.getComponent(t);
    }
    
    public Component[] getPropComponents()
    {
        System.out.println("DEBUG: Running GuiDUIPropertiesPanel-getPropComponents");
        return propcont.getComponents();
    }
}
