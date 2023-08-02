package tech.wetech.metacode.jsonlogic.evaluator;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;

/**
 * @author cjbi
 * @date 2022/9/5
 */
public interface JsonLogicExpression {
  String key();

  <T extends JsonLogicEvaluator> Object evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException;
}
