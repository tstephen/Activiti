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

package org.activiti.rest.api.runtime.process;


/**
 * @author Frederik Heremans
 */
public class ExecutionResponse {

  protected String id;
  protected String url;
  protected String parentUrl;
  protected String processInstanceUrl;
  protected boolean suspended;
  protected String activityId;
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getUrl() {
    return url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  public String getParentUrl() {
    return parentUrl;
  }
  
  public void setParentUrl(String parentUrl) {
    this.parentUrl = parentUrl;
  }
  
  public String getProcessInstanceUrl() {
    return processInstanceUrl;
  }
  
  public void setProcessInstanceUrl(String processInstanceUrl) {
    this.processInstanceUrl = processInstanceUrl;
  }
  
  public boolean isSuspended() {
    return suspended;
  }
  
  public void setSuspended(boolean suspended) {
    this.suspended = suspended;
  }
  
  public String getActivityId() {
    return activityId;
  }
  
  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }
}
