package eu.rodrigocamara.securepassword.controller;

import eu.rodrigocamara.dto.PasswordRequest;
import eu.rodrigocamara.securepassword.service.PasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping
@RestController
public class PasswordController {

    private final PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @PostMapping("/validate-password")
    public Mono<ResponseEntity<?>> validatePassword(@RequestBody PasswordRequest passwordRequest){
        return passwordService.validatePassword(passwordRequest).map(errors -> {
            if (errors.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        });
    }
}
