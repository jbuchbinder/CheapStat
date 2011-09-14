
import gnu.io.CommPortIdentifier;

import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Event;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.KeyStroke;
import java.awt.Point;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JDialog;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;


public class ElectrodeSense {

	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private JPanel jContentPane = null;
	private JMenuBar jJMenuBar = null;
	private JMenu fileMenu = null;
	private JMenu editMenu = null;
	private JMenu helpMenu = null;
	private JMenuItem exitMenuItem = null;
	private JMenuItem aboutMenuItem = null;
	private JMenuItem cutMenuItem = null;
	private JMenuItem copyMenuItem = null;
	private JMenuItem pasteMenuItem = null;
	private JMenuItem saveMenuItem = null;
	private JDialog aboutDialog = null;  //  @jve:decl-index=0:visual-constraint="639,81"
	private JPanel aboutContentPane = null;
	private JLabel aboutVersionLabel = null;
	private JButton uploadButton = null;
	private JButton downloadButton = null;
	private JButton fileButton = null;
	private JList profileList = null;
	private JTextField name = null;
	private JLabel nameLabel = null;
	private JTextField freq = null;
	private JLabel freqLabel = null;
	private JLabel startLabel = null;
	private JLabel stopLabel = null;
	private JLabel heightLabel = null;
	private JLabel incrementLabel = null;
	private JLabel rangeLabel = null;
	private JTextField start = null;
	private JTextField stop = null;
	private JTextField height = null;
	private JTextField increment = null;
	private JTextField range = null;
	private JScrollPane jScrollPane = null;

	//custom
	private DefaultListModel listModel = null;
	public ArrayList<Profile> profiles = null;  //  @jve:decl-index=0:
	private int index = 0;
	private Result result;  //  @jve:decl-index=0:
	java.util.Enumeration<CommPortIdentifier> portEnum;  //  @jve:decl-index=0:
	SerialComm com;
	
	
	
	private DefaultListModel getListModel() {
		if (listModel == null) {
			listModel = new DefaultListModel();
		}
		return listModel;
	}
	
	  //  @jve:decl-index=0:
	private ArrayList<Profile> getProfiles() {
		if (profiles == null) {
			profiles = new ArrayList<Profile>();
		}
		return profiles;
	}
	
