package fi.haagahelia.pet;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import fi.haagahelia.pet.domain.TypeExpense;
import fi.haagahelia.pet.domain.TypeExpenseRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TypeExpenseRepositoryTest {

    @Autowired
    private TypeExpenseRepository repository;

    @Test
    public void findByTypeShouldReturnTypeExpense() {
        String type = "Housing";
        assertThat(repository.findByType(type)).isNotNull();
    }

    @Test
    public void testCreateTypeExpense() {
        repository.save(new TypeExpense("Test"));
        assertThat(repository.findByType("Test")).isNotNull();
    }

    @Test
    public void testDeleteTypeExpense() {
        repository.save(new TypeExpense("Test"));
        repository.delete(repository.findByType("Test"));
        assertThat(repository.findByType("Test")).isNull();
    }

    @Test
    public void testUpdateTypeExpense() {
        TypeExpense type = repository.findByType("Housing");
        type.setType("Test");
        repository.save(type);
        assertThat(repository.findByType("Test")).isNotNull();
    }
}
