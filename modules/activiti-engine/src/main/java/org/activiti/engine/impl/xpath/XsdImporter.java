package org.activiti.engine.impl.xpath;

import org.activiti.bpmn.model.Import;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.XMLImporter;


public class XsdImporter implements XMLImporter {

  @Override
  public void importFrom(Import theImport, BpmnParse parse) {
    // Strange as it may seem, apparently nothing is needed as XSD simple
    // types are supported natively without the import.
  }

}
