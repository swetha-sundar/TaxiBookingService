package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by swets on 3/11/18.
 */
public class TestCustomer {

    private Customer customer;

    @Before
    public void setup() {
        LocationMap sourceLocation = new LocationMap(0, 1);
        LocationMap destinationLocation = new LocationMap(1, 2);
        customer = new Customer("test", sourceLocation, destinationLocation);
    }

    @Test
    public void testPickedUp() {
        assertNotNull(customer);
        assertFalse(customer.isPickedUp());
    }

    @Test
    public void testDroppedOff() {
        assertNotNull(customer);
        assertFalse(customer.isDroppedOff());
    }

    @Test
    public void testUpdateCurrentLocation() {
        assertEquals(0, customer.getCurrentLocation().getX().intValue());
        assertEquals(1, customer.getCurrentLocation().getY().intValue());
        assertEquals(1, customer.getDestinationLocation().getX().intValue());
        assertEquals(2, customer.getDestinationLocation().getY().intValue());
        customer.updateCurrentLocation(1, 1);
        assertEquals(1, customer.getCurrentLocation().getX().intValue());
        assertEquals(1, customer.getCurrentLocation().getY().intValue());
    }
}
