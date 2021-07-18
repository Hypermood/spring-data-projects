package course.springdata.mvcproject.data.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    @NotNull
    @Column(unique = true)
    private String authority;


    @Override
    public String getAuthority() {
        return authority;
    }
}
