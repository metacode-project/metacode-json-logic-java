package tech.wetech.metacode.jsonlogic.evaluator.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicExpression;

import java.time.LocalDateTime;

/**
 * @author cjbi
 */
public class CurrentDatetimeExpression implements JsonLogicExpression {

  public static final CurrentDatetimeExpression INSTANCE = new CurrentDatetimeExpression();

  @Override
  public String key() {
    return "current_datetime";
  }

  @Override
  public <T extends JsonLogicEvaluator> Object evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
    return LocalDateTime.now();
  }
}
