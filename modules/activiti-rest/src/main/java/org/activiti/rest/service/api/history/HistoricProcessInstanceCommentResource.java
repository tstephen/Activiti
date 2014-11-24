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

package org.activiti.rest.service.api.history;

<<<<<<< HEAD
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.rest.common.api.ActivitiUtil;
import org.activiti.rest.common.api.SecuredResource;
import org.activiti.rest.service.api.engine.CommentResponse;
import org.activiti.rest.service.application.ActivitiRestServicesApplication;
import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
=======
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.rest.service.api.RestResponseFactory;
import org.activiti.rest.service.api.engine.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
>>>>>>> upstream/master


/**
 * @author Frederik Heremans
 */
<<<<<<< HEAD
public class HistoricProcessInstanceCommentResource extends SecuredResource {

  @Get
  public CommentResponse getComment() {
  	 if(!authenticate())
       return null;
     
     HistoricProcessInstance instance = getHistoricProcessInstanceFromRequest();
     
     String commentId = getAttribute("commentId");
     if(commentId == null) {
       throw new ActivitiIllegalArgumentException("CommentId is required.");
     }
     
     Comment comment = ActivitiUtil.getTaskService().getComment(commentId);
     if(comment == null || comment.getProcessInstanceId() == null || !comment.getProcessInstanceId().equals(instance.getId())) {
       throw new ActivitiObjectNotFoundException("Process instance '" + instance.getId() +"' doesn't have a comment with id '" + commentId + "'.", Comment.class);
     }
    
    return getApplication(ActivitiRestServicesApplication.class).getRestResponseFactory()
            .createRestComment(this, comment);
  }
  
  @Delete
  public void deleteComment() {
    if(!authenticate())
      return;
    
    HistoricProcessInstance instance = getHistoricProcessInstanceFromRequest();
    
    String commentId = getAttribute("commentId");
    if(commentId == null) {
      throw new ActivitiIllegalArgumentException("CommentId is required.");
    }
    
    Comment comment = ActivitiUtil.getTaskService().getComment(commentId);
    if(comment == null || comment.getProcessInstanceId() == null || !comment.getProcessInstanceId().equals(instance.getId())) {
      throw new ActivitiObjectNotFoundException("Process instance '" + instance.getId() +"' doesn't have a comment with id '" + commentId + "'.", Comment.class);
    }
    
    ActivitiUtil.getTaskService().deleteComment(commentId);
    setStatus(Status.SUCCESS_NO_CONTENT);
  }
  
 protected HistoricProcessInstance getHistoricProcessInstanceFromRequest() {
    String processInstanceId = getAttribute("processInstanceId");
    if (processInstanceId == null) {
      throw new ActivitiIllegalArgumentException("The processInstanceId cannot be null");
    }
    
    HistoricProcessInstance processInstance = ActivitiUtil.getHistoryService().createHistoricProcessInstanceQuery()
=======
@RestController
public class HistoricProcessInstanceCommentResource {

  @Autowired
  protected RestResponseFactory restResponseFactory;
  
  @Autowired
  protected HistoryService historyService;
  
  @Autowired
  protected TaskService taskService;
  
  @RequestMapping(value="/history/historic-process-instances/{processInstanceId}/comments/{commentId}", method = RequestMethod.GET, produces = "application/json")
  public CommentResponse getComment(@PathVariable("processInstanceId") String processInstanceId, 
      @PathVariable("commentId") String commentId, HttpServletRequest request) {
     
    HistoricProcessInstance instance = getHistoricProcessInstanceFromRequest(processInstanceId);
     
    Comment comment = taskService.getComment(commentId);
    if (comment == null || comment.getProcessInstanceId() == null || !comment.getProcessInstanceId().equals(instance.getId())) {
      throw new ActivitiObjectNotFoundException("Process instance '" + instance.getId() + "' doesn't have a comment with id '" + commentId + "'.", Comment.class);
    }
    
    String serverRootUrl = request.getRequestURL().toString();
    serverRootUrl = serverRootUrl.substring(0, serverRootUrl.indexOf("/history/historic-process-instances/"));
    return restResponseFactory.createRestComment(comment, serverRootUrl);
  }
  
  @RequestMapping(value="/history/historic-process-instances/{processInstanceId}/comments/{commentId}", method = RequestMethod.DELETE)
  public void deleteComment(@PathVariable("processInstanceId") String processInstanceId, 
      @PathVariable("commentId") String commentId, HttpServletRequest request, HttpServletResponse response) {
    
    HistoricProcessInstance instance = getHistoricProcessInstanceFromRequest(processInstanceId);
    
    Comment comment = taskService.getComment(commentId);
    if (comment == null || comment.getProcessInstanceId() == null || !comment.getProcessInstanceId().equals(instance.getId())) {
      throw new ActivitiObjectNotFoundException("Process instance '" + instance.getId() + "' doesn't have a comment with id '" + commentId + "'.", Comment.class);
    }
    
    taskService.deleteComment(commentId);
    response.setStatus(HttpStatus.NO_CONTENT.value());
  }
  
 protected HistoricProcessInstance getHistoricProcessInstanceFromRequest(String processInstanceId) {
    HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery()
>>>>>>> upstream/master
           .processInstanceId(processInstanceId).singleResult();
    if (processInstance == null) {
      throw new ActivitiObjectNotFoundException("Could not find a process instance with id '" + processInstanceId + "'.", HistoricProcessInstance.class);
    }
    return processInstance;
  }
}
