package project_management_system.demo.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project_management_system.demo.dto.AssignRoleRequestDTO;
import project_management_system.demo.dto.CreateRegisterRequestDTO;
import project_management_system.demo.dto.CreateRegisterResponseDTO;
import project_management_system.demo.entity.Role;
import project_management_system.demo.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<CreateRegisterResponseDTO> register(@Valid @RequestBody CreateRegisterRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(dto));
    }

    @PostMapping("/assign-role")
    public ResponseEntity<Void> assignRole(@Valid @RequestBody AssignRoleRequestDTO dto){
        userService.assignRole(dto.getUserId(), dto.getRole());
        return ResponseEntity.ok().build();
    }
}
