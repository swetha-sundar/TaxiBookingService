/**
 * Created by swetha on 3/4/18.
 */

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

//Defines the base URI for all resource URIs.
@ApplicationPath("/")
//The java class declares root resource and provider classes
public class SimpleService extends Application {
    //The method returns a non-empty collection with classes, that must be included in the published JAX-RS application

    @Override
    public Set<Class<?>> getClasses() {
        TaxiBookingService.buildInventory();
        HashSet h = new HashSet<Class<?>>();
        //h.add(HelloWorld.class);
        h.add(TaxiBookingService.class);
        return h;
    }
}