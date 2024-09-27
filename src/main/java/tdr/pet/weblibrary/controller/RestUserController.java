package tdr.pet.weblibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tdr.pet.weblibrary.model.dto.UserDTO;
import tdr.pet.weblibrary.model.mapper.UserMapper;
import tdr.pet.weblibrary.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "Users Controller", description = "Endpoints for managing users")
public class RestUserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Invalid user details")
    })
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserDTO userDTO) {
        userService.createNewUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "User login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody UserDTO userDTO) {
        // todo impl
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "User logout")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // todo impl
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'MANAGER')")
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PostMapping("/")
    public ResponseEntity<Void> createUser(@RequestBody UserDTO userDTO) {
        userService.createNewUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'MANAGER')")
    @Operation(summary = "Delete a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'MANAGER')")
    @Operation(summary = "Check if a user exists by email or username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping("/exists")
    public ResponseEntity<Boolean> exists(@RequestParam String email, @RequestParam String username) {
        boolean exists = userService.existsByEmailOrUsername(email, username);
        return ResponseEntity.ok(exists);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'MANAGER')")
    @Operation(summary = "Get paginated users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping("/")
    public ResponseEntity<Page<UserDTO>> getUsers(@RequestParam Integer pageNumber, Integer pageSize) {
        return ResponseEntity.ok(userService.getUsers(PageRequest.of(pageNumber, pageSize)).map(userMapper::toDTO));
    }
}