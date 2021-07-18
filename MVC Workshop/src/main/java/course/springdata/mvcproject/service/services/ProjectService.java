package course.springdata.mvcproject.service.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface ProjectService {

    void importProjects() throws IOException, JAXBException;

    boolean areImported();

    String readProjectsXmlFile() throws IOException;

    String exportFinishedProjects();
}
