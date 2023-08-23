package tech.wetech.metacode.jsonlogic.evaluator.sql.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.sql.PlaceholderHandler;
import tech.wetech.metacode.jsonlogic.evaluator.sql.SqlRuntimeContext;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cjbi
 * @date 2022/11/6
 */
public class ContainsSqlClauseExpression implements SqlClauseExpression {

  public static final ContainsSqlClauseExpression CONTAINS = new ContainsSqlClauseExpression(false);

  public static final ContainsSqlClauseExpression NOT_CONTAINS = new ContainsSqlClauseExpression(true);

  private final boolean isNot;

  public ContainsSqlClauseExpression(boolean isNot) {
    this.isNot = isNot;
  }

  @Override
  public String key() {
    return isNot ? "not_contains" : "contains";
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends JsonLogicEvaluator> SqlIdentifier evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
    SqlRuntimeContext sqlRuntimeContext = (SqlRuntimeContext) data;
    PlaceholderHandler placeholderHandler = sqlRuntimeContext.getPlaceholderHandler();
    Object left = evaluator.evaluate(arguments.get(0), data);
    Object right = evaluator.evaluate(arguments.get(1), data);
    if (right instanceof List<?> list) {
      if (list.isEmpty()) {
        return FALSE;
      }
      if (list.stream().allMatch(i -> i instanceof String || i instanceof Number)) {
        String s = left + " in" + list.stream()
          .map(i -> placeholderHandler.handle(left.toString(), i))
          .collect(Collectors.joining(", ", " (", ") "));
        return new SqlIdentifier(s);
      }
      return new SqlIdentifier(list.stream()
        .map(element -> getSingle(placeholderHandler, left, right, isTableFieldExpression(arguments.get(1))))
        .collect(Collectors.joining(" and ", " (", ") ")));
    }
    return new SqlIdentifier(getSingle(placeholderHandler, left, right, isTableFieldExpression(arguments.get(1))));
  }

  public String getSingle(PlaceholderHandler placeholderHandler, Object left, Object right, boolean rightIsTableField) {
    StringBuilder sb = new StringBuilder(" ");
    sb.append(left);
    sb.append(" ");
    sb.append(isNot ? "not like" : "like");
    sb.append(" ");
    sb.append("concat('%', concat(").append(rightIsTableField ? right : placeholderHandler.handle(left.toString(), right)).append(",'%'))");
    return sb.toString();
  }

}
