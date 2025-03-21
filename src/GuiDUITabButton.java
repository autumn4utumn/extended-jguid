//package JGUID.UI;


import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.*;

/** 
* Customises the attributes for the buttons on the ribbon
*/

public class GuiDUITabButton extends JButton 
{
	int buttonHeight=80 ; //pixels
	int buttonWidth=110 ; //pixels
	Font buttonFont = new Font("Tw Cen MT", Font.BOLD,11);
	
	public GuiDUITabButton(String text, ImageIcon img)
	{
		this.setSize(buttonWidth,buttonHeight);
		this.setIcon(img);
		this.setText(text);
		this.setHorizontalTextPosition(AbstractButton.CENTER);
        this.setVerticalTextPosition(AbstractButton.BOTTOM);
		this.setFont(buttonFont);
		
		Color buttonColor = new Color(0xFFDE03);
		this.setBackground(buttonColor);
	}
	
	public GuiDUITabButton(String text)
	{
		this.setSize(buttonWidth,buttonHeight);
		this.setText(text);
		this.setHorizontalTextPosition(AbstractButton.CENTER);
        this.setVerticalTextPosition(AbstractButton.BOTTOM);
	}
	
	public int getButtonHeight()
	{
		return buttonHeight;
	}
	
	public int getButtonWidth()
	{
		return buttonWidth;
	}
}
