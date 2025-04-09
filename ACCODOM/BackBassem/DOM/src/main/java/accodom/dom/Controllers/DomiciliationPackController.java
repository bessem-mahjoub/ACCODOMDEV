package accodom.dom.Controllers;

import accodom.dom.Entities.DomiciliationPack;
import accodom.dom.Repository.DomiciliationPackRepo;
import accodom.dom.Service.DomiciliationPackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pack")
@CrossOrigin("http://localhost:4200/")
public class DomiciliationPackController {

    @Autowired
    private DomiciliationPackService domiciliationPackService;

    @Autowired
    private DomiciliationPackRepo dpr;

    @GetMapping("/getAll")
    public ResponseEntity<List<DomiciliationPack>> getAllDomiciliationPacks() {
        List<DomiciliationPack> packs = dpr.findAll();
        return ResponseEntity.ok(packs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DomiciliationPack> getDomiciliationPackById(@PathVariable Long id) {
        DomiciliationPack pack = domiciliationPackService.getDomiciliationPackById(id);
        if (pack != null) {
            return ResponseEntity.ok(pack);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<DomiciliationPack> createDomiciliationPack(@RequestBody DomiciliationPack domiciliationPack) {
        DomiciliationPack createdPack = domiciliationPackService.createDomiciliationPack(domiciliationPack);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPack);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DomiciliationPack> updateDomiciliationPack(
            @PathVariable Long id,
            @RequestBody DomiciliationPack domiciliationPack) {
        try {
            domiciliationPack.setDomicilationId(id);
            DomiciliationPack updatedPack = domiciliationPackService.updateDomiciliationPack(domiciliationPack);
            return ResponseEntity.ok(updatedPack);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDomiciliationPack(@PathVariable Long id) {
        domiciliationPackService.deleteDomiciliationPack(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/assign")
    public ResponseEntity<?> assignPackToUser(@RequestParam Long packId, @RequestParam Long userId) {
        domiciliationPackService.assignPackToUser(packId, userId);
        return ResponseEntity.ok().body("Pack assigned to user successfully");
    }
}
