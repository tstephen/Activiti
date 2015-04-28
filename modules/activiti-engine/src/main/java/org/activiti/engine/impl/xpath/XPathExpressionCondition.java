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

package org.activiti.engine.impl.xpath;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.Condition;


/**
 * {@link org.activiti.engine.impl.Condition} that resolves an XPath expression at runtime.
 *
 * @author Tim Stephenson
 */
public class XPathExpressionCondition implements Condition {

  protected Expression expression;

  public XPathExpressionCondition(Expression expression) {
    this.expression = expression;
  }

  public boolean evaluate(DelegateExecution execution) {
    Object result = expression.getValue(execution);
    
    if (result==null) {
      throw new ActivitiException("condition expression returns null");
    }
    if (result instanceof Boolean) {
      return (Boolean) result;
    } else if (result instanceof String ){
      return Boolean.parseBoolean((String) result);
    } else {
      throw new ActivitiException("condition expression returns non-Boolean: "+result+" ("+result.getClass().getName()+")");
    }
  }

}
