package course.springdata.mvcproject.service.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import course.springdata.mvcproject.service.models.UserServiceModel;
import course.springdata.mvcproject.web.models.UserRegisterModel;

public interface UserService extends UserDetailsService {

    UserServiceModel registerUser(UserRegisterModel userRegisterModel);
}
