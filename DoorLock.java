//Class file for door lock devices

public class DoorLock extends Device
{
   boolean doorLock;

   public DoorLock(int deviceID)
    {
        super(deviceID);
        doorLock=lock;
    }
    public boolean isDoorLocked()
    {
        return doorLock;
    }
    public void toggleDoorLock()
    {
       if(doorLock)
       return doorLock;
       
       else
         toggle lock;
           
        }
    }
}
