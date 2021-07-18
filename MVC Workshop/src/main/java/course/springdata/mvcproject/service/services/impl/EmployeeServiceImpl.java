package course.springdata.mvcproject.service.services.impl;

import course.springdata.mvcproject.data.entities.Company;
import course.springdata.mvcproject.data.entities.Employee;
import course.springdata.mvcproject.data.entities.Project;
import course.springdata.mvcproject.data.repositories.CompanyRepository;
import course.springdata.mvcproject.data.repositories.EmployeeRepository;
import course.springdata.mvcproject.data.repositories.ProjectRepository;
import course.springdata.mvcproject.service.dtos.companies.CompanyDto;
import course.springdata.mvcproject.service.dtos.employees.EmployeeDto;
import course.springdata.mvcproject.service.dtos.employees.EmployeesDto;
import course.springdata.mvcproject.service.dtos.projects.ProjectDto;
import course.springdata.mvcproject.service.dtos.projects.ProjectsDto;
import course.springdata.mvcproject.util.XmlParser;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import course.springdata.mvcproject.service.services.EmployeeService;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               ProjectRepository projectRepository,
                               CompanyRepository companyRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public void importEmployees() throws IOException, JAXBException {
        EmployeesDto employeesDto = xmlParser.parse(EmployeesDto.class, readEmployeesXmlFile());

        Converter<ProjectDto, Project> projConverter = ctx->{
            Optional<Project> checked = projectRepository.findAll().stream()
                    .filter(p -> p.getName().equals(ctx.getSource().getName())).findFirst();

            if(checked.isPresent()){
                return checked.get();
            }
            else{
                ProjectDto source = ctx.getSource();
                Project project = new Project(source.getName(),source.getDescription()
                        ,source.isFinished(),source.getPayment(),source.getStartDate());

                Optional<Company> checkedComp = companyRepository.findAll().stream()
                        .filter(c -> c.getName().equals(ctx.getSource().getName())).findFirst();

                if(checkedComp.isPresent()){
                    project.setCompany(checkedComp.get());
                }
                else{
                    project.setCompany(new Company(source.getCompanyDto().getName()));
                }
                return project;
            }
        };


        TypeMap<EmployeeDto, Employee> empMap = modelMapper.createTypeMap(EmployeeDto.class,Employee.class)
                .addMappings(mapper->{
                    mapper.map(EmployeeDto::getFirstName,Employee::setFirstName);
                    mapper.map(EmployeeDto::getLastName,Employee::setLastName);
                    mapper.map(EmployeeDto::getAge,Employee::setAge);
                    mapper.using(projConverter).map(EmployeeDto::getProject,Employee::setProject);
                });


        employeesDto.getEmployeeDtoList().stream().forEach(c->{
            try{
                Employee emp = empMap.map(c);
                System.out.println();
                employeeRepository.save(emp);
            }
            catch(Exception err){

            }
        });
    }

    @Override
    public boolean areImported() {
        return employeeRepository.findAll().size() >0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/xmls/employees.xml"));
    }

    @Override
    public String exportEmployeesWithAgeAbove() {
        return employeeRepository.findAll().stream().filter(p-> p.getAge()>25).map(p->{
            EmployeeDto temp = modelMapper.map(p, EmployeeDto.class);
            String stringed = null;
            try {
                stringed = xmlParser.stringify(temp);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
            return stringed;
        }).collect(Collectors.joining("\n"));
    }
}
