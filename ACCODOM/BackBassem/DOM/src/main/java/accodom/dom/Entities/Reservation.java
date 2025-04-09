package accodom.dom.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Bureau bureau;

    @NotNull(message = "Start time is required")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @NotNull(message = "End time is required")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    private String clientName;
    private String clientEmail;
    private String clientPhone;
    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Contract contract;
}
