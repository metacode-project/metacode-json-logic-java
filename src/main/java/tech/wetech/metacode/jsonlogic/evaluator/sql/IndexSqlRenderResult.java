package tech.wetech.metacode.jsonlogic.evaluator.sql;

/**
 * @author cjbi
 * @date 2022/9/6
 */
public record IndexSqlRenderResult(String sqlClause, Object[] args) implements SqlRenderResult {

}
