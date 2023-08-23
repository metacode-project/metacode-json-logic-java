package tech.wetech.metacode.jsonlogic.ast;

import org.junit.jupiter.api.Test;
import tech.wetech.metacode.jsonlogic.JsonLogic;
import tech.wetech.metacode.jsonlogic.JsonLogicException;
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

  private static final JsonLogic jsonLogic = new JsonLogic();

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
    NamedSqlRenderResult result = jsonLogic.evaluateNamedSql(json);
    assertEquals(" (defaultvaluetest.duoxuan2 in (:defaultvaluetest_duoxuan2_0, :defaultvaluetest_duoxuan2_1)  )", result.sqlClause());
    assertTrue(jsonLogic.evaluateBoolean(json, Map.of("defaultvaluetest", Map.of("duoxuan2", Arrays.asList("天空", "大地", "海洋")))));
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
    NamedSqlRenderResult result = jsonLogic.evaluateNamedSql(json);
    assertEquals(" (defaultvaluetest.duoxuan2 in (:defaultvaluetest_duoxuan2_0, :defaultvaluetest_duoxuan2_1)  )", result.sqlClause());
    assertTrue(jsonLogic.evaluateBoolean(json, Map.of("defaultvaluetest", Map.of("duoxuan2", Map.of("multiple", Arrays.asList("天空", "大地", "海洋"))))));
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
    NamedSqlRenderResult result = jsonLogic.evaluateNamedSql(json);
    assertEquals(" ( defaultvaluetest.duoxuan2 like concat('%', concat(:defaultvaluetest_duoxuan2_0,'%')) )", result.sqlClause());
    assertTrue(jsonLogic.evaluateBoolean(json, Map.of("defaultvaluetest", Map.of("duoxuan2", Arrays.asList("天空", "大地", "海洋")))));
  }

}
