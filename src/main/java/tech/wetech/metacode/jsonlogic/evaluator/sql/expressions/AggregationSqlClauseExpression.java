package tech.wetech.metacode.jsonlogic.evaluator.sql.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;

/**
 * @author cjbi
 */
public class AggregationSqlClauseExpression implements SqlClauseExpression {

  public static final AggregationSqlClauseExpression COUNT = new AggregationSqlClauseExpression("count_agg");

  public static final AggregationSqlClauseExpression MAX = new AggregationSqlClauseExpression("max_agg");

  public static final AggregationSqlClauseExpression MIN = new AggregationSqlClauseExpression("min_agg");

  public static final AggregationSqlClauseExpression SUM = new AggregationSqlClauseExpression("sum_agg");

  public static final AggregationSqlClauseExpression AVG = new AggregationSqlClauseExpression("avg_agg");

  private final String key;

  public AggregationSqlClauseExpression(String key) {
    this.key = key;
  }

  @Override
  public String key() {
    return key;
  }

  @Override
  public <T extends JsonLogicEvaluator> SqlIdentifier evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
    Object item = evaluator.evaluate(arguments.get(0), data);
    String operator = switch (key) {
      case "count_agg" -> "count";
      case "max_agg" -> "max";
      case "min_agg" -> "min";
      case "sum_agg" -> "sum";
      case "avg_agg" -> "avg";
      default -> null;
    };
    return new SqlIdentifier(operator + "(" + item + ")");
  }
}
