package tech.wetech.metacode.jsonlogic.evaluator.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicExpression;

/**
 * @author cjbi
 * @date 2022/9/6
 */
public class InequalityExpression implements JsonLogicExpression {
  public static final InequalityExpression INSTANCE = new InequalityExpression(EqualityExpression.INSTANCE);

  private final EqualityExpression delegate;

  private InequalityExpression(EqualityExpression delegate) {
    this.delegate = delegate;
  }

  @Override
  public String key() {
    return "!=";
  }

  @Override
  public Object evaluate(JsonLogicEvaluator evaluator, JsonLogicArray arguments, Object data)
    throws JsonLogicEvaluationException {
    boolean result = (boolean) delegate.evaluate(evaluator, arguments, data);
    return !result;
  }
}

