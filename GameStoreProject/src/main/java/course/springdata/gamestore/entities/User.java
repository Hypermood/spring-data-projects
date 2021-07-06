package course.springdata.gamestore.entities;

import course.springdata.gamestore.validations.annotations.Email;
import course.springdata.gamestore.validations.annotations.Password;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Email
    @Column(unique = true)
    private String email;
    @NonNull
    @Password
    private String password;
    @NonNull
    private String fullName;
    private boolean isAdministrator;
    private boolean isLogged;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Game> games = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
    @Transient
    private List<Game> shoppingCart;

    public User(@NonNull @Email String email, @NonNull @Password String password, @NonNull String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public User(@NonNull String email, @NonNull String password, @NonNull String fullName, List<Game> games) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.games = games;
    }
}
