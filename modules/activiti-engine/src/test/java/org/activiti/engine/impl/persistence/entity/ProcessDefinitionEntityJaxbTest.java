package org.activiti.engine.impl.persistence.entity;

import static org.junit.Assert.fail;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ProcessDefinitionEntityJaxbTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  @Ignore
  public void testMinimalEntity() {
    ProcessDefinitionEntity pd = new ProcessDefinitionEntity();
    StringWriter out = new StringWriter();
    try {
      JAXBContext contextA = JAXBContext.newInstance(
	  ProcessDefinitionEntity.class,
	  VariableScopeImpl.class);
      Marshaller marshaller = contextA.createMarshaller();
      marshaller.marshal(pd, out);
      System.out.println("Marshalled:" + out.toString());
    } catch (JAXBException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      fail();
    }
    
  }

}
