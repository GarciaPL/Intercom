package ie.intercom.drinks;

import static org.mockito.Mockito.when;

import com.google.common.collect.Sets;
import ie.intercom.drinks.config.AppConfig;
import org.assertj.core.util.Lists;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@SpringBootTest
public class IntercomDrinksRunnerTest {

    @Mock
    private ApplicationArguments applicationArguments;

    @InjectMocks
    private IntercomDrinksRunner intercomDrinksRunner;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testNoParameters() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Cannot find parameter customers");

        when(applicationArguments.getOptionNames()).thenReturn(Sets.newHashSet());

        intercomDrinksRunner.run(applicationArguments);
    }

    @Test
    public void testParametersWithoutArgument() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Cannot find arguments for parameter customers");

        when(applicationArguments.getOptionNames()).thenReturn(Sets.newHashSet("customers"));
        when(applicationArguments.getNonOptionArgs()).thenReturn(Lists.newArrayList());

        intercomDrinksRunner.run(applicationArguments);
    }

    @Test
    public void testManyParametersWithoutCustomer() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Cannot find parameter customers");

        when(applicationArguments.getOptionNames()).thenReturn(Sets.newHashSet("test", "test2", "test3"));
        when(applicationArguments.getNonOptionArgs()).thenReturn(Lists.newArrayList("pathToJson"));

        intercomDrinksRunner.run(applicationArguments);
    }

    @Test
    public void testManyArguments() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Too many parameters");

        when(applicationArguments.getOptionNames()).thenReturn(Sets.newHashSet("customers"));
        when(applicationArguments.getNonOptionArgs()).thenReturn(Lists.newArrayList("pathToJson", "extra", "additional"));

        intercomDrinksRunner.run(applicationArguments);
    }

    @Test
    public void testWrongFileExtension() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("File should have a JSON extension");

        when(applicationArguments.getOptionNames()).thenReturn(Sets.newHashSet("customers"));
        when(applicationArguments.getNonOptionArgs()).thenReturn(Lists.newArrayList("/path/to/json/file.txt"));

        intercomDrinksRunner.run(applicationArguments);
    }

    @Test
    public void testFileDoesNotExist() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("JSON file does not exist");

        when(applicationArguments.getOptionNames()).thenReturn(Sets.newHashSet("customers"));
        when(applicationArguments.getNonOptionArgs()).thenReturn(Lists.newArrayList("/path/to/json/file.json"));

        intercomDrinksRunner.run(applicationArguments);
    }
}