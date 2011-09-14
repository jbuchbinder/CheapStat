import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;




public class Result {
    char[] name = new char[15];
    short type;
    short op1;
    short op2;
    short op3;
    short op4;
    short op5;
    short op6;
    short curr_range;
    
    public static short SWV = 0;
    public static short CV = 1;
    public static short ACV = 2;
    public static short LSV = 3;
    public static short CA = 5;
    
	short[] SWV_forward;
	short[] SWV_reverse;
	int[] SWV_diff;
	short[] CV_current;
	short[] ACV_mag;
	short[] ACV_phase;
	short[] LSV_current;
	short[] CA_current;
	short length;
	

	MyGraph graph;
//	float incX;
//float scaleX;
//	float scaleY;
	int max;
	int min;
	String ident = new String();
	
	//410, 270
	//1578 -> -1578
	public void paintComponent(Graphics g) {
		if(max==min)
		{
			max = 1;
			min = 0;
		}
		//parameters
		g.drawString(ident, 10, 15);
		//line
		g.drawLine(0,((max)*240/(max-min))+15,410,((max)*240/(max-min))+15);
		//data points
		for(int i = 0; i < length; i++)
		{
			if(type == SWV)
			{
				g.drawRect((int)(i*410/length),((max-SWV_forward[i])*240/(max-min))+15, 2, 2);
				g.drawRect((int)(i*410/length),((max-SWV_reverse[i])*240/(max-min))+15, 2, 2);
				g.drawRect((int)(i*410/length),((max-SWV_diff[i])*240/(max-min))+15, 2, 2);
			}
			else if(type == CV)
			{
				g.drawRect((int)(i*410/length),((max-CV_current[i])*240/(max-min))+15, 2, 2);
			}
			else if(type == ACV)
			{
				//TODO
			}
			else if(type == LSV)
			{
				g.drawRect((int)(i*410/length),((max-LSV_current[i])*240/(max-min))+30, 2, 2);
			}
			else if(type == CA)
			{
				g.drawRect((int)(i*410/length),((max-CA_current[i])*240/(max-min))+15, 2, 2);
			}
		}
	}

	public void updateGraph() {
		System.out.println("Updating Graph");
		max = -9999;
		min = 9999;
		if(type == SWV)
		{
			//calculate difference and max and min currents
			int i = 0;
			System.out.println("SWV");
			System.out.println("Data Points = " + length);
			SWV_diff = new int[length];
			for(i = 0; i < length; i++)
			{
				SWV_diff[i] = Math.abs(SWV_forward[i]-SWV_reverse[i]);
				if(SWV_diff[i]<min)
					min=SWV_diff[i];
				if(SWV_forward[i]<min)
					min=SWV_forward[i];
				if(SWV_reverse[i]<min)
					min=SWV_reverse[i];
				if(SWV_diff[i]>max)
					max=SWV_diff[i];
				if(SWV_forward[i]>max)
					max=SWV_forward[i];
				if(SWV_reverse[i]>max)
					max=SWV_reverse[i];	
			}
			//calculate inc and scaling factor
			//scaleX = 410/Math.abs(stop-start);
			//scaleY = 270/(max-min);
	
			System.out.println("Max = " + max);
			System.out.println("Min = " + min);
			
			String stringName = new String(name);
			String range = new String();
			if(curr_range == 1)
				range = "0-10uA";
			else
				range = "0-50uA";
				
			ident = String.format("%s: %dHz, %d to %dmV by %dmV,%dmV,%s", stringName,op1,op2,op3,op4,op5,range);
			
			graph.repaint();
		}
		else if(type == CV)
		{
			int i = 0;
			System.out.println("CV");
			System.out.println("Data Points = " + length);
			for(i = 0; i < length; i++)
			{
				if(CV_current[i]<min)
					min=CV_current[i];
				if(CV_current[i]>max)
					max=CV_current[i];
			}

			System.out.println("Max = " + max);
			System.out.println("Min = " + min);
			
			String stringName = new String(name);
			String range = new String();
			if(curr_range == 1)
				range = "0-10uA";
			else
				range = "0-50uA";
				
			ident = String.format("%s: %d to %dmV at %dmV/s,%d time, %dmV/sample, %s", stringName,op2,op3,op1,op4,op5,range);
			
			graph.repaint();
		}
		else if(type == ACV)
		{
			String stringName = new String(name);
			String range = new String();
			if(curr_range == 1)
				range = "0-10uA";
			else
				range = "0-50uA";
			
			ident = String.format("%s: %d cycles at %dHz with %dmV amplitude, %dmV to %dmV, %dmV incr, %s", stringName,op3,op1,op2,op4,op5,op6,range);
			graph.repaint();
		}
		else if(type == LSV)
		{
			int i = 0;
			System.out.println("LSV");
			System.out.println("Data Points = " + length);
			for(i = 0; i < length; i++)
			{
				if(LSV_current[i]<min)
					min=LSV_current[i];
				if(LSV_current[i]>max)
					max=LSV_current[i];
			}

			System.out.println("Max = " + max);
			System.out.println("Min = " + min);
			
			String stringName = new String(name);
			String range = new String();
			if(curr_range == 1)
				range = "0-10uA";
			else
				range = "0-50uA";
			ident = String.format("%s:Settle %ds, %dmV to %dmV \nat %dmV/s, %dmV/sample, %s", stringName,op1,op2,op3,op4,op5,range);
			
			graph.repaint();
		}
		else if(type == CA)
		{
			int i = 0;
			System.out.println("CA");
			System.out.println("Data Points = " + length);
			
			for(i = 0; i < length; i++)
			{
				if(CA_current[i]<min)
					min=CA_current[i];
				if(CA_current[i]>max)
					max=CA_current[i];
				
			}
			System.out.println("Max = " + max);
			System.out.println("Min = " + min);
			
			String stringName = new String(name);
			String range = new String();
			if(curr_range == 1)
				range = "0-10uA";
			else
				range = "0-50uA";
				
			ident = String.format("%s: %ds, %ds at %dmV, %ds, %dmV/sample, %d times, %s", stringName,op1,op3,op2,op4,op5,op6,range);
			
			graph.repaint();
		}
	}
	
