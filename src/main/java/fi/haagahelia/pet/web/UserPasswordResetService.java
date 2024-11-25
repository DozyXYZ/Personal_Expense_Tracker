package fi.haagahelia.pet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fi.haagahelia.pet.domain.AppUser;
import fi.haagahelia.pet.domain.AppUserRepository;

/**
 * This file defines the UserPasswordResetService class, which is a Spring
 * service class that interacts with the AppUserRepository class. It includes a
 * method for resetting a user's password.
 * 
 * The users can reset their password by providing their username and the unique
 * recovery code.
 * 
 * The class uses the AppUserRepository class and BcryptPasswordEncoder method.
 */
@Service
public class UserPasswordResetService {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String resetPassword(String username, String recoveryCode, String newPassword) {
        AppUser user = userRepository.findByUsername(username);
        if (user != null && user.getRecoveryCode().equals(recoveryCode)) {
            String newPasswordHash = passwordEncoder.encode(newPassword);
            user.setPasswordHash(newPasswordHash);
            userRepository.save(user);
            return "Password reset successful. Please log in with your new password.";
        } else {
            return "Invalid recovery code or username. Please try again.";
        }
    }

}
