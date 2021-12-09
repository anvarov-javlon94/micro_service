package uz.sqb.micro_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalDto {

    Long id;

    //BUYER
    Long buyer_id;

    String full_name;

    String phone;

    String status_name;

    String date_of_register;

    Integer quantity;

    Double total_price;

}
