package ua.logos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.logos.domain.ErrorDTO;
import ua.logos.domain.SigninRequest;
import ua.logos.domain.SigninResponse;
import ua.logos.domain.UserDTO;
import ua.logos.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserDTO userDTO, BindingResult br) {


        boolean confirmPassword = false;
        if (userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            confirmPassword = true;
        }

        if (br.hasErrors()) {
            System.out.println("[USER]: Validation error");

            String errMsg = br.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().get().toString();
            ErrorDTO error = new ErrorDTO(errMsg);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        } else if (!confirmPassword) {
            System.out.println("[USER]: Validation error. The passwords are not equals");
            ErrorDTO error = new ErrorDTO("The passwords are not equals");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        } else {
            authService.signup(userDTO);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping("signin")
    public ResponseEntity<?> signin(@RequestBody SigninRequest request) {
        String token = authService.signin(request);
        return new ResponseEntity<>(new SigninResponse(token), HttpStatus.OK);
    }
}
