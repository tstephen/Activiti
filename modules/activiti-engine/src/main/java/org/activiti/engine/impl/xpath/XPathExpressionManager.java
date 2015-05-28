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

import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFunctionResolver;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.VariableScope;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


/**
 * <p>
 * Central manager for XPath expressions.
 * </p>
 * <p>
 * Process parsers will use this to build expression objects that are stored in
 * the process definitions.
 * </p>
 * <p>
 * Then also this class is used as an entry point for runtime evaluation of the
 * expressions.
 * </p>
 * 
 * @author Tim Stephenson
 */
public class XPathExpressionManager /*extends ExpressionManager*/ {

  protected XPathFunctionResolver xpathFunctionResolver;
  protected XPathFactory expressionFactory;
  protected DocumentBuilder docBuilder;
  protected Map<Object, Object> beans;


  public XPathExpressionManager() {
	    this(null);
  }

  public XPathExpressionManager(Map<Object, Object> beans) {
	  initFactory();
	  this.beans = beans;
  }

  protected void initFactory() {
    expressionFactory = XPathFactory.newInstance();
    expressionFactory.setXPathFunctionResolver(getXPathFunctionResolver());

    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    try {
      docBuilder = docBuilderFactory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new ActivitiException(e.getMessage(), e);
    }
  }

  protected void setXpathFunctionResolver(XPathFunctionResolver functionResolver) {
    this.xpathFunctionResolver = functionResolver;
  }

  protected XPathFunctionResolver getXPathFunctionResolver() {
    if (xpathFunctionResolver ==null) {
      xpathFunctionResolver = new BpmnXPathFunctionResolver();
    }
    return xpathFunctionResolver;
  }

  public Expression createExpression(String expression) {
    try {
      XPath xpath = expressionFactory.newXPath();
      xpath.setXPathFunctionResolver(getXPathFunctionResolver());
      return new XPathExpression(xpath.compile(expression), expression.trim());
    } catch (XPathExpressionException e) {
      throw new ActivitiException(String.format("Unable to compile the expression %1$s", expression));
    }
  }

  public void setExpressionFactory(javax.xml.xpath.XPathFactory expressionFactory) {
    this.expressionFactory = expressionFactory;
  }

  public Node getElContext(VariableScope variableScope) {

    Document doc = docBuilder.newDocument();
    // TODO this is insufficient for all but simple data
    for(String varName : variableScope.getVariableNames()) {
      Element el = doc.createElement(varName);
      Object var = variableScope.getVariable(varName);
      el.setTextContent(var == null ? "" : var.toString());
    }
    return doc;
  }

	public Map<Object, Object> getBeans() {
		return beans;
	}

	public void setBeans(Map<Object, Object> beans) {
		this.beans = beans;
	}
  
}
