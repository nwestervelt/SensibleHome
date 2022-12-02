//Abstract class file for devices

public abstract class Device
{
    private int deviceID;

    public Device(int deviceID)
    {
        this.deviceID = deviceID;
    }
    public int getDeviceID()
    {
        return deviceID;
    }
}
