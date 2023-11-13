package tech.wetech.metacode.jsonlogic.evaluator.sql.expressions;

/**
 * @author cjbi
 */
public class SqlIdentifier extends SqlNode {
  private final String table;
  private final String field;

  public SqlIdentifier(String table, String field, String value) {
    super(value);
    this.table = table;
    this.field = field;
  }

  public String getTable() {
    return table;
  }

  public String getField() {
    return field;
  }
}
