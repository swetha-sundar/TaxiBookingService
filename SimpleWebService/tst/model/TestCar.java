package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by swetha on 3/4/18.
 */
public class TestCar {

    private Car car;

    @Before
    public void setup() {
        car = new Car(123);
    }

    @Test
    public void testGetId() {
        assertNotNull(car);
        assertEquals(123, car.getId().intValue());
    }

    @Test
    public void testGetCurrentLocation() {
        assertNotNull(car);
        //After initialize
        assertEquals(0, car.getCurrentLocation().getX().intValue());
        assertEquals(0, car.getCurrentLocation().getY().intValue());
    }

    @Test
    public void testGetAvailability() {
        assertNotNull(car);
        assertTrue(car.isAvailable());
    }

    @Test
    public void testUpdateLocation() {
        assertEquals(0, car.getCurrentLocation().getX().intValue());
        assertEquals(0, car.getCurrentLocation().getY().intValue());
        car.updateCurrentLocation(1, 1);
        assertEquals(1, car.getCurrentLocation().getX().intValue());
        assertEquals(1, car.getCurrentLocation().getY().intValue());
    }

    @Test
    public void testGetDistanceFrom() {
        LocationMap target = new LocationMap(1, 3);
        assertEquals(4, car.getDistanceFrom(target));
        target = new LocationMap(-1, -1);
        assertEquals(2, car.getDistanceFrom(target));
    }

    @Test
    public void testMoveCarAlongX() {
        car.updateCurrentLocation(1, 3);
        assertEquals(1, car.getCurrentLocation().getX().intValue());
        car.moveCarAlongX(2);
        assertEquals(3, car.getCurrentLocation().getX().intValue());
        car.moveCarAlongX(-1);
        assertEquals(2, car.getCurrentLocation().getX().intValue());
    }

    @Test
    public void testMoveCarAlongY() {
        car.updateCurrentLocation(3, 0);
        assertEquals(0, car.getCurrentLocation().getY().intValue());
        car.moveCarAlongY(2);
        assertEquals(2, car.getCurrentLocation().getY().intValue());
        car.moveCarAlongY(-3);
        assertEquals(-1, car.getCurrentLocation().getY().intValue());
    }
}
