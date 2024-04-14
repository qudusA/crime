package org.fexisaf.crimerecordmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "userEntity", orphanRemoval = true)
    private List<TokenEntity> tokenEntities;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "investigatingOfficer", orphanRemoval = true)
    private List<CrimeEntity> crimeEntities;


    @Email(message = "not a valid email address",
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @NotNull(message = "email is required")
    @NotBlank(message = "email cannot be blank")
    @Column(name = "email", unique = true)
    private String email;

    @NotNull(message = "password is empty")
    @NotBlank(message = "password cannot be blank")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z]).{10,}$",
            message = "password must be at least 10 words," +
                    "must include number, at least 1 upperCase character ")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "first name is required")
    @NotNull(message = "first name is required")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "last name is required")
    @NotNull(message = "last name is required")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "date of birth is required")
    @NotNull(message = "date of birth is required")
    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @NotBlank(message = "phone number is required")
    @NotNull(message = "phone number is required")
    @Pattern(regexp = "^\\+?\\d{8,15}$",message = "invalid phone number")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull(message = "home address is required")
    @NotBlank(message = "home address cannot be blank")
    @Column(name = "home_address")
    private String address;


    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    private boolean isVerified = false;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime cratedAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isVerified;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
