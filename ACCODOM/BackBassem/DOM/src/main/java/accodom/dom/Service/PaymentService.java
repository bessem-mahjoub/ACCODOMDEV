package accodom.dom.Service;

import accodom.dom.Entities.StripePack;
import accodom.dom.Entities.UserPackReservation;
import accodom.dom.Repository.ReservationRepo;
import accodom.dom.Repository.UserPackReservationRepo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {

    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private UserPackReservationRepo userPackReservationRepo;

    @Autowired
    public PaymentService(@Value("${stripe.key.secret}") String secretKey) {
        Stripe.apiKey = secretKey;
    }

    public Session createCheckoutSession(String reservationId, String successUrl, String cancelUrl) throws StripeException {
        StripePack SP = this.getProductById(reservationId);
        if (SP == null) {
            throw new RuntimeException("Reservation not found");
        }

        // Create the session params
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("eur")
                                                .setUnitAmount(Math.round(SP.price * 100))
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Reservation #" + reservationId)
                                                                .build())
                                                .build())
                                .setQuantity(1L)
                                .build())
                .build();

        return Session.create(params);
    }

    public List<Map<String, Object>> getAllProducts() throws StripeException {
        List<Map<String, Object>> productsList = new ArrayList<>();

        // Retrieve all products from Stripe
        var products = com.stripe.model.Product.list(new HashMap<>());

        for (var product : products.getData()) {
            Map<String, Object> productDetails = new HashMap<>();
            productDetails.put("id", product.getId());
            productDetails.put("name", product.getName());
            productDetails.put("description", product.getDescription() != null ? product.getDescription() : "No description available");

            // Retrieve prices for each product
            Map<String, Object> priceParams = new HashMap<>();
            priceParams.put("product", product.getId());
            var prices = com.stripe.model.Price.list(priceParams);

            if (!prices.getData().isEmpty()) {
                // Assume each product has a price
                var price = prices.getData().get(0);
                productDetails.put("price", price.getUnitAmount() / 100.0); // Convert cents to euros
            } else {
                productDetails.put("price", null);
            }

            productsList.add(productDetails);
        }

        return productsList;
    }
    public StripePack getProductById(String productId) throws StripeException {
        Map<String, Object> productDetails = new HashMap<>();

        // Récupérer le produit spécifique par son ID
        var product = com.stripe.model.Product.retrieve(productId);

        // Remplir les détails du produit
        productDetails.put("id", product.getId());
        productDetails.put("name", product.getName());
        productDetails.put("description", product.getDescription() != null ? product.getDescription() : "No description available");

        // Récupérer les prix associés au produit
        Map<String, Object> priceParams = new HashMap<>();
        priceParams.put("product", product.getId());
        var prices = com.stripe.model.Price.list(priceParams);

        if (!prices.getData().isEmpty()) {
            // Assume each product has at least one price
            var price = prices.getData().get(0);
            productDetails.put("price", price.getUnitAmount() / 100.0); // Convertir les cents en euros
        } else {
            productDetails.put("price", null);
        }

        StripePack SP = new StripePack();
        SP.description= product.getDescription();
        SP.id=product.getId();
        SP.name=product.getName();
        SP.price=prices.getData().get(0).getUnitAmount()/100.0;
        return SP;
    }

}
