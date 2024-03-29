package tech.wetech.metacode.jsonlogic;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicParser;
import tech.wetech.metacode.jsonlogic.evaluator.*;
import tech.wetech.metacode.jsonlogic.evaluator.sql.IndexSqlRenderResult;
import tech.wetech.metacode.jsonlogic.evaluator.sql.NamedSqlLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.sql.NamedSqlRenderResult;
import tech.wetech.metacode.jsonlogic.evaluator.sql.SqlRenderLogicEvaluator;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cjbi
 * @date 2022/9/6
 */
public class JsonLogic {

  private final Map<Class<? extends JsonLogicEvaluator>, JsonLogicEvaluator> evaluators = new HashMap<>();

  public JsonLogic() {
    evaluators.put(StringLogicEvaluator.class, new StringLogicEvaluator());
    evaluators.put(BooleanJsonLogicEvaluator.class, new BooleanJsonLogicEvaluator());
    evaluators.put(NumberJsonLogicEvaluator.class, new NumberJsonLogicEvaluator());
    evaluators.put(NamedSqlLogicEvaluator.class, new NamedSqlLogicEvaluator());
    evaluators.put(SqlRenderLogicEvaluator.class, new SqlRenderLogicEvaluator());
  }

  public static boolean truthy(Object value) {
    if (value == null) {
      return false;
    }

    if (value instanceof Boolean) {
      return (boolean) value;
    }

    if (value instanceof Number) {
      if (value instanceof Double) {
        Double d = (Double) value;

        if (d.isNaN()) {
          return false;
        } else if (d.isInfinite()) {
          return true;
        }
      }

      if (value instanceof Float) {
        Float f = (Float) value;

        if (f.isNaN()) {
          return false;
        } else if (f.isInfinite()) {
          return true;
        }
      }

      return ((Number) value).doubleValue() != 0.0;
    }

    if (value instanceof String) {
      return !((String) value).isEmpty();
    }

    if (value instanceof Collection) {
      return !((Collection) value).isEmpty();
    }

    if (value.getClass().isArray()) {
      return Array.getLength(value) > 0;
    }

    return true;
  }

  public static boolean isEligible(Object data) {
    return data != null && (data instanceof Iterable || data.getClass().isArray());
  }

  public void addBooleanOperation(JsonLogicExpression expression) {
    evaluators.get(BooleanJsonLogicEvaluator.class).addOperation(expression);
  }

  public void addNumberOperation(JsonLogicExpression expression) {
    evaluators.get(NumberJsonLogicEvaluator.class).addOperation(expression);
  }

  public void addIndexSqlOperation(JsonLogicExpression expression) {
    evaluators.get(SqlRenderLogicEvaluator.class).addOperation(expression);
  }

  public void addNamedSqlOperation(JsonLogicExpression expression) {
    evaluators.get(NamedSqlLogicEvaluator.class).addOperation(expression);
  }

  public boolean evaluateBoolean(String json, Object data) throws JsonLogicException {
    return (boolean) evaluators.get(BooleanJsonLogicEvaluator.class).evaluate(JsonLogicParser.parse(json), data);
  }

  public Number evaluateNumber(String json, Object data) throws JsonLogicException {
    return (Number) evaluators.get(NumberJsonLogicEvaluator.class).evaluate(JsonLogicParser.parse(json), data);
  }

  public String evaluateString(String json, Object data) throws JsonLogicException {
    return (String) evaluators.get(StringLogicEvaluator.class).evaluate(JsonLogicParser.parse(json), data);
  }

  public NamedSqlRenderResult evaluateNamedSql(String json) throws JsonLogicException {
    return evaluateNamedSql(json, "");
  }

  public NamedSqlRenderResult evaluateNamedSql(String json, String identifierQuoteString) throws JsonLogicException {
    return ((NamedSqlLogicEvaluator) evaluators.get(NamedSqlLogicEvaluator.class)).evaluate(JsonLogicParser.parse(json), identifierQuoteString);
  }

  public IndexSqlRenderResult evaluateIndexSql(String json) throws JsonLogicException {
    return evaluateIndexSql(json, "");
  }

  public IndexSqlRenderResult evaluateIndexSql(String json, String identifierQuoteString) throws JsonLogicException {
    return ((SqlRenderLogicEvaluator) evaluators.get(SqlRenderLogicEvaluator.class)).evaluate(JsonLogicParser.parse(json), identifierQuoteString);
  }

}
