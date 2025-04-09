package accodom.dom.Repository;

import accodom.dom.Entities.Payment;
import accodom.dom.Entities.UserPackReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPackReservationRepo extends JpaRepository<UserPackReservation ,Long > {
    UserPackReservation findByUserEmail(String userEmail);
}
