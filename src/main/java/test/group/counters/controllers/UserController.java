package test.group.counters.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import test.group.counters.dto.AuthRequest;
import test.group.counters.dto.AuthenticationResponse;
import test.group.counters.services.AuthenticationService;

@RestController
public class UserController
{
    private final AuthenticationService authenticationService;

    public UserController(AuthenticationService authenticationService)
    {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/api/v1/registration")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthRequest data)
    {
        return ResponseEntity.ok(authenticationService.register(data));
    }

    @PostMapping("/api/v1/authorization")
    public ResponseEntity<AuthenticationResponse> authorization(@RequestBody AuthRequest data)
    {
        return ResponseEntity.ok(authenticationService.authorization(data));
    }

    @GetMapping("/api/v1/admin")
    public ResponseEntity<String> getAdmin()
    {
        return ResponseEntity.ok("admin is working");
    }
}
