package tech.wetech.metacode.jsonlogic.evaluator.sql;

import tech.wetech.metacode.jsonlogic.ast.*;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicExpression;
import tech.wetech.metacode.jsonlogic.evaluator.expressions.*;
import tech.wetech.metacode.jsonlogic.evaluator.sql.expressions.ComparisonSqlRenderExpression;
import tech.wetech.metacode.jsonlogic.evaluator.sql.expressions.ContainsExpression;
import tech.wetech.metacode.jsonlogic.evaluator.sql.expressions.LogicSqlRenderExpression;
import tech.wetech.metacode.jsonlogic.evaluator.sql.expressions.TableFieldExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cjbi
 * @date 2022/9/7
 */
public abstract class AbstractSqlRenderLogicEvaluator implements JsonLogicEvaluator {

    protected final List<JsonLogicExpression> expressions = new ArrayList<>();

    protected AbstractSqlRenderLogicEvaluator() {

        addOperation(LogicSqlRenderExpression.AND);
        addOperation(LogicSqlRenderExpression.OR);

        addOperation(ComparisonSqlRenderExpression.EQ);
        addOperation(ComparisonSqlRenderExpression.NE);
        addOperation(ComparisonSqlRenderExpression.GT);
        addOperation(ComparisonSqlRenderExpression.GTE);
        addOperation(ComparisonSqlRenderExpression.LT);
        addOperation(ComparisonSqlRenderExpression.LTE);

        addOperation(ContainsExpression.CONTAINS);
        addOperation(ContainsExpression.NOT_CONTAINS);

        addOperation(TableFieldExpression.INSTANCE);

        addOperation(RadioExpression.INSTANCE);
        addOperation(DatetimeExpression.INSTANCE);
        addOperation(MultipleExpression.INSTANCE);
        addOperation(AttachExpression.INSTANCE);
        addOperation(IdentifierExpression.INSTANCE);
    }

    public Object evaluate(JsonLogicPrimitive<?> primitive, Object data) {
        switch (primitive.getPrimitiveType()) {
            case NUMBER:
                return ((JsonLogicNumber) primitive).getValue();
            default:
                return primitive.getValue();
        }
    }

    public Object evaluate(JsonLogicVariable variable, Object data) {
        return evaluate((JsonLogicPrimitive) variable.getKey(), data);
    }

    public List<Object> evaluate(JsonLogicArray array, Object data) throws JsonLogicEvaluationException {
        List<Object> values = new ArrayList<>(array.size());

        for (JsonLogicNode element : array) {
            values.add(evaluate(element, data));
        }
        return values;
    }

    @Override
    public List<JsonLogicExpression> getExpressions() {
        return expressions;
    }

}
