package accodom.dom.Repository;
import accodom.dom.Entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepo extends JpaRepository<Contract, Long> {
}
