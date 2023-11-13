package tech.wetech.metacode.jsonlogic.evaluator.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicExpression;

/**
 * @author cjbi
 * @date 2022/11/7
 */
public class IdentifierExpression implements JsonLogicExpression {

  public static final IdentifierExpression INSTANCE = new IdentifierExpression();

  @Override
  public String key() {
    return "identifier";
  }

  @Override
  public <T extends JsonLogicEvaluator> Object evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
    return evaluator.evaluate(arguments.get(0), data).toString();
  }
}
