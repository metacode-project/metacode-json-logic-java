package tech.wetech.metacode.jsonlogic.evaluator.sql.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicNode;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicOperation;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicVariable;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicExpression;
import tech.wetech.metacode.jsonlogic.evaluator.sql.PlaceholderHandler;

/**
 * @author cjbi
 * @date 2022/11/6
 */
public interface SqlExpression extends JsonLogicExpression {

  String TRUE = "1=1";
  String FALSE = "1<>1";

  default boolean isTableFieldExpression(JsonLogicNode node) {
    return node instanceof JsonLogicOperation operation && operation.getOperator().equals("table_field");
  }

  default Object handlePlace(PlaceholderHandler placeholderHandler, JsonLogicNode valueNode, Object key, Object value) {
    if (valueNode instanceof JsonLogicVariable || value instanceof SqlIdentifier) {
      return value;
    }
    return placeholderHandler.handle(key.toString(), value);
  }
}
