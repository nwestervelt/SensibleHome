//Class file for bed devices

public class Bed extends Device
{
    public Bed(int deviceID)
    {
        super(deviceID);
    }
    public int getTemp()
    {
        return 50;
    }
    public void setTemp(int temp)
    {
        int Temp;
        if(0<= temp && temp<=100)
             Temp=temp; // set temperature
        else
        {
            System.out.println(
                    "New temp out of range.  Setting to default of 50F" );
            Temp = 50;                 // Set to default if t out of range
        }
    }
}
