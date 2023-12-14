package rs.raf.demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.Teacher;
import rs.raf.demo.model.User;
import rs.raf.demo.services.TeacherService;
import rs.raf.demo.services.UserService;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers(@RequestHeader (name = "Authorization") String token){
        List<User> userList = userService.findAllUsers(token);
        if(userList == null){
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(userList);
    };
    @PostMapping(value = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewUser(@RequestHeader (name = "Authorization") String token, @RequestBody User user){
        User usr = userService.createUser(token, user);
        if(usr == null){
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(usr);
    };
    @PostMapping(value = "/delete",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@RequestHeader (name = "Authorization") String token, @RequestBody User user){
        User usr = userService.deleteUser(token, user);
        if(usr == null){
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(usr);
    };
    @PostMapping(value = "/update",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestHeader (name = "Authorization") String token, @RequestBody User user){
        User usr = userService.updateUser(token, user);
        if(usr == null){
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(usr);
    };


}
