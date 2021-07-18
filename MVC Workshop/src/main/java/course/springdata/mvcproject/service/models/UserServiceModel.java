package course.springdata.mvcproject.service.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceModel {

    private String username;
    private String password;
    private String email;
    private Set<RoleServiceModel> roles;
}
