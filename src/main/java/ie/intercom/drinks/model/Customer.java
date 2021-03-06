package ie.intercom.drinks.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Customer {

    private Long userId;
    private String name;
    private Double latitude;
    private Double longitude;

    @JsonCreator
    public Customer(@JsonProperty("user_id") Long userId, @JsonProperty("name") String name,
            @JsonProperty("latitude") Double latitude, @JsonProperty("longitude") Double longitude) {
        this.userId = userId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
