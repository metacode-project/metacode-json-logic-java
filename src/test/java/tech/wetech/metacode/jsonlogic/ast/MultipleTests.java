package tech.wetech.metacode.jsonlogic.ast;

import org.junit.jupiter.api.Test;
import tech.wetech.metacode.jsonlogic.JsonLogic;
import tech.wetech.metacode.jsonlogic.JsonLogicException;
import tech.wetech.metacode.jsonlogic.evaluator.BooleanLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.NamedSqlRenderLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.sql.NamedSqlRenderResult;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author cjbi
 * @date 2022/11/6
 */
public class MultipleTests {

    @Test
    void testContains() throws JsonLogicException {
        String json = """
            {
              "and": [
                {
                  "contains": [
                    { "table_field": ["defaultvaluetest", "duoxuan2"] },
                    ["天空","大地"]
                  ]
                }
              ]
            }
            """;
        NamedSqlRenderResult result = JsonLogic.apply(json, NamedSqlRenderLogicEvaluator::new).evaluate();
        assertEquals(" ( ( defaultvaluetest.duoxuan2 like concat('%', concat(:defaultvaluetest_duoxuan2_0,'%')) and  defaultvaluetest.duoxuan2 like concat('%', concat(:defaultvaluetest_duoxuan2_1,'%')))  )", result.whereClause());
        assertTrue(JsonLogic.apply(json, BooleanLogicEvaluator::new).evaluate(Map.of("defaultvaluetest", Map.of("duoxuan2", Arrays.asList("天空", "大地", "海洋")))));
    }

    @Test
    void testContains2() throws JsonLogicException {
        String json = """
            {
              "and": [
                {
                  "contains": [
                    { "table_field": ["defaultvaluetest", "duoxuan2"] },
                    { "multiple": ["天空", "大地"] }
                  ]
                }
              ]
            }
            """;
        NamedSqlRenderResult result = JsonLogic.apply(json, NamedSqlRenderLogicEvaluator::new).evaluate();
        assertEquals(" ( ( defaultvaluetest.duoxuan2 like concat('%', concat(:defaultvaluetest_duoxuan2_0,'%')) and  defaultvaluetest.duoxuan2 like concat('%', concat(:defaultvaluetest_duoxuan2_1,'%')))  )", result.whereClause());
        assertTrue(JsonLogic.apply(json, BooleanLogicEvaluator::new).evaluate(Map.of("defaultvaluetest", Map.of("duoxuan2", Map.of("multiple", Arrays.asList("天空", "大地", "海洋"))))));
    }

    @Test
    void testContains3() throws JsonLogicException {
        String json = """
            {
              "and": [
                {
                  "contains": [
                    { "table_field": ["defaultvaluetest", "duoxuan2"] },
                    "海洋"
                  ]
                }
              ]
            }
            """;
        NamedSqlRenderResult result = JsonLogic.apply(json, NamedSqlRenderLogicEvaluator::new).evaluate();
        assertEquals(" ( defaultvaluetest.duoxuan2 like concat('%', concat(:defaultvaluetest_duoxuan2_0,'%')) )", result.whereClause());
        assertTrue(JsonLogic.apply(json, BooleanLogicEvaluator::new).evaluate(Map.of("defaultvaluetest", Map.of("duoxuan2", Arrays.asList("天空", "大地", "海洋")))));
    }

}
