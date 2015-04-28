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

import org.activiti.engine.impl.context.Context;

import java.util.List;

import javax.xml.xpath.XPathFunction;
import javax.xml.xpath.XPathFunctionException;

public class GetDataObjectXPathFunction implements XPathFunction {

  @Override
  public Object evaluate(List args) throws XPathFunctionException {
    if (args.size() == 1) {
      String variableName = (String) args.get(0);
      return Context.getExecutionContext().getExecution().getVariable(variableName);
    } else {
      throw new RuntimeException("The XPath function getDataObject needs one variable name as an input, but the number of inputs was " + args.size() + ".");
    }
  }

}

