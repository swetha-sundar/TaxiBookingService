package api;

import model.Car;
import model.Customer;
import model.LocationMap;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by swetha on 3/11/18.
 */
public class TestTickTime {

    private static Set<Integer> availableCars = new TreeSet<>();
    private static Map<Integer, Customer> carIdToCustomerMap = new TreeMap<>();
    private static Map<Integer, Car> cars = new TreeMap<>();
    private static Customer customer;
    private static LocationMap sourceLocationMap;
    private static LocationMap destLocationMap;
    private static Car assignedCar;

    @Before
    public void setup() {
        for (int i = 1; i <= 2; i++) {
            Car car = new Car(i);
            cars.put(i, car);
            availableCars.add(i);
        }
        carIdToCustomerMap = new TreeMap<>();

        sourceLocationMap = new LocationMap(0, 1);
        destLocationMap = new LocationMap(1, 1);
        customer = new Customer("1", sourceLocationMap, destLocationMap);
        assignedCar = bookCars();
    }

    private Car bookCars() {
        BookTaxi bookTaxi = new BookTaxi(cars, availableCars, carIdToCustomerMap);
        return bookTaxi.getCar(customer);
    }

    @Test
    public void testTick() {
        assertEquals(0, assignedCar.getCurrentLocation().getX().intValue());
        assertEquals(0, assignedCar.getCurrentLocation().getY().intValue());
        assertFalse(customer.isPickedUp());

        Iterator<Map.Entry<Integer, Customer>> iterator = carIdToCustomerMap.entrySet().iterator();
        TickTime.updateLocations(assignedCar, customer, availableCars, iterator);

        LocationMap customerCurrentLocation = customer.getCurrentLocation();
        LocationMap carLocation = assignedCar.getCurrentLocation();

        assertEquals(sourceLocationMap.getX().intValue(), carLocation.getX().intValue());
        assertEquals(sourceLocationMap.getY().intValue(), carLocation.getY().intValue());
        assertEquals(sourceLocationMap.getX().intValue(), customerCurrentLocation.getX().intValue());
        assertEquals(sourceLocationMap.getY().intValue(), customerCurrentLocation.getY().intValue());
        assertTrue(customer.isPickedUp());
        assertFalse(customer.isDroppedOff());
    }
}
