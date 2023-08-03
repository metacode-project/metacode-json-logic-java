package tech.wetech.metacode.jsonlogic.evaluator.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicExpression;

import java.util.List;

/**
 * @author cjbi
 * @date 2022/9/5
 */
public interface PreEvaluatedArgumentsExpression extends JsonLogicExpression {

  Object evaluate(List arguments, Object data) throws JsonLogicEvaluationException;

  @Override

  default Object evaluate(JsonLogicEvaluator evaluator, JsonLogicArray arguments, Object data)
    throws JsonLogicEvaluationException {
    return evaluate(evaluator.evaluate(arguments, data), data);
  }

}
