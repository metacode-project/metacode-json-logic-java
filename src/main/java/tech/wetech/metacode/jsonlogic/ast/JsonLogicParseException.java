package tech.wetech.metacode.jsonlogic.ast;

import tech.wetech.metacode.jsonlogic.JsonLogicException;

/**
 * @author cjbi
 * @date 2022/9/4
 */
public class JsonLogicParseException extends JsonLogicException {
  public JsonLogicParseException(String message) {
    super(message);
  }

  public JsonLogicParseException(Throwable cause) {
    super(cause);
  }

  public JsonLogicParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
