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

import java.util.List;

<<<<<<< HEAD
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.activiti.rest.service.BaseRestTestCase;
import org.activiti.rest.service.api.RestUrls;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
=======
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.test.Deployment;
import org.activiti.rest.service.BaseSpringRestTestCase;
import org.activiti.rest.service.api.RestUrls;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
>>>>>>> upstream/master


/**
 * @author Frederik Heremans
 */
<<<<<<< HEAD
public class HistoricProcessInstanceCommentResourceTest extends BaseRestTestCase {
=======
public class HistoricProcessInstanceCommentResourceTest extends BaseSpringRestTestCase {
>>>>>>> upstream/master

  /**
   * Test getting all comments for a historic process instance.
   * GET history/historic-process-instances/{processInstanceId}/comments
   */
	@Deployment(resources={"org/activiti/rest/service/api/repository/oneTaskProcess.bpmn20.xml"})
  public void testGetComments() throws Exception {
		ProcessInstance pi = null;
		
		try {
    	pi = runtimeService.startProcessInstanceByKey("oneTaskProcess");

      // Add a comment as "kermit"
      identityService.setAuthenticatedUserId("kermit");
      Comment comment = taskService.addComment(null, pi.getId(), "This is a comment...");
      identityService.setAuthenticatedUserId(null);
      
<<<<<<< HEAD
      ClientResource client = getAuthenticatedClient(RestUrls.createRelativeResourceUrl(
              RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT_COLLECTION, pi.getId()));
      
      Representation response = client.get();
      assertEquals(Status.SUCCESS_OK, client.getResponse().getStatus());
      
      JsonNode responseNode = objectMapper.readTree(response.getStream());
=======
      CloseableHttpResponse response = executeRequest(new HttpGet(SERVER_URL_PREFIX + RestUrls.createRelativeResourceUrl(
          RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT_COLLECTION, pi.getId())), HttpStatus.SC_OK);
      
      assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
      
      JsonNode responseNode = objectMapper.readTree(response.getEntity().getContent());
      closeResponse(response);
>>>>>>> upstream/master
      assertNotNull(responseNode);
      assertTrue(responseNode.isArray());
      assertEquals(1, responseNode.size());
      
      ObjectNode commentNode = (ObjectNode) responseNode.get(0);
<<<<<<< HEAD
      assertEquals("kermit", commentNode.get("author").getTextValue());
      assertEquals("This is a comment...", commentNode.get("message").getTextValue());
      assertEquals(comment.getId(), commentNode.get("id").getTextValue());
      assertTrue(commentNode.get("url").getTextValue().endsWith(RestUrls.createRelativeResourceUrl(RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT, pi.getId(), comment.getId())));
      
      // Test with unexisting task
      client = getAuthenticatedClient(RestUrls.createRelativeResourceUrl(RestUrls.URL_TASK_COMMENT_COLLECTION, "unexistingtask"));
      try {
        client.get();
        fail("Exception expected");
      } catch(ResourceException expected) {
        assertEquals(Status.CLIENT_ERROR_NOT_FOUND, expected.getStatus());
        assertEquals("Could not find a task with id 'unexistingtask'.", expected.getStatus().getDescription());
      }
		} finally {
			if(pi != null) {
				List<Comment> comments = taskService.getProcessInstanceComments(pi.getId());
				for(Comment c : comments) {
=======
      assertEquals("kermit", commentNode.get("author").textValue());
      assertEquals("This is a comment...", commentNode.get("message").textValue());
      assertEquals(comment.getId(), commentNode.get("id").textValue());
      assertTrue(commentNode.get("processInstanceUrl").textValue().endsWith(RestUrls.createRelativeResourceUrl(RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT, pi.getId(), comment.getId())));
      assertEquals(pi.getProcessInstanceId(), commentNode.get("processInstanceId").asText());
      assertTrue(commentNode.get("taskUrl").isNull());
      assertTrue(commentNode.get("taskId").isNull());
      
      // Test with unexisting task
      closeResponse(executeRequest(new HttpGet(SERVER_URL_PREFIX + RestUrls.createRelativeResourceUrl(
          RestUrls.URL_TASK_COMMENT_COLLECTION, "unexistingtask")), HttpStatus.SC_NOT_FOUND));
      
		} finally {
			if (pi != null) {
				List<Comment> comments = taskService.getProcessInstanceComments(pi.getId());
				for (Comment c : comments) {
>>>>>>> upstream/master
					taskService.deleteComment(c.getId());
				}
			}
		}
  }
  
  /**
   * Test creating a comment for a process instance.
   * POST history/historic-process-instances/{processInstanceId}/comments
   */
	@Deployment(resources={"org/activiti/rest/service/api/repository/oneTaskProcess.bpmn20.xml"})
  public void testCreateComment() throws Exception {
		ProcessInstance pi = null;
		
    try {
    	pi = runtimeService.startProcessInstanceByKey("oneTaskProcess");
    	
<<<<<<< HEAD
      ClientResource client = getAuthenticatedClient(RestUrls.createRelativeResourceUrl(
              RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT_COLLECTION, pi.getId()));
      ObjectNode requestNode = objectMapper.createObjectNode();
      requestNode.put("message", "This is a comment...");
      
      Representation response = client.post(requestNode);
      assertEquals(Status.SUCCESS_CREATED, client.getResponse().getStatus());
=======
    	HttpPost httpPost = new HttpPost(SERVER_URL_PREFIX + RestUrls.createRelativeResourceUrl(
          RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT_COLLECTION, pi.getId()));
    	ObjectNode requestNode = objectMapper.createObjectNode();
      requestNode.put("message", "This is a comment...");
      httpPost.setEntity(new StringEntity(requestNode.toString()));
      
      CloseableHttpResponse response = executeRequest(httpPost, HttpStatus.SC_CREATED);
    	
      assertEquals(HttpStatus.SC_CREATED, response.getStatusLine().getStatusCode());
>>>>>>> upstream/master

      List<Comment> commentsOnProcess = taskService.getProcessInstanceComments(pi.getId());
      assertNotNull(commentsOnProcess);
      assertEquals(1, commentsOnProcess.size());
      
<<<<<<< HEAD
      JsonNode responseNode = objectMapper.readTree(response.getStream());
      assertNotNull(responseNode);
      assertEquals("kermit", responseNode.get("author").getTextValue());
      assertEquals("This is a comment...", responseNode.get("message").getTextValue());
      assertEquals(commentsOnProcess.get(0).getId(), responseNode.get("id").getTextValue());
      assertTrue(responseNode.get("url").getTextValue().endsWith(RestUrls.createRelativeResourceUrl(RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT, pi.getId(), commentsOnProcess.get(0).getId())));
    } finally {
    	if(pi != null) {
				List<Comment> comments = taskService.getProcessInstanceComments(pi.getId());
				for(Comment c : comments) {
=======
      JsonNode responseNode = objectMapper.readTree(response.getEntity().getContent());
      closeResponse(response);
      assertNotNull(responseNode);
      assertEquals("kermit", responseNode.get("author").textValue());
      assertEquals("This is a comment...", responseNode.get("message").textValue());
      assertEquals(commentsOnProcess.get(0).getId(), responseNode.get("id").textValue());
      assertTrue(responseNode.get("processInstanceUrl").textValue().endsWith(RestUrls.createRelativeResourceUrl(RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT, pi.getId(), commentsOnProcess.get(0).getId())));
      assertEquals(pi.getProcessInstanceId(), responseNode.get("processInstanceId").asText());
      assertTrue(responseNode.get("taskUrl").isNull());
      assertTrue(responseNode.get("taskId").isNull());
      
    } finally {
    	if (pi != null) {
				List<Comment> comments = taskService.getProcessInstanceComments(pi.getId());
				for (Comment c : comments) {
>>>>>>> upstream/master
					taskService.deleteComment(c.getId());
				}
			}
    }
  }
  
  /**
   * Test getting a comment for a historic process instance.
   * GET history/historic-process-instances/{processInstanceId}/comments/{commentId}
   */
	@Deployment(resources={"org/activiti/rest/service/api/repository/oneTaskProcess.bpmn20.xml"})
  public void testGetComment() throws Exception {
		ProcessInstance pi = null;
		
    try {
    	pi = runtimeService.startProcessInstanceByKey("oneTaskProcess");

      // Add a comment as "kermit"
      identityService.setAuthenticatedUserId("kermit");
      Comment comment = taskService.addComment(null, pi.getId(), "This is a comment...");
      identityService.setAuthenticatedUserId(null);
      
<<<<<<< HEAD
      ClientResource client = getAuthenticatedClient(RestUrls.createRelativeResourceUrl(
              RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT, pi.getId(), comment.getId()));
      
      Representation response = client.get();
      assertEquals(Status.SUCCESS_OK, client.getResponse().getStatus());
      
      JsonNode responseNode = objectMapper.readTree(response.getStream());
      assertNotNull(responseNode);
      
      assertEquals("kermit", responseNode.get("author").getTextValue());
      assertEquals("This is a comment...", responseNode.get("message").getTextValue());
      assertEquals(comment.getId(), responseNode.get("id").getTextValue());
      assertTrue(responseNode.get("url").getTextValue().endsWith(RestUrls.createRelativeResourceUrl(RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT, pi.getId(), comment.getId())));
      
      // Test with unexisting process-instance
      client = getAuthenticatedClient(RestUrls.createRelativeResourceUrl(RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT, "unexistinginstance", "123"));
      try {
        client.get();
        fail("Exception expected");
      } catch(ResourceException expected) {
        assertEquals(Status.CLIENT_ERROR_NOT_FOUND, expected.getStatus());
        assertEquals("Could not find a process instance with id 'unexistinginstance'.", expected.getStatus().getDescription());
      }
      
      // Test with unexisting comment
      client = getAuthenticatedClient(RestUrls.createRelativeResourceUrl(RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT, pi.getId(), "unexistingcomment"));
      try {
        client.get();
        fail("Exception expected");
      } catch(ResourceException expected) {
        assertEquals(Status.CLIENT_ERROR_NOT_FOUND, expected.getStatus());
        assertEquals("Process instance '" + pi.getId() +"' doesn't have a comment with id 'unexistingcomment'.", expected.getStatus().getDescription());
      }
=======
      CloseableHttpResponse response = executeRequest(new HttpGet(SERVER_URL_PREFIX + RestUrls.createRelativeResourceUrl(
          RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT, pi.getId(), comment.getId())), 200);
      
      assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
      
      JsonNode responseNode = objectMapper.readTree(response.getEntity().getContent());
      closeResponse(response);
      assertNotNull(responseNode);
      
      assertEquals("kermit", responseNode.get("author").textValue());
      assertEquals("This is a comment...", responseNode.get("message").textValue());
      assertEquals(comment.getId(), responseNode.get("id").textValue());
      assertTrue(responseNode.get("processInstanceUrl").textValue().endsWith(RestUrls.createRelativeResourceUrl(RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT, pi.getId(), comment.getId())));
      assertEquals(pi.getProcessInstanceId(), responseNode.get("processInstanceId").asText());
      assertTrue(responseNode.get("taskUrl").isNull());
      assertTrue(responseNode.get("taskId").isNull());
      
      // Test with unexisting process-instance
      closeResponse(executeRequest(new HttpGet(SERVER_URL_PREFIX + RestUrls.createRelativeResourceUrl(
          RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT, "unexistinginstance", "123")), HttpStatus.SC_NOT_FOUND));
      
      closeResponse(executeRequest(new HttpGet(SERVER_URL_PREFIX + RestUrls.createRelativeResourceUrl(
          RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT, pi.getId(), "unexistingcomment")), HttpStatus.SC_NOT_FOUND));
      
>>>>>>> upstream/master
    } finally {
    	if(pi != null) {
				List<Comment> comments = taskService.getProcessInstanceComments(pi.getId());
				for(Comment c : comments) {
					taskService.deleteComment(c.getId());
				}
			}
    }
  }
  
  /**
   * Test deleting a comment for a task.
   * DELETE runtime/tasks/{taskId}/comments/{commentId}
   */
	@Deployment(resources={"org/activiti/rest/service/api/repository/oneTaskProcess.bpmn20.xml"})
  public void testDeleteComment() throws Exception {
		ProcessInstance pi = null;
		
    try {
    	pi = runtimeService.startProcessInstanceByKey("oneTaskProcess");

      // Add a comment as "kermit"
      identityService.setAuthenticatedUserId("kermit");
      Comment comment = taskService.addComment(null, pi.getId(), "This is a comment...");
      identityService.setAuthenticatedUserId(null);
      
<<<<<<< HEAD
      ClientResource client = getAuthenticatedClient(RestUrls.createRelativeResourceUrl(
              RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT, pi.getId(), comment.getId()));
      
      Representation response = client.delete();
      assertEquals(Status.SUCCESS_NO_CONTENT, client.getResponse().getStatus());
      assertEquals(0, response.getSize());
      
      // Test with unexisting instance
      client = getAuthenticatedClient(RestUrls.createRelativeResourceUrl(RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT, "unexistinginstance", "123"));
      try {
        client.get();
        fail("Exception expected");
      } catch(ResourceException expected) {
        assertEquals(Status.CLIENT_ERROR_NOT_FOUND, expected.getStatus());
        assertEquals("Could not find a process instance with id 'unexistinginstance'.", expected.getStatus().getDescription());
      }
      
      // Test with unexisting comment
      client = getAuthenticatedClient(RestUrls.createRelativeResourceUrl(RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT, pi.getId(), "unexistingcomment"));
      try {
        client.get();
        fail("Exception expected");
      } catch(ResourceException expected) {
        assertEquals(Status.CLIENT_ERROR_NOT_FOUND, expected.getStatus());
        assertEquals("Process instance '" + pi.getId() +"' doesn't have a comment with id 'unexistingcomment'.", expected.getStatus().getDescription());
      }
=======
      closeResponse(executeRequest(new HttpDelete(SERVER_URL_PREFIX + RestUrls.createRelativeResourceUrl(
          RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT, pi.getId(), comment.getId())), HttpStatus.SC_NO_CONTENT));
      
      // Test with unexisting instance
      closeResponse(executeRequest(new HttpGet(SERVER_URL_PREFIX + RestUrls.createRelativeResourceUrl(
          RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT, "unexistinginstance", "123")), HttpStatus.SC_NOT_FOUND));
      
      // Test with unexisting comment
      closeResponse(executeRequest(new HttpGet(SERVER_URL_PREFIX + RestUrls.createRelativeResourceUrl(
          RestUrls.URL_HISTORIC_PROCESS_INSTANCE_COMMENT, pi.getId(), "unexistingcomment")), HttpStatus.SC_NOT_FOUND));
      
>>>>>>> upstream/master
    } finally {
    	if(pi != null) {
				List<Comment> comments = taskService.getProcessInstanceComments(pi.getId());
				for(Comment c : comments) {
					taskService.deleteComment(c.getId());
				}
			}
    }
  }
}
