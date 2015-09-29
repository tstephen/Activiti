package org.activiti.engine.test.bpmn.miwg;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ExecutionDemo2015Test {
    
    private static final String USER_ID = "demo";
    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();
    
    @Before
    public void setUp() {
        activitiRule.getIdentityService().saveUser(
                activitiRule.getIdentityService().newUser(USER_ID));
    }

    @After
    public void tearDown() {
        activitiRule.getIdentityService().deleteUser(USER_ID);
    }

    @Test
    @Deployment(resources = { "2015-06-15-19-omg-technical-meeting-berlin/execution-demo/handle-invoice.bpmn" })
    /*
     * Issues - cannot parse formKey (resolution: remove it) - cannot parse
     * collaboration (resolution: remove it)
     */
    public void testHandleInvoice() {
        ProcessInstance processInstance = activitiRule.getRuntimeService()
                .startProcessInstanceByKey("handle-invoice");
		assertNotNull(processInstance);

		// Assign Approver
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
		assertEquals("assignApprover", task.getTaskDefinitionKey());
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("approver", USER_ID);
        activitiRule.getTaskService().complete(task.getId(), vars);

		// Approve Invoice
		task = activitiRule.getTaskService().createTaskQuery().singleResult();
		assertEquals("approveInvoice", task.getTaskDefinitionKey());
        vars.clear();
        vars.put("approved", false);
        activitiRule.getTaskService().complete(task.getId(), vars);

        // Review Invoice
		task = activitiRule.getTaskService().createTaskQuery().singleResult();
		assertEquals("reviewInvoice", task.getTaskDefinitionKey());
        vars.put("clarified", "yes");
        activitiRule.getTaskService().complete(task.getId(), vars);

    }

}