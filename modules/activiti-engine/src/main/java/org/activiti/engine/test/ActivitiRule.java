/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.engine.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.test.TestHelper;
import org.activiti.engine.impl.util.ClockUtil;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;


/** Convenience for ProcessEngine and services initialization in the form of a JUnit rule.
 * 
 * <p>Usage:</p>
 * <pre>public class YourTest {
 * 
 *   &#64;Rule
 *   public ActivitiRule activitiRule = new ActivitiRule();
 *   
 *   ...
 * }
 * </pre>
 * 
 * <p>The ProcessEngine and the services will be made available to the test class 
 * through the getters of the activitiRule.  
 * The processEngine will be initialized by default with the activiti.cfg.xml resource 
 * on the classpath.  To specify a different configuration file, pass the 
 * resource location in {@link #ActivitiRule(String) the appropriate constructor}.
 * Process engines will be cached statically.  Right before the first time the setUp is called for a given 
 * configuration resource, the process engine will be constructed.</p>
 * 
 * <p>You can declare a deployment with the {@link Deployment} annotation.
 * This base class will make sure that this deployment gets deployed before the
 * setUp and {@link RepositoryService#deleteDeployment(String, boolean) cascade deleted}
 * after the tearDown.
 * </p>
 * 
 * <p>The activitiRule also lets you {@link ActivitiRule#setCurrentTime(Date) set the current time used by the 
 * process engine}. This can be handy to control the exact time that is used by the engine
 * in order to verify e.g. e.g. due dates of timers.  Or start, end and duration times
 * in the history service.  In the tearDown, the internal clock will automatically be 
 * reset to use the current system time rather then the time that was set during 
 * a test method.  In other words, you don't have to clean up your own time messing mess ;-)
 * </p>
 * 
 * <p>Also supports Activiti-related assertions in tests (see assert* methods) 
 * and a number of convenient test helpers:
 * <ul>
 * <li>{@link ActivitiRule#dumpProcessState(String)}
 * <li>{@link ActivitiRule#reassignTask(String, String, String)}
 * <li>{@link ActivitiRule#replaceCandidateUserForTask(String, String, String)}
 * 
 * 
 * @author Tom Baeyens
 * @author Tim Stephenson
 */
public class ActivitiRule extends TestWatchman {

  private static final String SEP = System.getProperty("line.separator");
  protected String configurationResource = "activiti.cfg.xml";
  protected String deploymentId = null;

  protected ProcessEngine processEngine;
  protected RepositoryService repositoryService;
  protected RuntimeService runtimeService;
  protected TaskService taskService;
  protected HistoryService historyService;
  protected IdentityService identityService;
  protected ManagementService managementService;
  protected FormService formService;
  
  public ActivitiRule() {
  }

  public ActivitiRule(String configurationResource) {
    this.configurationResource = configurationResource;
  }
  
