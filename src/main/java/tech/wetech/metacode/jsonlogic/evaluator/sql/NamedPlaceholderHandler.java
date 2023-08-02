package tech.wetech.metacode.jsonlogic.evaluator.sql;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cjbi
 * @date 2022/9/7
 */
public class NamedPlaceholderHandler implements PlaceholderHandler {
  private final Map<String, Object> parameters = new HashMap<>();
  private int placeholderIndex;

  @Override
  public String handle(String field, Object value) {
    String key = field.replace(".", "_")
      .replaceAll("[\"'`]", "")
      + "_" + placeholderIndex++;
    parameters.put(key, value);
    return ":" + key;
  }

  @Override
  public Map<String, Object> getParameters() {
    return parameters;
  }

}
