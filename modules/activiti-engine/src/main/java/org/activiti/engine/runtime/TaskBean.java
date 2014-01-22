package org.activiti.engine.runtime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;

/**
 * Wrapper around Activiti entity that supports JAXB serialisation. Also serves
 * to separate the view from the back end which provides a useful indirection.
 * 
 * @author tstephen
 */
@XmlRootElement(name = "task")
// indicates the order to output elements in XML output.
@XmlType(propOrder = { "id", "processInstanceId", "processDefinitionId",
    "assignee", "name", "taskDefinitionKey", "description", "priority",
    /* "formKey", */
    "millisSinceCreated", "millisUntilDue", "dueDate", "delegationState",
    "owner", "parentTaskId", "variables" })
// "pail": "${fn:trim(pail)}",
// "businessKey":
// "${empty requestScope['pailz.extensions'][task.id] ? '' : requestScope['pailz.extensions'][task.id].businessKey}",
public class TaskBean implements Task {

  public static List<TaskBean> toList(List<Task> tasks) {
    List<TaskBean> taskBeans = new ArrayList<TaskBean>();
    for (Task task : tasks) {
      taskBeans.add(new TaskBean(task));
    }
    return taskBeans;
  }
  
  private Task task;
  private Map<String, Object> vars;

  public TaskBean() {
  }

  public TaskBean(Task task) {
    this();
    setTask(task);
  }

  public TaskBean setTask(Task task) {
    this.task = task;

    return this;
  }

  /**
   * @return the id
   */
  public String getId() {
    return task.getId();
  }

  /**
   * Seems that Jackson requires this to recognise the property exists, will be
   * ignored as you cannot change the task id!
   * 
   * @param id
   *          the id to set
   */
  public void setId(String id) {
    // this.id = id;
  }

  /**
   * @return the processInstanceId
   */
  public String getProcessInstanceId() {
    return task.getProcessInstanceId();
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

  /**
   * @return the processDefinitionId
   */
  public String getProcessDefinitionId() {
    return task.getProcessDefinitionId();
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

  /**
   * @return the assignee
   */
  public String getAssignee() {
    return task.getAssignee();
  }

  /**
   * @param assignee
   *          the assignee to set
   */
  public void setAssignee(String assignee) {
    task.setAssignee(assignee);
  }

  /**
   * @return the name
   */
  public String getName() {
    return task.getName();
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    task.setName(name);
  }

  /**
   * @return the taskDefinitionKey
   */
  public String getTaskDefinitionKey() {
    return task.getTaskDefinitionKey();
  }

  /**
   * Seems that Jackson requires this to recognise the property exists, will be
   * ignored as you cannot change the task definition key!
   * 
   * @param taskDefinitionKey
   *          the taskDefinitionKey to set
   */
  public void setTaskDefinitionKey(String taskDefinitionKey) {
    // this.taskDefinitionKey = taskDefinitionKey;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return task.getDescription();
  }

  /**
   * @param description
   *          the description to set
   */
  public void setDescription(String description) {
    task.setDescription(description);
  }

  /**
   * @return the priority
   */
  public int getPriority() {
    return task.getPriority();
  }

  /**
   * @param priority
   *          the priority to set
   */
  public void setPriority(int priority) {
    task.setPriority(priority);
  }

  /**
   * @return the millisSinceCreated
   */
  public int getMillisSinceCreated() {
    return task.getCreateTime().compareTo(new Date());
  }

  /**
   * Seems that Jackson requires this to recognise the property exists, will be
   * ignored as you cannot change the creation date!
   * 
   * @param millisSinceCreated
   *          the millisSinceCreated to set
   */
  public void setMillisSinceCreated(int millisSinceCreated) {
    // this.millisSinceCreated = millisSinceCreated;
  }

  /**
   * @return the millisUntilDue
   */
  public int getMillisUntilDue() {
    return 0;
  }

  /**
   * 
   * @param millisUntilDue
   *          the millisUntilDue to set
   */
  public void setMillisUntilDue(int millisUntilDue) {
    setDueDate(new Date(new Date().getTime() + millisUntilDue));
  }

  @Override
  public void delegate(String arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public Date getCreateTime() {
    return task.getCreateTime();
  }

  @Override
  public DelegationState getDelegationState() {
    return task.getDelegationState();
  }

  @Override
  public Date getDueDate() {
    return task.getDueDate();
  }

  @Override
  public String getExecutionId() {
    return task.getExecutionId();
  }

  @Override
  public String getOwner() {
    return task.getOwner();
  }

  @Override
  public String getParentTaskId() {
    return task.getParentTaskId();
  }

  @Override
  public void setDelegationState(DelegationState arg0) {
    task.setDelegationState(arg0);
  }

  @Override
  public void setDueDate(Date arg0) {
    task.setDueDate(arg0);
  }

  @Override
  public void setOwner(String arg0) {
    task.setOwner(arg0);
  }

  @Override
  public void setParentTaskId(String arg0) {
    task.setParentTaskId(arg0);
  }

  public Object getVariable(String varName) {
    return getVariables().get(varName);
  }

  public Map<String, Object> getVariables() {
    if (this.vars == null) {
      vars = new HashMap<String, Object>();
    }
    return this.vars;
  }

  public void setVariables(Map<String, Object> taskVariables) {
    this.vars = taskVariables;
  }

  public void addVariables(Map<String, Object> taskVariables) {
    getVariables().putAll(taskVariables);
  }

  @Override
  public boolean isSuspended() {
    return this.task.isSuspended();
  }
  
  @Override
  public String getCategory() {
    return task.getCategory();
  }

  @Override
  public void setCategory(String category) {
    task.setCategory(category);
  }

  @Override
  public String getTenantId() {
    return task.getTenantId();
  }

  @Override
  public Map<String, Object> getTaskLocalVariables() {
    return task.getTaskLocalVariables();
  }

  @Override
  public Map<String, Object> getProcessVariables() {
    return task.getProcessVariables();
  }
}
