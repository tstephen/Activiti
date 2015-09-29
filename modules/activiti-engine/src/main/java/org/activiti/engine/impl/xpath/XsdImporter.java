package org.activiti.engine.impl.xpath;

import org.activiti.bpmn.model.Import;
import org.activiti.engine.impl.bpmn.parser.BpmnParseXMLImportHandler;
import org.activiti.engine.impl.bpmn.parser.XMLImporter;


public class XsdImporter implements XMLImporter {

  @Override
  public void importFrom(Import theImport,
      BpmnParseXMLImportHandler parseHandler) {
    // Strange as it may seem, apparently nothing is needed as XSD simple
    // types are supported natively without the import.
  }

}
