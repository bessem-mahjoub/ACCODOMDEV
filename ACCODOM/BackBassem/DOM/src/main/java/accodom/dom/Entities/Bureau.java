package accodom.dom.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bureau implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bureauid;

    public Long getBureauid() {
        return bureauid;
    }

    private String name;         // New field
    private String location;     // New field
    private int capacity;        // New field

    private int price;        // New field


    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Transient
    private boolean available;
    @JsonIgnore // Ignore the reservations field during serialization
    @OneToMany(mappedBy = "bureau", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;
}
