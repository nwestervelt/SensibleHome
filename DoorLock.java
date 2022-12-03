//Class file for door lock devices

public class DoorLock extends Device
{
    public DoorLock(int deviceID)
    {
        super(deviceID);
    }
    public boolean isDoorLocked()
    {
        return true;
    }
    public void toggleDoorLock()
    {
    }
}
