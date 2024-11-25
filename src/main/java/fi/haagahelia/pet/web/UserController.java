package fi.haagahelia.pet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This file defines the UserController class, which is a Spring MVC controller
 * class that handles HTTP requests related to users in the application.
 * 
 * The class includes methods for displaying the login, registration, and reset
 * password forms, as well as creating a new user and resetting a user's
 * password.
 * 
 * The class utilizes the UserCreationService and UserPasswordResetService
 * classes.
 */
@Controller
public class UserController {

    @Autowired
    private UserCreationService creationService;

    @Autowired
    private UserPasswordResetService passwordResetService;

    /**
     * Handles requests to the "/login" URL.
     * 
     * @return the name of the view to be rendered ("login")
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Handles requests to the "/register" URL.
     * 
     * @return the name of the view to be rendered ("register")
     */
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    /**
     * Handles requests to the "/reset" URL.
     * 
     * @return the name of the view to be rendered ("resetpassword")
     */
    @GetMapping("/reset")
    public String showResetPasswordForm() {
        return "resetpassword";
    }

    /**
     * Handles requests to the "/createUser" URL.
     * 
     * @param username the username of the new user
     * @param password the password of the new user
     * @param email    the email of the new user
     * @return a redirect to the "/login" URL
     */
    @PostMapping("/createUser")
    public String createUser(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        creationService.createUser(username, password, email);
        return "redirect:/login";
    }

    /**
     * Handles requests to the "/resetPassword" URL.
     * 
     * @param username     the username of the user whose password is being reset
     * @param recoveryCode the recovery code of the user
     * @param newPassword  the new password for the user
     * @param model        the model to which attributes are added
     * @return the name of the view to be rendered ("resetpassword")
     */
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam String username, @RequestParam String recoveryCode,
            @RequestParam String newPassword, Model model) {
        String message = passwordResetService.resetPassword(username, recoveryCode, newPassword);
        model.addAttribute("message", message);
        return "resetpassword";
    }

}
