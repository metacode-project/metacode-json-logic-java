package tech.wetech.metacode.jsonlogic.evaluator.sql.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicNode;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicOperation;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicVariable;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicExpression;
import tech.wetech.metacode.jsonlogic.evaluator.sql.PlaceholderHandler;
import tech.wetech.metacode.jsonlogic.evaluator.sql.SqlRuntimeContext;

import java.util.Objects;

/**
 * @author cjbi
 * @date 2022/11/6
 */
public interface SqlExpression extends JsonLogicExpression {

  SqlIdentifier TRUE = new SqlIdentifier("1=1");
  SqlIdentifier FALSE = new SqlIdentifier("1<>1");

  default boolean isTableFieldExpression(JsonLogicNode node) {
    return node instanceof JsonLogicOperation operation && operation.getOperator().equals("table_field");
  }

  default Object handlePlace(SqlRuntimeContext sqlRuntimeContext, JsonLogicNode valueNode, Object key, Object value) {
    PlaceholderHandler placeholderHandler = sqlRuntimeContext.getPlaceholderHandler();
    if (valueNode instanceof JsonLogicVariable) {
      if (Objects.toString(value, "").contains(".")) {
        String[] items = value.toString().split("\\.");
        return sqlRuntimeContext.quoteIdentifier(items[0]) + "." + sqlRuntimeContext.quoteIdentifier(items[1]);
      }
      return value;
    }
    if (value instanceof SqlIdentifier) {
      return value;
    }
    return placeholderHandler.handle(key.toString(), value);
  }

  <T extends JsonLogicEvaluator> SqlIdentifier evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException;

}
