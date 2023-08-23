package tech.wetech.metacode.jsonlogic.evaluator.sql.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicNode;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author cjbi
 */
public class MathSqlClauseExpression implements SqlClauseExpression {

  public static final MathSqlClauseExpression ADD = new MathSqlClauseExpression("+", i -> new SqlIdentifier(String.join(" + ", i)));
  public static final MathSqlClauseExpression SUBTRACT = new MathSqlClauseExpression("-", i -> new SqlIdentifier(String.join(" - ", i)));
  public static final MathSqlClauseExpression MULTIPLY = new MathSqlClauseExpression("*", i -> new SqlIdentifier(String.join(" * ", i)));
  public static final MathSqlClauseExpression DIVIDE = new MathSqlClauseExpression("/", i -> new SqlIdentifier(String.join(" / ", i)));
  public static final MathSqlClauseExpression MODULO = new MathSqlClauseExpression("mod", i -> new SqlIdentifier("mod(" + String.join(", ") + ")"), 2);
  public static final MathSqlClauseExpression MIN = new MathSqlClauseExpression("min", i -> new SqlIdentifier("min(" + String.join(", ") + ")"));
  public static final MathSqlClauseExpression MAX = new MathSqlClauseExpression("max", i -> new SqlIdentifier("max(" + String.join(", ") + ")"));

  private final String key;
  private final Function<List<String>, SqlIdentifier> fn;
  private final int maxArguments;

  public MathSqlClauseExpression(String key, Function<List<String>, SqlIdentifier> fn) {
    this(key, fn, 0);
  }

  public MathSqlClauseExpression(String key, Function<List<String>, SqlIdentifier> fn, int maxArguments) {
    this.key = key;
    this.fn = fn;
    this.maxArguments = maxArguments;
  }

  @Override
  public String key() {
    return key;
  }

  @Override
  public <T extends JsonLogicEvaluator> SqlIdentifier evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
    if (maxArguments > 0 && arguments.size() != maxArguments) {
      throw new JsonLogicEvaluationException(key + "expressions expect exactly " + arguments + " arguments");
    }
    List<String> list = new ArrayList<>();
    for (JsonLogicNode element : arguments) {
      list.add(evaluator.evaluate(element, data).toString());
    }
    return fn.apply(list);
  }
}
