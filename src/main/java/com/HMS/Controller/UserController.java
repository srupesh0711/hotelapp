package com.HMS.Controller;

import com.HMS.Entity.AppUser;
import com.HMS.Payloads.LoginDto;
import com.HMS.Payloads.TokenDto;
import com.HMS.Repository.AppUserRepository;
import com.HMS.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
// http://localhost:8080/api/v1/users/signup
// http://localhost:8080/api/v1/users/message
// http://localhost:8080/api/v1/users/login
// http://localhost:8080/api/v1/users/signup-property-owner

public class UserController {
    private AppUserRepository appUserRepository;
    private UserService userService;

    public UserController(AppUserRepository appUserRepository, UserService userService) {
        this.appUserRepository = appUserRepository;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(
            @RequestBody AppUser user
    ){
        Optional<AppUser> opUsername =
                appUserRepository.findByUsername(user.getUsername());

        if(opUsername.isPresent()){
            return new ResponseEntity<>("Username already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<AppUser> opEmail =
                appUserRepository.findByEmail(user.getEmail());

        if(opEmail.isPresent()){
            return new ResponseEntity<>("Email already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String encryptedPasword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5));
        user.setPassword(encryptedPasword);
        user.setRole("ROLE_USER");
        AppUser savedUser = appUserRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/message")
    public String getMessage(){
        return "hello";
    }
    //http://localhost:8080/login
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginDto dto
    ){
        String token = userService.verifyLogin(dto);
        if(token!=null){
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(token);
            tokenDto.setType("JWT");
            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Invalid username/password", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/signup-property-owner")
    public ResponseEntity<?> createPropertyOwnerUSer(
            @RequestBody AppUser user
    ){
        Optional<AppUser> opUsername =
                appUserRepository.findByUsername(user.getUsername());

        if(opUsername.isPresent()){
            return new ResponseEntity<>("Username already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<AppUser> opEmail =
                appUserRepository.findByEmail(user.getEmail());

        if(opEmail.isPresent()){
            return new ResponseEntity<>("Email already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String encryptedPasword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5));
        user.setPassword(encryptedPasword);
        user.setRole("ROLE_OWNER");
        AppUser savedUser = appUserRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

}
