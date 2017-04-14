package ie.intercom.drinks.service;

import ie.intercom.drinks.config.IntercomProperties;
import ie.intercom.drinks.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DrinksServiceImpl implements DrinksService {

    @Autowired
    IntercomProperties intercomProperties;

    @Override
    public String findCustomersInRange(List<Customer> customers) {

        List<Customer> sortedCustomers = customers.stream().sorted(Comparator.comparing(Customer::getUserId)).collect(Collectors.toList());

        StringBuffer customersInRange = new StringBuffer();
        sortedCustomers.forEach(customer -> {

            // radius * arccos(sin(x1) * sin(x2) + cos(x1) * cos(x2) * cos(y1 - y2))
            double distance = intercomProperties.getEarthRadius() *
                    Math.acos(Math.sin(Math.toRadians(intercomProperties.getOfficeLatitude())) * Math.sin(Math.toRadians(customer.getLatitude())) +
                            Math.cos(Math.toRadians(intercomProperties.getOfficeLatitude())) * Math.cos(Math.toRadians(customer.getLatitude())) *
                                    Math.cos(Math.toRadians(intercomProperties.getOfficeLongitude() - (double) customer.getLongitude())));

            if (distance < intercomProperties.getDistance()) {
                customersInRange.append(customer.getName()).append("(").append(customer.getUserId()).append(") \n");
            }
        });

        return customersInRange.toString();
    }
}
