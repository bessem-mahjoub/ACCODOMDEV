package accodom.dom.Service;
import accodom.dom.Entities.DomiciliationPack;


import java.util.List;

public interface DomiciliationPackService {
    List<DomiciliationPack> getAllDomiciliationPacks();
    DomiciliationPack getDomiciliationPackById(Long id);
    DomiciliationPack createDomiciliationPack(DomiciliationPack domiciliationPack);
    DomiciliationPack updateDomiciliationPack(DomiciliationPack domiciliationPack);
    void deleteDomiciliationPack(Long id);
    void assignPackToUser(Long packId, Long userId);
}
