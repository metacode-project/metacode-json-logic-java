package tech.wetech.metacode.jsonlogic.evaluator.sql.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;

import java.util.function.Function;

/**
 * @author cjbi
 */
public class AggregationSqlClauseExpression implements SqlClauseExpression {

  public static final AggregationSqlClauseExpression COUNT = new AggregationSqlClauseExpression("count_agg", i -> new SqlIdentifier("count(" + i + ")"));
  public static final AggregationSqlClauseExpression MAX = new AggregationSqlClauseExpression("max_agg", i -> new SqlIdentifier("max(" + i + ")"));
  public static final AggregationSqlClauseExpression MIN = new AggregationSqlClauseExpression("min_agg", i -> new SqlIdentifier("min(" + i + ")"));
  public static final AggregationSqlClauseExpression SUM = new AggregationSqlClauseExpression("sum_agg", i -> new SqlIdentifier("sum(" + i + ")"));
  public static final AggregationSqlClauseExpression AVG = new AggregationSqlClauseExpression("avg_agg", i -> new SqlIdentifier("avg(" + i + ")"));

  private final String key;
  private final Function<String, SqlIdentifier> fn;

  public AggregationSqlClauseExpression(String key, Function<String, SqlIdentifier> fn) {
    this.key = key;
    this.fn = fn;
  }

  @Override
  public String key() {
    return key;
  }

  @Override
  public <T extends JsonLogicEvaluator> SqlIdentifier evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
    return fn.apply(evaluator.evaluate(arguments.get(0), data).toString());
  }
}
