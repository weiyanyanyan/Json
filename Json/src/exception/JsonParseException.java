package exception;

import java.io.IOException;

/**
 *  @author MaiBenBen on 2017/11/15.
 */
public class JsonParseException extends IOException {
    public JsonParseException(String msg) {
        super(msg);
    }
}
