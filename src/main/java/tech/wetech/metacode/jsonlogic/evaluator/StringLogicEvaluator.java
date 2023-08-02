package tech.wetech.metacode.jsonlogic.evaluator;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cjbi
 */
public class StringLogicEvaluator implements JsonLogicEvaluator {

  private final List<JsonLogicExpression> expressions = new ArrayList<>();

  public StringLogicEvaluator() {

  }

  @Override
  public Object evaluate(JsonLogicNode node, Object data) throws JsonLogicEvaluationException {
    return null;
  }

  @Override
  public List<JsonLogicExpression> getExpressions() {
    return null;
  }
}
