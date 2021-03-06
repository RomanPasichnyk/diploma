package ua.logos.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.logos.config.jwt.JwtTokenProvider;
import ua.logos.domain.SigninRequest;
import ua.logos.domain.UserDTO;
import ua.logos.entity.RoleEntity;
import ua.logos.entity.UserEntity;
import ua.logos.exception.AlreadyExistsException;
import ua.logos.exception.ResourceNotFoundException;
import ua.logos.repository.RoleRepository;
import ua.logos.repository.UserRepository;
import ua.logos.service.AuthService;
import ua.logos.utils.ObjectMapperUtils;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapperUtils modelMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void signup(UserDTO userDTO) {

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new AlreadyExistsException("User with email " + userDTO.getEmail() + " already exists");
        }

        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        RoleEntity role = roleRepository.findByName("USER").orElseThrow(
                () -> new ResourceNotFoundException("Role not found!")
        );

        Set<RoleEntity> roles = new HashSet<>();
        roles.add(role);
        userEntity.setRoles(roles);

        userRepository.save(userEntity);
    }

    @Override
    public String signin(SigninRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }
}