	public void exportToClipboard()
	{
		String string = new String();

		double voltage = op2;
		boolean up;
		int max, min;
		double inc;
		if(op3-op2 > 0)
		{
			up = true;
			min = op2;
			max = op3;
		}
		else
		{
			up = false;
			min = op3;
			max = op2;
		}
		inc = (Math.round(op5 * (4096.0/3300)) * (3300.0/4096));
	System.out.println(Math.round(op5 * (4096.0/3300)) + " " + inc);
	
		if(type == ACV)
		{
			voltage = op4;
			if(op5-op4 > 0)
			{
				up = true;
				min = op4;
				max = op5;
			}
			else
			{
				up = false;
				min = op5;
				max = op4;
			}
			inc = (Math.round(op6 * (4096.0/3300)) * (3300.0/4096));
			System.out.println("voltage: " + voltage + " min: " + min + " max: " + max + " up: " + up + " inc: " + inc);
		}
		for(int i = 0; i < length; i++)
		{
			if(type == SWV)
			{
				if(op3-op2 > 0) //up
					voltage = op2+i*op5;
				else
					voltage = op2-i*op5;
				string = string.concat(String.format("%f\t%d\t%d\t%d\n",voltage,SWV_forward[i],SWV_reverse[i],SWV_diff[i]));
			}
			else if(type == CV)
			{
				string = string.concat(String.format("%f\t%d\n",voltage,CV_current[i]));
				if(up)
					voltage += inc;
				else
					voltage -= inc;
				if(up && voltage > max)
					up = false;
				else if(!up && voltage < min)
					up = true;
			}
			else if(type == ACV)
			{
				double shift = ((10-ACV_phase[i])/10.0)*180;
				string = string.concat(String.format("%f\t%d\t%f\n",voltage,ACV_mag[i],shift));
				if(up)
					voltage += inc;
				else
					voltage -= inc;
			}
			else if(type == LSV)
			{

				string = string.concat(String.format("%f\t%d\n",voltage,LSV_current[i]));
				if(up)
					voltage += inc;
				else
					voltage -= inc;
			}
			else if(type == CA)
			{

				string = string.concat(String.format("%d\n",CA_current[i]));
			}

		}
	//	string = string.concat();
		StringSelection stringSelection = new StringSelection(string);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}
	
	public void exportToFile()
	{
		File file = new File("output.txt");
		JFileChooser save = new JFileChooser();
		try {
			
			//FileSaveAction.setCurrentDirectory(new java.io.File("").getAbsoluteFile());
			save.showSaveDialog(null);
			file = save.getSelectedFile();
		} catch (java.awt.HeadlessException e1) {
			e1.printStackTrace();
		}

		String string = new String();

		double voltage = op2;
		boolean up;
		int max, min;
		double inc;
		if(op3-op2 > 0)
		{
			up = true;
			min = op2;
			max = op3;
		}
		else
		{
			up = false;
			min = op3;
			max = op2;
		}
		inc = (Math.round(op5 * (4096.0/3300)) * (3300.0/4096));
	System.out.println(Math.round(op5 * (4096.0/3300)) + " " + inc);
		for(int i = 0; i < length; i++)
		{
			if(type == SWV)
			{
				if(op3-op2 > 0) //up
					voltage = op2+i*op5;
				else
					voltage = op2-i*op5;
				string = string.concat(String.format("%f\t%d\t%d\t%d\n",voltage,SWV_forward[i],SWV_reverse[i],SWV_diff[i]));
			}
			else if(type == CV)
			{
				string = string.concat(String.format("%f\t%d\n",voltage,CV_current[i]));
				if(up)
					voltage += inc;
				else
					voltage -= inc;
				if(up && voltage > max)
					up = false;
				else if(!up && voltage < min)
					up = true;
			}
			else if(type == ACV)
			{
				
			}

		}
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(string);
			writer.close();
		} catch (IOException ex) {}
	}
}
