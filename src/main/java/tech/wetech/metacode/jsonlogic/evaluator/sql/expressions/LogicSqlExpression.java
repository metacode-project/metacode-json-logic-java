package tech.wetech.metacode.jsonlogic.evaluator.sql.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicNode;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.sql.SqlRuntimeContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cjbi
 * @date 2022/9/6
 */
public class LogicSqlExpression implements SqlExpression {

  public static final LogicSqlExpression AND = new LogicSqlExpression(true);
  public static final LogicSqlExpression OR = new LogicSqlExpression(false);

  private final boolean isAnd;

  private LogicSqlExpression(boolean isAnd) {
    this.isAnd = isAnd;
  }

  @Override
  public String key() {
    return isAnd ? "and" : "or";
  }

  @Override
  public <T extends JsonLogicEvaluator> String evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
    SqlRuntimeContext sqlRuntimeContext = (SqlRuntimeContext) data;
    if (arguments.size() < 1) {
      throw new JsonLogicEvaluationException("and operator expects at least 1 argument");
    }
    List<String> list = new ArrayList<>();
    for (JsonLogicNode element : arguments) {
      list.add((String) evaluator.evaluate(element, sqlRuntimeContext));
    }
    String whereClause = list.stream()
      .collect(Collectors.joining(" " + key() + " ", " (", " )"));
    return whereClause;
  }
}
