package eu.rodrigocamara.securepassword.service;

import eu.rodrigocamara.dto.PasswordRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class PasswordService {

    private List<String> errors;

    public Mono<List<String>> validatePassword(PasswordRequest password){
        errors = new ArrayList<>();
        return Mono.just(password.getPassword())
                .map(pw -> {
                    validateLength(pw);
                    validateUpperCase(pw);
                    validateLowerCase(pw);
                    validateNumber(pw);
                    validateSpecialCharacter(pw);

                    return errors;
                });
    }

    private void validateLength(String password){
        if (!StringUtils.isNotBlank(password) || password.length() < 8) {
            errors.add("Password does not contain at least 8 characters");
        }
    }

    private void validateUpperCase(String password){
        if (password.chars().filter(Character::isUpperCase).findAny().isEmpty()) {
            errors.add("Password does not contain upper case characters");
        }
    }

    private void validateLowerCase(String password){
        if (password.chars().filter(Character::isLowerCase).findAny().isEmpty()) {
            errors.add("Password does not contain lower case characters");
        }
    }

    private void validateNumber(String password){
        if (password.chars().filter(Character::isDigit).findAny().isEmpty()) {
            errors.add("Password does not contain numbers");
        }
    }

    private void validateSpecialCharacter(String password){
        if (!Pattern.compile("[^\\p{L}\\d\\s_]").matcher(password).find()) {
            errors.add("Password does not contain special character");
        }
    }

}
