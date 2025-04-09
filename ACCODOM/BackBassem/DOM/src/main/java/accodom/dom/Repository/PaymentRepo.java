package accodom.dom.Repository;

import accodom.dom.Entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
    Payment findByUserEmail(String userEmail);

}
