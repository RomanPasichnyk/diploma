package ua.logos.service;

import ua.logos.domain.FilmDTO;
import ua.logos.domain.SigninRequest;
import ua.logos.domain.UserDTO;

import java.util.List;

public interface AuthService {

    void signup(UserDTO userDTO);

    String signin(SigninRequest request);



}
