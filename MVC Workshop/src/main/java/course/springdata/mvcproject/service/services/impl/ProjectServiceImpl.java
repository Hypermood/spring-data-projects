package course.springdata.mvcproject.service.services.impl;

import course.springdata.mvcproject.data.entities.Company;
import course.springdata.mvcproject.data.entities.Project;
import course.springdata.mvcproject.data.repositories.CompanyRepository;
import course.springdata.mvcproject.data.repositories.ProjectRepository;
import course.springdata.mvcproject.service.dtos.companies.CompaniesDto;
import course.springdata.mvcproject.service.dtos.companies.CompanyDto;
import course.springdata.mvcproject.service.dtos.projects.ProjectDto;
import course.springdata.mvcproject.service.dtos.projects.ProjectsDto;
import course.springdata.mvcproject.util.XmlParser;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import course.springdata.mvcproject.service.services.ProjectService;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              CompanyRepository companyRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public void importProjects() throws IOException, JAXBException {
        ProjectsDto projectsDto = xmlParser.parse(ProjectsDto.class, readProjectsXmlFile());

        Converter<CompanyDto,Company> compConverter = ctx->{
            Optional<Company> checked = companyRepository.findAll().stream()
                    .filter(c -> c.getName().equals(ctx.getSource().getName())).findFirst();

            return checked.orElseGet(() -> new Company(ctx.getSource().getName()));

        };


        TypeMap<ProjectDto,Project> customMap = modelMapper.createTypeMap(ProjectDto.class,Project.class)
                .addMappings(mapper->{
                    mapper.map(ProjectDto::getName,Project::setName);
                    mapper.map(ProjectDto::getDescription,Project::setDescription);
                    mapper.map(ProjectDto::getStartDate,Project::setStartDate);
                    mapper.map(ProjectDto::isFinished,Project::setFinished);
                    mapper.map(ProjectDto::getPayment,Project::setPayment);
                    mapper.using(compConverter).map(ProjectDto::getCompanyDto,Project::setCompany);
                });


        projectsDto.getListProjectDtos().stream().forEach(c->{
            try{
                Project proj = customMap.map(c);
                System.out.println();
                projectRepository.save(proj);
            }
            catch(Exception err){

            }
        });
    }

    @Override
    public boolean areImported() {
        return projectRepository.findAll().size() >0;
    }

    @Override
    public String readProjectsXmlFile() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/xmls/projects.xml"));
    }

    @Override
    public String exportFinishedProjects(){
        return projectRepository.findAll().stream().filter(p-> p.isFinished()).map(p->{
            ProjectDto temp = modelMapper.map(p, ProjectDto.class);
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
