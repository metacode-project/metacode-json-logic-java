package tech.wetech.metacode.jsonlogic.ast;

import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;

import java.util.function.Function;

/**
 * @author cjbi
 * @date 2022/9/4
 */
public interface JsonLogicNode {
  JsonLogicNodeType getType();

  default <R extends JsonLogicEvaluator> R evaluator(Function<JsonLogicNode, R> evaluator) {
    return evaluator.apply(this);
  }
}
