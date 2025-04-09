package accodom.dom.Controllers;

import accodom.dom.Entities.Bureau;
import accodom.dom.Service.BureauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bureaux")
public class BureauController {

    @Autowired
    private BureauService bureauService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Bureau>> getAllBureaux() {
        List<Bureau> bureaux = bureauService.getAllBureaux();
        return ResponseEntity.ok(bureaux);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bureau> getBureauById(@PathVariable Long id) {
        Bureau bureau = bureauService.getBureauById(id);
        if (bureau != null) {
            return ResponseEntity.ok(bureau);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Bureau> createBureau(@RequestBody Bureau bureau) {
        Bureau createdBureau = bureauService.createBureau(bureau);
        return ResponseEntity.ok(createdBureau);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bureau> updateBureau(@PathVariable Long id, @RequestBody Bureau bureauDetails) {
        Bureau bureau = bureauService.getBureauById(id);
        if (bureau != null) {
            bureau.setName(bureauDetails.getName());
            bureau.setLocation(bureauDetails.getLocation());
            bureau.setCapacity(bureauDetails.getCapacity());
            Bureau updatedBureau = bureauService.updateBureau(bureau);
            return ResponseEntity.ok(updatedBureau);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
