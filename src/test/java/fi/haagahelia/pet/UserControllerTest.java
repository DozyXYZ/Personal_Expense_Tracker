package fi.haagahelia.pet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Test for accessing the login page. Status 200 is expected.
    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(get("/login")).andExpect(status().isOk());
    }

    // Test for accessing the registration page. Status 200 is expected.
    @Test
    public void testShowResgistrationForm() throws Exception {
        mockMvc.perform(get("/register")).andExpect(status().isOk());
    }

    // Test for accessing the reset password page. Status 200 is expected.
    @Test
    public void testShowResetPasswordForm() throws Exception {
        mockMvc.perform(get("/reset")).andExpect(status().isOk());
    }
}
