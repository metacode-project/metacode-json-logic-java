package tech.wetech.metacode.jsonlogic.ast;

import java.time.LocalDateTime;

/**
 * @author cjbi
 */
public class JsonLogicCurrentDatetime implements JsonLogicNode {
  @Override
  public JsonLogicNodeType getType() {
    return JsonLogicNodeType.CURRENT_DATETIME;
  }

  public LocalDateTime now() {
    return LocalDateTime.now();
  }

}
