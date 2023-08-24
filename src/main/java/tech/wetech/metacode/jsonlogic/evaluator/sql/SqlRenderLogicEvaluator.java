package tech.wetech.metacode.jsonlogic.evaluator.sql;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicNode;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicOperation;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;

/**
 * @author cjbi
 * @date 2022/9/5
 */
public class SqlRenderLogicEvaluator extends AbstractSqlLogicEvaluator {

  public SqlRenderLogicEvaluator() {
    super();
  }

  @Override
  public IndexSqlRenderResult evaluate(JsonLogicNode root, String identifierQuoteString) throws JsonLogicEvaluationException {
    SqlRuntimeContext sqlRuntimeContext = new SqlRuntimeContext();
    sqlRuntimeContext.setIdentifierQuoteString(identifierQuoteString);
    IndexPlaceholderHandler placeholderHandler = new IndexPlaceholderHandler();
    sqlRuntimeContext.setPlaceholderHandler(placeholderHandler);

    return new IndexSqlRenderResult(evaluate((JsonLogicOperation) root, sqlRuntimeContext).toString(), placeholderHandler.getParameters());
  }

}
