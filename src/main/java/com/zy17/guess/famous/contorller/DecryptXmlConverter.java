package com.zy17.guess.famous.contorller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.Source;

/**
 * 2017/3/6 zy17
 */
//@Component
public class DecryptXmlConverter extends Jaxb2RootElementHttpMessageConverter {
  @Override
  protected Object readFromSource(Class<?> clazz, HttpHeaders headers, Source source) throws IOException {
    try {
      source = processSource(source);
      Unmarshaller unmarshaller = createUnmarshaller(clazz);
      if (clazz.isAnnotationPresent(XmlRootElement.class)) {
        return unmarshaller.unmarshal(source);
      } else {
        JAXBElement<?> jaxbElement = unmarshaller.unmarshal(source, clazz);
        return jaxbElement.getValue();
      }
    } catch (NullPointerException ex) {
      if (!isSupportDtd()) {
        throw new HttpMessageNotReadableException("NPE while unmarshalling. " +
            "This can happen on JDK 1.6 due to the presence of DTD " +
            "declarations, which are disabled.", ex);
      }
      throw ex;
    } catch (UnmarshalException ex) {
      throw new HttpMessageNotReadableException("Could not unmarshal to [" + clazz + "]: " + ex.getMessage(), ex);

    } catch (JAXBException ex) {
      throw new HttpMessageConversionException("Could not instantiate JAXBContext: " + ex.getMessage(), ex);
    }
  }
}
