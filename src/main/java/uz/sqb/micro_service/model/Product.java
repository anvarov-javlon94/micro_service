package uz.sqb.micro_service.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    String short_name;
    Long id;
    Double price;

}
