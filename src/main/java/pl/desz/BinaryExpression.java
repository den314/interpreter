package pl.desz;

public class BinaryExpression implements Expression {

    private int value;

    public BinaryExpression(int value) {
        this.value = value;
    }

    public String interpret(InterpreterContext ic) {
        return ic.intToBin(value);
    }
}
