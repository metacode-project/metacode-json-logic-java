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
public class MathSqlExpression implements SqlExpression {

  public static final MathSqlExpression ADD = new MathSqlExpression("+", i -> new SqlNode(String.join(" + ", i)));
  public static final MathSqlExpression SUBTRACT = new MathSqlExpression("-", i -> new SqlNode(String.join(" - ", i)));
  public static final MathSqlExpression MULTIPLY = new MathSqlExpression("*", i -> new SqlNode(String.join(" * ", i)));
  public static final MathSqlExpression DIVIDE = new MathSqlExpression("/", i -> new SqlNode(String.join(" / ", i)));
  public static final MathSqlExpression MODULO = new MathSqlExpression("mod", i -> new SqlNode("mod(" + String.join(", ") + ")"), 2);
  public static final MathSqlExpression MIN = new MathSqlExpression("min", i -> new SqlNode("min(" + String.join(", ") + ")"));
  public static final MathSqlExpression MAX = new MathSqlExpression("max", i -> new SqlNode("max(" + String.join(", ") + ")"));

  private final String key;
  private final Function<List<String>, SqlNode> fn;
  private final int maxArguments;

  public MathSqlExpression(String key, Function<List<String>, SqlNode> fn) {
    this(key, fn, 0);
  }

  public MathSqlExpression(String key, Function<List<String>, SqlNode> fn, int maxArguments) {
    this.key = key;
    this.fn = fn;
    this.maxArguments = maxArguments;
  }

  @Override
  public String key() {
    return key;
  }

  @Override
  public <T extends JsonLogicEvaluator> SqlNode evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
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
