package com.project.open_hands.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oh_users")
public class User {

    @Id
    @Email(message = "Provide valid email")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "Provide valid first name")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Provide valid last name")
    @Column(nullable = false)
    private String lastName;

    @NotBlank(message = "Provide valid password")
    @Column(nullable = false)
    private String password;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy o1 ? o1.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy thisObj ? thisObj.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getEmail() != null && Objects.equals(getEmail(), user.getEmail());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
