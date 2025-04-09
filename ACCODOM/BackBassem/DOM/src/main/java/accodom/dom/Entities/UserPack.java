package accodom.dom.Entities;

import jakarta.persistence.*;
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
public class UserPack implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private DomiciliationPack pack;

    private String clientName;
    private String clientEmail;
    private String clientPhone;
    private String companyName;

    @OneToOne(mappedBy = "userPack", cascade = CascadeType.ALL, orphanRemoval = true)
    private Contract contract;
}
