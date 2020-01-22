package ua.logos.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.logos.domain.ErrorDTO;
import ua.logos.domain.UserDTO;
import ua.logos.service.FileStorageService;
import ua.logos.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDTO userDTO, BindingResult br) {

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
            userService.saveUser(userDTO);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> userDTOS = userService.getAllUser();
        return new ResponseEntity<List<UserDTO>>(userDTOS, HttpStatus.OK);
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long id) {
        UserDTO userDTO = userService.findUserById(id);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @GetMapping("find")
    public ResponseEntity<?> getUserByEmail(@RequestParam("email") String email){
        UserDTO userDTO = userService.findUserByEmail(email);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<?> getUserByEmailOrName(
            @RequestParam("email") String email,
            @RequestParam("name") String name
    ) {
        List<UserDTO> userDTOS = userService.findUserByNameLikeOrEmail("%" + name + "%", email);
        return new ResponseEntity<List<UserDTO>>(userDTOS, HttpStatus.OK);
    }

    @PostMapping("{userId}/image")
    public ResponseEntity<?> uploadImage(
            @PathVariable("userId") Long id,
            @RequestParam("file") MultipartFile file
    ) {
        fileStorageService.storeFile(file, "user_id_" + id + getFileExtension(file));
        UserDTO userDTO = userService.findUserById(id);
        userDTO.setImage("user_id_" + id + getFileExtension(file));
        userService.saveUser(userDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("{userId}/image")
    public ResponseEntity<?> getImage(
            @PathVariable("userId") Long id,
            HttpServletRequest request
    ) {
        UserDTO userDTO = userService.findUserById(id);
        String fileName = userDTO.getImage();
        Resource resource = fileStorageService.loadFile(fileName);

        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline: filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private String getFileExtension(MultipartFile file) {
        String name = file.getOriginalFilename();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }

}
