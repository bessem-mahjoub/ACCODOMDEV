package accodom.dom.Repository;

import accodom.dom.Entities.Bureau;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface BureauRepo extends JpaRepository<Bureau, Long> {
  /*  Optional<Bureau> findByAddress(String address);
    Bureau findById(int officeId);*/
}
