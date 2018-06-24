package pl.desz;

public class HexExpression implements Expression {

    private int value;

    public HexExpression(int value) {
        this.value = value;
    }

    public String interpret(InterpreterContext ic) {
        return ic.intToHex(value);
    }
}
