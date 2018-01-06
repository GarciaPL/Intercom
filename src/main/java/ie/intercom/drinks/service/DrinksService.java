package ie.intercom.drinks.service;

import ie.intercom.drinks.model.Customer;
import java.util.List;

public interface DrinksService {

    String findCustomersInRange(List<Customer> customers);
}
