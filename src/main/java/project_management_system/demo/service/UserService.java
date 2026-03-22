package project_management_system.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project_management_system.demo.dto.CreateRegisterRequestDTO;
import project_management_system.demo.dto.CreateRegisterResponseDTO;
import project_management_system.demo.entity.Role;
import project_management_system.demo.entity.Task;
import project_management_system.demo.entity.User;
import project_management_system.demo.exception.BadRequestException;
import project_management_system.demo.exception.NotFoundException;
import project_management_system.demo.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CreateRegisterResponseDTO registerUser(CreateRegisterRequestDTO createRegisterRequestDTO) {
        if(userRepository.existsByEmail(createRegisterRequestDTO.getEmail())){
            throw new BadRequestException("User with this email already exists!");
        }
       User user = new User();
       user.setEmail(createRegisterRequestDTO.getEmail());
       user.setPassword(passwordEncoder.encode(createRegisterRequestDTO.getPassword()));
       user.setRole(Role.ROLE_USER);

       User savedUser = userRepository.save(user);

       return CreateRegisterResponseDTO.builder()
               .id(savedUser.getId())
               .email(savedUser.getEmail())
               .role(savedUser.getRole())
               .build();
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public Optional<User> findById(UUID id){
        return userRepository.findById(id);
    }

    public void assignRole(UUID userId, Role newRole){
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found! " + userId));
        user.setRole(newRole);
        userRepository.save(user);
    }




}
