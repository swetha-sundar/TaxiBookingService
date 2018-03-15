package api;

import model.Car;
import model.Customer;
import model.LocationMap;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.Assert.*;

/**
 * Created by swetha on 3/11/18.
 */
public class TestReset {
    private static Set<Integer> availableCars = new TreeSet<>();
    private static Map<Integer, Customer> carIdToCustomerMap = new TreeMap<>();
    private static Map<Integer, Car> cars = new TreeMap<>();
    private static Customer customer;
    private static LocationMap sourceLocationMap;
    private static LocationMap destLocationMap;
    private static Car assignedCar;

    @Before
    public void setup() {
        for (int i = 1; i <= 1; i++) {
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
    public void testReset() {
        assertNotNull(cars);
        assertEquals(1, cars.size());
        assertNotNull(availableCars);
        assertEquals(0, availableCars.size());
        assertNotNull(carIdToCustomerMap);
        assertEquals(1, carIdToCustomerMap.size());
        assertNotNull(assignedCar);
        assertFalse(assignedCar.isAvailable());

        Reset.resetAllCarData(cars, availableCars, carIdToCustomerMap);

        assertEquals(1, cars.size());
        assertNotNull(availableCars);
        assertEquals(1, availableCars.size());
        assertNotNull(carIdToCustomerMap);
        assertEquals(0, carIdToCustomerMap.size());
        assertTrue(assignedCar.isAvailable());
    }

}
