//Class file for unit tests

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class UnitTests
{
    private MainClass mc;
    private Thermostat therm;

    @BeforeEach
    public void setUp()
    {
        mc = new MainClass();
        therm = new Thermostat(0);
    }
    //Thermostat default value test
    @Test
    public void thermDefaultValueTest()
    {
        assertEquals(mc.getTemp(0), 70);
    }
    //Thermostat negative value test
    @Test
    public void thermNegativeTemp()
    {
        mc.setTemp(-50, 0);
        assertEquals(mc.getTemp(0), -50);
    }
    //Thermostat zero value test
    @Test
    public void thermZeroTemp()
    {
        mc.setTemp(0, 0);
        assertEquals(mc.getTemp(0), 0);
    }
    //Thermostat positive value test
    @Test
    public void thermPositiveTemp()
    {
        mc.setTemp(50, 0)
        assertEquals(mc.getTemp(0), 50);
    }
}
