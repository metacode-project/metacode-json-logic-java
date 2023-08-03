package tech.wetech.metacode.jsonlogic.evaluator.sql.expressions;

/**
 * sql 标识符
 *
 * @author cjbi
 */
public class SqlIdentifier {

  private final String name;

  public SqlIdentifier(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
