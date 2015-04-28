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
package org.activiti.engine.impl.bpmn.parser.handler;

import java.util.List;
import java.util.Map;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.Condition;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.el.UelExpressionCondition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ScopeImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.impl.xpath.XPathExpressionCondition;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Joram Barrez
 * @author Tim Stephenson
 */
public class SequenceFlowParseHandler extends AbstractBpmnParseHandler<SequenceFlow> implements BpmnXMLConstants {
  
  public static final String PROPERTYNAME_CONDITION = "condition";
  public static final String PROPERTYNAME_CONDITION_TEXT = "conditionText";

  public Class< ? extends BaseElement> getHandledType() {
    return SequenceFlow.class;
  }

  protected void executeParse(BpmnParse bpmnParse, SequenceFlow sequenceFlow) {
    
    ScopeImpl scope = bpmnParse.getCurrentScope();

    ActivityImpl sourceActivity = scope.findActivity(sequenceFlow.getSourceRef());
    ActivityImpl destinationActivity = scope.findActivity(sequenceFlow.getTargetRef());

    Expression skipExpression;
    if (StringUtils.isNotEmpty(sequenceFlow.getSkipExpression())) {
      ExpressionManager expressionManager = bpmnParse.getExpressionManager();
      skipExpression = expressionManager.createExpression(sequenceFlow.getSkipExpression());
    } else {
      skipExpression = null;
    }
    
    TransitionImpl transition = sourceActivity.createOutgoingTransition(sequenceFlow.getId(), skipExpression);
    bpmnParse.getSequenceFlows().put(sequenceFlow.getId(), transition);
    transition.setProperty("name", sequenceFlow.getName());
    transition.setProperty("documentation", sequenceFlow.getDocumentation());
    transition.setDestination(destinationActivity);

    if (StringUtils.isNotEmpty(sequenceFlow.getConditionExpression())) {
      Map<String,List<ExtensionAttribute>> definitionsAttributes = bpmnParse.getBpmnModel().getDefinitionsAttributes();
      Condition expressionCondition;
      if (definitionsAttributes.containsKey(EXPRESSION_LANGUAGE_ATTRIBUTE) && definitionsAttributes.get(EXPRESSION_LANGUAGE_ATTRIBUTE) != null
              && definitionsAttributes.get("expressionLanguage").get(0).getValue().equals(XPATH_NAMESPACE)) {
        expressionCondition = new XPathExpressionCondition(bpmnParse.getXPathExpressionManager().createExpression(sequenceFlow.getConditionExpression()));
      } else {
        expressionCondition = new UelExpressionCondition(bpmnParse.getExpressionManager().createExpression(sequenceFlow.getConditionExpression()));
      }
      transition.setProperty(PROPERTYNAME_CONDITION_TEXT, sequenceFlow.getConditionExpression());
      transition.setProperty(PROPERTYNAME_CONDITION, expressionCondition);
    }

    createExecutionListenersOnTransition(bpmnParse, sequenceFlow.getExecutionListeners(), transition);
    
  }

}
