package ie.intercom.drinks.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "intercom")
public class IntercomProperties {

    private Double distance;
    private Double officeLatitude;
    private Double officeLongitude;
    private Double earthRadius;

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getOfficeLatitude() {
        return officeLatitude;
    }

    public void setOfficeLatitude(Double officeLatitude) {
        this.officeLatitude = officeLatitude;
    }

    public Double getOfficeLongitude() {
        return officeLongitude;
    }

    public void setOfficeLongitude(Double officeLongitude) {
        this.officeLongitude = officeLongitude;
    }

    public Double getEarthRadius() {
        return earthRadius;
    }

    public void setEarthRadius(Double earthRadius) {
        this.earthRadius = earthRadius;
    }
}
