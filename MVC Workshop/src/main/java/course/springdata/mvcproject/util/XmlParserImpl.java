package course.springdata.mvcproject.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlParserImpl implements XmlParser {
    @Override
    public <T> T parse(Class<T> clazz,String text) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public <T> String stringify(T object) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        marshaller.marshal(object,stream);
        return stream.toString();

    }
}
