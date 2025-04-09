package accodom.dom.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Contract implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;

    @OneToOne
    private Reservation reservation;

    @OneToOne
    private UserPack userPack;

    private Date contractDate;
    private String terms;
    private String clientName;
    private String clientEmail;
    private String companyName;
    private String contractDetails;
}
