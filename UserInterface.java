//Class file for the GUI, entry point of the system for clients

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.*;

public class UserInterface extends JFrame
{
    public UserInterface()
    {
    }
    //class for handling user interface events (buttons clicked, etc..)
    private class ActionHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
        }
    }
    public static void main(String[] args)
    {
        new UserInterface();
    }
}
