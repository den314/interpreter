package pl.desz.custom.interpreter;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

interface Expression {
    boolean evaluate(Context ctx);
}

class Constant implements Expression {
    private boolean value;

    public Constant(boolean value) {
        this.value = value;
    }

    @Override
    public boolean evaluate(Context ctx) {
        return value;
    }
}

class AndExpression implements Expression {

    private Expression ex1;
    private Expression ex2;

    public AndExpression(Expression ex1, Expression ex2) {
        this.ex1 = ex1;
        this.ex2 = ex2;
    }

    @Override
    public boolean evaluate(Context ctx) {
        return ex1.evaluate(ctx) && ex2.evaluate(ctx);
    }
}

class Context {

    private Map<String, Boolean> variables = new HashMap<>();

    public void assign(VariableExpression v, Boolean b) {
        Objects.requireNonNull(v);
        Objects.requireNonNull(b);

        variables.put(v.getName(), b);
    }

    public boolean lookup(String name) {
        return variables.get(name);
    }
}

class VariableExpression implements Expression {

    private String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean evaluate(Context ctx) {
        return ctx.lookup(name);
    }
}

class OrExpression implements Expression {

    private Expression ex1;
    private Expression ex2;

    public OrExpression(Expression ex1, Expression ex2) {
        this.ex1 = ex1;
        this.ex2 = ex2;
    }

    @Override
    public boolean evaluate(Context ctx) {
        return ex1.evaluate(ctx) || ex2.evaluate(ctx);
    }
}

public class Client {

    public static void main(String[] args) {

        Context ctx = new Context();

        VariableExpression x = new VariableExpression("X");
        VariableExpression y = new VariableExpression("Y");

        ctx.assign(x, false);
        ctx.assign(y, false);

        Expression exp = new AndExpression(new Constant(true), new OrExpression(x, y));

        boolean evaluate = exp.evaluate(ctx);
        System.out.println(evaluate);

    }
}