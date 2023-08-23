package tech.wetech.metacode.jsonlogic.evaluator.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicExpression;

import java.time.LocalDateTime;

/**
 * @author cjbi
 * @date 2022/11/7
 */
public class DatetimeExpression implements JsonLogicExpression {

  public static final DatetimeExpression INSTANCE = new DatetimeExpression();

  @Override
  public String key() {
    return "datetime";
  }

  @Override
  public <T extends JsonLogicEvaluator> Object evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
    String datetimeString = (String) evaluator.evaluate(arguments.get(0), data);
    return LocalDateTime.parse(datetimeString);
  }
}
