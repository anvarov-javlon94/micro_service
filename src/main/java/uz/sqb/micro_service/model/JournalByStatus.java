package uz.sqb.micro_service.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JournalByStatus {

    Long id;

    //BUYER
    Long buyer_id;

    String full_name;

    String phone;

    String status;

    Integer quantity;

    Double total_price;

    String register_date;

}
