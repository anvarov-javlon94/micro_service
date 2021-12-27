package uz.sqb.micro_service.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportBuyer {
    Long id;

    String name;

    String date;

    Integer product_count;

    String phone;

    Double price;

}
