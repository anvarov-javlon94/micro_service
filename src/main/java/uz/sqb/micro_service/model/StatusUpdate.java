package uz.sqb.micro_service.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class StatusUpdate {

    Long buyer_id;
    Long status_id;

    public StatusUpdate(Long buyer_id, Long status_id){
        this.buyer_id = buyer_id;
        this.status_id = status_id;
    }
}
