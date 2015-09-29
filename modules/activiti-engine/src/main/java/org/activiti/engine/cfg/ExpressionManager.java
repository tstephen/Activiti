package org.activiti.engine.cfg;

import org.activiti.engine.delegate.Expression;

public interface ExpressionManager {

    Expression createExpression(String expression);
}
