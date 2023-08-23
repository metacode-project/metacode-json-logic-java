package tech.wetech.metacode.jsonlogic.evaluator.sql.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.sql.SqlRuntimeContext;

/**
 * @author cjbi
 * @date 2022/11/7
 */
public class TableFieldSqlClauseExpression implements SqlClauseExpression {

  public static final TableFieldSqlClauseExpression INSTANCE = new TableFieldSqlClauseExpression();

  @Override
  public String key() {
    return "table_field";
  }

  @Override
  public <T extends JsonLogicEvaluator> SqlIdentifier evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
    SqlRuntimeContext sqlRuntimeContext = (SqlRuntimeContext) data;
    String table = sqlRuntimeContext.quoteIdentifier((String) evaluator.evaluate(arguments.get(0), data));
    String field = sqlRuntimeContext.quoteIdentifier((String) evaluator.evaluate(arguments.get(1), data));
    return new SqlIdentifier(table + "." + field);
  }
}