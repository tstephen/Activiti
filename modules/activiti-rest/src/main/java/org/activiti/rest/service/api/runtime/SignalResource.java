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

package org.activiti.rest.service.api.runtime;

import java.util.HashMap;
import java.util.Map;

<<<<<<< HEAD
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.rest.common.api.ActivitiUtil;
import org.activiti.rest.common.api.SecuredResource;
import org.activiti.rest.service.api.RestResponseFactory;
import org.activiti.rest.service.api.engine.variable.RestVariable;
import org.activiti.rest.service.api.runtime.process.SignalEventReceivedRequest;
import org.activiti.rest.service.application.ActivitiRestServicesApplication;
import org.restlet.data.Status;
import org.restlet.resource.Post;
=======
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.RuntimeService;
import org.activiti.rest.service.api.RestResponseFactory;
import org.activiti.rest.service.api.engine.variable.RestVariable;
import org.activiti.rest.service.api.runtime.process.SignalEventReceivedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
>>>>>>> upstream/master

/**
 * Resource for notifying the engine a signal event has been received, independent of an execution.
 * 
 * @author Frederik Heremans
 */
<<<<<<< HEAD
public class SignalResource extends SecuredResource {


  @Post
  public void signalEventReceived(SignalEventReceivedRequest signalRequest) {
    if (authenticate() == false)
      return;
    
    if(signalRequest.getSignalName() == null) {
=======
@RestController
public class SignalResource {
  
  @Autowired
  protected RestResponseFactory restResponseFactory;
  
  @Autowired
  protected RuntimeService runtimeService;

  @RequestMapping(value="/runtime/signals", method = RequestMethod.POST)
  public void signalEventReceived(@RequestBody SignalEventReceivedRequest signalRequest, HttpServletResponse response) {
    if (signalRequest.getSignalName() == null) {
>>>>>>> upstream/master
    	throw new ActivitiIllegalArgumentException("signalName is required");
    }
    
    Map<String, Object> signalVariables = null;
<<<<<<< HEAD
    if(signalRequest.getVariables() != null) {
    	RestResponseFactory factory = getApplication(ActivitiRestServicesApplication.class).getRestResponseFactory();
      signalVariables = new HashMap<String, Object>();
      for(RestVariable variable : signalRequest.getVariables()) {
        if(variable.getName() == null) {
          throw new ActivitiIllegalArgumentException("Variable name is required.");
        }
        signalVariables.put(variable.getName(), factory.getVariableValue(variable));
      }
    }
    
    
    if(signalRequest.isAsync()) {
=======
    if (signalRequest.getVariables() != null) {
      signalVariables = new HashMap<String, Object>();
      for (RestVariable variable : signalRequest.getVariables()) {
        if (variable.getName() == null) {
          throw new ActivitiIllegalArgumentException("Variable name is required.");
        }
        signalVariables.put(variable.getName(), restResponseFactory.getVariableValue(variable));
      }
    }
    
    if (signalRequest.isAsync()) {
>>>>>>> upstream/master
    	if(signalVariables != null) {
    		throw new ActivitiIllegalArgumentException("Async signals cannot take variables as payload");
    	}
    	
<<<<<<< HEAD
    	if(signalRequest.isCustomTenantSet()) {
    		ActivitiUtil.getRuntimeService().signalEventReceivedAsyncWithTenantId(signalRequest.getSignalName(), signalRequest.getTenantId());
    	} else {
    		ActivitiUtil.getRuntimeService().signalEventReceivedAsync(signalRequest.getSignalName());
    	}
    	setStatus(Status.SUCCESS_ACCEPTED);
    } else {
    	if(signalRequest.isCustomTenantSet()) {
    		ActivitiUtil.getRuntimeService().signalEventReceivedWithTenantId(signalRequest.getSignalName(), signalVariables, signalRequest.getTenantId());
    	} else {
    		ActivitiUtil.getRuntimeService().signalEventReceived(signalRequest.getSignalName(), signalVariables);
    	}
    	setStatus(Status.SUCCESS_OK);
=======
    	if (signalRequest.isCustomTenantSet()) {
    	  runtimeService.signalEventReceivedAsyncWithTenantId(signalRequest.getSignalName(), signalRequest.getTenantId());
    	} else {
    	  runtimeService.signalEventReceivedAsync(signalRequest.getSignalName());
    	}
    	response.setStatus(HttpStatus.ACCEPTED.value());
    } else {
    	if (signalRequest.isCustomTenantSet()) {
    		runtimeService.signalEventReceivedWithTenantId(signalRequest.getSignalName(), signalVariables, signalRequest.getTenantId());
    	} else {
    		runtimeService.signalEventReceived(signalRequest.getSignalName(), signalVariables);
    	}
    	response.setStatus(HttpStatus.NO_CONTENT.value());
>>>>>>> upstream/master
    }
  }
}
