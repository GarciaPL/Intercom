package ie.intercom.drinks.service;

import ie.intercom.drinks.config.IntercomProperties;
import ie.intercom.drinks.model.Customer;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrinksServiceImpl implements DrinksService {

    private IntercomProperties intercomProperties;

    @Autowired
    public DrinksServiceImpl(IntercomProperties intercomProperties) {
        this.intercomProperties = intercomProperties;
    }

    @Override
    public String findCustomersInRange(List<Customer> customers) {

        List<Customer> sortedCustomersById = customers.stream().sorted(Comparator.comparing(Customer::getUserId))
                .collect(Collectors.toList());

        StringBuilder customersInRange = new StringBuilder();
        sortedCustomersById.forEach(customer -> {

            double distance = calculateRadius(customer);

            if (distance < intercomProperties.getDistance()) {
                customersInRange.append(customer.getName()).append("(").append(customer.getUserId()).append(") \n");
            }
        });

        return customersInRange.toString();

    }

    // radius * arccos(sin(x1) * sin(x2) + cos(x1) * cos(x2) * cos(y1 - y2))
    private double calculateRadius(Customer customer) {
        return intercomProperties.getEarthRadius() *
            Math.acos(Math.sin(Math.toRadians(intercomProperties.getOfficeLatitude())) * Math
                .sin(Math.toRadians(customer.getLatitude())) +
                Math.cos(Math.toRadians(intercomProperties.getOfficeLatitude())) * Math
                    .cos(Math.toRadians(customer.getLatitude())) *
                    Math.cos(Math.toRadians(intercomProperties.getOfficeLongitude() - (double) customer.getLongitude())));
    }
}
