package course.springdata.mvcproject.web.controllers;


import course.springdata.mvcproject.service.services.EmployeeService;
import course.springdata.mvcproject.service.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/export")
public class ExportController extends BaseController {

    private ProjectService projectService;
    private EmployeeService employeeService;

    @Autowired
    public ExportController(ProjectService projectService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/project-if-finished")
    public ModelAndView finishedProjects() throws IOException {
        ModelAndView modelAndView = new ModelAndView("/export/export-project-if-finished");
        modelAndView.addObject("projectsIfFinished",projectService.exportFinishedProjects());
        return modelAndView;
    }
    @GetMapping("/employees-above")
    public ModelAndView companies() throws IOException {
        ModelAndView modelAndView = new ModelAndView("/export/export-employees-with-age");
        modelAndView.addObject("employeesAbove",employeeService.exportEmployeesWithAgeAbove());
        return modelAndView;
    }
}
