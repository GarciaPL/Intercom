package ie.intercom.drinks;

import ie.intercom.drinks.config.IntercomProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = IntercomProperties.class)
public class IntercomDrinksApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(IntercomDrinksApplication.class)
                .logStartupInfo(false)
                .application().run(args);
    }
}
