package fi.haagahelia.pet;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import fi.haagahelia.pet.domain.AppUser;
import fi.haagahelia.pet.domain.AppUserRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository repository;

    @Test
    public void findByUsernameShouldReturnAppUser() {
        AppUser user = repository.findByUsername("user1");
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("user1");
    }

    @Test
    public void testCreateUser() {
        AppUser user999 = new AppUser("user999", "$2a$12$U7bImtm90EDjzZ6jb6V0aecm8wW1dsOOLW0ghl2ItumyzH3f4UD9G",
                "USER", "xxx@email.fi", "X123456");
        repository.save(user999);
        assertThat(user999.getId()).isNotNull();
    }

    @Test
    public void testUpdateUser() {
        AppUser user1 = repository.findByUsername("user1");
        user1.setRole("ADMIN");
        repository.save(user1);
        assertThat(user1.getRole()).isEqualTo("ADMIN");
    }
}
