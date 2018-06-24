package pl.desz.sql;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Context {

    private static final Predicate<String> matchAnyString = s -> s.length() > 0;

    private static Map<String, List<String>> people = new HashMap<>();
    private static Map<String, List<String>> cars = new HashMap<>();

    static {
        List<String> list = Arrays.asList("Denis", "Damian", "Andrzej");
        people.put("name", list);

        list = Arrays.asList("Kowalski", "Jarzeba", "Doroba");
        people.put("surname", list);

        list = Arrays.asList("Tesla", "Porsche", "Ferrari");
        cars.put("mark", list);

        list = Arrays.asList("S", "macan", "458");
        cars.put("model", list);
    }

    private String column;
    private String table;
    private Predicate<String> filter = s -> s.equalsIgnoreCase("");

    public void setColumn(String column) {
        this.column = column;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public void setFilter(Predicate<String> filter) {
        this.filter = filter;
    }

    public void clear() {
        column = "";
        filter = matchAnyString;
    }

    public List<String> search() {

        List<String> result = new ArrayList<>();

        switch (table) {
            case "people":
                result = people.entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().equalsIgnoreCase(column))
                        .flatMap(entry -> Stream.of(entry.getValue()))
                        .flatMap(Collection::stream)
                        .filter(filter)
                        .collect(Collectors.toList());
                break;
            case "cars":
                result = cars.entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().equalsIgnoreCase(column))
                        .flatMap(entry -> Stream.of(entry.getValue()))
                        .flatMap(Collection::stream)
                        .filter(filter)
                        .collect(Collectors.toList());
        }
        return result;
    }
}

interface Expression {
    List<String> interpret(Context ctx);
}

class Select implements Expression {

    private String column;
    private From from;

    public Select(String column, From from) {
        this.column = column;
        this.from = from;
    }

    @Override
    public List<String> interpret(Context ctx) {
        ctx.setColumn(column);
        return from.interpret(ctx);
    }
}

class From implements Expression {

    private String table;
    private Where where;

    public From(String table) {
        this.table = table;
    }

    public From(String table, Where where) {
        this.table = table;
        this.where = where;
    }

    @Override
    public List<String> interpret(Context ctx) {
        ctx.setTable(table);
        if (where == null) {
            return ctx.search();
        }
        return where.interpret(ctx);
    }
}

class Where implements Expression {

    private Predicate<String> filter;

    public Where(Predicate<String> filter) {
        this.filter = filter;
    }

    @Override
    public List<String> interpret(Context ctx) {
        ctx.setFilter(filter);
        return ctx.search();
    }
}

public class Main {

    public static void main(String[] args) {

        Expression query = new Select("mark", new From("cars", new Where(s -> s.toLowerCase().startsWith("t"))));
        Context ctx = new Context();
        List<String> result = query.interpret(ctx);
        System.out.println(result);

        ctx.clear();
        Expression query2 = new Select("surname", new From("people"));
        List<String> result2 = query2.interpret(ctx);
        System.out.println(result2);
    }
}
