package course.springdata.mvcproject.config;

import course.springdata.mvcproject.util.XmlParser;
import course.springdata.mvcproject.util.XmlParserImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationBeanConfiguration {

    //TODO

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public XmlParser xmlParser() {
        return new XmlParserImpl();
    }
}
