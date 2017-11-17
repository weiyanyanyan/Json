package parser;
import exception.JsonParseException;
import tokenizer.Token;
import tokenizer.TokenType;
import tokenizer.Tokenizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MaiBenBen on 2017/11/15.
 */
public class Parser {
    private final Tokenizer tokenizer;           //解析器中的词法分析对象类

    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public JObject object() throws JsonParseException {
        tokenizer.next(); //consume '{'
        Map<String, Value> map = new HashMap<>();
        if (isToken(TokenType.END_OBJ)) {
            tokenizer.next(); //consume '}'
            return new JObject(map);
        } else if (isToken(TokenType.STRING)) {
            map = key(map);
        }
        return new JObject(map);
    }

    private Map<String, Value> key(Map<String,Value> map) throws JsonParseException {
        String key = tokenizer.next().getValue();
        if (!isToken(TokenType.COLON)) {
            throw new JsonParseException("Invalid JSON input.");
        } else {
            tokenizer.next(); //consume ':'
            if (isPrimary()) {
                Value primary = new Primary(tokenizer.next().getValue());
                map.put(key, primary);
            } else if (isToken(TokenType.START_ARRAY)) {
                Value array = array();
                map.put(key, array);
            }
            if (isToken(TokenType.COMMA)) {
                tokenizer.next(); //consume ','
                if (isToken(TokenType.STRING)) {
                    map = key(map);
                }
            } else if (isToken(TokenType.END_OBJ)) {
                tokenizer.next(); //consume '}'
                return map;
            } else {
                throw new JsonParseException("Invalid JSON input.");
            }
        }
        return map;
    }

    private JArray array() throws JsonParseException {
        tokenizer.next(); //consume '['
        List<Json> list = new ArrayList<>();
        JArray array = null;
        if (isToken(TokenType.START_ARRAY)) {
            array = array();
            list.add(array);
            if (isToken(TokenType.COMMA)) {
                tokenizer.next(); //consume ','
                list = element(list);
            }
        } else if (isPrimary()) {
            list = element(list);
        } else if (isToken(TokenType.START_OBJ)) {
            list.add(object());
            while (isToken(TokenType.COMMA)) {
                tokenizer.next(); //consume ','
                list.add(object());
            }
        } else if (isToken(TokenType.END_ARRAY)) {
            tokenizer.next(); //consume ']'
            array =  new JArray(list);
            return array;
        }
        tokenizer.next(); //consume ']'
        array = new JArray(list);
        return array;
    }

    private List<Json> element(List<Json> list) throws JsonParseException {
        list.add(new Primary(tokenizer.next().getValue()));
        if (isToken(TokenType.COMMA)) {
            tokenizer.next(); //consume ','
            if (isPrimary()) {
                list = element(list);
            } else if (isToken(TokenType.START_OBJ)) {
                list.add(object());
            } else if (isToken(TokenType.START_ARRAY)) {
                list.add(array());
            } else {
                throw new JsonParseException("Invalid JSON input.");
            }
        } else if (isToken(TokenType.END_ARRAY)) {
            return list;
        } else {
            throw new JsonParseException("Invalid JSON input.");
        }
        return list;
    }

    public Json json() throws JsonParseException {
        TokenType type = tokenizer.peek(0).getType();
        if (null == type) {
            throw new JsonParseException("Invalid JSON input.");
        } else switch (type) {
            case START_ARRAY:
                return array();
            case START_OBJ:
                return object();
            default:
                throw new JsonParseException("Invalid JSON input.");
        }
    }

    private boolean isToken(TokenType tokenType) {
        Token t = tokenizer.peek(0);
        return t.getType() == tokenType;
    }

    private boolean isToken(String name) {
        Token t = tokenizer.peek(0);
        return t.getValue().equals(name);
    }

    private boolean isPrimary() {
        TokenType type = tokenizer.peek(0).getType();
        return type == TokenType.BOOLEAN || type == TokenType.NULL  ||
                type == TokenType.NUMBER || type == TokenType.STRING;
    }

    public Json parse() throws Exception {
        Json result = json();
        return result;
    }
//
//    public static JObject parseJSONObject(String s) throws Exception {
//        Tokenizer tokenizer = new Tokenizer(new BufferedReader(new StringReader(s)));
//        tokenizer.tokenize();
//        Parser parser = new Parser(tokenizer);
//        return parser.object();
//    }
//
//    public static JArray parseJSONArray(String s) throws Exception {
//        Tokenizer tokenizer = new Tokenizer(new BufferedReader(new StringReader(s)));
//        tokenizer.tokenize();
//        Parser parser = new Parser(tokenizer);
//        return parser.array();
//    }


}
