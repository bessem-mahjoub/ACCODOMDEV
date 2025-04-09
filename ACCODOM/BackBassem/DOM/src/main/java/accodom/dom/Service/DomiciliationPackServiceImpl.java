package accodom.dom.Service;

import accodom.dom.Entities.DomiciliationPack;
import accodom.dom.Entities.User;
import accodom.dom.Repository.DomiciliationPackRepo;
import accodom.dom.Repository.UserRepo;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DomiciliationPackServiceImpl implements DomiciliationPackService {

    @Autowired
    private DomiciliationPackRepo domiciliationPackRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EntityManager entityManager;

    public List<DomiciliationPack> getAllDomiciliationPacks() {
        entityManager.clear(); // Clearing the JPA cache
        List<DomiciliationPack> packs = domiciliationPackRepo.findAll();
        System.out.println("Number of packs found: " + packs.size());
        return packs;    }

    @Override
    public DomiciliationPack getDomiciliationPackById(Long id) {
        Optional<DomiciliationPack> optionalPack = domiciliationPackRepo.findById(id);
        return optionalPack.orElse(null); // Or throw an exception if not found
    }

    @Override
    public DomiciliationPack createDomiciliationPack(DomiciliationPack domiciliationPack) {
        return domiciliationPackRepo.save(domiciliationPack);
    }

    @Override
    public DomiciliationPack updateDomiciliationPack(DomiciliationPack domiciliationPack) {
        if (domiciliationPack.getDomicilationId() == null || domiciliationPack.getDomicilationId() == 0) {
            throw new IllegalArgumentException("DomiciliationPack ID must not be null or zero");
        }
        Optional<DomiciliationPack> existingPack = domiciliationPackRepo.findById(domiciliationPack.getDomicilationId());
        if (existingPack.isPresent()) {
            return domiciliationPackRepo.save(domiciliationPack);
        } else {
            throw new IllegalArgumentException("DomiciliationPack with ID " + domiciliationPack.getDomicilationId() + " not found");
        }
    }

    @Override
    public void deleteDomiciliationPack(Long id) {
        domiciliationPackRepo.deleteById(id);
    }

    @Override
    public void assignPackToUser(Long packId, Long userId) {
        Optional<DomiciliationPack> optionalPack = domiciliationPackRepo.findById(packId);
        Optional<User> optionalUser = userRepo.findById(userId);

        if (optionalPack.isPresent() && optionalUser.isPresent()) {
            DomiciliationPack pack = optionalPack.get();
            User user = optionalUser.get();

            pack.setUser(user);
            domiciliationPackRepo.save(pack);
        } else {
            throw new IllegalArgumentException("Pack or User not found");
        }
    }
}
