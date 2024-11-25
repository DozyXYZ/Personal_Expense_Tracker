package fi.haagahelia.pet.web;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fi.haagahelia.pet.domain.AppUser;
import fi.haagahelia.pet.domain.AppUserRepository;

/**
 * This file defines the UserCreationService class, which is a Spring service
 * class that interacts with the AppUserRepository class. It includes a method
 * for creating a new user with a unique recovery code.
 * 
 * The class uses the AppUserRepository class and BcryptPasswordEncoder method.
 */
@Service
public class UserCreationService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AppUserRepository userRepository;

    // Generate a unique string for a recovery code with 1 capital letter and 6
    // digits (e.g., A123456)
    private String generateUniqueString() {
        Random random = new Random();
        String uniqueString;
        Iterable<AppUser> users = userRepository.findAll();
        Set<String> existingRecoveryCodes = new HashSet<>();

        for (AppUser user : users) {
            existingRecoveryCodes.add(user.getRecoveryCode());
        }

        do {
            char capitalLetter = (char) ('A' + random.nextInt(26));
            int numbers = random.nextInt(900000) + 100000;
            uniqueString = capitalLetter + String.valueOf(numbers);
        } while (existingRecoveryCodes.contains(uniqueString));

        return uniqueString;
    }

    // Create a new user with a unique recovery code
    public AppUser createUser(String username, String password, String email) {
        String passwordHash = passwordEncoder.encode(password);
        String recoveryCode = generateUniqueString();
        AppUser newUser = new AppUser(username, passwordHash, "USER", email, recoveryCode);
        return userRepository.save(newUser);
    }

}
