package course.springdata.mvcproject.service.services.impl;
import course.springdata.mvcproject.data.entities.Company;
import course.springdata.mvcproject.data.repositories.CompanyRepository;
import course.springdata.mvcproject.service.dtos.companies.CompaniesDto;
import course.springdata.mvcproject.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import course.springdata.mvcproject.service.services.CompanyService;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }



    @Override
    public void importCompanies() throws IOException, JAXBException {

        CompaniesDto companiesDto = xmlParser.parse(CompaniesDto.class, readCompaniesXmlFile());
        companiesDto.getListCompanyDtos().stream().forEach(c->{
            try{
                companyRepository.save(modelMapper.map(c, Company.class));
            }
            catch(Exception err){

            }
        });
    }

    @Override
    public boolean areImported() {
        return companyRepository.findAll().size() > 0;
    }

    @Override
    public String readCompaniesXmlFile() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/xmls/companies.xml"));
    }
}
