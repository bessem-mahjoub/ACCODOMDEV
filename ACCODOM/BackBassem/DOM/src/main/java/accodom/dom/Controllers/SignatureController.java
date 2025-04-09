package accodom.dom.Controllers;
import accodom.dom.DTO.SignatureRequest;
import accodom.dom.Service.YousignService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/signature")
public class SignatureController {

    @Autowired
    private YousignService yousignService;

    @PostMapping("/request")
    public ResponseEntity<String> createSignature(@RequestBody @Valid SignatureRequest request) {
        try {
            String response = yousignService.createSignatureRequest(request).block();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

}
