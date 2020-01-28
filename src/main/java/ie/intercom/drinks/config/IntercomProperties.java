package ie.intercom.drinks.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "intercom")
@Getter
@Setter
public class IntercomProperties {

    private Double distance;
    private Double officeLatitude;
    private Double officeLongitude;
    private Double earthRadius;

}
