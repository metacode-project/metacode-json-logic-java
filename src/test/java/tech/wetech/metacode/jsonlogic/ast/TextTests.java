package tech.wetech.metacode.jsonlogic.ast;

import org.junit.jupiter.api.Test;
import tech.wetech.metacode.jsonlogic.JsonLogic;
import tech.wetech.metacode.jsonlogic.JsonLogicException;
import tech.wetech.metacode.jsonlogic.evaluator.sql.NamedSqlRenderResult;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author cjbi
 * @date 2022/11/6
 */
public class TextTests {

  private static final JsonLogic jsonLogic = new JsonLogic();

  @Test
  void testTableField() throws JsonLogicException {
    String json = """
      {
        "==": [
          { "table_field": ["defaultvaluetest", "wenben1"] },
          { "table_field": ["defaultvaluetest", "wenben1"] }
        ]
      }
      """;
    NamedSqlRenderResult result = jsonLogic.evaluateNamedSql(json);
    assertEquals(" defaultvaluetest.wenben1 = defaultvaluetest.wenben1", result.sqlClause());
  }

  @Test
  void testContains() throws JsonLogicException {
    String json = """
      {
        "and": [
          {
            "contains": [{ "table_field": ["defaultvaluetest", "wenben1"] }, "二十大"]
          }
        ]
      }
      """;
    NamedSqlRenderResult result = jsonLogic.evaluateNamedSql(json);
    assertEquals(" ( defaultvaluetest.wenben1 like concat('%', concat(:defaultvaluetest_wenben1_0,'%')) )", result.sqlClause());
    assertTrue(result.args().values().contains("二十大"));
    assertTrue(jsonLogic.evaluateBoolean(json, Map.of("defaultvaluetest", Map.of("wenben1", "党的二十大"))));
  }

  @Test
  void testNotContains() throws JsonLogicException {
    String json = """
      {
        "and": [
          {
            "not_contains": [{ "table_field": ["defaultvaluetest", "wenben1"] }, "二十大"]
          }
        ]
      }
      """;
    NamedSqlRenderResult result = jsonLogic.evaluateNamedSql(json);
    assertEquals(" ( defaultvaluetest.wenben1 not like concat('%', concat(:defaultvaluetest_wenben1_0,'%')) )", result.sqlClause());
    assertTrue(result.args().containsValue("二十大"));
    assertFalse(jsonLogic.evaluateBoolean(json, Map.of("defaultvaluetest", Map.of("wenben1", "党的二十大"))));
  }

}
