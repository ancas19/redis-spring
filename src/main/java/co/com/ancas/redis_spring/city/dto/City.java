package co.com.ancas.redis_spring.city.dto;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class City {
    private String zip;
    private String city;
    private String stateName;
    private int temperature;
}
