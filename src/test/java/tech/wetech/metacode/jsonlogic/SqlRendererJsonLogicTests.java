package tech.wetech.metacode.jsonlogic;

import org.junit.jupiter.api.Test;
import tech.wetech.metacode.jsonlogic.evaluator.sql.IndexSqlRenderResult;
import tech.wetech.metacode.jsonlogic.evaluator.sql.NamedSqlRenderResult;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author cjbi
 * @date 2022/9/5
 */
public class SqlRendererJsonLogicTests {

    private static final JsonLogic jsonLogic = new JsonLogic();

    @Test
    void testIndex() throws JsonLogicException {
        String json = """
            {
              "and": [
                { ">": [{ "table_field": ["user", "id"] }, 2] },
                { "==": ["jack", { "table_field": ["user", "name"] }] },
                { "<": [{ "table_field": ["user", "age"] }, 21] }
              ]
            }
            """;
        IndexSqlRenderResult renderResult = jsonLogic.evaluateIndexSql(json);
        assertNotNull(renderResult.whereClause());
        assertEquals(3, renderResult.args().length);
    }

    @Test
    void testNamed() throws JsonLogicException {
        String json = """
            {
              "or": [
                {
                  "and": [
                    { ">": [{ "table_field": ["user", "id"] }, 2] },
                    { "==": ["jack", { "table_field": ["user", "name"] }] },
                    { "<": [{ "table_field": ["user", "age"] }, 21] }
                  ]
                },
                {
                  "and": [
                    { ">": [{ "table_field": ["user", "id"] }, 2] },
                    { "==": ["jack", { "table_field": ["user", "name"] }] },
                    { "<": [{ "table_field": ["user", "age"] }, 21] }
                  ]
                },
                {
                  "and": [
                    { ">": [{ "var": ["id", 3] }, 2] },
                    { "==": ["mark", { "table_field": ["user", "name"] }] },
                    { "==": [1, 1] }
                  ]
                }
              ]
            }
            """;
        NamedSqlRenderResult renderResult = jsonLogic.evaluateNamedSql(json);
        assertNotNull(renderResult.whereClause());
        assertEquals(11, renderResult.args().size());
    }

    @Test
    void testPrimitiveContains() throws JsonLogicException {
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
        NamedSqlRenderResult renderResult = jsonLogic.evaluateNamedSql(json);
        assertEquals(Map.of("xuesheng_created_by_0", 2.0,
            "xuesheng_created_by_1", 3.0,
            "xuesheng_created_by_2", 4.0,
            "xuesheng_created_by_3", 5.0,
            "xuesheng_created_by_4", 6.0), renderResult.args());
        assertEquals("xuesheng.created_by in (:xuesheng_created_by_0, :xuesheng_created_by_1, :xuesheng_created_by_2, :xuesheng_created_by_3, :xuesheng_created_by_4) ",
            renderResult.whereClause()
        );
    }

    @Test
    void testVar() throws JsonLogicException {
        String json = """
            { "==": [{ "var": "__flow.status" }, "ACTIVE"] }
            """;
        NamedSqlRenderResult renderResult = jsonLogic.evaluateNamedSql(json);
        assertEquals(Map.of("__flow_status_0", "ACTIVE"), renderResult.args());
        assertEquals(" __flow.status = :__flow_status_0", renderResult.whereClause());
    }


}
