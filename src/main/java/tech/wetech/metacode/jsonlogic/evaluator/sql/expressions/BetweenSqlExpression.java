package tech.wetech.metacode.jsonlogic.evaluator.sql.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.sql.SqlRuntimeContext;

/**
 * @author cjbi
 */
public class BetweenSqlExpression implements SqlExpression {

  public static final BetweenSqlExpression INSTANCE = new BetweenSqlExpression();

  @Override
  public String key() {
    return "between";
  }

  @Override
  public <T extends JsonLogicEvaluator> SqlNode evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
    if (arguments.size() != 3) {
      throw new JsonLogicEvaluationException("between expressions expect exactly 3 arguments");
    }
    SqlRuntimeContext sqlRuntimeContext = (SqlRuntimeContext) data;
    Object field = evaluator.evaluate(arguments.get(0), data);
    Object left = evaluator.evaluate(arguments.get(1), data);
    Object right = evaluator.evaluate(arguments.get(2), data);
    return new SqlNode(String.format(" %s between %s and %s", field,
      sqlRuntimeContext.getPlaceholderHandler().handle(field.toString(), left),
      sqlRuntimeContext.getPlaceholderHandler().handle(field.toString(), right)
    ));
  }
}
