package tech.wetech.metacode.jsonlogic.evaluator.sql;

/**
 * @author cjbi
 * @date 2022/9/6
 */
public interface SqlRenderResult {

  Object args();

  String sqlClause();

}
