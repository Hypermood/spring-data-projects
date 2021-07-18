package course.springdata.mvcproject.service.services.impl;

import course.springdata.mvcproject.data.entities.Role;
import course.springdata.mvcproject.data.entities.User;
import course.springdata.mvcproject.data.repositories.RoleRepository;
import course.springdata.mvcproject.data.repositories.UserRepository;
import course.springdata.mvcproject.service.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import course.springdata.mvcproject.service.models.UserServiceModel;
import course.springdata.mvcproject.service.services.UserService;
import course.springdata.mvcproject.web.models.UserRegisterModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,ModelMapper modelMapper,
                           RoleService roleService,RoleRepository roleRepository
            ,BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserServiceModel registerUser(UserRegisterModel userRegisterModel) {
        User user = modelMapper.map(userRegisterModel, User.class);

            roleService.seedRolesInDb();

            if(userRepository.count() == 0){
                user.setRoles(Set.of(roleRepository.findAll().stream().filter(r->r.getAuthority().equals("ADMIN")).findFirst().get()));
            }
            else{
                user.setRoles(Set.of(roleRepository.findAll().stream().filter(r->r.getAuthority().equals("BASE")).findFirst().get()));
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

        return modelMapper.map(user,UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findAll().stream()
                .filter(u->u.getUsername().equals(s))
                .findFirst()
                .orElseThrow(()->new UsernameNotFoundException("User with such username doesn't exist."));
    }

    @Override
    public String toString() {
        return "UserServiceImpl{}";
    }
}
