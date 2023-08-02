package tech.wetech.metacode.jsonlogic.evaluator.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicNull;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicString;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicVariable;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicExpression;

/**
 * @author cjbi
 * @date 2022/11/7
 */
public class TableFieldExpression implements JsonLogicExpression {

  public static final TableFieldExpression INSTANCE = new TableFieldExpression();

  @Override
  public String key() {
    return "table_field";
  }

  @Override
  public <T extends JsonLogicEvaluator> Object evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
    Object table = evaluator.evaluate(arguments.get(0), data);
    Object field = evaluator.evaluate(arguments.get(1), data);
    return evaluator.evaluate(new JsonLogicVariable(new JsonLogicString(table + "." + field), JsonLogicNull.NULL), data);
  }
}
