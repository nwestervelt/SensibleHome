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
    private Socket sock;
    private JButton connectButton, testButton, quitButton;
    private JComboBox<String> deviceCB;

    public UserInterface()
    {
        //create ActionHandler to be used in buttons
        ActionHandler ah = new ActionHandler();

        //create and add the mainPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(500, 500));
        add(mainPanel);

        //create connectButton and add event handling
        connectButton = new JButton("Connect");
        connectButton.addActionListener(ah);
        mainPanel.add(connectButton);

        //create testButton and add event handling
        testButton = new JButton("Test");
        testButton.addActionListener(ah);
        testButton.setEnabled(false);
        mainPanel.add(testButton);

        //create quitButton and add event handling
        quitButton = new JButton("Quit");
        quitButton.addActionListener(ah);
        quitButton.setEnabled(false);
        mainPanel.add(quitButton);

        mainPanel.add(new JLabel("Devices: "));
        deviceCB=new JComboBox<String>();
        mainPanel.add(deviceCB);

        //set appearance and behavior of the window
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    //class for handling user interface events (buttons clicked, etc..)
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

                        Scanner in = new Scanner(sock.getInputStream());
                        System.out.println("Hello, you sonofabitch!");
                        
                        String testString = "test,Thermostat,1,temp";
                        out.println(testString);
                        System.out.println(in.nextLine());
                        System.out.println("Major Tom to Ground Control");

                        //update enabled status of buttons
                        connectButton.setEnabled(false);
                        testButton.setEnabled(true);
                        quitButton.setEnabled(true);

                        //TODO: populate combobox
                        
                    }
                    catch(Exception ex)
                    {
                        System.out.println("You dingus, " + ex);
                    }
                }
            }
            else if(e.getSource() == testButton)
            {
                //sends "testing" text over socket
                out.println("testing");
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
                testButton.setEnabled(false);
                quitButton.setEnabled(false);
            }
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
    public static void main(String[] args)
    {
        new UserInterface();
    }
}
