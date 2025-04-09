package accodom.dom.Controllers;

import accodom.dom.Service.PaymentService;
import accodom.dom.Utils.ExtractJWT;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payment/secure")
@NoArgsConstructor
@AllArgsConstructor
public class PayementController {


    @Autowired
    private PaymentService paymentService;

    // Endpoint for creating a Stripe Checkout session
    @PostMapping("/checkout-session")
    public ResponseEntity<String> createCheckoutSession(@RequestParam String reservationId,
                                                        @RequestParam String successUrl,
                                                        @RequestParam String cancelUrl) {
        try {
            Session session = paymentService.createCheckoutSession(reservationId, successUrl, cancelUrl);
            return new ResponseEntity<>(session.getUrl(), HttpStatus.OK); // Return the URL of the Stripe Checkout session
        } catch (StripeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint for handling the completion of a payment (optional based on your implementation)
    @PutMapping("/payment-complete")
    public ResponseEntity<String> stripePaymentComplete(@RequestHeader(value = "Authorization") String token)
            throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        // Implement any necessary logic to handle payment completion
        return new ResponseEntity<>("Payment completed successfully", HttpStatus.OK);
    }

    // Endpoint to retrieve and display the list of products (packs) with their prices
    @GetMapping("/products")
    public ResponseEntity<List<Map<String, Object>>> getStripeProducts() {
        try {
            List<Map<String, Object>> products = paymentService.getAllProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (StripeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
