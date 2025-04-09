package accodom.dom.Service;

import accodom.dom.Entities.Bureau;
import accodom.dom.Repository.BureauRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BureauServiceImpl implements BureauService {
    @Autowired
    private BureauRepo bureauRepository;

    @Override
    public List<Bureau> getAllBureaux() {
        return bureauRepository.findAll();
    }

    @Override
    public Bureau getBureauById(Long bureauId) {
        Optional<Bureau> bureau = bureauRepository.findById(bureauId);
        return bureau.orElse(null); // or throw an exception if preferred
    }

    @Override
    public Bureau createBureau(Bureau bureau) {
        return bureauRepository.save(bureau);
    }

    @Override
    public Bureau updateBureau(Bureau bureau) {
         return bureauRepository.save(bureau);
    }
}
