package pl.desz.sql;

import java.util.List;


public class InterpreterDemo {

    public static void main(String[] args) {

        Expression query = new Select("name", new From("people", new Where(s -> s.toLowerCase().startsWith("d"))));
        Context ctx = new Context();

        List<String> result = query.interpret(ctx);
        System.out.println(result);

        ctx.clear();
        Expression query2 = new Select("surname", new From("people"));
        List<String> result2 = query2.interpret(ctx);
        System.out.println(result2);

        ctx.clear();
        Expression query3 = new Select("*", new From("people"));
        List<String> result3 = query3.interpret(ctx);
        System.out.println(result3);
    }
}
