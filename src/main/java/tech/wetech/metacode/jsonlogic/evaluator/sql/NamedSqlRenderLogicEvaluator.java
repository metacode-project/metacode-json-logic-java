package tech.wetech.metacode.jsonlogic.evaluator.sql;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicNode;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicOperation;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;

/**
 * @author cjbi
 * @date 2022/9/7
 */
public class NamedSqlRenderLogicEvaluator extends AbstractSqlRenderLogicEvaluator {

  public NamedSqlRenderLogicEvaluator() {
    super();
  }

  @Override
  public NamedSqlRenderResult evaluate(JsonLogicNode root, String identifierQuoteString) throws JsonLogicEvaluationException {
    NamedPlaceholderHandler placeholderHandler = new NamedPlaceholderHandler();
    SqlRuntimeContext runtimeContext = new SqlRuntimeContext();
    runtimeContext.setPlaceholderHandler(placeholderHandler);
    runtimeContext.setIdentifierQuoteString(identifierQuoteString);
    Object sql = evaluate((JsonLogicOperation) root, runtimeContext);
    return new NamedSqlRenderResult(sql.toString(), placeholderHandler.getParameters());
  }



}
