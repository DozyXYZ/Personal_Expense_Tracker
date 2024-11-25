package fi.haagahelia.pet.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * This file defines the AppUserRepository interface, which extends the
 * CrudRepository interface provided by Spring Data JPA. It is used for
 * performing CRUD operations on AppUser entities.
 * The interface includes a method for finding an AppUser entity by username.
 */

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}
