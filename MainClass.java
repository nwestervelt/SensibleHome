//Class file for the main class, entry point of the system for the server

import java.net.*;
import java.io.*;
import java.util.*;

public class MainClass
{
    public MainClass()
    {
        ServerSocket servSock;

        try
        {
            ClientThread ct;

            //create a server socket on port 5000
            servSock = new ServerSocket(5000);

            while(true)
            {
                //program blocks here until a client connects
                ct = new ClientThread(servSock.accept());
                ct.start();
            }
        }
        catch(IOException ioe)
        {
            System.out.println(ioe);
            System.exit(1);
        }
    }
    //thread for handling requests from clients (UserInterface instances)
    private class ClientThread extends Thread
    {
        private Scanner in;

        public ClientThread(Socket sock)
        {

            try
            {
                in = new Scanner(sock.getInputStream());
            }
            catch(IOException ioe)
            {
                System.out.println(ioe);
                System.exit(1);
            }
        }
        //this method runs whenever a client connects,
        //executed by calling start() method of this class 
        public void run()
        {
            try
            {
                //loop while the client is connected
                while(true)
                {
                    if(in.hasNext())
                    {
                        String command = in.nextLine();

                        //process commands sent over network here

                        //print out the command for debugging purposes
                        System.out.println(command);

                        //exit loop if client disconnects
                        if(command.equals("quit"))
                            break;
                    }
                    //sleep to keep from using CPU cycles unnecessarily while waiting
                    sleep(50);
                }
            }
            catch(InterruptedException ie)
            {
                System.out.println(ie);
            }
        }
    }
    public static void main(String[] args)
    {
        new MainClass();
    }
}