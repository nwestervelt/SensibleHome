# Sensible Home

A college project by Gideon Hockenbarger, Noah Westervelt, Ryker Kramer,  
Tisha Prather, and Xuan Lu for CM333 Software Engineering.

## Features

 - Central Device Control Server
 - Simultaneous User Support
 - Mock Device Classes For Demonstration
 - Add, Delete, And Modify Devices

## Compiling From Source

Install a JDK to use for compiling the source code. OpenJDK 17 was used during  
development, but most versions should work.

Clone this repository, then compile the MainClass and UserInterface classes  
either from an IDE or from the commandline. This will compile all the classes  
in the program necessary for running the client and the server.

Commandline:

    git clone https://github.com/nwestervelt/SensibleHome
    cd SensibleHome
    javac MainClass.java UserInterface.java

After all the classes have compiled, run the MainClass class to start a server  
instance.

Commandline:

    java MainClass

Multiple client instances can be connected to the server instance simultaneously,  
and these instances can be on machines other than the one running the server  
instance. Starting a client instance requires running the UserInterface class  
on the client machines.

Commandline:

    java UserInterface

## Using The Client

The client will need to be connected to the server in order to function, which  
requires the user to provide the port and ip address of the server to be  
accomplished.

Click on the "Connect" button to open a window prompting the user for a port  
number and ip address. The port for the server instance is 5000, and the ip  
address is whatever ip address the server's machine is using. If the server  
is running on the same machine as the client, localhost can be used as the ip  
address.

After connecting the client to the server, the client can now create, delete,  
and modify the value of attributes within each existing device using the combo  
box labeled "Devices" and the "Expand" button.

After a device has been selected in the "Devices" combo box, clicking the "Expand"  
button will show the current attribute values of that device and allow the user to  
update the values of those attributes using the "Update" button within the shown  
dialog window.

A similar sequence of events is required for adding and deleting devices, for which  
there are buttons next to the "Expand" button.
