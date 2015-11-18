package org.activiti.bdd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import org.activiti.engine.impl.test.JobTestHelper;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.toxos.activiti.assertion.ProcessAssert;

/**
 * Builds and runs process acceptance test cases using a fluent API.
 *
 * @author Tim Stephenson
 */
public class ActivitiSpec {

    private ActivitiRule activitiRule;

    private String specName;

    private ProcessInstance processInstance;

    private String messageName;

    private HashMap<String, Object> collectVars;

    private String processDefinitionKey;

    public ActivitiSpec(ActivitiRule activitiRule, String specName) {
        this.activitiRule = activitiRule;
        this.specName = specName;
        this.collectVars = new HashMap<String, Object>();
    }

    public Object getVar(String varName) {
        return collectVars.get(varName);
    }

    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public ActivitiSpec startByKey(String key, Set<String> collectVars,
            Map<String, Object> putVars, String tenantId) {
        this.processDefinitionKey = key;

        HashMap<String, Object> vars = new HashMap<String, Object>();
        for (Entry<String, Object> entry : putVars.entrySet()) {
            vars.put(entry.getKey(), entry.getValue());
        }
        processInstance = activitiRule.getRuntimeService()
                .startProcessInstanceByKeyAndTenantId(key, vars, tenantId);
        assertNotNull(processInstance);
        assertNotNull(processInstance.getId());
        return this;
    }

    public ActivitiSpec startByMsg(String messageName, String messageResource,
            String tenantId) {
        this.messageName = messageName;

        InputStream is = null;
        Reader source = null;
        Scanner scanner = null;
        try {
            is = getClass().getResourceAsStream(messageResource);
            assertNotNull("Unable to load test resource: " + messageResource,
                    is);
            source = new InputStreamReader(is);
            scanner = new Scanner(source);
            String json = scanner.useDelimiter("\\A").next();

            HashMap<String, Object> vars = new HashMap<String, Object>();
            vars.put("messageName", adapt(messageName));
            vars.put(adapt(messageName), json);
            processInstance = activitiRule.getRuntimeService()
                    .startProcessInstanceByMessageAndTenantId(messageName,
                            vars, tenantId);
            assertNotNull(processInstance);
            assertNotNull(processInstance.getId());
        } finally {
            try {
                scanner.close();
            } catch (Exception e) {
                ;
            }
        }
        return this;
    }

    public ActivitiSpec userTask(String taskDefinitionKey,
            Set<String> collectVars, Map<String, Object> putVars) {
        Task task = activitiRule.getTaskService().createTaskQuery()
                .singleResult();
        assertNotNull("Did not find the expected task with key "
                + taskDefinitionKey, task);
        assertEquals(taskDefinitionKey, task.getTaskDefinitionKey());

        for (String varName : collectVars) {
            collectVar(varName);
        }

        HashMap<String, Object> vars = new HashMap<String, Object>();
        for (Entry<String, Object> entry : putVars.entrySet()) {
            vars.put(entry.getKey(), entry.getValue());
        }

        activitiRule.getTaskService().complete(task.getId(), vars, false);
        for (Entry<String, Object> entry : putVars.entrySet()) {
            ProcessAssert.assertProcessVariableLatestValueEquals(
                    processInstance, entry.getKey(), entry.getValue());
        }
        return this;
    }

    public ActivitiSpec external(ExternalAction action) throws Exception {
        action.execute(this);
        return this;
    }

    public ActivitiSpec executeJobsForTime(int delay) {
        JobTestHelper.executeJobExecutorForTime(activitiRule, delay, 1);

        List<Job> jobs = activitiRule.getManagementService().createJobQuery()
                .list();
        System.out.println("Found " + jobs.size() + " jobs remaining");
        for (Job job : jobs) {
            System.out.println("  job: " + job.getDuedate());
        }

        return this;
    }

    public ActivitiSpec executeAllJobs(int timeout) {
        JobTestHelper.waitForJobExecutorToProcessAllJobs(activitiRule
                .getProcessEngine().getProcessEngineConfiguration(),
                activitiRule.getManagementService(), timeout, 1);
        return this;
    }

    public ActivitiSpec advanceProcessTime(int field, int amount) {
        Calendar cal = activitiRule.getProcessEngine()
                .getProcessEngineConfiguration().getClock()
                .getCurrentCalendar();
        cal.add(field, amount);
        Date time = cal.getTime();
        System.out.println("  set process time to :" + time);
        activitiRule.setCurrentTime(time);
        return this;
    }

    public ActivitiSpec assertProcessEnded() {
        ProcessAssert.assertProcessEnded(processInstance);
        return this;
    }

    public ActivitiSpec assertProcessEndedAndInEndEvents(String... endEventIds) {
        ProcessAssert.assertProcessEndedAndInEndEvents(processInstance,
                endEventIds);
        return this;
    }

    public ActivitiSpec assertProcessEndedAndInExclusiveEndEvent(
            String endEventId) {
        ProcessAssert.assertProcessEndedAndInExclusiveEndEvent(processInstance,
                endEventId);
        return this;
    }

    public ActivitiSpec collectVar(String varName) {
        Object var = activitiRule.getRuntimeService().getVariable(
                processInstance.getId(), varName);
        assertNotNull(var);
        collectVars.put(varName, var);
        return this;
    }

    private String adapt(String msgName) {
        return msgName.replace('.', '_');
    }

    public static ImmutablePair<String, Object> newPair(String left,
            Object right) {
        return new ImmutablePair<String, Object>(left, right);
    }

    public static Set<String> buildSet(String... strings) {
        Set<String> set = new HashSet<String>();
        for (String s : strings) {
            set.add(s);
        }
        return set;
    }

    public static Map<String, Object> buildMap(
            @SuppressWarnings("unchecked") ImmutablePair<String, Object>... immutablePair) {
        Map<String,Object> map = new HashMap<String, Object>();
        for (ImmutablePair<String, Object> pair : immutablePair) {
            map.put(pair.getKey(), pair.getValue());
        }
        return map;
    }

}
