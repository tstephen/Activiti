package org.activiti.engine.impl.util.condition;

import org.activiti.bpmn.constants.BpmnXMLConstants;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.Condition;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.el.UelExpressionCondition;
import org.activiti.engine.impl.xpath.XPathExpressionCondition;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Joram Barrez
 * @author Tim Stephenson
 */
public class ConditionUtil {

  public static boolean hasTrueCondition(SequenceFlow sequenceFlow, DelegateExecution execution) {
    String conditionExpression = sequenceFlow.getConditionExpression();
    if (StringUtils.isNotEmpty(conditionExpression)) {

      // TODO: should be done at parse time?
      String lang = sequenceFlow.getAttributeValue(BpmnXMLConstants.BPMN2_NAMESPACE, "expressionLanguage");
      Expression expression = Context.getProcessEngineConfiguration().getExpressionManager(lang).createExpression(sequenceFlow.getConditionExpression());
      Condition condition = createCondition(lang, expression);
      if (condition.evaluate(execution)) {
        return true;
      }

      return false;

    } else {
      return true;
    }

  }

  public static Condition createCondition(String lang, Expression expression) {
    if (BpmnXMLConstants.JUEL_NAMESPACE.equals(lang)) {
      return new UelExpressionCondition(expression);
    } else if (BpmnXMLConstants.XPATH_NAMESPACE.equals(lang)) {
      return new XPathExpressionCondition(expression);
    } else {
      throw new ActivitiIllegalArgumentException(String.format(
          "Do not know how to create condition for expressionLanguage %1$s",
          lang));
    }
  }
}
