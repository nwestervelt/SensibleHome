# Sensible Home

A college project by Gideon Hockenbarger, Noah Westervelt, Ryker Kramer,  
Tisha Prather, and Xuan Lu for CM333 Software Engineering.

## Features

 - Central Device Control Server
 - Simultaneous User Support
 - Generic Device Classes For Demonstration

## Compiling From Source

Install a JDK to use for compiling the source code. OpenJDK 17 was used during  
development, but most versions should work.

Clone this repository, then compile the MainClass and UserInterface classes  
either from an IDE or from the commandline. This will compile all the classes  
in the program necessary for running it.

Commandline:

    git clone https://github.com/nwestervelt/SensibleHome
    cd SensibleHome
    javac MainClass.java UserInterface.java

After all the classes have compiled, run the MainClass class to start a server instance.

Commandline:

    java MainClass

Multiple client instances can be connected to the server instance simultaneously,  
and these instances can be on machines other than the one running the server  
instance. Starting a client instance requires running the UserInterface class  
on the client machines.

Commandline:

    java UserInterface
