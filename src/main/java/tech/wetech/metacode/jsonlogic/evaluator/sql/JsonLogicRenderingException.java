package tech.wetech.metacode.jsonlogic.evaluator.sql;

import tech.wetech.metacode.jsonlogic.JsonLogicException;

/**
 * @author cjbi
 * @date 2022/9/5
 */
public class JsonLogicRenderingException extends JsonLogicException {

  public JsonLogicRenderingException(String message) {
    super(message);
  }

  public JsonLogicRenderingException(String message, Throwable cause) {
    super(message, cause);
  }

  public JsonLogicRenderingException(Throwable cause) {
    super(cause);
  }
}
