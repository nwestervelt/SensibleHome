//Abstract class file for devices

public abstract class Device
{
    private int deviceID;
    private boolean adminOnly;

    public Device(int deviceID, boolean adminOnly)
    {
        this.deviceID = deviceID;
        this.adminOnly = adminOnly;
    }
    public int getDeviceID()
    {
        return deviceID;
    }
    public boolean isAdminOnly()
    {
        return adminOnly;
    }
}
