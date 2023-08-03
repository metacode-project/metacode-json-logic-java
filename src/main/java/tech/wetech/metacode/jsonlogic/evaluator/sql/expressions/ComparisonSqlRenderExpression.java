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
public class ComparisonSqlRenderExpression implements SqlExpression {


  public static final ComparisonSqlRenderExpression EQ = new ComparisonSqlRenderExpression("==");
  public static final ComparisonSqlRenderExpression NE = new ComparisonSqlRenderExpression("!=");
  public static final ComparisonSqlRenderExpression GT = new ComparisonSqlRenderExpression(">");
  public static final ComparisonSqlRenderExpression GTE = new ComparisonSqlRenderExpression(">=");
  public static final ComparisonSqlRenderExpression LT = new ComparisonSqlRenderExpression("<");
  public static final ComparisonSqlRenderExpression LTE = new ComparisonSqlRenderExpression("<=");
  private static Map<String, Object> OPERATOR_MAP = new HashMap<>();

  static {
    OPERATOR_MAP.put("==", "=");
  }

  private final String key;

  private ComparisonSqlRenderExpression(String key) {
    this.key = key;
  }

  @Override
  public String key() {
    return key;
  }

  @Override
  public <T extends JsonLogicEvaluator> String evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
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
    return sb.toString();
  }

}
