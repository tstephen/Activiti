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
package org.activiti.engine.impl;

import org.activiti.engine.delegate.VariableScope;


public class Expression implements org.activiti.engine.delegate.Expression {

  private static final long serialVersionUID = -3910595868921963905L;

  @Override
  public Object getValue(VariableScope variableScope) {
    return null;
  }

  @Override
  public void setValue(Object value, VariableScope variableScope) {
  }

  @Override
  public String getExpressionText() {
    return null;
  }

}
