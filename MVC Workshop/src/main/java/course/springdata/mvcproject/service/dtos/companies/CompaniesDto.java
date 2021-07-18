package course.springdata.mvcproject.service.dtos.companies;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompaniesDto {

    @XmlElement(name = "company")
    private List<CompanyDto> listCompanyDtos;
}
