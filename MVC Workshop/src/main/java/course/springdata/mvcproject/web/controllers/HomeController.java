package course.springdata.mvcproject.web.controllers;


import course.springdata.mvcproject.service.services.CompanyService;
import course.springdata.mvcproject.service.services.EmployeeService;
import course.springdata.mvcproject.service.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController {

    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    @Autowired
    public HomeController(CompanyService companyService, EmployeeService employeeService, ProjectService projectService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @RequestMapping(path = "/",method = RequestMethod.GET)
    public ModelAndView index(){
        return new ModelAndView("index");
    }

    @RequestMapping(path = "/home",method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = super.view("home");
        boolean areImported = companyService.areImported() && employeeService.areImported() && projectService.areImported();
        modelAndView.addObject("areImported",areImported);
        return modelAndView;
    }

}
