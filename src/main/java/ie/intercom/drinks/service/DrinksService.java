package ie.intercom.drinks.service;

import ie.intercom.drinks.model.Customer;

import java.util.List;

public interface DrinksService {
    public String findCustomersInRange(List<Customer> customers);
}
