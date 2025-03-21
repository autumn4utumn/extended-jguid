//package JGUID.Util;

//import JGUID.Session.*;
import java.io.*;
import java.util.Vector;

public class GuiDFileProjectHandler 
{
	Vector<GuiDComponent> v;
	String par_props[][];
	File f;
	String type;
	
	public final int FRAMEVARNAME=0;
	public final int FRAMEXLOCATION=1;
	public final int FRAMEYLOCATION=2;
	public final int FRAMEWIDTH=3;
	public final int FRAMEHEIGHT=4;
	public final int FRAMEFOREGROUND=5;
	public final int FRAMEBACKGROUND=6;
	public final int FRAMEVISIBLE=7;
	public final int FRAMEALLCONTENT=8;
	
	//Get Existing File
	public GuiDFileProjectHandler(File f,String type,String props[][],Vector<GuiDComponent> v)
	{
		System.out.println("DEBUG: Running GuiDFileProjectHandler constructor"); 	
		
		String path = f.getAbsolutePath();
		if(!(path.endsWith(".GuiD")))
		{
			path+=".GuiD";
			f = new File(path);
		}
		this.f = f;
		this.par_props = props;
		this.v= v;
		this.type = type;
	}
	
	//Save project file?
	public void generateProjectFile()
	{
		System.out.println("DEBUG: Running GuiDFileProjectHandler generateProjectFile"); 	
		
		int i;
		String s=type+"\n";
		for(i=0;i<par_props.length;i++)
		{
			s+=par_props[i][0]+","+par_props[i][1]+","+par_props[i][2]+"\n";
		}
		s+="[END]\n";
		for(i=0;i<v.size();i++)
		{
			GuiDComponent gc = v.elementAt(i);
			String props[][] = gc.getPropertiesArray();
			String cast = gc.getCast();
			s+=cast+"\n";
			for(int j=0;j<props.length;j++)
			{
				if(props[j][0]==null)
				{
					break;
				}
			    s+=props[j][0]+","+props[j][1]+","+props[j][2]+"\n";
			}
			s+="[END]\n";
		}
		s+="[EOF]\n\n";
		GuiDFileHandler.writeToFile(f,s);
	}

	//Open existing project file and split
	public void parseProjectFile(iGuiDPropMessenger gpm)
	{
		System.out.println("DEBUG: Running GuiDFileProjectHandler parseProjectFile D1"); 
		
		int i,j,st,ed;
		String s = GuiDFileHandler.readFromFile(f);

		par_props = new String[7][3];
		v = new Vector<GuiDComponent>();
		System.out.println("DEBUG: Running GuiDFileProjectHandler parseProjectFile D2");
		String slines[] = s.split("\n"); 
		System.out.println("***parseProjectFile1: "+ s); //THE WHOLE PROJECT ON NEW LINES
	    type = slines[1]; //type is on the second line
		System.out.println("***parseProjectFile2: "+ type);
		for (i=2,j=0;!(slines[i].equals("[END]"));i++,j++)
		{
    		System.out.println("***parseProjectFile3: "+ slines[i]);
			par_props[j] = slines[i].split(",");
			//System.out.println("***parseProjectFile4: "+ par_props[j]);
			
		}
		System.out.println("DEBUG: Running GuiDFileProjectHandler parseProjectFile5");
		for(i=i+1;!slines[i].equals("[EOF]");i++)
		{
			String props[][]=new String[19][4];
			String cast = slines[i];
			for (j=0,i=i+1;!(slines[i].equals("[END]"));j++,i++)
			{
				System.out.println("***parseProjectFile6 SLines("+i+") :"+ slines[i]);
				
				String[] items = slines[i].split(",");
				
				
		
				
				if(items.length==3) //0,1,2
				{
					props[j] = items;
				}
				
				else
				{
					
					
					String[] reduced = new String[3];
					reduced[0]=items[0];
					reduced[1]=items[1];
					
					if(items.length>3)
					{
						System.out.println("MORE THAN 3 PARTS");
						
						String tempThirdValue = items[2];
						for(int k=3;k<items.length;k++)
						{
							tempThirdValue = tempThirdValue + "," +items[k];
						}
					
						System.out.println("Temp 3rd: "+tempThirdValue);
						reduced[2]=tempThirdValue;
					}
					else if (items.length<3 || items[2].equals(""))//less than 3 e.g. TextArea with no text
					{
						//reduced[2]="NOTEXT";
						reduced[2]="NOTEXT";
						System.out.println("SPLIT - FAKE 3rd PART ");
					}
					
					props[j] = reduced;
					//System.out.println(items[0]);
					//System.out.println(items[1]);
					//System.out.println(items[2]);
					//System.out.println(items[3]);
					System.out.println("END 3 PARTS");
				}
			}
			GuiDComponent gc = new GuiDComponent(cast,props,-1,-1,-1,gpm); //-1 is rogue value for no/x/y
			v.add(gc);
		}
		System.out.println("DEBUG: Running GuiDFileProjectHandler parseProjectFile7");
	}

	public String getType()
	{
		System.out.println("DEBUG: Running GuiDFileProjectHandler getType"); 
		return type;
	}
	public String[][] getParentProps()
	{
		System.out.println("DEBUG: Running GuiDFileProjectHandler getParentProps"); 
		return par_props;
	}
	public Vector<GuiDComponent> getComponentVector()
	{
		System.out.println("DEBUG: Running GuiDFileProjectHandler getComponentVector"); 
		return v;
	}

}
