package accodom.dom.Service;

import accodom.dom.Entities.DomiciliationPack;
import accodom.dom.Entities.User;
import accodom.dom.Entities.UserPackReservation;
import accodom.dom.Repository.DomiciliationPackRepo;
import accodom.dom.Repository.UserPackReservationRepo;
import accodom.dom.Repository.UserRepo;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPackReservationServiceImpl implements UserPackReservationService {
    @Autowired
    private UserPackReservationRepo userPackReservationRepository;

    @Autowired
    private DomiciliationPackRepo domiciliationPackRepository;
    @Autowired
    private UserRepo userRepo; // Repository to handle User entity

    @Autowired
    private UserPackReservationRepo reservationRepo;
    @Autowired
    private ServiceEmail serviceEmail;
    @Transactional
    public UserPackReservation saveReservation(UserPackReservation reservation, Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        reservation.setUser(user); // Set the existing user to the reservation
        try {
            String emailBody = "Dear " + user.getUsername() + ",\n\nYour reservation for " + reservation.getCompanyLegalName() + " has been successfully created.";
            serviceEmail.sendEmail(user.getEmail(), "Reservation Confirmation", emailBody);
        } catch (MessagingException e) {
            // Log the error and decide what to do next:
            // For instance, you could throw a custom runtime exception that doesn't roll back the transaction
            throw new RuntimeException("Failed to send email", e);
        }

        return reservation;
    }
}
