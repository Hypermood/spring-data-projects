package course.springdata.mvcproject.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {
    <T> T parse(Class<T> clazz,String text) throws JAXBException;
    <T> String stringify(T object) throws JAXBException;
}
