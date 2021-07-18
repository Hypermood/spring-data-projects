package course.springdata.mvcproject.web.controllers;

import course.springdata.mvcproject.service.services.CompanyService;
import course.springdata.mvcproject.service.services.EmployeeService;
import course.springdata.mvcproject.service.services.ProjectService;
import jdk.jfr.Registered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Controller
@RequestMapping("/import")
public class ImportController extends BaseController {

    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    @Autowired
    public ImportController(CompanyService companyService, EmployeeService employeeService, ProjectService projectService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @GetMapping("/xml")
    public ModelAndView xml(){
        ModelAndView modelAndView = new ModelAndView("/xml/import-xml");
        boolean[] areImported = new boolean[3];
        areImported[0] = companyService.areImported();
        areImported[1] = projectService.areImported();
        areImported[2] = employeeService.areImported();

        modelAndView.addObject("areImported",areImported);
        return modelAndView;
    }

    @GetMapping("/companies")
    public ModelAndView companies() throws IOException {
        ModelAndView modelAndView = new ModelAndView("/xml/import-companies");
        modelAndView.addObject("companies",companyService.readCompaniesXmlFile());
        return modelAndView;
    }
    @PostMapping("/companies")
    public ModelAndView companiesImport() throws IOException, JAXBException {
        companyService.importCompanies();
        return super.redirect("/import/xml");
    }
    @GetMapping("/projects")
    public ModelAndView projects() throws IOException {
        ModelAndView modelAndView = new ModelAndView("/xml/import-projects");
        modelAndView.addObject("projects",projectService.readProjectsXmlFile());
        return modelAndView;
    }
    @PostMapping("/projects")
    public ModelAndView projectsImport() throws IOException, JAXBException {
        projectService.importProjects();
        return super.redirect("/import/xml");
    }
    @GetMapping("/employees")
    public ModelAndView employees() throws IOException {
        ModelAndView modelAndView = new ModelAndView("/xml/import-employees");
        modelAndView.addObject("employees",employeeService.readEmployeesXmlFile());
        return modelAndView;
    }
    @PostMapping("/employees")
    public ModelAndView employeesImport() throws IOException, JAXBException {
        employeeService.importEmployees();
        return super.redirect("/import/xml");
    }

}
