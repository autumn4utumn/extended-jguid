//package JGUID.UI;


import javax.swing.*;
import java.awt.event.ActionListener;    //listens for actions
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.*;


//TOOLBOX IS THE LIST OF JCOMPONENTS
public class GuiDUIComponentList extends JDesktopPane 
{
	JInternalFrame toolfrm;
	JButton but;
	JButton lbl;
	JButton radbut;
	JButton chk;
	JButton txtfld;
	JButton txtarea;
	JButton lst;
	JButton cmb;
	JButton passfld;
	

	public GuiDUIComponentList()
	{
		System.out.println("DEBUG: Running GuiDUIComponentList-constructor"); 

		toolfrm = new JInternalFrame("ToolBox",false,false,false,false);
		toolfrm.setFrameIcon(new ImageIcon("Images/icons/GuiD_icon1.png"));
		
		toolfrm.setSize(165,600);
		
		
		
		this.setPreferredSize(new Dimension(165,600));
		toolfrm.setVisible(true);
		
		//STICK TO LEFT IF MOVED
		toolfrm.addComponentListener(new ComponentAdapter(){
		  public void componentMoved(ComponentEvent ce) 
		  {
		          toolfrm.setLocation(new Point(0,0));
		  }
		});


		toolfrm.setLayout(new GridLayout(0,1));
		

		but = new JButton(new ImageIcon(getClass().getResource("images/compJButton.png")));
		lbl = new JButton(new ImageIcon(getClass().getResource("images/compJLabel.png")));
		radbut = new JButton(new ImageIcon(getClass().getResource("images/compJRadioButton.png")));
		chk = new JButton(new ImageIcon(getClass().getResource("images/compJCheckBox.png")));
		txtfld = new JButton(new ImageIcon(getClass().getResource("images/compJTextField.png")));
		passfld = new JButton(new ImageIcon(getClass().getResource("images/compJPasswordField.png")));
		txtarea = new JButton(new ImageIcon(getClass().getResource("images/compJTextArea.png")));
		lst = new JButton(new ImageIcon(getClass().getResource("images/compJList.png")));
		cmb = new JButton(new ImageIcon(getClass().getResource("images/compJComboBox.png")));

		

		setLookOfComponentButtons();
		setActionCommands();
		addTools();
		
		super.add(toolfrm);
		
		toolfrm.show();
	}
	
	public void setToolboxSelection(String s)
	{
		System.out.println("DEBUG: Running GuiDUIComponentList-setToolboxSelection"); 
		toolfrm.setTitle("ToolBox : "+s);
	}
	
	public void clearToolboxSelection()
	{
		System.out.println("DEBUG: Running GuiDUIComponentList-clearToolboxSelection"); 
		toolfrm.setTitle("ToolBox");
	}
	
	private void setLookOfComponentButtons()
	{
		System.out.println("DEBUG: Running GuiDUIComponentList-setLookOfComponentButtons"); 
		but.setBorderPainted(false);
		lbl.setBorderPainted(false);
		chk.setBorderPainted(false);
		txtfld.setBorderPainted(false);
		txtarea.setBorderPainted(false);
		lst.setBorderPainted(false);
		cmb.setBorderPainted(false);
		radbut.setBorderPainted(false);
		passfld.setBorderPainted(false);
		
		but.setBackground(Color.WHITE);
	
		lbl.setBackground(Color.WHITE);
		chk.setBackground(Color.WHITE);
		txtfld.setBackground(Color.WHITE);
		txtarea.setBackground(Color.WHITE);
		lst.setBackground(Color.WHITE);
		cmb.setBackground(Color.WHITE);
		radbut.setBackground(Color.WHITE);
		passfld.setBackground(Color.WHITE);
		
	}
	
	//add buttons to the toolbox
	private void addTools()
	{
		toolfrm.add(lbl);
		toolfrm.add(txtfld);
		toolfrm.add(but);
		toolfrm.add(chk);
		toolfrm.add(radbut);
		toolfrm.add(passfld);
		toolfrm.add(txtarea);
		toolfrm.add(lst);
		toolfrm.add(cmb);	
	}
	
	private void setActionCommands()
	{
		but.setActionCommand("JButton");
		lbl.setActionCommand("JLabel");
		chk.setActionCommand("JCheckBox");
		txtfld.setActionCommand("JTextField");
		txtarea.setActionCommand("JTextArea");
		lst.setActionCommand("JList");
		cmb.setActionCommand("JComboBox");
		radbut.setActionCommand("JRadioButton");
		passfld.setActionCommand("JPasswordField");

	}
	
	public void addListeners(ActionListener al)
	{
		but.addActionListener(al);
		lbl.addActionListener(al);	
		chk.addActionListener(al);
		txtfld.addActionListener(al);
		txtarea.addActionListener(al);
		lst.addActionListener(al);
		cmb.addActionListener(al);
		radbut.addActionListener(al);
		passfld.addActionListener(al);
	}
}
