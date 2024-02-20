package az.code.agency.controller;

import az.code.agency.dto.request.LoginRequest;
import az.code.agency.dto.request.RegisterRequest;
import az.code.agency.dto.response.Response;
import az.code.agency.entity.Offer;
import az.code.agency.service.AgentServiceImpl;
import az.code.agency.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerAgent(@RequestBody RegisterRequest request) {
        return userService.registerAgent(request);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> loginAgent(@RequestBody LoginRequest loginRequest) {
        return userService.loginAgent(loginRequest);
    }


    @GetMapping("/confirmation")
    public ResponseEntity<?> confirmation(@RequestParam("confirmationToken") String confirmationToken) {
        return userService.confirmation(confirmationToken);
    }

}
