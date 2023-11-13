package tech.wetech.metacode.jsonlogic.evaluator.sql.expressions;

/**
 * sqlClause 标识符
 *
 * @author cjbi
 */
public class SqlNode {

  private final String value;

  public SqlNode(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}
