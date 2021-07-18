package course.springdata.mvcproject.web.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterModel {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;

}
