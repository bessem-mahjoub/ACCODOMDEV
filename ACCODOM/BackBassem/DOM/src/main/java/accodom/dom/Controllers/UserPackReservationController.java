package accodom.dom.Controllers;

import accodom.dom.Entities.UserPackReservation;
import accodom.dom.Repository.UserPackReservationRepo;
import accodom.dom.Service.UserPackReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class UserPackReservationController {

    @Autowired
    private UserPackReservationRepo UserRepo;
    @Autowired
    private UserPackReservationService userPackReservationService;

    @PostMapping("/{userId}")
    public ResponseEntity<UserPackReservation> createReservation(
            @PathVariable Long userId,
            @RequestBody UserPackReservation reservation) {
        UserPackReservation savedReservation = userPackReservationService.saveReservation(reservation, userId);


        return ResponseEntity.ok(savedReservation);
    }
}
