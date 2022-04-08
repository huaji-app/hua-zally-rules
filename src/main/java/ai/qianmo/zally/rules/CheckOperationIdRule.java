package ai.qianmo.zally.rules;

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
    id = "HUA-002",
    severity = Severity.MUST,
    title = "operationId is required"
)
public class CheckOperationIdRule {
  @Check(severity = Severity.MUST)
  public List<Violation> checkOperationId(Context context) {
    final Paths paths = context.getApi().getPaths();
    List<Violation> list = new ArrayList<>();
    for (final String s : paths.keySet()) {
      final PathItem pathItem = paths.get(s);
      if (!checkOperationIds(pathItem.getGet()) || !checkOperationIds(pathItem.getDelete()) || !checkOperationIds(
          pathItem.getPost()) || !checkOperationIds(pathItem.getPut())) {
        final Violation violation = context.violation("operationId is required " + s, pathItem);
        list.add(violation);
      }
    }
    return list;
  }
  private boolean checkOperationIds(Operation operation) {
    if (operation == null) {
      return true;
    }
    return operation.getOperationId() != null;
  }
}
