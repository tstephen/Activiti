package org.activiti.rest.application;

import org.activiti.rest.api.engine.ProcessEngineResource;
import org.activiti.rest.api.history.HistoricFormPropertiesResource;
import org.activiti.rest.api.history.HistoricProcessInstanceQueryResource;
import org.activiti.rest.api.identity.GroupCreateResource;
import org.activiti.rest.api.identity.GroupResource;
import org.activiti.rest.api.identity.GroupSearchResource;
import org.activiti.rest.api.identity.GroupUsersResource;
import org.activiti.rest.api.identity.LoginResource;
import org.activiti.rest.api.identity.UserCreateResource;
import org.activiti.rest.api.identity.UserGroupsDeleteResource;
import org.activiti.rest.api.identity.UserGroupsResource;
import org.activiti.rest.api.identity.UserPictureResource;
import org.activiti.rest.api.identity.UserResource;
import org.activiti.rest.api.identity.UserSearchResource;
import org.activiti.rest.api.legacy.LegacyTaskAttachmentResource;
import org.activiti.rest.api.legacy.TaskAddResource;
import org.activiti.rest.api.legacy.TaskAttachmentAddResource;
import org.activiti.rest.api.legacy.TaskFormResource;
import org.activiti.rest.api.legacy.TaskOperationResource;
import org.activiti.rest.api.legacy.TaskPropertiesResource;
import org.activiti.rest.api.legacy.TaskUrlAddResource;
import org.activiti.rest.api.legacy.TasksResource;
import org.activiti.rest.api.legacy.TasksSummaryResource;
import org.activiti.rest.api.legacy.deployment.DeploymentArtifactResource;
import org.activiti.rest.api.legacy.deployment.DeploymentArtifactsResource;
import org.activiti.rest.api.legacy.deployment.DeploymentDeleteResource;
import org.activiti.rest.api.legacy.deployment.DeploymentUploadResource;
import org.activiti.rest.api.legacy.deployment.DeploymentsDeleteResource;
import org.activiti.rest.api.legacy.deployment.DeploymentsResource;
import org.activiti.rest.api.legacy.management.JobExecuteResource;
import org.activiti.rest.api.legacy.management.JobsExecuteResource;
import org.activiti.rest.api.legacy.management.JobsResource;
import org.activiti.rest.api.legacy.management.LegacyJobResource;
import org.activiti.rest.api.legacy.management.LegacyTableDataResource;
import org.activiti.rest.api.legacy.management.LegacyTableResource;
import org.activiti.rest.api.legacy.management.TablesResource;
import org.activiti.rest.api.legacy.process.LegacyProcessInstanceResource;
import org.activiti.rest.api.legacy.process.LegacyProcessInstancesResource;
import org.activiti.rest.api.legacy.process.ProcessDefinitionsResource;
import org.activiti.rest.api.legacy.task.LegacyTaskResource;
import org.activiti.rest.api.management.TableCollectionResource;
import org.activiti.rest.api.management.TableColumnsResource;
import org.activiti.rest.api.management.TableDataResource;
import org.activiti.rest.api.management.TableResource;
import org.activiti.rest.api.repository.DeploymentCollectionResource;
import org.activiti.rest.api.repository.DeploymentResource;
import org.activiti.rest.api.repository.DeploymentResourceCollectionResource;
import org.activiti.rest.api.repository.DeploymentResourceDataResource;
import org.activiti.rest.api.repository.DeploymentResourceResource;
import org.activiti.rest.api.repository.ProcessDefinitionCollectionResource;
import org.activiti.rest.api.repository.ProcessDefinitionResource;
import org.activiti.rest.api.repository.SimpleWorkflowResource;
import org.activiti.rest.api.runtime.process.ExecutionCollectionResource;
import org.activiti.rest.api.runtime.process.ExecutionQueryResource;
import org.activiti.rest.api.runtime.process.ExecutionResource;
import org.activiti.rest.api.runtime.process.ExecutionVariableCollectionResource;
import org.activiti.rest.api.runtime.process.ExecutionVariableDataResource;
import org.activiti.rest.api.runtime.process.ExecutionVariableResource;
import org.activiti.rest.api.runtime.process.ProcessDefinitionDiagramResource;
import org.activiti.rest.api.runtime.process.ProcessDefinitionFormResource;
import org.activiti.rest.api.runtime.process.ProcessDefinitionPropertiesResource;
import org.activiti.rest.api.runtime.process.ProcessInstanceCollectionResource;
import org.activiti.rest.api.runtime.process.ProcessInstanceDiagramResource;
import org.activiti.rest.api.runtime.process.ProcessInstanceQueryResource;
import org.activiti.rest.api.runtime.process.ProcessInstanceResource;
import org.activiti.rest.api.runtime.process.ProcessInstanceSignalExecutionResource;
import org.activiti.rest.api.runtime.process.ProcessInstanceTaskResource;
import org.activiti.rest.api.runtime.process.ProcessInstanceVariableCollectionResource;
import org.activiti.rest.api.runtime.process.ProcessInstanceVariableDataResource;
import org.activiti.rest.api.runtime.process.ProcessInstanceVariableResource;
import org.activiti.rest.api.runtime.process.SignalEventSubscriptionResource;
import org.activiti.rest.api.runtime.process.StartProcessInstanceResource;
import org.activiti.rest.api.runtime.task.TaskAttachmentCollectionResource;
import org.activiti.rest.api.runtime.task.TaskAttachmentContentResource;
import org.activiti.rest.api.runtime.task.TaskAttachmentResource;
import org.activiti.rest.api.runtime.task.TaskCollectionResource;
import org.activiti.rest.api.runtime.task.TaskCommentCollectionResource;
import org.activiti.rest.api.runtime.task.TaskCommentResource;
import org.activiti.rest.api.runtime.task.TaskEventCollectionResource;
import org.activiti.rest.api.runtime.task.TaskEventResource;
import org.activiti.rest.api.runtime.task.TaskIdentityLinkCollectionResource;
import org.activiti.rest.api.runtime.task.TaskIdentityLinkFamilyResource;
import org.activiti.rest.api.runtime.task.TaskIdentityLinkResource;
import org.activiti.rest.api.runtime.task.TaskQueryResource;
import org.activiti.rest.api.runtime.task.TaskResource;
import org.activiti.rest.api.runtime.task.TaskVariableCollectionResource;
import org.activiti.rest.api.runtime.task.TaskVariableDataResource;
import org.activiti.rest.api.runtime.task.TaskVariableResource;
import org.restlet.routing.Router;

