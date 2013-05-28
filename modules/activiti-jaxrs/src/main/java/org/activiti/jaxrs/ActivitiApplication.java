package org.activiti.jaxrs;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ActivitiApplication extends Application implements
        ApplicationContextAware {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ManagementService managementService;
    @Autowired
    private IdentityService identityService;

    private ApplicationContext appCtxt;
    private ProcessEngine processEngine;

    public Set<Object> getSingletons() {
        System.out.println("getSingletons");
        if (appCtxt == null) {
            System.out.println("not yet inited, use non-Spring config");
            processEngine = ProcessEngines.getDefaultProcessEngine();
            repositoryService = processEngine.getRepositoryService();
            runtimeService = processEngine.getRuntimeService();
            taskService = processEngine.getTaskService();
            historyService = processEngine.getHistoryService();
            managementService = processEngine.getManagementService();
            identityService = processEngine.getIdentityService();
        }
        if (repositoryService == null) {
            throw new RuntimeException("not yet wired");
        }
        Set<Object> s = new HashSet<Object>();
        s.add(repositoryService);
        s.add(runtimeService);
        s.add(taskService);
        s.add(historyService);
        s.add(managementService);
        s.add(identityService);
        return s;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.appCtxt = applicationContext;
    }

}
