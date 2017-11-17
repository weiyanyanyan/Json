package parser;


/**
 * @author MaiBenBen on 2017/11/15.
 */
public class Primary implements Value,Json{
    private String value;

    public Primary(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }


    public Object value() {
        return value;
    }
}
