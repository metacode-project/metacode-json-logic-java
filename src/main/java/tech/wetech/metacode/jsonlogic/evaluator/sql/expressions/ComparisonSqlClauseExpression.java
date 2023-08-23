package tech.wetech.metacode.jsonlogic.evaluator.sql.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.sql.PlaceholderHandler;
import tech.wetech.metacode.jsonlogic.evaluator.sql.SqlRuntimeContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cjbi
 * @date 2022/9/5
 */
public class ComparisonSqlClauseExpression implements SqlClauseExpression {


  public static final ComparisonSqlClauseExpression EQ = new ComparisonSqlClauseExpression("==");
  public static final ComparisonSqlClauseExpression NE = new ComparisonSqlClauseExpression("!=");
  public static final ComparisonSqlClauseExpression GT = new ComparisonSqlClauseExpression(">");
  public static final ComparisonSqlClauseExpression GTE = new ComparisonSqlClauseExpression(">=");
  public static final ComparisonSqlClauseExpression LT = new ComparisonSqlClauseExpression("<");
  public static final ComparisonSqlClauseExpression LTE = new ComparisonSqlClauseExpression("<=");
  private static Map<String, Object> OPERATOR_MAP = new HashMap<>();

  static {
    OPERATOR_MAP.put("==", "=");
  }

  private final String key;

  private ComparisonSqlClauseExpression(String key) {
    this.key = key;
  }

  @Override
  public String key() {
    return key;
  }

  @Override
  public <T extends JsonLogicEvaluator> SqlIdentifier evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
    SqlRuntimeContext sqlRuntimeContext = (SqlRuntimeContext) data;
    PlaceholderHandler placeholderHandler = sqlRuntimeContext.getPlaceholderHandler();
    Object left = evaluator.evaluate(arguments.get(0), data);
    Object right = evaluator.evaluate(arguments.get(1), data);

    StringBuilder sb = new StringBuilder(" ");
    sb.append(handlePlace(placeholderHandler, arguments.get(0), right, left));
    sb.append(" ");
    sb.append(OPERATOR_MAP.getOrDefault(key, key));
    sb.append(" ");
    sb.append(handlePlace(placeholderHandler, arguments.get(1), left, right));
    return new SqlIdentifier(sb.toString());
  }

}
