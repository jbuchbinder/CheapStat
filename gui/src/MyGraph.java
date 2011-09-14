import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;


public class MyGraph extends JPanel {
	Result result;
	
	public MyGraph(Result result)
	{
		this.result = result;
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);  
        //g2.setStroke(new BasicStroke(1));
		//g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        result.paintComponent(g);
        
       
   }


}
