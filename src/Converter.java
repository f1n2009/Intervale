import java.util.*;

abstract class Converter {

    private String operators = "+-*/%^√";
    private String delimiters = "() " + operators;

    //проверка скобок
    private boolean isDelimiter(String token) {
        if (token.length() != 1) return false;
        for (int i = 0; i < delimiters.length(); i++) {
            if (token.charAt(0) == delimiters.charAt(i)) return true;
        }
        return false;
    }

    //проверка оператора
    private boolean isOperator(String token) {
        if (token.equals("u-")) return true;
        for (int i = 0; i < operators.length(); i++) {
            if (token.charAt(0) == operators.charAt(i)) return true;
        }
        return false;
    }

    //проверка функции
    private boolean isFunction(String token) {
        return token.equals("√") || token.equals("sin") || token.equals("cos") || token.equals("^");
    }

    //установка приоритета операторов
    private int priority(String token) {
        switch (token) {
            case "(":
                return 1;
            case "+":
            case "-":
                return 2;
            case "*":
            case "/":
                return 3;
        }
        return 4;
    }

    //приведение выражения к обрастной польской записи
    List<String> parse(String infix) {
        List<String> postfix = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        StringTokenizer tokenizer = new StringTokenizer(infix, delimiters, true);
        String prev = "";
        String curr;
        while (tokenizer.hasMoreTokens()) {
            curr = tokenizer.nextToken();
            if (!tokenizer.hasMoreTokens() && isOperator(curr)) {
                System.out.println("Некорректное выражение.");
                return postfix;
            }
            if (curr.equals(" ")) continue;
            if (isFunction(curr)) stack.push(curr);
            else if (isDelimiter(curr)) {
                switch (curr) {
                    case "(":
                        stack.push(curr);
                        break;
                    case ")":
                        while (!stack.peek().equals("(")) {
                            postfix.add(stack.pop());
                            if (stack.isEmpty()) {
                                System.out.println("Скобки не согласованы.");
                                return postfix;
                            }
                        }
                        stack.pop();
                        if (!stack.isEmpty() && isFunction(stack.peek())) {
                            postfix.add(stack.pop());
                        }
                        break;
                    default:
                        if (curr.equals("-") && (prev.equals("") || (isDelimiter(prev) && !prev.equals(")")))) {
                            // унарный минус
                            curr = "u-";
                        } else {
                            while (!stack.isEmpty() && (priority(curr) <= priority(stack.peek()))) {
                                postfix.add(stack.pop());
                            }
                        }
                        stack.push(curr);
                        break;
                }
            }

            else {
                postfix.add(curr);
            }
            prev = curr;
        }

        while (!stack.isEmpty()) {
            if (isOperator(stack.peek()))
                postfix.add(stack.pop());
            else {
                System.out.println("Скобки не согласованы.");
                return postfix;
            }
        }
        return postfix;
    }

    abstract Double calc(List<String> expression);

}
