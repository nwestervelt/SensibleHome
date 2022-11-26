//Class file for the main class, entry point of the system for the server

public class MainClass
{
    public MainClass()
    {
    }
    //thread for handling requests from clients (UserInterface instances)
    private class ClientThread extends Thread
    {
        public void run()
        {
        }
    }
    public static void main(String[] args)
    {
        new MainClass();
    }
}