  public ActivitiRule(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

  @Override
  public void starting(FrameworkMethod method) {
    if (processEngine==null) {
      initializeProcessEngine();
      initializeServices();
    }

    deploymentId = TestHelper.annotationDeploymentSetUp(processEngine, method.getMethod().getDeclaringClass(), method.getName());
  }
  
  protected void initializeProcessEngine() {
    processEngine = TestHelper.getProcessEngine(configurationResource);
  }

  protected void initializeServices() {
    repositoryService = processEngine.getRepositoryService();
    runtimeService = processEngine.getRuntimeService();
    taskService = processEngine.getTaskService();
    historyService = processEngine.getHistoryService();
    identityService = processEngine.getIdentityService();
    managementService = processEngine.getManagementService();
    formService = processEngine.getFormService();
  }

  @Override
  public void finished(FrameworkMethod method) {
    TestHelper.annotationDeploymentTearDown(processEngine, deploymentId, method.getMethod().getDeclaringClass(), method.getName());

    ClockUtil.reset();
  }
  
  public void setCurrentTime(Date currentTime) {
    ClockUtil.setCurrentTime(currentTime);
  }

  public String getConfigurationResource() {
    return configurationResource;
  }
  
  public void setConfigurationResource(String configurationResource) {
    this.configurationResource = configurationResource;
  }
  
  public ProcessEngine getProcessEngine() {
    return processEngine;
  }
  
  public void setProcessEngine(ProcessEngine processEngine) {
    this.processEngine = processEngine;
	initializeServices();
  }
  
  public RepositoryService getRepositoryService() {
    return repositoryService;
  }
  
  public void setRepositoryService(RepositoryService repositoryService) {
    this.repositoryService = repositoryService;
  }
  
  public RuntimeService getRuntimeService() {
    return runtimeService;
  }
  
  public void setRuntimeService(RuntimeService runtimeService) {
    this.runtimeService = runtimeService;
  }
  
  public TaskService getTaskService() {
    return taskService;
  }
  
  public void setTaskService(TaskService taskService) {
    this.taskService = taskService;
  }
  
  public HistoryService getHistoryService() {
    return historyService;
  }

  public void setHistoricDataService(HistoryService historicDataService) {
    this.historyService = historicDataService;
  }
  
  public IdentityService getIdentityService() {
    return identityService;
  }
  
  public void setIdentityService(IdentityService identityService) {
    this.identityService = identityService;
  }
  
  public ManagementService getManagementService() {
    return managementService;
  }
  
  public FormService getFormService() {
    return formService;
  }
  
  public void setManagementService(ManagementService managementService) {
    this.managementService = managementService;
  }

  public void dumpProcessState(String piid, Writer out) throws IOException {
    ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery()
	.processInstanceId(piid);
    List<ProcessInstance> list = query.list();
    if (list.size() == 1) {
      ProcessInstance pi = list.get(0);
      out.write("process instance: " + pi.getId() + SEP);
      out.write("... ended?: " + pi.isEnded() + SEP);

      List<Job> jobList = managementService.createJobQuery()
	  .processInstanceId(piid).list();
      for (Job job : jobList) {
	out.write("job: " + job.getId() + ", " + job.getExceptionMessage()
	    + SEP);
      }

      List<Task> list2 = taskService.createTaskQuery().processInstanceId(piid)
	  .list();
      for (Task task : list2) {
	out.write("...task: " + task.getName());
	if (task.getAssignee() == null
	    || task.getAssignee().trim().length() == 0) {
	  List<IdentityLink> identityLinksForTask = taskService
	      .getIdentityLinksForTask(task.getId());
	  for (IdentityLink identityLink : identityLinksForTask) {
	    out.write("group=" + identityLink.getGroupId() + ",user="
		+ identityLink.getUserId() + ";");
	  }
	  out.write(SEP);
	} else {
	  out.write(", assignee: " + task.getAssignee() + SEP);
	}
      }

      Map<String, Object> variables = runtimeService.getVariables(piid);
      for (Map.Entry<String, Object> entry : variables.entrySet()) {
	out.write(entry.getKey() + " = " + entry.getValue());
      }
    } else {
      assertEquals(0, list.size());
      out.write("found '" + list.size() + "' proc instances, assume ended"
	  + SEP);
    }

    out.write("History info for process: " + piid + SEP);
    List<HistoricActivityInstance> activityHistory = historyService
	.createHistoricActivityInstanceQuery().processInstanceId(piid).list();
    for (HistoricActivityInstance ai : activityHistory) {
      out.write("..." + ai.getActivityName() + "("
	  + ai.getActivityType() + "): assigned to: " + ai.getAssignee()
	  + ", complete?: "
	  + (ai.getEndTime() == null ? "outstanding" : ai.getEndTime()) + SEP);
    }
  }

  /**
   * Assert that <em>one and only one</em> task matches the parameters and
   * return its id.
   * 
   * @return task
   */
  public Task assertGroupCandidateTaskExists(String taskName, String group,
      String descriptionLike, int priority, List<String> groups) {
    List<Task> tasks = getTaskService().createTaskQuery()
	.taskCandidateGroupIn(groups).taskName(taskName)
	.taskDescriptionLike(descriptionLike).taskPriority(priority)
	.list();
    assertEquals("Unexpected no. of tasks named '" + taskName + "'", 1,
	tasks.size());
    return tasks.get(0);
  }

  /**
   * Assert that <em>one and only one</em> task matches the parameters and
   * return its id.
   * 
   * @return task
   */
  public Task assertAssignedTaskExists(String taskName, String assignee,
      String descriptionLike, int priority) {
    List<Task> tasks = getTaskService().createTaskQuery()
	.taskAssignee(assignee).taskName(taskName)
	.taskDescriptionLike(descriptionLike)
	.taskPriority(priority)
	.list();
    assertEquals("Unexpected no. of tasks named '" + taskName + "'", 1,
	tasks.size());
    return tasks.get(0);
  }

  public void replaceCandidateUserForTask(String taskId, String currentUser,
      String newUser) {
    Task task = taskService.createTaskQuery().taskCandidateUser(currentUser)
	.taskId(taskId).singleResult();
    taskService.deleteCandidateUser(task.getId(), currentUser);
    taskService.addCandidateUser(task.getId(), newUser);
  }

  public void reassignTask(String taskName, String currentAssignee,
      String newAssignee) {
    List<Task> list = taskService.createTaskQuery()
	.taskAssignee(currentAssignee).taskName(taskName).list();
    for (Task task : list) {
      task.setAssignee(newAssignee);
      taskService.saveTask(task);
    }
  }

  public void assertComplete(String piid) {
    ProcessInstance result = getRuntimeService()
	.createProcessInstanceQuery()
	.processInstanceId(piid).singleResult();
    assertTrue(result.isEnded());
  }
}