@SuppressWarnings("deprecation")
public class RestServicesInit {

  public static void attachResources(Router router) {
    
    // New REST-urls
    router.attach("/repository/deployments", DeploymentCollectionResource.class);
    router.attach("/repository/deployments/{deploymentId}", DeploymentResource.class);
    router.attach("/repository/deployments/{deploymentId}/resources", DeploymentResourceCollectionResource.class);
    router.attach("/repository/deployments/{deploymentId}/resources/{resourceId}", DeploymentResourceResource.class);
    router.attach("/repository/deployments/{deploymentId}/resourcedata/{resourceId}", DeploymentResourceDataResource.class);
    
    router.attach("/repository/process-definitions", ProcessDefinitionCollectionResource.class);
    router.attach("/repository/process-definitions/{processDefinitionId}", ProcessDefinitionResource.class);
    
    router.attach("/runtime/tasks", TaskCollectionResource.class);
    router.attach("/runtime/tasks/{taskId}", TaskResource.class);
    router.attach("/runtime/tasks/{taskId}/variables", TaskVariableCollectionResource.class);
    router.attach("/runtime/tasks/{taskId}/variables/{variableName}", TaskVariableResource.class);
    router.attach("/runtime/tasks/{taskId}/variables/{variableName}/data", TaskVariableDataResource.class);
    router.attach("/runtime/tasks/{taskId}/identitylinks", TaskIdentityLinkCollectionResource.class);
    router.attach("/runtime/tasks/{taskId}/identitylinks/{family}", TaskIdentityLinkFamilyResource.class);
    router.attach("/runtime/tasks/{taskId}/identitylinks/{family}/{identityId}/{type}", TaskIdentityLinkResource.class);
    router.attach("/runtime/tasks/{taskId}/comments", TaskCommentCollectionResource.class);
    router.attach("/runtime/tasks/{taskId}/comments/{commentId}", TaskCommentResource.class);
    router.attach("/runtime/tasks/{taskId}/events", TaskEventCollectionResource.class);
    router.attach("/runtime/tasks/{taskId}/events/{eventId}", TaskEventResource.class);
    router.attach("/runtime/tasks/{taskId}/attachments", TaskAttachmentCollectionResource.class);
    router.attach("/runtime/tasks/{taskId}/attachments/{attachmentId}", TaskAttachmentResource.class);
    router.attach("/runtime/tasks/{taskId}/attachments/{attachmentId}/content", TaskAttachmentContentResource.class);
    
    router.attach("/runtime/process-instances/{processInstanceId}", ProcessInstanceResource.class);
    router.attach("/runtime/process-instances", ProcessInstanceCollectionResource.class);
    router.attach("/runtime/process-instances/{processInstanceId}/variables", ProcessInstanceVariableCollectionResource.class);
    router.attach("/runtime/process-instances/{processInstanceId}/variables/{variableName}", ProcessInstanceVariableResource.class);
    router.attach("/runtime/process-instances/{processInstanceId}/variables/{variableName}/data", ProcessInstanceVariableDataResource.class);
    
    router.attach("/runtime/executions", ExecutionCollectionResource.class);
    router.attach("/runtime/executions/{executionId}", ExecutionResource.class);
    router.attach("/runtime/executions/{executionId}/variables", ExecutionVariableCollectionResource.class);
    router.attach("/runtime/executions/{executionId}/variables/{variableName}", ExecutionVariableResource.class);
    router.attach("/runtime/executions/{executionId}/variables/{variableName}/data", ExecutionVariableDataResource.class);
    
    router.attach("/management/tables", TableCollectionResource.class);
    router.attach("/management/tables/{tableName}", TableResource.class);
    router.attach("/management/tables/{tableName}/columns", TableColumnsResource.class);
    router.attach("/management/tables/{tableName}/data", TableDataResource.class);
    
    router.attach("/query/tasks", TaskQueryResource.class);
    router.attach("/query/process-instances", ProcessInstanceQueryResource.class);
    router.attach("/query/executions", ExecutionQueryResource.class);
    router.attach("/query/historic-process-instances", HistoricProcessInstanceQueryResource.class);
    
    // Old rest-urls
    router.attach("/process-engine", ProcessEngineResource.class);
    
    router.attach("/login", LoginResource.class);
    
    router.attach("/user", UserCreateResource.class);
    router.attach("/user/{userId}", UserResource.class);
    router.attach("/user/{userId}/groups", UserGroupsResource.class);
    router.attach("/user/{userId}/groups/{groupId}", UserGroupsDeleteResource.class);
    router.attach("/user/{userId}/picture", UserPictureResource.class);
    router.attach("/users", UserSearchResource.class);

    router.attach("/group", GroupCreateResource.class);
    router.attach("/group/{groupId}", GroupResource.class);
    router.attach("/group/{groupId}/users/{userId}", UserGroupsDeleteResource.class);
    router.attach("/group/{groupId}/users", GroupUsersResource.class);
    router.attach("/groups", GroupSearchResource.class);
    
    router.attach("/process-definitions", ProcessDefinitionsResource.class);
    router.attach("/process-instances", LegacyProcessInstancesResource.class);
    router.attach("/process-instance", StartProcessInstanceResource.class);
    router.attach("/process-instance/{processInstanceId}", LegacyProcessInstanceResource.class);
    router.attach("/process-instance/{processInstanceId}/diagram", ProcessInstanceDiagramResource.class);
    router.attach("/process-instance/{processInstanceId}/tasks", ProcessInstanceTaskResource.class);
    router.attach("/process-instance/{processInstanceId}/signal", ProcessInstanceSignalExecutionResource.class);
    router.attach("/process-instance/{processInstanceId}/event/{signalName}", SignalEventSubscriptionResource.class);
    router.attach("/process-definition/{processDefinitionId}/form", ProcessDefinitionFormResource.class);
    router.attach("/process-definition/{processDefinitionId}/diagram", ProcessDefinitionDiagramResource.class);
    router.attach("/process-definition/{processDefinitionId}/properties", ProcessDefinitionPropertiesResource.class);
    
    router.attach("/tasks", TasksResource.class);
    router.attach("/tasks-summary", TasksSummaryResource.class);
    router.attach("/task", TaskAddResource.class);
    router.attach("/task/{taskId}", LegacyTaskResource.class);
    router.attach("/task/{taskId}/form", TaskFormResource.class);
    router.attach("/task/{taskId}/attachment", TaskAttachmentAddResource.class);
    router.attach("/task/{taskId}/url", TaskUrlAddResource.class);
    router.attach("/task/{taskId}/{operation}", TaskOperationResource.class);
    
    router.attach("/history/{taskId}/form-properties", HistoricFormPropertiesResource.class);
    
    router.attach("/attachment/{attachmentId}", LegacyTaskAttachmentResource.class);
    
    router.attach("/form/{taskId}/properties", TaskPropertiesResource.class);
    
    router.attach("/deployments", DeploymentsResource.class);
    router.attach("/deployment", DeploymentUploadResource.class);
    router.attach("/deployments/delete", DeploymentsDeleteResource.class);
    router.attach("/deployment/{deploymentId}", DeploymentDeleteResource.class);
    router.attach("/deployment/{deploymentId}/resources", DeploymentArtifactsResource.class);
    router.attach("/deployment/{deploymentId}/resource/{resourceName}", DeploymentArtifactResource.class);
    
    router.attach("/management/jobs", JobsResource.class);
    router.attach("/management/job/{jobId}", LegacyJobResource.class);
    router.attach("/management/job/{jobId}/execute", JobExecuteResource.class);
    router.attach("/management/jobs/execute", JobsExecuteResource.class);
    
    router.attach("/management/tables", TablesResource.class);
    router.attach("/management/table/{tableName}", LegacyTableResource.class);
    router.attach("/management/table/{tableName}/data", LegacyTableDataResource.class);
    
    router.attach("/simple-workflow", SimpleWorkflowResource.class);
    
  }
  
}
