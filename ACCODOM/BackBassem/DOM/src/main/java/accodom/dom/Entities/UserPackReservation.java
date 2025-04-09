package accodom.dom.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPackReservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "Company legal name is required")
    @Size(min = 1, message = "Company legal name must not be empty")
    private String companyLegalName;

    @NotNull(message = "Company legal status is required")
    @Size(min = 1, message = "Company legal status must not be empty")
    private String numSiren;

    @NotNull(message = "Company address is required")
    @Size(min = 1, message = "Company address must not be empty")
    private String companyAddress;

}
