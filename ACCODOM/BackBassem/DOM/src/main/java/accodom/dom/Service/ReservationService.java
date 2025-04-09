package accodom.dom.Service;

import accodom.dom.Entities.Bureau;
import accodom.dom.Entities.Reservation;

import java.util.Date;
import java.util.List;

public interface ReservationService {
    List<Bureau> findAvailableBureaux(Date startDate, Date endDate);
    boolean isBureauAvailable(Bureau bureau, Date startDate, Date endDate);
    Reservation createReservation(Reservation reservation);

    Reservation reserver(Reservation reservation) throws Exception;

    public boolean isBureauAvailable(Long bureauId, Date startTime, Date endTime);
    public Reservation addReservation(Reservation reservation);

}
