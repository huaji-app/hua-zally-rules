package ai.qianmo.zally.rules;

import static org.assertj.core.api.Assertions.assertThat;

import io.swagger.v3.oas.models.OpenAPI;
import java.util.List;
import org.junit.Test;
import org.zalando.zally.core.DefaultContext;
import org.zalando.zally.rule.api.Context;
import org.zalando.zally.rule.api.Violation;

public class CheckOperationIdRuleTest {
  @Test
  public void checkOperationIdRule()
  {
    final String url = "modified_petstore/petstore.yaml";
    final OpenAPI openApi = new OpenApiParser().parse(url);
    final Context context = new DefaultContext("", openApi, null);
    final List<Violation> violations = new CheckOperationIdRule().checkOperationId(context);
    assertThat(violations.size() == 1);
  }
}
