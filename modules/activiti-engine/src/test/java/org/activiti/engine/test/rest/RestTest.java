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
package org.activiti.engine.test.rest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.header.MediaTypes;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

/**
 * 
 * @author Tim Stephenson
 */
public class RestTest extends JerseyTest {

  public RestTest() throws Exception {
    super(new WebAppDescriptor.Builder("org.activiti.engine.impl")
	.initParam("javax.ws.rs.Application",
	    "org.activiti.engine.impl.ActivitiApplication")
	.contextPath("activiti-engine").build());
  }

  /**
   * Test basic end to end REST functionality.
   * 
   * Includes:
   * <ol>
   * <li>PUT new process definition.
   * <li>GET list of processes to see new one is present.
   * <li>POST a new process instance, including some variables.
   * <li>GET the process instance to see status and variables.
   * <li>GET historic (audit) data for the process instance.
   * <li>GET a task list.
   * <li>POST a task completion request.
   * <li>GET the process instance (to ensure it is complete and no longer
   * exists).
   * <li>DELETE the process definition.
   * 
   * @throws java.lang.Exception
   */
  @Test
  @Ignore
  public void testSimpleProcess() throws Exception {
    WebResource webResource = resource();
    String responseMsg = null;
    String bpmn = getProcessDefinition();
    webResource.path("repository/definition/FinancialReportProcess.bpmn")
	.header("Content-Type", "application/xml")
	.accept(MediaType.APPLICATION_XML).put(bpmn);

    responseMsg = webResource.path("/repository/definitions")
	.accept(MediaType.APPLICATION_JSON).get(String.class);
    System.out.println("process definitions: " + responseMsg);
    assertTrue(responseMsg.contains("\"id\":\"financialReport:"));
    assertTrue(responseMsg.contains("\"key\":\"financialReport\""));
    int start = responseMsg.indexOf("deploymentId\":\"")
	+ "deploymentId\":\"".length();
    // TODO this is only correct as long as there is just one process deployed
    String deploymentId = responseMsg.substring(start,
	responseMsg.indexOf('"', start));
    System.out.println("deployment id: " + deploymentId);

    responseMsg = webResource.path("runtime/financialReport")
	.accept(MediaType.APPLICATION_JSON).post(String.class);
    System.out.println("respose to POST new process instance: " + responseMsg);
    assertTrue(responseMsg.contains("\"processInstanceId\":\""));

    String pi = "5";
    responseMsg = webResource.path("runtime/financialReport/" + pi)
	.accept(MediaType.APPLICATION_JSON).get(String.class);
    System.out.println("response to GET process instance : " + responseMsg);
    assertTrue(responseMsg.contains("\"processInstanceId\":\""));

    // TODO historic data / audit trail

    responseMsg = webResource.path("/tasks/team/accountancy")
	.accept(MediaType.APPLICATION_JSON).get(String.class);
    System.out.println("response to GET accountancy tasks : " + responseMsg);
    // assertTrue(responseMsg.contains("\"processInstanceId\":\""));

  }

  private String getProcessDefinition() {
    InputStream is = null;
    StringWriter out = new StringWriter();
    try {
      is = getClass()
	  .getResourceAsStream(
	      "/org/activiti/examples/bpmn/usertask/FinancialReportProcess.bpmn20.xml");
      Reader reader = new InputStreamReader(is);
      char[] cbuf = new char[1024];
      while ((reader.read(cbuf) != -1)) {
	out.write(cbuf);
	cbuf = new char[1024];
      }
    } catch (Exception e) {
      fail(e.getClass().getName() + ":" + e.getMessage());
    } finally {
      try {
	is.close();
      } catch (IOException e) {
      }
    }
    return out.toString();
  }

  @Test
  public void testApplicationWadl() {
    WebResource webResource = resource();
    String serviceWadl = webResource.path("application.wadl")
	.accept(MediaTypes.WADL).get(String.class);
    System.out.println("WADL: " + serviceWadl);

    Assert.assertTrue(serviceWadl.length() > 0);
  }
}
