package tech.wetech.metacode.jsonlogic.evaluator.sql.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;

import java.util.function.Function;

/**
 * @author cjbi
 */
public class AggregationSqlExpression implements SqlExpression {

  public static final AggregationSqlExpression COUNT = new AggregationSqlExpression("count_agg", i -> new SqlNode("count(" + i + ")"));
  public static final AggregationSqlExpression MAX = new AggregationSqlExpression("max_agg", i -> new SqlNode("max(" + i + ")"));
  public static final AggregationSqlExpression MIN = new AggregationSqlExpression("min_agg", i -> new SqlNode("min(" + i + ")"));
  public static final AggregationSqlExpression SUM = new AggregationSqlExpression("sum_agg", i -> new SqlNode("sum(" + i + ")"));
  public static final AggregationSqlExpression AVG = new AggregationSqlExpression("avg_agg", i -> new SqlNode("avg(" + i + ")"));

  private final String key;
  private final Function<String, SqlNode> fn;

  public AggregationSqlExpression(String key, Function<String, SqlNode> fn) {
    this.key = key;
    this.fn = fn;
  }

  @Override
  public String key() {
    return key;
  }

  @Override
  public <T extends JsonLogicEvaluator> SqlNode evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
    if (key.equals("count_agg") && arguments.isEmpty()) {
      return fn.apply("*");
    }
    return fn.apply(evaluator.evaluate(arguments.get(0), data).toString());
  }
}
