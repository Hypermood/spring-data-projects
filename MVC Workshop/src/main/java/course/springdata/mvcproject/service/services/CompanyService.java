package course.springdata.mvcproject.service.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CompanyService {

    void importCompanies() throws IOException, JAXBException;

    boolean areImported();

    String readCompaniesXmlFile() throws IOException;
}
