package course.springdata.mvcproject.service.dtos.employees;

import course.springdata.mvcproject.service.dtos.companies.CompanyDto;
import course.springdata.mvcproject.service.dtos.projects.ProjectDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeDto {

    @XmlElement(name = "first-name")
    private String firstName;

    @XmlElement(name = "last-name")
    private String lastName;

    private int age;

    @XmlElement(name = "project")
    private ProjectDto project;

    public EmployeeDto() {
    }
}
