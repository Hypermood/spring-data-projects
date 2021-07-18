package course.springdata.mvcproject.service.dtos.projects;

import course.springdata.mvcproject.service.dtos.companies.CompanyDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectDto {

    @XmlElement
    private String name;

    @XmlElement
    private String description;

    @XmlElement(name = "start-date")
    private String startDate;

    @XmlElement(name = "is-finished")
    private boolean isFinished;

    @XmlElement
    private BigDecimal payment;

    @XmlElement(name = "company")
    private CompanyDto companyDto;

    public ProjectDto() {
    }
}
