//Class file for light devices

public class Light extends Device
{
    private int dimLevel;
    public Light(int deviceID)
    {
        super(deviceID);
        dimLevel=30;
    }
    public int getDim()
    {
        return dimLevel;
    }
    public void setDim(int dimLevel)
    {
        this.dimLevel=dimLevel;
    }
}
