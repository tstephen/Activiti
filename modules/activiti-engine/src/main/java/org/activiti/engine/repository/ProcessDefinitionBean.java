package org.activiti.engine.repository;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Implementation of <code>ProcessDefinition</code> that supports JAXB
 * serialization.
 * 
 * @author Tim Stephenson
 */
@XmlRootElement(name = "definition")
public class ProcessDefinitionBean implements ProcessDefinition {

  private ProcessDefinition processDefinition;

  public static List<ProcessDefinitionBean> toList(List<ProcessDefinition> list) {
    List<ProcessDefinitionBean> wrapper = new ArrayList<ProcessDefinitionBean>();
    for (ProcessDefinition def : list) {
      wrapper.add(new ProcessDefinitionBean(def));
    }
    return wrapper;
  }

  public ProcessDefinitionBean() {
  }

  public ProcessDefinitionBean(ProcessDefinition def) {
    this();
    setProcessDefinition(def);
  }

  public ProcessDefinitionBean setProcessDefinition(ProcessDefinition pd) {
    this.processDefinition = pd;
    return this;
  }

  @Override
  public String getId() {
    return processDefinition.getId();
  }

  public void setId(String id) {
  }

  @Override
  public String getCategory() {
    return processDefinition.getCategory();
  }

  public void setCategory(String category) {
  }

  @Override
  public String getName() {
    return processDefinition.getName();
  }

  public void setName(String name) {
  }

  @Override
  public String getKey() {
    return processDefinition.getKey();
  }

  public void setKey(String key) {
  }

  @Override
  public int getVersion() {
    return processDefinition.getVersion();
  }

  public void setVersion(int v) {
  }

  @Override
  public String getResourceName() {
    return processDefinition.getResourceName();
  }

  public void setResourceName(String resourceName) {
  }

  @Override
  public String getDeploymentId() {
    return processDefinition.getDeploymentId();
  }

  public void setDeploymentId(String deploymentId) {
  }

  @Override
  public String getDiagramResourceName() {
    return processDefinition.getDiagramResourceName();
  }

  public void setDiagramResourceName() {
  }

  @Override
  public boolean hasStartFormKey() {
    return processDefinition.hasStartFormKey();
  }

  public void setStartFormKey(String formKey) {
  }

  @Override
  public boolean isSuspended() {
    return processDefinition.isSuspended();
  }

  public void setSuspended(boolean b) {
  }

  @Override
  public String getDescription() {
    return this.processDefinition.getDescription();
  }

  @Override
  public String getTenantId() {
    return processDefinition.getTenantId();
  }
}
