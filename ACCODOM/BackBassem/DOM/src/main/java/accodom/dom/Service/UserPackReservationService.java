package accodom.dom.Service;

import accodom.dom.Entities.UserPackReservation;

public interface UserPackReservationService {
     UserPackReservation saveReservation(UserPackReservation reservation, Long userId);

    }
