package fi.haagahelia.pet.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

/**
 * This file defines the AppUser class, which represents a user in the
 * application.
 * The class includes attributes: username, password, email, role, and recovery
 * It provides methods for user authentication, role management, and other
 * user-related functionalities.
 */

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "passwordHash", nullable = false)
    private String passwordHash;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "recoveryCode", nullable = false)
    private String recoveryCode;

    public AppUser() {
    }

    public AppUser(String username, String passwordHash, String role, String email, String recoveryCode) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.email = email;
        this.recoveryCode = recoveryCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRecoveryCode() {
        return recoveryCode;
    }

    public void setRecoveryCode(String recoveryCode) {
        this.recoveryCode = recoveryCode;
    }

}
