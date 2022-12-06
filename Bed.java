//Class file for bed devices

public class Bed extends Device
{
    private int temp;
    public Bed(int deviceID)
    {
        super(deviceID);
    }
    public int getTemp()
    {
        return temp;
    }
    public void setTemp(int temp)
    {
        if(0<= temp && temp<= 100)
           this.temp= temp;// set temperature
        else
        {
            System.out.println(
                    "New temp out of range.  Setting to default of 50F" );
            temp = 50;                 // Set to default if t out of range
        }
    }
}
