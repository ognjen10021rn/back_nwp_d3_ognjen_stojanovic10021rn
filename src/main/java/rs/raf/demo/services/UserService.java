package rs.raf.demo.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.raf.demo.configuration.SpringSecurityConfig;
import rs.raf.demo.model.User;
import rs.raf.demo.repositories.UserRepository;
import rs.raf.demo.utils.JwtUtil;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private JwtUtil jwtUtil;
    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User myUser = this.userRepository.findByUsername(username);
        if(myUser == null) {
            throw new UsernameNotFoundException("User name "+username+" not found");
        }

        return new org.springframework.security.core.userdetails.User(myUser.getUsername(), myUser.getPassword(), new ArrayList<>());
    }

    public List<User> findAllUsers(String token){
        String jwt = token.substring(7);
        String username = jwtUtil.extractUsername(jwt);
        User user = userRepository.findByUsername(username);
        if (user.isCan_read_users()){
            return userRepository.findAll();
        }
        return null;
    }
    public User createUser(String token, User usr){
        String jwt = token.substring(7);
        String username = jwtUtil.extractUsername(jwt);
        User user = userRepository.findByUsername(username);
        if (user.isCan_create_users()){
            User newUser = new User();
            newUser.setUsername(usr.getUsername());
            newUser.setPassword(usr.getPassword());
            newUser.setCan_delete_users(usr.isCan_delete_users());
            newUser.setCan_update_users(usr.isCan_update_users());
            newUser.setCan_read_users(usr.isCan_read_users());
            newUser.setCan_create_users(usr.isCan_create_users());
            return (User)userRepository.save(newUser);
        }
        return null;
    }
    public User deleteUser(String token, User usr){
        String jwt = token.substring(7);
        String username = jwtUtil.extractUsername(jwt);
        User user = userRepository.findByUsername(username);
        if (user.isCan_delete_users()){
            User deleteUser = userRepository.findByUsername(usr.getUsername());
            userRepository.delete(deleteUser);
            return deleteUser;
        }
        return null;
    }
    public User updateUser(String token, User usr){
        String jwt = token.substring(7);
        String username = jwtUtil.extractUsername(jwt);
        User user = userRepository.findByUsername(username);
        if (user.isCan_update_users()){
            return userRepository.save(usr);
        }
        return null;
    }

}
