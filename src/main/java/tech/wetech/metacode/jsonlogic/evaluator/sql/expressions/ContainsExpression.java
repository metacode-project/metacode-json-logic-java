package tech.wetech.metacode.jsonlogic.evaluator.sql.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.SqlRuntimeContext;
import tech.wetech.metacode.jsonlogic.evaluator.sql.PlaceholderHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cjbi
 * @date 2022/11/6
 */
public class ContainsExpression implements SqlRenderExpression {

    public static final ContainsExpression CONTAINS = new ContainsExpression(false);

    public static final ContainsExpression NOT_CONTAINS = new ContainsExpression(true);

    private final boolean isNot;

    public ContainsExpression(boolean isNot) {
        this.isNot = isNot;
    }

    @Override
    public String key() {
        return isNot ? "not_contains" : "contains";
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends JsonLogicEvaluator> Object evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
        SqlRuntimeContext sqlRuntimeContext = (SqlRuntimeContext) data;
        PlaceholderHandler placeholderHandler = sqlRuntimeContext.getPlaceholderHandler();
        Object left = evaluator.evaluate(arguments.get(0), data);
        Object right = evaluator.evaluate(arguments.get(1), data);
        if (right instanceof List<?> list) {
            if (list.isEmpty()) {
                return FALSE;
            }
            if (list.stream().allMatch(i -> i instanceof String || i instanceof Number)) {
                String s = left + " in" + list.stream()
                    .map(i -> placeholderHandler.handle(left.toString(), i))
                    .collect(Collectors.joining(", ", " (", ") "));
                return s;
            }
            return list.stream()
                .map(element -> getSingle(placeholderHandler, left, right, isTableFieldExpression(arguments.get(1))))
                .collect(Collectors.joining(" and ", " (", ") "));
        }
        return getSingle(placeholderHandler, left, right, isTableFieldExpression(arguments.get(1)));
    }

    public String getSingle(PlaceholderHandler placeholderHandler, Object left, Object right, boolean rightIsTableField) {
        StringBuilder sb = new StringBuilder(" ");
        sb.append(left);
        sb.append(" ");
        sb.append(isNot ? "not like" : "like");
        sb.append(" ");
        sb.append("concat('%', concat(").append(rightIsTableField ? right : placeholderHandler.handle(left.toString(), right)).append(",'%'))");
        return sb.toString();
    }

}
