//Class file for unit tests

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class UnitTests
{
    private Thermostat therm;

    @BeforeEach
    public void setUp()
    {
        therm = new Thermostat(0);
    }
    //Thermostat default value test
    @Test
    public void thermDefaultValueTest()
    {
        assertEquals(therm.getTemp(), 65);
    }
    //Thermostat negative value test
    @Test
    public void thermNegativeTemp()
    {
        therm.setTemp(-50);
        assertEquals(therm.getTemp(), -50);
    }
    //Thermostat zero value test
    @Test
    public void thermZeroTemp()
    {
        therm.setTemp(0);
        assertEquals(therm.getTemp(), 0);
    }
    //Thermostat positive value test
    @Test
    public void thermPositiveTemp()
    {
        therm.setTemp(50);
        assertEquals(therm.getTemp(), 50);
    }
}
