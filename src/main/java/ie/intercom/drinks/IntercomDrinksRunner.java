package ie.intercom.drinks;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.intercom.drinks.config.IntercomProperties;
import ie.intercom.drinks.model.Customer;
import ie.intercom.drinks.service.DrinksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class IntercomDrinksRunner implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final String CUSTOMERS = "customers";
    private final String NO_PARAMETER = "Cannot find parameter " + CUSTOMERS;
    private final String NO_ARGUMENT = "Cannot find arguments for parameter " + CUSTOMERS;
    private final String MANY_ARGUMENTS = "Too many arguments";
    private final String MANY_PARAMETERS = "Too many parameters";
    private final String NO_FILE = "JSON file does not exist";
    private final String WRONG_EXTENSION = "File should have a JSON extension";

    @Autowired
    DrinksService drinksService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    IntercomProperties intercomProperties;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        Assert.notEmpty(applicationArguments.getOptionNames(), NO_PARAMETER);
        Assert.notEmpty(applicationArguments.getNonOptionArgs(), NO_ARGUMENT);

        Assert.isTrue(applicationArguments.getOptionNames().stream().anyMatch(i -> i.equals(CUSTOMERS)), NO_PARAMETER);
        Assert.isTrue(applicationArguments.getOptionNames().size() > 0 && applicationArguments.getOptionNames().size() < 2, MANY_ARGUMENTS);
        Assert.isTrue(applicationArguments.getNonOptionArgs().size() > 0 && applicationArguments.getNonOptionArgs().size() < 2, MANY_PARAMETERS);

        String path = applicationArguments.getNonOptionArgs().stream().findFirst().get();
        Assert.isTrue(path.replaceAll("^.*\\.([^.]+)$", "$1").equals("json"), WRONG_EXTENSION);
        Path jsonPath = Paths.get(path);
        Assert.isTrue(Files.exists(jsonPath), NO_FILE);

        List<Customer> customers = new ArrayList<>();
        Files.readAllLines(jsonPath).forEach(s -> {
            try {
                customers.add(objectMapper.readValue(s, Customer.class));
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        });

        System.out.println("\n=== CUSTOMERS IN RANGE " + intercomProperties.getDistance() + " km ===");
        System.out.println(drinksService.findCustomersInRange(customers));
    }

}
