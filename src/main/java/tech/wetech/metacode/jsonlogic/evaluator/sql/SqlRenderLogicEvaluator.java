package tech.wetech.metacode.jsonlogic.evaluator.sql;

import tech.wetech.metacode.jsonlogic.ast.*;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicExpression;

/**
 * @author cjbi
 * @date 2022/9/5
 */
public class SqlRenderLogicEvaluator extends AbstractSqlRenderLogicEvaluator {

  public SqlRenderLogicEvaluator() {
    super();
  }

  public IndexSqlRenderResult evaluate(JsonLogicNode root, String identifierQuoteString) throws JsonLogicEvaluationException {
    SqlRuntimeContext sqlRuntimeContext = new SqlRuntimeContext();
    sqlRuntimeContext.setIdentifierQuoteString(identifierQuoteString);
    IndexPlaceholderHandler placeholderHandler = new IndexPlaceholderHandler();
    sqlRuntimeContext.setPlaceholderHandler(placeholderHandler);

    return new IndexSqlRenderResult((String) evaluate((JsonLogicOperation) root, sqlRuntimeContext), placeholderHandler.getParameters());
  }

  @Override
  public Object evaluate(JsonLogicNode node, Object data) throws JsonLogicEvaluationException {
    switch (node.getType()) {
      case PRIMITIVE:
        return evaluate((JsonLogicPrimitive) node, data);
      case VARIABLE:
        return evaluate((JsonLogicVariable) node, data);
      case ARRAY:
        return evaluate((JsonLogicArray) node, data);
      default:
        return this.evaluate((JsonLogicOperation) node, data);
    }
  }

  public Object evaluate(JsonLogicOperation operation, Object data) throws JsonLogicEvaluationException {
    JsonLogicExpression expression = getExpression(operation.getOperator());
    return expression.evaluate(this, operation.getArguments(), data);
  }
}
