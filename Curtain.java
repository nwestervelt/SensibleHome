//Class file for curtain devices

public class Curtain extends Device
{
    boolean curtain;
    
    public Curtain(int deviceID)
    {
        super(deviceID);
        curtain=true;
    }
    public boolean isCurtainClosed()
    {
        return curtain;
    }
    public void toggleCurtainClosed()
    {
        if(curtain)
            curtain = false;

        else 
            curtain = true;
    }
}
