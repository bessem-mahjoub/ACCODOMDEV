package accodom.dom.Controllers;


import accodom.dom.Entities.Bureau;
import accodom.dom.Entities.User;
import accodom.dom.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@NoArgsConstructor

public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @PostMapping
    User createUser(@RequestBody User user){

        return userService.createUser(user);
    }
    @PutMapping("/{id}")
    User updateUser(@RequestBody User user){
         return userService.updateUser(user);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllBureaux() {
        List<User> bureaux = userService.getAllUsers();
        return ResponseEntity.ok(bureaux);
    }

    @GetMapping("/get/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        User user = userService.findUserByEmail(email);
        return ResponseEntity.ok(user);
    }


}
