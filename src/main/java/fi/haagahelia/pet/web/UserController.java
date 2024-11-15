package fi.haagahelia.pet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserCreationService creationService;

    @Autowired
    private UserPasswordResetService passwordResetService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @GetMapping("/reset")
    public String showResetPasswordForm() {
        return "resetpassword";
    }

    @PostMapping("/createUser")
    public String createUser(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        creationService.createUser(username, password, email);
        return "redirect:/login";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam String username, @RequestParam String recoveryCode,
            @RequestParam String newPassword, Model model) {
        String message = passwordResetService.resetPassword(username, recoveryCode, newPassword);
        model.addAttribute("message", message);
        return "resetpassword";
    }

}
