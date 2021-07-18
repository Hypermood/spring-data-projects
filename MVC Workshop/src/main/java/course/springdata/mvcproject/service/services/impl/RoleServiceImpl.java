package course.springdata.mvcproject.service.services.impl;

import course.springdata.mvcproject.data.entities.Role;
import course.springdata.mvcproject.data.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import course.springdata.mvcproject.service.models.RoleServiceModel;
import course.springdata.mvcproject.service.services.RoleService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository,ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedRolesInDb() {
        Role admin = new Role("ADMIN");
        Role base = new Role("BASE");
        List<Role> collector = List.of(admin,base);

        collector.stream().forEach(r->{
                Optional<Role> temp = roleRepository.findAll().stream()
                        .filter(m -> m.getAuthority().equals(r.getAuthority())).findFirst();
                if(temp.isEmpty()){
                    roleRepository.save(r);
                }
        });

    }

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        List<Role> all = roleRepository.findAll();
        return all.stream().map(r -> modelMapper.map(r, RoleServiceModel.class)).collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        Role role = roleRepository.findByAuthority(authority);
        return modelMapper.map(role,RoleServiceModel.class);
    }
}
