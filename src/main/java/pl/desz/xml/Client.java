package pl.desz.xml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

interface Expression {
    String interpret(Context ctx);
}

class Empty implements Expression {

    @Override
    public String interpret(Context ctx) {
        return "";
    }
}

class Tag implements Expression {

    private String tag;
    private Expression exp;

    public Tag(String tag) {
        this.tag = tag;
        exp = new Empty();
    }

    public Tag(String tag, Expression exp) {
        this.tag = tag;
        this.exp = exp;
    }

    @Override
    public String interpret(Context ctx) {
        return exp.interpret(ctx);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tag{");
        sb.append("tag='").append(tag).append('\'');
        sb.append(", exp=").append(exp);
        sb.append('}');
        return sb.toString();
    }
}

class Value implements Expression {

    private String value;

    public Value(String value) {
        this.value = value;
    }

    @Override
    public String interpret(Context ctx) {
        return value;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Value{");
        sb.append("value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

class Context {

}

public class Client {

    public static void main(String[] args) throws IOException {

        String file = new String(Files.readAllBytes(Paths.get("interpreter/src/main/resources/sample.html")));
        List<Expression> tokens = parse(file);

        tokens.forEach(System.out::println);

//
//        Tag openPeople = new Tag("people", new Tag("person", new Tag("name", new Value("denis"))));
//        String interpret = openPeople.interpret(new Context());
//        System.out.println(interpret);
    }

    private static List<Expression> parse(String file) {
        List<Expression> tokens = new ArrayList<>();

        for (int i = 0; i < file.length(); i++) {
            StringBuilder sb = new StringBuilder();
            switch (file.charAt(i)) {
                case '<':
                    ++i;
                    for (int j = 0; file.charAt(i) != '>'; j++, i++) {
                        sb.append(file.charAt(i));
                    }
                    tokens.add(new Tag(sb.toString()));
                    break;
                case '/':
                    for (int j = 0; file.charAt(i) != '>'; j++) {
                        i++;
                        sb.append(file.charAt(i));
                    }
                    i++;
                    break;
                default:
                    for (int j = 0; file.charAt(i) != '<'; j++, i++) {
                        sb.append(file.charAt(i));
                    }
                    tokens.add(new Value(sb.toString()));
            }
        }

        return tokens;
    }
}
