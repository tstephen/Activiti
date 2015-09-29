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

import javax.xml.xpath.XPathFunction;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionResolver;

public class BpmnXPathFunctionResolver implements XPathFunctionResolver {

  @Override
  public XPathFunction resolveFunction(QName functionName, int arity) {
    if ("getDataObject".equals(functionName.getLocalPart())) {
      return new GetDataObjectXPathFunction();
    }
    return null;
  }

}