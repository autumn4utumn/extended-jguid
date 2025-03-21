
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyTestGUI
{
	JFrame NAMEOFFRAME = new JFrame();
	JTabbedPane MYGUIDTABS = new JTabbedPane();

    JPanel panelOne = new JPanel(null);
	
 	JLabel rt = new JLabel();
	JButton gsdfgdfg = new JButton();




    public void initFrame()
    {
        NAMEOFFRAME.setLayout(new GridLayout(1,1));
        NAMEOFFRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        NAMEOFFRAME.setSize(700,400);
		
        initPanel();

        
		MYGUIDTABS.add("My Created Tab", panelOne );
		MYGUIDTABS.add("Fake Panel 2", new JPanel());
		MYGUIDTABS.add("Fake Panel 3", new JPanel());
		MYGUIDTABS.add("Fake Panel 4", new JPanel());
		MYGUIDTABS.add("Fake Panel 5", new JPanel());
		
		NAMEOFFRAME.add(MYGUIDTABS);
		NAMEOFFRAME.setVisible(true);
    }




    public void initPanel()
    {
	rt.setLocation(216,138);
	rt.setSize(100,50);
	rt.setOpaque(true);
	rt.setText("rte");
    panelOne.add(rt);

	gsdfgdfg.setLocation(253,243);
	gsdfgdfg.setSize(100,50);
	gsdfgdfg.addActionListener(e->gsdfgdfg_Click());
	gsdfgdfg.setText("dsgfsd");
    panelOne.add(gsdfgdfg);

    }




	
	public void gsdfgdfg_Click()
	{
		System.out.println("gsdfgdfg_Click() has been pressed ");
	}
	


	
    public static void main(String[] args )
    {
        MyTestGUI obj = new MyTestGUI();
        obj.initFrame();
    }
}  
