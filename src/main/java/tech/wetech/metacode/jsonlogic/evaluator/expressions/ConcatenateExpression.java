package tech.wetech.metacode.jsonlogic.evaluator.expressions;

import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cjbi
 */
public class ConcatenateExpression implements PreEvaluatedArgumentsExpression {

  public static final ConcatenateExpression INSTANCE = new ConcatenateExpression();

  private ConcatenateExpression() {
    // Use INSTANCE instead.
  }

  @Override
  public String key() {
    return "cat";
  }

  @Override
  @SuppressWarnings("all")
  public Object evaluate(List arguments, Object data) throws JsonLogicEvaluationException {
    return arguments.stream()
      .map(obj -> {
        if (obj instanceof Double && obj.toString().endsWith(".0")) {
          return ((Double) obj).intValue();
        }

        return obj;
      })
      .map(Object::toString)
      .collect(Collectors.joining());
  }
}
