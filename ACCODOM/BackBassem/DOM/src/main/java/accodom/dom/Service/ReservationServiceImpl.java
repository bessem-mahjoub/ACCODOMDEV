package accodom.dom.Service;

import accodom.dom.Entities.Bureau;
import accodom.dom.Entities.Reservation;
import accodom.dom.Repository.BureauRepo;
import accodom.dom.Repository.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private BureauRepo bureauRepo;

    @Autowired
    private ReservationRepo reservationRepo;

    @Override
    public List<Bureau> findAvailableBureaux(Date startDate, Date endDate) {
        List<Bureau> allBureaux = bureauRepo.findAll();
        for (Bureau bureau : allBureaux) {
            boolean isAvailable = isBureauAvailable(bureau, startDate, endDate);
            bureau.setAvailable(isAvailable);
        }
        return allBureaux;
    }

    @Override
    public boolean isBureauAvailable(Bureau bureau, Date startDate, Date endDate) {
        List<Reservation> reservations = reservationRepo.findOverlappingReservations(bureau.getBureauid(), startDate, endDate);
        return reservations.isEmpty();
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        Date startTime = reservation.getStartTime();
        Date endTime = reservation.getEndTime();
        validateReservationPeriod(startTime, endTime);

        boolean isAvailable = isBureauAvailable(reservation.getBureau(), startTime, endTime);
        if (!isAvailable) {
            throw new IllegalArgumentException("The bureau is not available for the specified period.");
        }
        return reservationRepo.save(reservation);
    }

    private void validateReservationPeriod(Date startTime, Date endTime) {
        long duration = endTime.getTime() - startTime.getTime();
        long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);

        if (!isValidStartTime(startTime)) {
            throw new IllegalArgumentException("The reservation must start at 8 AM or 1 PM.");
        }
        if (diffInHours < 4 || diffInHours > 8) {
            throw new IllegalArgumentException("The reservation duration must be between 4 and 8 hours.");
        }
    }

    private boolean isValidStartTime(Date startTime) {
        int hour = startTime.getHours();
        return (hour == 8 || hour == 13);
    }

    @Override
    public Reservation reserver(Reservation reservation) throws Exception {
        long duration = reservation.getEndTime().getTime() - reservation.getStartTime().getTime();
        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        long days = TimeUnit.MILLISECONDS.toDays(duration);

        if (hours < 4 || days > 30) {
            throw new Exception("Reservation must be more than 4 hours and less than 30 days.");
        }

        Bureau bureau = bureauRepo.findById(reservation.getBureau().getBureauid()).orElseThrow(() -> new RuntimeException("Bureau not found"));
        boolean isAvailable = isBureauAvailable(bureau, reservation.getStartTime(), reservation.getEndTime());
        if (!isAvailable) {
            throw new IllegalArgumentException("The bureau is not available for the specified period.");
        }
        reservation.setBureau(bureau);

        return reservationRepo.save(reservation);
    }

    @Override
    public boolean isBureauAvailable(Long bureauId, Date startTime, Date endTime) {
        List<Reservation> overlappingReservations = reservationRepo.findOverlappingReservations(bureauId, startTime, endTime);
        return overlappingReservations.isEmpty();
    }

    @Override
    public Reservation addReservation(Reservation reservation) {
        Bureau bureau = bureauRepo.findById(reservation.getBureau().getBureauid()).orElseThrow(() -> new RuntimeException("Bureau not found"));
        reservation.setBureau(bureau);
        return reservationRepo.save(reservation);
    }
}
