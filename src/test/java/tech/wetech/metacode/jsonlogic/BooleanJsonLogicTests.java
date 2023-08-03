package tech.wetech.metacode.jsonlogic;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author cjbi
 * @date 2022/9/4
 */
public class BooleanJsonLogicTests {

  private static final JsonLogic jsonLogic = new JsonLogic();

  @Test
  void testOr() throws JsonLogicException {
    String json = """
      { "or": [{ ">": [1, 2] }, { "<": [1, 2] }] }
      """;
    assertTrue(jsonLogic.evaluateBoolean(json, null));
  }

  @Test
  void testAnd() throws JsonLogicException {
    String json = """
      { "and": [{ ">": [3, 2] }, { "<": [1, 2] }] }
      """;
    assertTrue(jsonLogic.evaluateBoolean(json, null));
  }

  @Test
  void testAndOr() throws JsonLogicException {
    String json = """
      {
        "and": [
          { "and": [{ ">=": [3, 5] }, { "<=": [1, 2] }] },
          { "or": [{ ">": [3, 2] }, { "<": [1, 2] }] }
        ]
      }
      """;
    assertFalse(jsonLogic.evaluateBoolean(json, null));
  }

  @Test
  void testVar() throws JsonLogicException {
    Map<String, Object> data = new HashMap<>();
    data.put("b", 10);
    data.put("c", Collections.singletonMap("cc", 20));
    String json = """
      {
        "and": [
          { ">": [{ "var": ["a", 3] }, 2] },
          { "<": [1, { "var": "b" }] },
          { "<": [{ "var": "c.cc" }, 21] }
        ]
      }
      """;
    assertTrue(jsonLogic.evaluateBoolean(json, data));
  }

  @Test
  void testTableField() throws JsonLogicException {
    Map<String, Object> data = new HashMap<>();
    data.put("name", "jack");
    data.put("age", 20);
    String json = """
          {
            "and": [
              { "==": [{ "table_field": ["user", "name"] }, "jack"] },
              { "<": [18, { "table_field": ["user", "age"] }] },
              { "<": [{ "table_field": ["user", "age"] }, 21] }
            ]
          }
      """;
    assertTrue(jsonLogic.evaluateBoolean(json, Map.of("user", data)));

  }

}
