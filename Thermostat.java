//Class file for thermostat devices

public class Thermostat extends Device
{
    private int temp;
    public Thermostat(int deviceID)
    {
        super(deviceID);
        temp = 65;
    }
    public int getTemp()
    {
        return temp;
    }
    public void setTemp(int temp)
    {
        this.temp = temp;
    }
}
