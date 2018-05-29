import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

class Calculator extends Converter {

    //вычисление значения функции
    @Override
    Double calc(List<String> postfix) {
        Deque<Double> stack = new ArrayDeque<>();
        for (String x : postfix) {
            Double a, b;
            switch (x) {
                case "√":
                    stack.push(Math.sqrt(stack.pop()));
                    break;
                case "%":
                    b = stack.pop();
                    a = stack.pop();
                    stack.push(a/100*b);
                    break;
                case "^":
                    a = stack.pop(); b = stack.pop();
                    stack.push(Math.pow(b, a));
                    break;
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    a = stack.pop();
                    b = stack.pop();
                    stack.push(b - a);
                    break;
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/":
                    a = stack.pop();
                    b = stack.pop();
                    if (a == 0){throw new ArithmeticException("Division by zero");}
                    else {
                        stack.push(b / a);}
                    break;
                case "u-":
                    stack.push(-stack.pop());
                    break;
                case "sin":
                    stack.push(Math.sin(Math.toRadians(stack.pop())));
                    break;
                case "cos":
                    stack.push(Math.cos(Math.toRadians(stack.pop())));
                    break;
                default:
                    stack.push(Double.valueOf(x));
                    break;
            }
        }
        return stack.pop();
    }
}
