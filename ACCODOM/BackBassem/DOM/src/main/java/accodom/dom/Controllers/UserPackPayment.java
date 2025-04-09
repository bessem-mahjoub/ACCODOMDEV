package accodom.dom.Controllers;

import accodom.dom.DTO.PaymentInfoRequest;
import accodom.dom.Service.PackPaymentService;
import accodom.dom.Service.PaymentService;
import accodom.dom.Utils.ExtractJWT;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pack-payment/secure")
@NoArgsConstructor
@AllArgsConstructor
public class UserPackPayment {
    @Autowired
    private PackPaymentService packPaymentService;


    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoRequest paymentInfoRequest)
            throws StripeException {

        PaymentIntent paymentIntent = packPaymentService.createPaymentIntent(paymentInfoRequest);
        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }

    @PutMapping("/payment-complete")
    public ResponseEntity<String> stripePaymentComplete(@RequestHeader(value = "Authorization") String token)
            throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        return packPaymentService.stripePayment(userEmail);
    }
    @GetMapping("/products")
    public ResponseEntity<List<java.util.Map<String, Object>>> getAllProducts() {
        try {
            List<Map<String, Object>> products = packPaymentService.getAllProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (StripeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
