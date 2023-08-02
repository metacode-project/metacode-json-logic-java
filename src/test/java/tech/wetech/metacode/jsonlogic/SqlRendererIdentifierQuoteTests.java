package tech.wetech.metacode.jsonlogic;

import org.junit.jupiter.api.Test;
import tech.wetech.metacode.jsonlogic.evaluator.sql.IndexSqlRenderResult;
import tech.wetech.metacode.jsonlogic.evaluator.sql.NamedSqlRenderResult;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author cjbi
 */
public class SqlRendererIdentifierQuoteTests {

  private static final JsonLogic jsonLogic = new JsonLogic();

  @Test
  void testIndexSql() throws Exception {
    String json = """
      {
        "and": [
          { ">": [{ "table_field": ["user", "id"] }, 2] },
          { "==": ["jack", { "table_field": ["user", "name"] }] },
          { "<": [{ "table_field": ["user", "age"] }, 21] }
        ]
      }
      """;
    IndexSqlRenderResult renderResult = jsonLogic.evaluateIndexSql(json, "`");
    assertEquals(
      " ( `user`.`id` > ? and  ? = `user`.`name` and  `user`.`age` < ? )",
      renderResult.whereClause());
    assertEquals(3, renderResult.args().length);
  }

  @Test
  void testNamedSql() throws Exception {
    String json = """
      {
        "contains": [
          {
            "table_field": ["xuesheng", "created_by"]
          },
          [2, 3, 4, 5, 6]
        ]
      }
      """;
    NamedSqlRenderResult renderResult = jsonLogic.evaluateNamedSql(json, "\"");
    assertEquals(Map.of("xuesheng_created_by_0", 2.0,
      "xuesheng_created_by_1", 3.0,
      "xuesheng_created_by_2", 4.0,
      "xuesheng_created_by_3", 5.0,
      "xuesheng_created_by_4", 6.0), renderResult.args());
    assertEquals("\"xuesheng\".\"created_by\" in (:xuesheng_created_by_0, :xuesheng_created_by_1, :xuesheng_created_by_2, :xuesheng_created_by_3, :xuesheng_created_by_4) ",
      renderResult.whereClause()
    );
  }

}
