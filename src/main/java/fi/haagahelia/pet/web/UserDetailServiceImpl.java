package fi.haagahelia.pet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fi.haagahelia.pet.domain.AppUser;
import fi.haagahelia.pet.domain.AppUserRepository;

/**
 * This file defines the UserDetailServiceImpl class, which is a Spring service
 * class that interacts with the AppUserRepository class. It includes a method
 * for loading a user by username.
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    AppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser currentUser = userRepository.findByUsername(username);
        UserDetails user = new org.springframework.security.core.userdetails.User(
                username,
                currentUser.getPasswordHash(),
                AuthorityUtils.createAuthorityList(currentUser.getRole()));
        return user;
    }

}
