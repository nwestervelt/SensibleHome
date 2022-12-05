//Class file for the GUI, entry point of the system for clients

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;


public class UserInterface extends JFrame
{
    private PrintWriter out;
    private Scanner in;
    private Socket sock;
    private JButton addButton, connectButton, deleteButton, expandButton, quitButton;
    private JComboBox<String> deviceCB;
    private String def; 
    private String line;
    private String passIn;

    public UserInterface()
    {
        def = "Choose a Device";
        //create ActionHandler to be used in buttons
        ActionHandler ah = new ActionHandler();

        //create and add the mainPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(500, 500));
        add(mainPanel);

        //create addButton and add event handling
        addButton = new JButton("Add Device");
        addButton.addActionListener(ah);
        addButton.setEnabled(false);
        mainPanel.add(addButton);

        deleteButton = new JButton("Delete Device");
        deleteButton.addActionListener(ah);
        deleteButton.setEnabled(false);

        //create connectButton and add event handling
        connectButton = new JButton("Connect");
        connectButton.addActionListener(ah);
        mainPanel.add(connectButton);

        //create quitButton and add event handling
        quitButton = new JButton("Quit");
        quitButton.addActionListener(ah);
        quitButton.setEnabled(false);
        mainPanel.add(quitButton);

        JPanel cbPanel = new JPanel();
        mainPanel.add(cbPanel); 
        
        expandButton= new JButton("Expand");
        expandButton.addActionListener(ah);
        expandButton.setEnabled(false);

        JLabel cbLabel = new JLabel("Devices: ");
        cbPanel.add(cbLabel);
        
        deviceCB = new JComboBox<String>();
        deviceCB.addItem(def);
        deviceCB.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    if((deviceCB.getSelectedIndex() != 0) & !connectButton.isEnabled())
                    {
                        expandButton.setEnabled(true);
                        deleteButton.setEnabled(true);
                    }
                    else
                    {
                        expandButton.setEnabled(false);
                        deleteButton.setEnabled(false);
                    }
                }
            });
        cbPanel.add(deviceCB);

        cbPanel.add(expandButton);
        cbPanel.add(deleteButton);

        //set appearance and behavior of the window
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    //class for handling user interface events (buttons clicked, etc..)
    //NOTE: The protocol for passing commands over the socket is formatted as: commandName,deviceType,deviceID,parameter
    private class ActionHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == connectButton)
            {
                ConnectDialog conD = new ConnectDialog();

                //blocks here while dialog box is visible
                conD.setVisible(true);

                //if submitted button in dialog box was clicked, connect
                if(conD.submitted)
                {
                    try
                    {
                        //attempt to connect
                        sock = new Socket(conD.ipField.getText(), Integer.parseInt(conD.portField.getText()));

                        //create PrintWriter for writing to socket
                        out = new PrintWriter(sock.getOutputStream(), true);

                        //create Scanner for reading from socket
                        in = new Scanner(sock.getInputStream());


                        //update enabled status of buttons
                        connectButton.setEnabled(false);
                        addButton.setEnabled(true);
                        deleteButton.setEnabled(true);
                        //testButton.setEnabled(true); //Test Material
                        quitButton.setEnabled(true);
                        
                    }
                    catch(Exception ex)
                    {
                        System.out.println(ex);
                    }
                }
            }
            /*else if(e.getSource() == testButton)
            {
                //sends "testing" text over socket
                out.println("test,Thermostat,1,temp");
            }*/ //Test material
            else if(e.getSource() == addButton)
            {
                AddDialog addD = new AddDialog();

                //blocks here while dialog box is visible
                addD.setVisible(true);

                //if add button in dialog box was clicked, add new device to deviceList
                if(addD.add)
                {
                    //reads input from comboBox
                    String deviceType = (String)addD.deviceTypeCB.getSelectedItem();
                    int deviceID = Integer.parseInt(addD.deviceIDTF.getText()) + 1; 

                    //builds command string and passes to MainClass
                    passIn = "add," + deviceType + "," + deviceID + ",0";
                    out.println(passIn);

                    //calls for deviceList and prints it out;
                    out.println("getDeviceList,0,0,0");
                    deviceCB.removeAllItems();
                    deviceCB.addItem("Choose a device");
                    while(in.hasNext())
                    {
                        line = in.nextLine();

                        if(line.equals("done")) //The "done" message is for debugging, and should not be included in anything other than printing to terminal to confirm connection
                        {
                            System.out.println(line);
                            break;
                        }
                        else 
                        {
                            String[] deviceInfo = line.split(",");
                            String info = deviceInfo[0] + "   ID:   " + deviceInfo[1];
                            deviceCB.addItem(info);
                            System.out.println(line);
                        }
                    }

                    //resets the String variables "line" and "passIn"
                    line = "";
                    passIn = "";
                }
            }
            else if(e.getSource() == deleteButton)
            {
                String temp = (String)deviceCB.getSelectedItem();
                String[] selection = temp.split("   ");
                out.println("remove," + selection[0] + "," + selection[2] + ",0");

                out.println("getDeviceList,0,0,0");
                    deviceCB.removeAllItems();
                    deviceCB.addItem("Choose a device");
                    while(in.hasNext())
                    {
                        line = in.nextLine();

                        if(line.equals("done")) //The "done" message is for debugging, and should not be included in anything other than printing to terminal to confirm connection
                        {
                            System.out.println(line);
                            break;
                        }
                        else 
                        {
                            String[] deviceInfo = line.split(",");
                            String info = deviceInfo[0] + "   ID:   " + deviceInfo[1];
                            deviceCB.addItem(info);
                            System.out.println(line);
                        }
                    }

                    //resets the String variables "line" and "passIn"
                    line = "";
            }
            else if(e.getSource() == expandButton)
            {
                String temp = (String)deviceCB.getSelectedItem();
                String[] selection = temp.split("   ");
                System.out.println("Selection = " + selection[0] + " " + selection[2]);

                ExpandedDialog exD = new ExpandedDialog(selection[0], selection[2]);

                //blocks here while dialog box is visible
                exD.setVisible(true);
                
                //if update button in dialog box was clicked, change appropriate attributes in given device
                if(exD.update)
                {
                    System.out.println("This button works!");
                    if(selection[0].equals("Bed") | selection[0].equals("Thermostat"))
                    {
                        out.println("setTemp," + selection[0] + "," + selection[2] + "," + exD.tempField.getText());
                        //Test lines
                        out.println("getTemp," + selection[0] + "," + selection[2] + "," + exD.tempField.getText());
                        System.out.println(in.nextLine());
                    }
                    if(selection[0].equals("Light"))
                    {
                        out.println("setDim," + selection[0] + "," + selection[2] + "," + exD.lightField.getText());
                        //Test lines
                        out.println("getDim," + selection[0] + "," + selection[2] + "," + exD.lightField.getText());
                        System.out.println(in.nextLine());
                    }
                    if(selection[0].equals("DoorLock"))
                    {
                        out.println("toggleDoorLock," + selection[0] + "," + selection[2] + ",0");
                        //Test lines
                        out.println("isDoorLocked," + selection[0] + "," + selection[2] + ",0");
                        System.out.println(in.nextLine());
                    }
                    if(selection[0].equals("Curtain"))
                    {
                        out.println("toggleCurtainClosed," + selection[0] + "," + selection[2] + ",0");
                        //Test lines
                        out.println("isCurtainClosed," + selection[0] + "," + selection[2] + ",0");
                        System.out.println(in.nextLine());
                    }
                }
            }
            else if(e.getSource() == quitButton)
            {
                //disconnects by sending "quit" over the socket
                out.println("quit");

                //set out to null and disconnect
                out = null;
                try
                {
                    sock.close();
                }
                catch(IOException ioe)
                {
                    System.out.println(ioe);
                }

                //update enabled status of buttons
                connectButton.setEnabled(true);
                addButton.setEnabled(false);
                deleteButton.setEnabled(false);
                //testButton.setEnabled(false); //Test Material
                quitButton.setEnabled(false);
                expandButton.setEnabled(false);
            }
        }
    }
    private class AddDialog extends JDialog
    {
        private boolean add;
        private JComboBox<String> deviceTypeCB;
        private String[] deviceTypes = {"Choose a device type", "Bed", "Curtain", "DoorLock", "Light", "Thermostat"};
        private JTextField deviceIDTF;
        private String deviceID;

        public AddDialog()
        {
            super(UserInterface.this, true);

            add = false;

            JPanel dialogPanel = new JPanel();
            dialogPanel.setPreferredSize(new Dimension(400, 200));
            dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
            add(dialogPanel);

            JPanel devicePanel = new JPanel();
            dialogPanel.add(devicePanel);

            JLabel deviceLabel = new JLabel("Device Type: ");
            devicePanel.add(deviceLabel);

            JPanel idPanel = new JPanel();
            dialogPanel.add(idPanel);

            passIn = "getNextID,0,0,0";
            out.println(passIn);
            deviceID = in.nextLine();
            deviceIDTF = new JTextField(deviceID);
            deviceIDTF.setEnabled(false);
            idPanel.add(deviceIDTF);

            //create submit button and add custom ActionListener to it
            JButton dialogAddButton = new JButton("Add Device");
            dialogAddButton.setEnabled(false);
            dialogAddButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    add = true;
                    setVisible(false);
                }
            });
            dialogPanel.add(dialogAddButton);

            deviceTypeCB = new JComboBox<String>();
            for(int i=0; i<deviceTypes.length; i++)
            {
                deviceTypeCB.addItem(deviceTypes[i]);
            }
            deviceTypeCB.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    if(deviceTypeCB.getSelectedIndex() != 0)
                    {
                        dialogAddButton.setEnabled(true);
                    }
                    else
                    {
                        dialogAddButton.setEnabled(false);
                    }
                }
            });
            devicePanel.add(deviceTypeCB);

            pack();
        }
    }
    private class ConnectDialog extends JDialog
    {
        private boolean submitted;
        private JTextField ipField, portField;

        public ConnectDialog()
        {
            //make modal (block parent's execution while visible)
            super(UserInterface.this, true);

            //initialize submitted to false
            submitted = false;

            //create and change layout manager of panel
            JPanel dialogPanel = new JPanel();
            dialogPanel.setPreferredSize(new Dimension(400, 200));
            dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
            add(dialogPanel);

            //create panel, label, and text field for ip address
            JPanel ipPanel = new JPanel();
            dialogPanel.add(ipPanel);

            JLabel ipLabel = new JLabel("IP Address: ");
            ipPanel.add(ipLabel);

            ipField = new JTextField(20);
            ipPanel.add(ipField);

            //create panel, label, and text field for port number
            JPanel portPanel = new JPanel();
            dialogPanel.add(portPanel);

            JLabel portLabel = new JLabel("Port: ");
            portPanel.add(portLabel);

            portField = new JTextField(20);
            portPanel.add(portField);

            //create submit button and add custom ActionListener to it
            JButton dialogSubmitButton = new JButton("Submit");
            dialogSubmitButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    submitted = true;
                    setVisible(false);
                }
            });
            dialogPanel.add(dialogSubmitButton);

            //set appearance
            pack();
        }
    }
    private class ExpandedDialog extends JDialog
    {
        private Boolean update;
        private JCheckBox curtainCheck, doorCheck;
        private JLabel deviceLabel, idLabel;
        private JPanel curtainPanel, doorPanel, lightPanel, tempPanel;
        private JTextField lightField, tempField;
        public ExpandedDialog(String type, String id)
        {
            //make modal (block parent's execution while visible)
            super(UserInterface.this, true);

            //initialize update to false
            update = false;

            JPanel dialogPanel = new JPanel();
            dialogPanel.setPreferredSize(new Dimension(400, 200));
            dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
            add(dialogPanel);

            //Include all possible fields generated by devices, to be hid later in the expandButton actionHandler
            //Device Type and ID
            deviceLabel = new JLabel("Device Type:" + type);
            dialogPanel.add(deviceLabel);

            idLabel = new JLabel("Device ID: " + id);
            dialogPanel.add(idLabel);

            //Temperature Information for Bed and Thermostat devices
            tempPanel = new JPanel();
            dialogPanel.add(tempPanel);

            JLabel tempLabel = new JLabel("Current Temperature: ");
            tempPanel.add(tempLabel);

            tempField = new JTextField(/*results from getTemp call using selected device's info */5);
            tempPanel.add(tempField);

            //Brightness/dimness level information for Light devices
            lightPanel = new JPanel();
            dialogPanel.add(lightPanel);

            JLabel lightLabel = new JLabel("Brightness %: ");
            lightPanel.add(lightLabel);

            lightField = new JTextField(/*results from getDim using selected device's info*/ 5);
            lightPanel.add(lightField);

            //Door lock check box for DoorLock devices
            doorPanel = new JPanel();
            dialogPanel.add(doorPanel);

            doorCheck = new JCheckBox("Door Locked");
            doorPanel.add(doorCheck);
            //Will need to set checked state (i.e. doorCheck.setSelected) based off results of isDoorLocked in the expandButton actionHandler

            //Curtain closed check box for Curtain devices
            curtainPanel = new JPanel();
            dialogPanel.add(curtainPanel);

            curtainCheck = new JCheckBox("Curtain Closed");
            curtainPanel.add(curtainCheck);
            //Will need to set checked state (i.e. curtainCheck.setSelected) based off results of isCurtainClosed in the expandButton actionHandler
            
            JButton dialogUpdateButton = new JButton("Update");
            dialogUpdateButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    update = true; //Relevant in expandButton actionHandler
                    setVisible(false);
                }
            });
            tempPanel.setVisible(false);
            lightPanel.setVisible(false);
            doorPanel.setVisible(false);
            curtainPanel.setVisible(false);
            setDialog(type, id);


            dialogPanel.add(dialogUpdateButton);

            //set appearance
            pack();
        }
        public void setDialog(String type, String id)
        {
            if(type.equals("Bed") | type.equals("Thermostat"))
            {
                //Sets the Temperature-relevant feature to be visible
                tempPanel.setVisible(true);
                //Sends a command to the MainClass to get the temp attribute for the specific device
                out.println("getTemp," + type + "," + id + ",0");
                //Sets the value of the temperature text field to the current temperature stored in the device
                tempField.setText(in.nextLine()); 
            }
            else if(type.equals("Light"))
            {
                //Sets the Light-relevant feature to be visible
                lightPanel.setVisible(true);
                //Sends a command to the MainClass to get the dim attribute for the specific device
                out.println("getDim," + type + "," + id + ",0");
                //Sets the value of the dim text field to the current dimLevel stored in the device
                lightField.setText(in.nextLine()); 
            }
            else if(type.equals("DoorLock"))
            {
                //Sets the DoorLock-relevant feature to be visible
                doorPanel.setVisible(true);
                //Sends a command to the MainClass to get the isDoorLocked attribute for the specific device
                out.println("isDoorLocked," + type + "," + id + ",0");
                //Sets the value of the doorCheck text checkbox to the current lockState stored in the device
                doorCheck.setSelected(Boolean.parseBoolean(in.nextLine()));
            }
            else if(type.equals("Curtain"))
            {
                curtainPanel.setVisible(true);
                //Sends a command to the MainClass to get the isCurtainClosed attribute for the specific device
                out.println("isCurtainClosed," + type + "," + id + ",0");
                //Sets the value of the curtainCheck Boolean checkbox to the current curtainState stored in the device
                curtainCheck.setSelected(Boolean.parseBoolean(in.nextLine()));
            }
        }
    }
    public static void main(String[] args)
    {
        new UserInterface();
    }
}