	public Result getResult() {
		if (result == null) {
			result = new Result();
		}
		return result;
	}
	
	
	private JButton addProfile = null;
	private JButton removeProfile = null;
	private JLabel portLabel = null;
	private JComboBox portComboBox = null;
	private JTextField statusField = null;
	private MyGraph graph = null;
	private JButton clipboardButton = null;
	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setJMenuBar(getJJMenuBar());
			jFrame.setSize(444, 410);
			jFrame.setContentPane(getJContentPane());
			jFrame.setTitle("CheapStat");
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			portLabel = new JLabel();
			portLabel.setBounds(new Rectangle(11, 320, 65, 20));
			portLabel.setText("Serial Port:");
			rangeLabel = new JLabel();
			rangeLabel.setBounds(new Rectangle(10, 160, 100, 20));
			rangeLabel.setText("Range:");
			rangeLabel.setVisible(false);
			incrementLabel = new JLabel();
			incrementLabel.setBounds(new Rectangle(10, 140, 100, 20));
			incrementLabel.setText("Increment (mV):");
			incrementLabel.setVisible(false);
			heightLabel = new JLabel();
			heightLabel.setBounds(new Rectangle(10, 120, 100, 20));
			heightLabel.setText("Height (mV):");
			heightLabel.setVisible(false);
			stopLabel = new JLabel();
			stopLabel.setBounds(new Rectangle(10, 100, 100, 20));
			stopLabel.setText("Stop (mV):");
			stopLabel.setVisible(false);
			startLabel = new JLabel();
			startLabel.setBounds(new Rectangle(10, 80, 100, 20));
			startLabel.setText("Start (mV):");
			startLabel.setVisible(false);
			freqLabel = new JLabel();
			freqLabel.setBounds(new Rectangle(10, 60, 100, 20));
			freqLabel.setText("Frequency (Hz):");
			freqLabel.setVisible(false);
			nameLabel = new JLabel();
			nameLabel.setBounds(new Rectangle(10, 40, 100, 20));
			nameLabel.setText("Profile Name:");
			nameLabel.setVisible(false);
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getUploadButton(), null);
			jContentPane.add(getDownloadButton(), null);
			jContentPane.add(getFileButton(), null);
			jContentPane.add(getClipboardButton(), null);
			jContentPane.add(getName(), null);
			jContentPane.add(getFreq(), null);
			jContentPane.add(getStart(), null);
			jContentPane.add(getStop(), null);
			jContentPane.add(getHeight(), null);
			jContentPane.add(getIncrement(), null);
			jContentPane.add(getRange(), null);
			jContentPane.add(nameLabel, null);
			jContentPane.add(freqLabel, null);
			jContentPane.add(startLabel, null);
			jContentPane.add(stopLabel, null);
			jContentPane.add(heightLabel, null);
			jContentPane.add(incrementLabel, null);
			jContentPane.add(rangeLabel, null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getAddProfile(), null);
			jContentPane.add(getRemoveProfile(), null);
			jContentPane.add(portLabel, null);
			jContentPane.add(getPortComboBox(), null);
			jContentPane.add(getStatusField(), null);
			jContentPane.add(getGraph(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getFileMenu());
			jJMenuBar.add(getEditMenu());
			jJMenuBar.add(getHelpMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.add(getSaveMenuItem());
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getEditMenu() {
		if (editMenu == null) {
			editMenu = new JMenu();
			editMenu.setText("Edit");
			editMenu.setVisible(false);
			editMenu.add(getCutMenuItem());
			editMenu.add(getCopyMenuItem());
			editMenu.add(getPasteMenuItem());
		}
		return editMenu;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText("Help");
			helpMenu.add(getAboutMenuItem());
		}
		return helpMenu;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("Exit");
			exitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return exitMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText("About");
			aboutMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JDialog aboutDialog = getAboutDialog();
					aboutDialog.pack();
					Point loc = getJFrame().getLocation();
					loc.translate(20, 20);
					aboutDialog.setLocation(loc);
					aboutDialog.setVisible(true);
				}
			});
		}
		return aboutMenuItem;
	}

	/**
	 * This method initializes aboutDialog	
	 * 	
	 * @return javax.swing.JDialog
	 */
	private JDialog getAboutDialog() {
		if (aboutDialog == null) {
			aboutDialog = new JDialog(getJFrame(), true);
			aboutDialog.setTitle("About");
			aboutDialog.setSize(new Dimension(125, 73));
			aboutDialog.setContentPane(getAboutContentPane());
		}
		return aboutDialog;
	}

	/**
	 * This method initializes aboutContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAboutContentPane() {
		if (aboutContentPane == null) {
			aboutContentPane = new JPanel();
			aboutContentPane.setLayout(new BorderLayout());
			aboutContentPane.add(getAboutVersionLabel(), BorderLayout.CENTER);
		}
		return aboutContentPane;
	}

	/**
	 * This method initializes aboutVersionLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getAboutVersionLabel() {
		if (aboutVersionLabel == null) {
			aboutVersionLabel = new JLabel();
			aboutVersionLabel.setText("Version 1.0");
			aboutVersionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return aboutVersionLabel;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getCutMenuItem() {
		if (cutMenuItem == null) {
			cutMenuItem = new JMenuItem();
			cutMenuItem.setText("Cut");
			cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
					Event.CTRL_MASK, true));
		}
		return cutMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getCopyMenuItem() {
		if (copyMenuItem == null) {
			copyMenuItem = new JMenuItem();
			copyMenuItem.setText("Copy");
			copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
					Event.CTRL_MASK, true));
		}
		return copyMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getPasteMenuItem() {
		if (pasteMenuItem == null) {
			pasteMenuItem = new JMenuItem();
			pasteMenuItem.setText("Paste");
			pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
					Event.CTRL_MASK, true));
		}
		return pasteMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getSaveMenuItem() {
		if (saveMenuItem == null) {
			saveMenuItem = new JMenuItem();
			saveMenuItem.setText("Save");
			saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					Event.CTRL_MASK, true));
			saveMenuItem.setVisible(false);
		}
		return saveMenuItem;
	}

	/**
	 * This method initializes uploadButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getUploadButton() {
		if (uploadButton == null) {
			uploadButton = new JButton();
			uploadButton.setBounds(new Rectangle(184, 320, 90, 20));
			uploadButton.setText("Upload");
			uploadButton.setName("");
			uploadButton.setVisible(false);
			uploadButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					byte[] data = new byte[117];
					data[0] = 'u';
					//TODO # OF PROFILES
					for(int i = 0; i < 4; i++)
					{
						Profile profile = profiles.get(i);
						int offset = i*29+1;
						byte[] name = profile.name.getBytes();
						System.out.println(profile.name);
						for(int j = 0; j < 15; j++)
						{
								data[offset+j]=name[j];
						}
						System.out.println(profile.type);
						data[offset+15]=(byte) profile.type;
						System.out.println(profile.op1);
						data[offset+16]=(byte) (profile.op1>>8);
						data[offset+17]=(byte) (profile.op1);
						System.out.println(profile.op2);
						data[offset+18]=(byte) (profile.op2>>8);
						data[offset+19]=(byte) (profile.op2);
						System.out.println(profile.op3);
						data[offset+20]=(byte) (profile.op3>>8);
						data[offset+21]=(byte) (profile.op3);
						System.out.println(profile.op4);
						data[offset+22]=(byte) (profile.op4>>8);
						data[offset+23]=(byte) (profile.op4);
						System.out.println(profile.op5);
						data[offset+24]=(byte) (profile.op5>>8);
						data[offset+25]=(byte) (profile.op5);
						System.out.println(profile.op6);
						data[offset+26]=(byte) (profile.op6>>8);
						data[offset+27]=(byte) (profile.op6);
						System.out.println(profile.curr_range);
						data[offset+28]=(byte) (profile.curr_range);
					}
					com.write(data);
					statusField.setText("Profiles uploaded to CheapStat");
				}
			});
		}
		return uploadButton;
	}

	/**
	 * This method initializes downloadButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getDownloadButton() {
		if (downloadButton == null) {
			downloadButton = new JButton();
			downloadButton.setBounds(new Rectangle(290, 320, 90, 20));
			downloadButton.setText("Download");
			downloadButton.setVisible(false);
			downloadButton.addActionListener(new java.awt.event.ActionListener() {   
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					byte[] data = new byte[1];
					data[0] = 'd';
					com.write(data);
					statusField.setText("Profiles and Data downloaded from CheapStat");
					
				}
			
			});
		}
		return downloadButton;
	}

	/**
	 * This method initializes fileButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getFileButton() {
		if (fileButton == null) {
			fileButton = new JButton();
			fileButton.setBounds(new Rectangle(330, 320, 90, 20));
			fileButton.setText("Clipboard");
			fileButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getResult().exportToClipboard();
					statusField.setText("Data sent to clipboard");
				}
			});
		}
		return fileButton;
	}

	/**
	 * This method initializes profileList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getProfileList() {
		if (profileList == null) {
			profileList = new JList(getListModel());
			
			profileList.setSelectedIndex(0);
			profileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			profileList
					.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
						public void valueChanged(javax.swing.event.ListSelectionEvent e) {
							System.out.println("val cahnge");
							index = profileList.getSelectedIndex();
							updateProfileBox();
						}
					});
		}
		return profileList;
	}

	/**
	 * This method initializes name	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getName() {
		if (name == null) {
			name = new JTextField();
			name.setBounds(new Rectangle(110, 40, 60 , 20));
			name.setName("");
			name.setVisible(false);
		}
		return name;
	}

	/**
	 * This method initializes freq	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getFreq() {
		if (freq == null) {
			freq = new JTextField();
			freq.setBounds(new Rectangle(110, 60, 60, 20));
			freq.setVisible(false);
			freq.addCaretListener(new javax.swing.event.CaretListener() {
				public void caretUpdate(javax.swing.event.CaretEvent e) {
					int temp = 0;
					try {
						temp = Integer.parseInt(freq.getText());
					}
					 catch(NumberFormatException err) {
						 freq.setBackground(Color.RED);
					 }
					if(temp < 1 || temp > 200)
					{
						freq.setBackground(Color.RED);
					}
					else
					{
						getProfiles().get(index).op1 = temp;
						freq.setBackground(Color.WHITE);
					}
				}
			});
		}
		return freq;
	}

	/**
	 * This method initializes start	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getStart() {
		if (start == null) {
			start = new JTextField();
			start.setBounds(new Rectangle(110, 80, 60, 20));
			start.setVisible(false);
			start.addCaretListener(new javax.swing.event.CaretListener() {
				public void caretUpdate(javax.swing.event.CaretEvent e) {
					int temp = 0;
					try {
						temp = Integer.parseInt(start.getText());
					}
					 catch(NumberFormatException err) {
						 start.setBackground(Color.RED);
					 }
					if(temp < -500 || temp > 500)
					{
						start.setBackground(Color.RED);
					}
					else
					{
						getProfiles().get(index).op2 = temp;
						start.setBackground(Color.WHITE);
					}
				}
			});
		}
		return start;
	}

	/**
	 * This method initializes stop	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getStop() {
		if (stop == null) {
			stop = new JTextField();
			stop.setBounds(new Rectangle(110, 100, 60, 20));
			stop.setVisible(false);
			stop.addCaretListener(new javax.swing.event.CaretListener() {
				public void caretUpdate(javax.swing.event.CaretEvent e) {
					int temp = 0;
					try {
						temp = Integer.parseInt(stop.getText());
					}
					 catch(NumberFormatException err) {
						 stop.setBackground(Color.RED);
					 }
					if(temp < -500 || temp > 500)
					{
						stop.setBackground(Color.RED);
					}
					else
					{
						getProfiles().get(index).op3 = temp;
						stop.setBackground(Color.WHITE);
					}
				}
			});
		}
		return stop;
	}

	/**
	 * This method initializes height	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getHeight() {
		if (height == null) {
			height = new JTextField();
			height.setBounds(new Rectangle(110, 120, 60, 20));
			height.setVisible(false);
			height.addCaretListener(new javax.swing.event.CaretListener() {
				public void caretUpdate(javax.swing.event.CaretEvent e) {
					int temp = 0;
					try {
						temp = Integer.parseInt(height.getText());
					}
					 catch(NumberFormatException err) {
						 height.setBackground(Color.RED);
					 }
					if(temp < 0 || temp > 500)
					{
						height.setBackground(Color.RED);
					}
					else
					{
						getProfiles().get(index).op4 = temp;
						height.setBackground(Color.WHITE);
					}
				}
			});
		}
		return height;
	}

	/**
	 * This method initializes increment	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getIncrement() {
		if (increment == null) {
			increment = new JTextField();
			increment.setBounds(new Rectangle(110, 140, 60, 20));
			increment.setVisible(false);
			increment.addCaretListener(new javax.swing.event.CaretListener() {
				public void caretUpdate(javax.swing.event.CaretEvent e) {
					int temp = 0;
					try {
						temp = Integer.parseInt(increment.getText());
					}
					 catch(NumberFormatException err) {
						 increment.setBackground(Color.RED);
					 }
					if(temp < 0 || temp > 500)
					{
						increment.setBackground(Color.RED);
					}
					else
					{
						getProfiles().get(index).op5 = temp;
						increment.setBackground(Color.WHITE);
					}
				}
			});
		}
		return increment;
	}

	/**
	 * This method initializes range	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getRange() {
		if (range == null) {
			range = new JTextField();
			range.setBounds(new Rectangle(110, 160, 60, 20));
			range.setVisible(false);
		}
		return range;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(10, 190, 160, 120));
			jScrollPane.setVisible(false);
			jScrollPane.setViewportView(getProfileList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes addProfile	
	 * @return 
	 * 	
	 * @return javax.swing.JButton	
	 */
	
	private void initProfile() {
		for(int i = 0; i < 4; i++)
		{
			Profile newProfile = new Profile();
			getProfiles().add(newProfile);
			listModel.addElement(newProfile.name);
		}
		index = 0;
		profileList.setSelectedIndex(index);
		updateProfileBox();
	}
	private JButton getAddProfile() {
		if (addProfile == null) {
			addProfile = new JButton();
			addProfile.setBounds(new Rectangle(25, 320, 32, 20));
			addProfile.setEnabled(true);
			addProfile.setText("Add");
			addProfile.setVisible(false);
			addProfile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//todo check number of profiles
					Profile newProfile = new Profile();
					getProfiles().add(newProfile);
					index = getProfiles().size()-1;
					listModel.addElement(newProfile.name);
					profileList.setSelectedIndex(index);
					updateProfileBox();
					
				}
			});
		}
		return addProfile;
	}

	/**
	 * This method initializes removeProfile	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getRemoveProfile() {
		if (removeProfile == null) {
			removeProfile = new JButton();
			removeProfile.setBounds(new Rectangle(149, 320, 21, 20));
			removeProfile.setFont(new Font("Dialog", Font.BOLD, 12));
			removeProfile.setText("Delete");
			removeProfile.setVisible(false);
			removeProfile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return removeProfile;
	}

	/**
	 * Launches this application
	 */
	
	public void updateProfileBox()
	{
		//update edit box
		Profile currProfile = getProfiles().get(index);
		name.setText(currProfile.name);
		freq.setText(Integer.toString(currProfile.op1));
		start.setText(Integer.toString(currProfile.op2));
		stop.setText(Integer.toString(currProfile.op3));
		height.setText(Integer.toString(currProfile.op4));
		increment.setText(Integer.toString(currProfile.op5));
		range.setText(Integer.toString(currProfile.curr_range));

		
	}
	
	public void updateProfiles()
	{

		index = 0;
		
		for(int i = 0; i < profiles.size(); i++)
		{
			System.out.println(profiles.get(i).name);
			listModel.set(i,profiles.get(i).name);
		}
		updateProfileBox();
		
	}
	

	/**
	 * This method initializes portComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	
	private void ComConnect(String port)
	{
		com = new SerialComm(this);
		try
        {
            com.connect(port);
        }
        catch ( Exception e )
        {
            statusField.setText("Failed to connect to serial port: " + port);
        }
        statusField.setText("Connected to serial port: " + port);	
       
	}
	
	private JComboBox getPortComboBox() {
		if (portComboBox == null) {
			portComboBox = new JComboBox();
			portComboBox.setBounds(new Rectangle(85, 320, 95, 20));
			portComboBox.addItem("SCAN");
			portComboBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(com != null)
						com.disconnect();
					if(portComboBox.getSelectedIndex() == 0)
					{
						portComboBox.removeAllItems();
						portComboBox.addItem("SCAN");
						portEnum = CommPortIdentifier.getPortIdentifiers();
				        while ( portEnum.hasMoreElements() ) 
				        {
				            CommPortIdentifier portIdentifier = portEnum.nextElement();
				            if(portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL)
				            	portComboBox.addItem(portIdentifier.getName());
				        } 					
					}
					else
					{
						ComConnect((String)portComboBox.getSelectedItem());
					}
				}
			});
			portEnum = CommPortIdentifier.getPortIdentifiers();
	        while ( portEnum.hasMoreElements() ) 
	        {
	            CommPortIdentifier portIdentifier = portEnum.nextElement();
	            if(portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL)
	            	portComboBox.addItem(portIdentifier.getName());
	        }  
		}
		return portComboBox;
	}

	/**
	 * This method initializes statusField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getStatusField() {
		if (statusField == null) {
			statusField = new JTextField();
			statusField.setBounds(new Rectangle(11, 10, 410, 20));
			statusField.setEditable(false);
		}
		return statusField;
	}

	/**
	 * This method initializes graph	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getGraph() {
		if (graph == null) {
			graph = new MyGraph(getResult());
			getResult().graph = graph;
			graph.setLayout(new GridBagLayout());
			graph.setBounds(new Rectangle(11, 40, 410, 270));
			graph.repaint();
		}
		return graph;
	}

	/**
	 * This method initializes clipboardButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getClipboardButton() {
		if (clipboardButton == null) {
			clipboardButton = new JButton();
			clipboardButton.setBounds(new Rectangle(228, 320, 90, 20));
			clipboardButton.setText("To File");
			clipboardButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					getResult().exportToFile();
					statusField.setText("Data sent to file");
				}
			});
		}
		return clipboardButton;
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ElectrodeSense application = new ElectrodeSense();
			
				
				//SerialComm com = new SerialComm(application);
			/*
		        try
		        {
		            com.connect("COM3");
		        }
		        catch ( Exception e )
		        {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		            application.statusField.setText("Failed loading ");
		        }*/
				
				application.getJFrame().setVisible(true);
				application.initProfile();
			}
		});
	}
}
