//Class file for the main class, entry point of the system for the server

import java.net.*;
import java.io.*;
import java.util.*;

public class MainClass
{
    private ArrayList<Device> deviceList;

    public MainClass()
    {
        ServerSocket servSock;

        try
        {
            ClientThread ct;

            //create a server socket on port 5000
            servSock = new ServerSocket(5000);

            //create an arraylist to store devices
            deviceList = new ArrayList<Device>();

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
        private PrintWriter out;

        public ClientThread(Socket sock)
        {

            try
            {
                in = new Scanner(sock.getInputStream());
                out = new PrintWriter(sock.getOutputStream());
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
                        String[] command = in.nextLine().split(",");

                        //print out the command for debugging purposes
                        System.out.println(command[0]);

                        //exit loop if client disconnects
                        if(command[0].equals("quit"))
                            break;

                        //add a device
                        if(command[0].equals("add"))
                        {
                            if(command[1].equals("Bed"))
                                deviceList.add(new Bed(Integer.parseInt(command[2])));

                            else if(command[1].equals("Curtain"))
                                deviceList.add(new Curtain(Integer.parseInt(command[2])));

                            else if(command[1].equals("DoorLock"))
                                deviceList.add(new DoorLock(Integer.parseInt(command[2])));

                            else if(command[1].equals("Light"))
                                deviceList.add(new Light(Integer.parseInt(command[2])));

                            else if(command[1].equals("Thermostat"))
                                deviceList.add(new Thermostat(Integer.parseInt(command[2])));
                        }
                        //remove a device
                        else if(command[0].equals("remove"))
                        {
                            for(int i = 0; i < deviceList.size(); i++)
                            {
                                if(deviceList.get(i).getDeviceID() == Integer.parseInt(command[1]))
                                    deviceList.remove(i);
                            }
                        }
                        //get device list
                        else if(command[0].equals("getDeviceList"))
                        {
                            for(Device device : deviceList)
                            {
                                if(device instanceof Bed)
                                    out.println("Bed," + device.getDeviceID());
                                
                                else if(device instanceof Curtain)
                                    out.println("Curtain " + device.getDeviceID());

                                else if(device instanceof DoorLock)
                                    out.println("DoorLock," + device.getDeviceID());

                                else if(device instanceof Light)
                                    out.println("Light," + device.getDeviceID());

                                else if(device instanceof Thermostat)
                                    out.println("Thermostat," + device.getDeviceID());
                            }
                            out.println("done");
                        }
                        //do a command acting within a device
                        else
                            doDeviceCommand(command[0], Integer.parseInt(command[1]),
                                Integer.parseInt(command[2]));
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
        //do commands acting within devices
        private void doDeviceCommand(String command, int deviceID, int param)
        {
            for(Device device : deviceList)
            {
                if(device.getDeviceID() == deviceID)
                {
                    if(command.equals("getTemp"))
                    {
                        if(device instanceof Bed)
                            out.println(((Bed)device).getTemp());

                        else if(device instanceof Thermostat)
                            out.println(((Thermostat)device).getTemp());
                    }
                    else if(command.equals("setTemp"))
                    {
                        if(device instanceof Bed)
                            ((Bed)device).setTemp(param);

                        else if(device instanceof Thermostat)
                            ((Thermostat)device).setTemp(param);
                    }
                    else if(command.equals("getDim"))
                        out.println(((Light)device).getDim());

                    else if(command.equals("setDim"))
                        ((Light)device).setDim(param);

                    else if(command.equals("isCurtainClosed"))
                        out.println(((Curtain)device).isCurtainClosed());

                    else if(command.equals("toggleCurtainClosed"))
                        ((Curtain)device).toggleCurtainClosed();

                    else if(command.equals("isDoorLocked"))
                        out.println(((DoorLock)device).isDoorLocked());

                    else if(command.equals("toggleDoorLock"))
                        ((DoorLock)device).toggleDoorLock();
                }
            }
        }
    }
    public static void main(String[] args)
    {
        new MainClass();
    }
}