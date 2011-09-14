import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This version of the TwoWaySerialComm example makes use of the 
 * SerialPortEventListener to avoid polling.
 *
 */
public class SerialComm
{
    ElectrodeSense app;
    SerialPort serialPort;
    InputStream in;
    OutputStream out;
    
	public SerialComm(ElectrodeSense app)
    {
		super();
        this.app = app;
    }
	
	void disconnect()
	{
		if(serialPort != null)
		{
			serialPort.removeEventListener();
			serialPort.close();
		}
		
	}
    
    void connect ( String portName ) throws Exception
    {
    	System.out.println("connecting to com port");
    	CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
        	System.out.println("Error: Port is currently in use");
        }
        else
        {
        	System.out.println("trying to open");
        	CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            
            if ( commPort instanceof SerialPort )
            {
            	System.out.println("found serial port");
            	serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                
                in = serialPort.getInputStream();
                out = serialPort.getOutputStream();
                                    
                serialPort.addEventListener(new SerialReader(in,app));
                serialPort.notifyOnDataAvailable(true);
                
               System.out.println("done loading");

            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }     
    }
    
    void write(byte[] data)
    {
    	try {
    		for(int i = 0; i < data.length; i++)
    		{
    			System.out.println("sending[" + i + "]" + data[i]);
    			out.write(data[i]);
    			//try {
				//	Thread.sleep(100);
				//} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}
    		}
    		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    void write(String msg)
    {
    	try {
    		System.out.println("send"+out);
			out.write(msg.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Handles the input coming from the serial port. A new line character
     * is treated as the end of a block in this example. 
     */
    public static class SerialReader implements SerialPortEventListener 
    {
        private InputStream in;
        private byte[] buffer = new byte[1024];
        ElectrodeSense app;
        Result result;
        public static short SWV = 0;
        public static short CV = 1;
        public static short ACV = 2;
        public static short LSV = 3;
        public static short CA = 5;
        
        
        public SerialReader ( InputStream in , ElectrodeSense app)
        {
            this.in = in;
            this.app = app;
            
        }
        
        public void serialEvent(SerialPortEvent arg0) {
        	System.out.println("event");
        	result = app.getResult();
            short data;
            short type;
            int i,j,temp;
            try
            {
                type = (short) in.read();
                if(type == SWV || type == CV || type == ACV || type == LSV || type == CA)
                {
                	System.out.println("Getting results for: " + type);
	                //get name
	                for(i = 0; i < 15; i++)
	                {
	                	result.name[i] = (char) in.read();
	                }
	                System.out.println(result.name);
	                //get op1
	                result.op1 = (short) (in.read() << 8);
	                result.op1 |= in.read();
	                System.out.println(result.op1);
	                //get op2
	                result.op2 = (short) (in.read() << 8);
	                result.op2 |= in.read();
	                System.out.println(result.op2);
	                //get op3
	                result.op3 = (short) (in.read() << 8);
	                result.op3 |= in.read();
	                System.out.println(result.op3);
	                //get op4
	                result.op4 = (short) (in.read() << 8);
	                result.op4 |= in.read();
	                System.out.println(result.op4);
	                //get op5
	                result.op5 = (short) (in.read() << 8);
	                result.op5 |= in.read();
	                System.out.println(result.op5);
	                if(type == ACV)
	                {
	                	//op6
		                result.op6 = (short) (in.read() << 8);
		                result.op6 |= in.read();
		                System.out.println(result.op6);
	                }
	                else if(type == CA) 
	                {
	                	//op6
		                result.op6 = (short) in.read();
		                System.out.println(result.op6);
	                	
	                }
	                //get range
	                result.curr_range = (byte) in.read();
	                System.out.println(result.curr_range);
	                //get length
	                result.length = (short) (in.read() << 8);
	                result.length |= in.read();
	                System.out.println(result.length);

	                //put data points into array
	                if(type == SWV)
	                {
	                	short[] SWV_forward = new short[result.length];
	                	short[] SWV_reverse = new short[result.length];
	                	for(i = 0; i < result.length; i++)
	                	{
	                		data = (short) (in.read() << 8);
	                		data |= in.read();
	                		System.out.println(data);
	                		SWV_forward[i] = data;
	                		data = (short) (in.read() << 8);
	                		data |= in.read();
	                		System.out.println(data);
	                		SWV_reverse[i] = data;
	                	}
		                result.SWV_forward = SWV_forward;
		                result.SWV_reverse = SWV_reverse;
		                if(in.read()!=SWV)
		                	System.out.println("ERROR");
		                else
		                {
		                	System.out.println("SWV Results Received");
		                	result.type = SWV;
		                	result.updateGraph();
		                }
	                }
	                else if(type == CV)
	                {
	                	short[] CV_current = new short[result.length];
	                	for(i = 0; i < result.length; i++)
	                	{
	                		data = (short) (in.read() << 8);
	                		data |= in.read();
	                		System.out.println(data);
	                		CV_current[i] = data;
	                	}
		                result.CV_current = CV_current;
		                if(in.read()!=CV)
		                	System.out.println("ERROR");
		                else
		                {
		                	System.out.println("CV Results Received");
		                	result.type = CV;
		                	result.updateGraph();
		                }
	       
	                }
	                else if(type == ACV)
	                {
	                	short[] ACV_mag = new short[result.length];
	                	short[] ACV_phase = new short[result.length];
	                	for(i = 0; i < result.length; i++)
	                	{
	                		data = (short) (in.read() << 8);
	                		data |= in.read();
	                		System.out.println(data);
	                		ACV_mag[i] = data;
	                		data = (short) (in.read() << 8);
	                		data |= in.read();
	                		System.out.println(data);
	                		ACV_phase[i] = data;
	                	}
		                result.ACV_mag = ACV_mag;
		                result.ACV_phase = ACV_phase;
		                if(in.read()!=ACV)
		                	System.out.println("ERROR");
		                else
		                {
		                	System.out.println("ACV Results Received");
		                	result.type = ACV;
		                	result.updateGraph();
		                }
	                }    
	                else if(type == LSV)
	                {
	                	short[] LSV_current = new short[result.length];
	                	for(i = 0; i < result.length; i++)
	                	{
	                		data = (short) (in.read() << 8);
	                		data |= in.read();
	                		System.out.println(data);
	                		LSV_current[i] = data;
	                	}
		                result.LSV_current = LSV_current;
		                if(in.read()!=LSV)
		                	System.out.println("ERROR");
		                else
		                {
		                	System.out.println("LSV Results Received");
		                	result.type = LSV;
		                	result.updateGraph();
		                }
	                }
	                else if(type == CA)
	                {
	                	short[] CA_current = new short[result.length];
	                	for(i = 0; i < result.length; i++)
	                	{
	                		data = (short) (in.read() << 8);
	                		data |= in.read();
	                		System.out.println(data);
	                		CA_current[i] = data;
	                	}
		                result.CA_current = CA_current;
		                if(in.read()!=CA)
		                	System.out.println("ERROR");
		                else
		                {
		                	System.out.println("CA Results Received");
		                	result.type = CA;
		                	result.updateGraph();
		                }
	       
	                }
                }
                else if(type == 'z')
                {
                	System.out.println("Downloading Profiles");
                	//TODO # OF PROFILES
                	for(i = 0; i < 4; i++)
                	{
                		Profile profile = app.profiles.get(i);
    	                //get name
                		char[] name = new char[15];
    	                for(j = 0; j < 15; j++)
    	                {
    	                	name[j] = (char) in.read();
    	                }
    	                profile.name = new String(name);
    	                System.out.println(profile.name);
    	                //get type
    	                data = (byte) in.read();
    	                System.out.println(data);
    	                profile.type = data;   
    	                //get op1
    	                data = (short) (in.read() << 8);
    	                data |= in.read();
    	                System.out.println(data);
    	                profile.op1 = data;
    	                //get op2
    	                data = (short) (in.read() << 8);
    	                data |= in.read();
    	                System.out.println(data);
    	                profile.op2 = data;
    	                //get op3
    	                data = (short) (in.read() << 8);
    	                data |= in.read();
    	                System.out.println(data);
    	                profile.op3 = data;
    	                //get op4
    	                data = (short) (in.read() << 8);
    	                data |= in.read();
    	                System.out.println(data);
    	                profile.op4 = data;
    	                //get op5
    	                data = (short) (in.read() << 8);
    	                data |= in.read();
    	                System.out.println(data);
    	                profile.op5 = data;
    	                //get op6
    	                data = (short) (in.read() << 8);
    	                data |= in.read();
    	                System.out.println(data);
    	                profile.op6 = data;
    	                //get range
    	                data = (byte) in.read();
    	                System.out.println(data);
    	                profile.curr_range = data;   	                
                	}
                	if(in.read()!='z')
	                	System.out.println("error");
	                else
	                {
	                	System.out.println("should work?");
	                	app.updateProfiles();
	                }
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
                System.exit(-1);
            }             
        }

    }

   
    



}