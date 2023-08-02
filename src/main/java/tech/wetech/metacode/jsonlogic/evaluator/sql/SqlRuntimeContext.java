package tech.wetech.metacode.jsonlogic.evaluator.sql;

/**
 * @author cjbi
 */
public class SqlRuntimeContext {

  private String identifierQuoteString;
  private PlaceholderHandler placeholderHandler;

  public void setIdentifierQuoteString(String identifierQuoteString) {
    this.identifierQuoteString = identifierQuoteString;
  }

  public String quoteIdentifier(String identifier) {
    return identifierQuoteString + identifier + identifierQuoteString;
  }

  public PlaceholderHandler getPlaceholderHandler() {
    return placeholderHandler;
  }

  public void setPlaceholderHandler(PlaceholderHandler placeholderHandler) {
    this.placeholderHandler = placeholderHandler;
  }
}
