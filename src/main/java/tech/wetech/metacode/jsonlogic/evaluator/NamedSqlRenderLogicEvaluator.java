package tech.wetech.metacode.jsonlogic.evaluator;

import tech.wetech.metacode.jsonlogic.ast.*;
import tech.wetech.metacode.jsonlogic.evaluator.sql.AbstractSqlRenderLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.sql.NamedPlaceholderHandler;
import tech.wetech.metacode.jsonlogic.evaluator.sql.NamedSqlRenderResult;

/**
 * @author cjbi
 * @date 2022/9/7
 */
public class NamedSqlRenderLogicEvaluator extends AbstractSqlRenderLogicEvaluator {

    public NamedSqlRenderLogicEvaluator() {
        super();
    }

    public NamedSqlRenderResult evaluate(JsonLogicNode root) throws JsonLogicEvaluationException {
        NamedPlaceholderHandler placeholderHandler = new NamedPlaceholderHandler();
        Object sql = evaluate((JsonLogicOperation) root, placeholderHandler);
        return new NamedSqlRenderResult((String) sql, placeholderHandler.getParameters());
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
                return evaluate((JsonLogicOperation) node, data);
        }
    }

    public Object evaluate(JsonLogicOperation operation, Object data) throws JsonLogicEvaluationException {
        JsonLogicExpression expression = getExpression(operation.getOperator());
        return expression.evaluate(this, operation.getArguments(), data);
    }

}
