package tech.wetech.metacode.jsonlogic.evaluator.sql.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;

/**
 * @author cjbi
 */
public class CurrentDatetimeSqlClauseExpression implements SqlClauseExpression {

  public static final CurrentDatetimeSqlClauseExpression INSTANCE = new CurrentDatetimeSqlClauseExpression();

  @Override
  public String key() {
    return "current_datetime";
  }

  @Override
  public <T extends JsonLogicEvaluator> SqlIdentifier evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
    return new SqlIdentifier("now()");
  }
}
