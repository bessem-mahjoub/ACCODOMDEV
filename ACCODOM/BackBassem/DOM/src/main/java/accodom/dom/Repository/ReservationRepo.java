package accodom.dom.Repository;


import accodom.dom.Entities.Bureau;
import accodom.dom.Entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.bureau = :bureau " +
            "AND ((r.startTime >= :startDate AND r.startTime < :endDate) " +
            "OR (r.endTime > :startDate AND r.endTime <= :endDate))")
    List<Reservation> findByBureauAndDates(@Param("bureau") Bureau bureau,
                                           @Param("startDate") Date startDate,
                                           @Param("endDate") Date endDate);


    @Query("SELECT r FROM Reservation r WHERE r.bureau.id = :bureauId AND " +
            "((:startTime BETWEEN r.startTime AND r.endTime) OR " +
            "(:endTime BETWEEN r.startTime AND r.endTime) OR " +
            "(r.startTime BETWEEN :startTime AND :endTime) OR " +
            "(r.endTime BETWEEN :startTime AND :endTime))")
    List<Reservation> findOverlappingReservations(@Param("bureauId") Long bureauId,
                                                  @Param("startTime") Date startTime,
                                                  @Param("endTime") Date endTime);

    List<Reservation> findAllByBureauBureauid(Long bureauId);

}
