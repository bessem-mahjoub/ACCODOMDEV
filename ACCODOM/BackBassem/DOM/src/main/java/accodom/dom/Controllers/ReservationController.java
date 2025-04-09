package accodom.dom.Controllers;

import accodom.dom.Entities.Bureau;
import accodom.dom.Entities.Reservation;
import accodom.dom.Repository.ReservationRepo;
import accodom.dom.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepo res;

    @GetMapping("/available-bureaux")
    public ResponseEntity<List<Bureau>> findAvailableBureaux(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date endDate) {
        List<Bureau> availableBureaux = reservationService.findAvailableBureaux(startDate, endDate);
        return ResponseEntity.ok(availableBureaux);
    }

    @PostMapping("/check-availability")
    public ResponseEntity<Boolean> checkAvailability(@RequestBody Reservation reservation) {
        boolean isAvailable = reservationService.isBureauAvailable(reservation.getBureau().getBureauid(),
                reservation.getStartTime(), reservation.getEndTime());
        return new ResponseEntity<>(isAvailable, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        boolean isAvailable = reservationService.isBureauAvailable(reservation.getBureau().getBureauid(),
                reservation.getStartTime(), reservation.getEndTime());
        if (isAvailable) {
            Reservation savedReservation = reservationService.addReservation(reservation);
            return new ResponseEntity<>(savedReservation, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // 409 Conflict
        }
    }

    @PostMapping("/reserver")
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
        try {
            Reservation savedReservation = reservationService.reserver(reservation);
            return ResponseEntity.ok(savedReservation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/by-bureau/{bureauId}")
    public List<Reservation> getReservationsByBureau(@PathVariable Long bureauId) {
        return res.findAllByBureauBureauid(bureauId);
    }
}
