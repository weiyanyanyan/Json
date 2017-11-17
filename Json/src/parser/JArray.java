package parser;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MaiBenBen on 2017/11/15.
 */
public class JArray   implements Json,Value{
    private List<Json> list = new ArrayList<>();

    public JArray(List<Json> list) {
        this.list = list;
    }

    public int length() {
        return list.size();
    }

    public void add(Json element) {
        list.add(element);
    }

    public Object get(int i) {
        return list.get(i);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (int i =0; i < list.size(); i++) {
            sb.append(list.get(i).toString());
            if (i != list.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(" ]");
        return sb.toString();
    }
    public Object value() {
        return this;
    }
}
