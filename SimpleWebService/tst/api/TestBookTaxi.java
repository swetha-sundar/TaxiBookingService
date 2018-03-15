package api;

import model.Car;
import model.Customer;
import model.LocationMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.Assert.*;

/**
 * Created by swetha on 3/4/18.
 */
public class TestBookTaxi {

    private static Set<Integer> availableCars = new TreeSet<>();
    private static Map<Integer, Customer> carIdToCustomerMap = new TreeMap<>();
    private static Map<Integer, Car> cars = new TreeMap<>();


    @Before
    public void setup() {
        for (int i = 1; i <= 2; i++) {
            Car car = new Car(i);
            cars.put(i, car);
            availableCars.add(i);
        }
        carIdToCustomerMap = new TreeMap<>();
    }

    @After
    public void teardown() {
        availableCars = new TreeSet<>();
        carIdToCustomerMap = new TreeMap<>();
        cars = new TreeMap<>();
    }

    @Test
    public void testBookSunnyDay() {
        BookTaxi bookTaxi = new BookTaxi(cars, availableCars, carIdToCustomerMap);
        LocationMap source = new LocationMap(0, 1);
        LocationMap dest = new LocationMap(2, 2);
        Customer customer1 = new Customer("test1", source, dest);
        Car carToBook = bookTaxi.getCar(customer1);
        assertNotNull(carToBook);
        assertEquals(1, carToBook.getId().intValue());

        Customer customer2 = new Customer("test2", source, dest);
        Car carToBook2 = bookTaxi.getCar(customer2);
        assertNotNull(carToBook2);
        assertEquals(2, carToBook2.getId().intValue());
    }

    @Test
    public void testBookNoAvailableCar() {
        BookTaxi bookTaxi = new BookTaxi(cars, availableCars, carIdToCustomerMap);
        LocationMap source = new LocationMap(0, 1);
        LocationMap dest = new LocationMap(2, 2);

        Customer customer1 = new Customer("test1", source, dest);
        bookTaxi.getCar(customer1);

        Customer customer2 = new Customer("test2", source, dest);
        bookTaxi.getCar(customer2);

        Customer customer3 = new Customer("test3", source, dest);
        Car carToBook3 = bookTaxi.getCar(customer3);
        assertNull(carToBook3);
    }
}
