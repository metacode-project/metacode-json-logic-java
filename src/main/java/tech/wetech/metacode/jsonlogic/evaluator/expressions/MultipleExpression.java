package tech.wetech.metacode.jsonlogic.evaluator.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicExpression;

/**
 * @author cjbi
 * @date 2022/11/7
 */
public class MultipleExpression implements JsonLogicExpression {

  public static final MultipleExpression INSTANCE = new MultipleExpression();

  @Override
  public String key() {
    return "multiple";
  }

  @Override
  public <T extends JsonLogicEvaluator> Object evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
    return evaluator.evaluate(arguments, data);
  }

}
