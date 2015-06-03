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

package org.activiti.engine.test.xpath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;

/**
 * @author Tim Stephenson
 */
public class XPathExpressionManagerTest extends PluggableActivitiTestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }

  @Deployment
  public void testDataObjectFunction() {
    Map<String, Object> vars = new HashMap<String, Object>();

    vars.put("approved", true);
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("testDataObjectProcess", vars);
    
    List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstance.getProcessInstanceId());
    assertNotNull(activeActivityIds);
    assertEquals(1,activeActivityIds.size());
    assertEquals(activeActivityIds.get(0), "userTask1");

    vars.put("approved", "false");
    processInstance = runtimeService.startProcessInstanceByKey("testDataObjectProcess", vars);

    activeActivityIds = runtimeService.getActiveActivityIds(processInstance.getProcessInstanceId());
    assertNotNull(activeActivityIds);
    assertEquals(1, activeActivityIds.size());
    assertEquals(activeActivityIds.get(0), "userTask2");
  }
}
