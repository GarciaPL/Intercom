package ie.intercom.drinks.service;

import ie.intercom.drinks.config.IntercomProperties;
import ie.intercom.drinks.model.Customer;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DrinksServiceImplTest {

    @Mock
    IntercomProperties intercomProperties;

    @InjectMocks
    private DrinksService drinksService = new DrinksServiceImpl();

    private Customer customerFirst;
    private Customer customerSecond;
    private Customer customerThird;

    @Before
    public void setUp() {
        customerFirst = new Customer(1L, "John", 54.080556, -6.361944);
        customerSecond = new Customer(2L, "Mike", 53.038056, -7.653889);
        customerThird = new Customer(3L, "Lisa", 53.0033946, -6.3877505);

        when(intercomProperties.getEarthRadius()).thenReturn(6371.01);
        when(intercomProperties.getOfficeLatitude()).thenReturn(53.3393);
        when(intercomProperties.getOfficeLongitude()).thenReturn(-6.2576841);
        when(intercomProperties.getDistance()).thenReturn(100.0);
    }

    @Test
    public void testInRange() throws Exception {
        String result = drinksService.findCustomersInRange(Lists.newArrayList(customerFirst, customerSecond, customerThird));
        assertThat(result, containsString("John(1)"));
        assertThat(result, containsString("Mike(2)"));
        assertThat(result, containsString("Lisa(3)"));
    }

    @Test
    public void testOutRange() throws Exception {
        when(intercomProperties.getDistance()).thenReturn(10.0);
        String result = drinksService.findCustomersInRange(Lists.newArrayList(customerFirst, customerSecond, customerThird));
        assertThat(result, isEmptyString());
    }
}