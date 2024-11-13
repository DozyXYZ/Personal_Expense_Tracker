package fi.haagahelia.pet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fi.haagahelia.pet.domain.AppUser;
import fi.haagahelia.pet.domain.AppUserRepository;

@Service
public class UserCreationService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AppUserRepository userRepository;

    public AppUser createUser(String username, String password, String email) {
        String passwordHash = passwordEncoder.encode(password);
        AppUser newUser = new AppUser(username, passwordHash, "USER", email);
        return userRepository.save(newUser);
    }

}
