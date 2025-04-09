package accodom.dom.DTO;

import lombok.Data;

@Data
public class PaymentInfoRequest {
    private int amount;
    private String currency;
    private String recieptEmail;
    private Long reservationId;


}
