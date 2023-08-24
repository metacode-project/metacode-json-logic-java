package tech.wetech.metacode.jsonlogic.evaluator.sql;

import tech.wetech.metacode.jsonlogic.ast.*;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicExpression;
import tech.wetech.metacode.jsonlogic.evaluator.expressions.*;
import tech.wetech.metacode.jsonlogic.evaluator.sql.expressions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cjbi
 * @date 2022/9/7
 */
public abstract class AbstractSqlLogicEvaluator implements JsonLogicEvaluator {

  protected final List<JsonLogicExpression> expressions = new ArrayList<>();

  protected AbstractSqlLogicEvaluator() {

    addOperation(LogicSqlExpression.AND);
    addOperation(LogicSqlExpression.OR);

    addOperation(ComparisonSqlExpression.EQ);
    addOperation(ComparisonSqlExpression.NE);
    addOperation(ComparisonSqlExpression.GT);
    addOperation(ComparisonSqlExpression.GTE);
    addOperation(ComparisonSqlExpression.LT);
    addOperation(ComparisonSqlExpression.LTE);

    addOperation(AggregationSqlExpression.COUNT);
    addOperation(AggregationSqlExpression.MAX);
    addOperation(AggregationSqlExpression.MIN);
    addOperation(AggregationSqlExpression.SUM);
    addOperation(AggregationSqlExpression.AVG);

    addOperation(MathSqlExpression.ADD);
    addOperation(MathSqlExpression.SUBTRACT);
    addOperation(MathSqlExpression.MULTIPLY);
    addOperation(MathSqlExpression.DIVIDE);
    addOperation(MathSqlExpression.MODULO);
    addOperation(MathSqlExpression.MIN);
    addOperation(MathSqlExpression.MAX);

    addOperation(ContainsSqlExpression.CONTAINS);
    addOperation(ContainsSqlExpression.NOT_CONTAINS);

    addOperation(TableFieldSqlExpression.INSTANCE);

    addOperation(RadioExpression.INSTANCE);
    addOperation(DatetimeExpression.INSTANCE);
    addOperation(MultipleExpression.INSTANCE);
    addOperation(AttachExpression.INSTANCE);
    addOperation(IdentifierExpression.INSTANCE);

    addOperation(CurrentDatetimeSqlExpression.INSTANCE);
  }

  public SqlRenderResult evaluate(JsonLogicNode root, String identifierQuoteString) throws JsonLogicEvaluationException {
    SqlRuntimeContext sqlRuntimeContext = new SqlRuntimeContext();
    sqlRuntimeContext.setIdentifierQuoteString(identifierQuoteString);
    IndexPlaceholderHandler placeholderHandler = new IndexPlaceholderHandler();
    sqlRuntimeContext.setPlaceholderHandler(placeholderHandler);

    return new IndexSqlRenderResult((String) evaluate((JsonLogicOperation) root, sqlRuntimeContext), placeholderHandler.getParameters());
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

  @Override
  public Object evaluate(JsonLogicOperation operation, Object data) throws JsonLogicEvaluationException {
    JsonLogicExpression expression = getExpression(operation.getOperator());
    return expression.evaluate(this, operation.getArguments(), data);
  }

}
