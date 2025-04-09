package accodom.dom.Service;

import accodom.dom.Entities.Bureau;

import java.util.List;

public interface BureauService {
    List<Bureau> getAllBureaux();
    Bureau getBureauById(Long bureauId);
    Bureau createBureau(Bureau bureau);
    Bureau updateBureau(Bureau bureau);
}
