package org.activiti.engine.runtime;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.activiti.engine.history.HistoricActivityInstance;

/**
 * Implementation of <code>ProcessInstance</code> that supports JAXB
 * serialization.
 * 
 * @author Tim Stephenson
 */
@XmlRootElement(name = "process")
@XmlType(propOrder = { "processInstanceId", "processDefinitionId", "ended",
    "businessKey", "variables", "activeActivityIds", "tasks", "suspended" })
public class ProcessInstanceBean implements ProcessInstance {

  private ProcessInstance processInstance;
  private Map<String, Object> vars;
  private List<HistoricActivityInstance> activityHistory;
  private List<String> activeActivityIds;
  private List<TaskBean> tasks;

  public ProcessInstanceBean() {

  }

  public ProcessInstanceBean(ProcessInstance pi) {
    this();
    setProcessInstance(pi);
  }

  public ProcessInstanceBean setProcessInstance(ProcessInstance pi) {
    this.processInstance = pi;

    return this;
  }

  @Override
  public String getId() {
    return processInstance.getId();
  }

  @Override
  public String getProcessInstanceId() {
    return processInstance.getProcessInstanceId();
  }

  /**
   * Seems that Jackson requires this to recognise the property exists, will be
   * ignored as you cannot change the process instance id!
   * 
   * @param processInstanceId
   *          the processInstanceId to set
   */
  public void setProcessInstanceId(String processInstanceId) {
    //
    // this.processInstanceId = processInstanceId;
  }

  @Override
  public boolean isEnded() {
    return processInstance.isEnded();
  }

  /**
   * Seems that Jackson requires this to recognise the property exists, will be
   * ignored as you cannot change the state!
   * 
   */
  public void setEnded(boolean isEnded) {
    // this.processDefinitionId = processDefinitionId;
  }

  @Override
  public boolean isSuspended() {
    return processInstance.isSuspended();
  }

  /**
   * Seems that Jackson requires this to recognise the property exists, will be
   * ignored as you cannot change the state!
   * 
   */
  public void setSuspended(boolean isSuspended) {
    ;
  }

  @Override
  public String getBusinessKey() {
    return processInstance.getBusinessKey();
  }

  /**
   * Seems that Jackson requires this to recognise the property exists, will be
   * ignored as you cannot change the business key!
   * 
   * @param processDefinitionId
   *          the processDefinitionId to set
   */
  public void setBusinessKey(String businessKey) {
    // this.processDefinitionId = processDefinitionId;
  }

  @Override
  public String getProcessDefinitionId() {
    return processInstance.getProcessDefinitionId();
  }

  /**
   * Seems that Jackson requires this to recognise the property exists, will be
   * ignored as you cannot change the process definition id!
   * 
   * @param processDefinitionId
   *          the processDefinitionId to set
   */
  public void setProcessDefinitionId(String processDefinitionId) {
    // this.processDefinitionId = processDefinitionId;
  }

  @XmlTransient
  public ProcessInstanceBean setActivityHistory(
      List<HistoricActivityInstance> list) {
    this.activityHistory = list;
    return this;
  }

  public List<HistoricActivityInstance> getActivityHistory() {
    return this.activityHistory;
  }

  public Map<String, Object> getVariables() {
    return this.vars;
  }

  public void setVariables(Map<String, Object> taskVariables) {
    // TODO fix JAXB serialisation of this
    taskVariables.remove("markup");
    this.vars = taskVariables;
  }

  public List<String> getActiveActivityIds() {
    return activeActivityIds;
  }

  public void setActiveActivityIds(List<String> activeActivityIds) {
    this.activeActivityIds = activeActivityIds;
  }

  public List<TaskBean> getTasks() {
    return this.tasks;
  }

  public void setTasks(List<TaskBean> tasks) {
    this.tasks = tasks;
  }

  // public void setActiveTasks(List<TaskBean> tasks) {
  // this.tasks = tasks;
  // }

  @Override
  public String getActivityId() {
		return processInstance.getActivityId();
  }

  @Override
  public String getParentId() { 
		return processInstance.getParentId();
  }

  @Override
  public Map<String, Object> getProcessVariables() {
    return processInstance.getProcessVariables();
  }

  @Override
  public String getTenantId() {
    return processInstance.getTenantId();
  }

}
