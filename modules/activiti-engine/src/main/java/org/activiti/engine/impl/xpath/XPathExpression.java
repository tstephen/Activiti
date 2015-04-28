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
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.delegate.ExpressionGetInvocation;
import org.activiti.engine.impl.delegate.ExpressionSetInvocation;
import org.activiti.engine.impl.el.Expression;
import org.activiti.engine.impl.javax.el.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;


/**
 * Expression implementation backed by XPath {@link javax.xml.xpath.XPathExpression}.
 *
 * @author Tim Stephenson
 */
public class XPathExpression implements Expression {

  protected String expressionText;
  protected javax.xml.xpath.XPathExpression valueExpression;

  public XPathExpression(javax.xml.xpath.XPathExpression valueExpression, String expressionText) {
    this.valueExpression = valueExpression;
    this.expressionText = expressionText;
  }

  public Object getValue(VariableScope variableScope) {
    try {
      Node node = Context.getProcessEngineConfiguration().getXPathExpressionManager().getElContext(variableScope);
      return valueExpression.evaluate(node);
    } catch (XPathExpressionException e) {
      throw new ActivitiException("Error while evaluating XPath expression: " + expressionText, e);
    }
  }
    
  public void setValue(Object value, VariableScope variableScope) {
    throw new RuntimeException("Not yet implemented");
  }
  
  @Override
  public String toString() {
    return getExpressionText();
  }
  
  public String getExpressionText() {
    return expressionText;
  }
}
