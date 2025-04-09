package accodom.dom.Service;

import accodom.dom.DTO.PaymentInfoRequest;
import accodom.dom.Entities.Payment;
import accodom.dom.Entities.UserPackReservation;
import accodom.dom.Repository.PaymentRepo;
import accodom.dom.Repository.UserPackReservationRepo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Price;
import com.stripe.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PackPaymentService {

    @Autowired
    private PaymentRepo paymentRepository;

    @Autowired
    private UserPackReservationRepo userPackReservationRepo;

    @Autowired
    public PackPaymentService(PaymentRepo paymentRepository, @Value("${stripe.key.secret}") String secretKey) {
        this.paymentRepository = paymentRepository;
        Stripe.apiKey = secretKey;
    }

    public PaymentIntent createPaymentIntent(PaymentInfoRequest paymentInfoRequest) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfoRequest.getAmount());
        params.put("currency", paymentInfoRequest.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        // Save payment info to the database
        Payment payment = new Payment();
        payment.setAmount(paymentInfoRequest.getAmount());
        payment.setUserEmail(paymentInfoRequest.getRecieptEmail());

        // Fetch and set the reservation
        UserPackReservation userPackReservation = userPackReservationRepo.findById(paymentInfoRequest.getReservationId())
                .orElseThrow(() -> new RuntimeException("Pack Not Found"));
        payment.setUserPackReservation(userPackReservation);

        paymentRepository.save(payment);

        return paymentIntent;
    }

    public ResponseEntity<String> stripePayment(String userEmail) throws Exception {
        Payment payment = paymentRepository.findByUserEmail(userEmail);

        if (payment == null) {
            throw new Exception("Payment information is missing");
        }
        payment.setAmount(0.00);
        paymentRepository.save(payment);
        return ResponseEntity.ok("Payment completed successfully");
    }

    public List<Map<String, Object>> getAllProducts() throws StripeException {
        List<Product> products = Product.list(new HashMap<>()).getData();

        return products.stream().map(product -> {
            try {
                List<Price> prices = Price.list(Map.of("product", product.getId())).getData();
                return Map.of(
                        "id", product.getId(),
                        "name", product.getName(),
                        "description", product.getDescription(),
                        "prices", prices.stream().map(price -> Map.of(
                                "id", price.getId(),
                                "currency", price.getCurrency(),
                                "amount", price.getUnitAmount(),
                                "interval", price.getRecurring() != null ? price.getRecurring().getInterval() : "one-time"
                        )).collect(Collectors.toList())
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
}
