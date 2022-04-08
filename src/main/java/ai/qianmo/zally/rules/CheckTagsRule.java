package ai.qianmo.zally.rules;

/*-
 * #%L
 * zally-maven-plugin
 * %%
 * Copyright (C) 2021 - 2022 Morten Haraldsen (ethlo)
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
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
 * #L%
 */

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import java.util.ArrayList;
import java.util.List;
import org.zalando.zally.rule.api.Check;
import org.zalando.zally.rule.api.Context;
import org.zalando.zally.rule.api.Rule;
import org.zalando.zally.rule.api.Severity;
import org.zalando.zally.rule.api.Violation;

@Rule(
    ruleSet = HuaJiRuleSet.class,
    id = "HUA-001",
    severity = Severity.MUST,
    title = "tags field is required"
)
public class CheckTagsRule {

  @Check(severity = Severity.MUST)
  public List<Violation> checkTags(Context context) {
    final Paths paths = context.getApi().getPaths();
    List<Violation> list = new ArrayList<>();
    for (final String s : paths.keySet()) {
      final PathItem pathItem = paths.get(s);
      if (!checkOperationTags(pathItem.getGet()) || !checkOperationTags(pathItem.getDelete()) || !checkOperationTags(
          pathItem.getPost()) || !checkOperationTags(pathItem.getPut())) {
        final Violation violation = context.violation("tags field is required " + s, pathItem);
        list.add(violation);
      }
    }
    return list;
  }

  private boolean checkOperationTags(Operation operation) {
    if (operation == null) {
      return true;
    }
    return operation.getTags() != null && !operation.getTags().isEmpty();
  }
}
