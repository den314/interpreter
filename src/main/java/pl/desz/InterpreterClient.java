package pl.desz;

public class InterpreterClient {

    private InterpreterContext ctx;

    public InterpreterClient(InterpreterContext ctx) {
        this.ctx = ctx;
    }

    public String interpret(String input) {
        Expression exp;
        if (input.startsWith("16")) {
            exp = new HexExpression(Integer.parseInt(input.split(" ")[1]));
        } else if (input.startsWith("2")) {
            exp = new BinaryExpression(Integer.parseInt(input.split(" ")[1]));
        } else {
            throw new RuntimeException("Unknown format of the input, possibilities: 16 (hex), 2 (bin)");
        }
        return exp.interpret(ctx);
    }

    public static void main(String[] args) {

        InterpreterClient client = new InterpreterClient(new InterpreterContext());
        System.out.println(client.interpret("16 10"));
        System.out.println(client.interpret("16 11"));
        System.out.println(client.interpret("2 10"));
        System.out.println(client.interpret("2 5"));


    }
}
