package facade;

import java.util.function.Function;

public class ErrorHandlingAspect {
    public <T, R> Function<T, R> handleError(Function<T, R> func) {
        return (T t) -> {
            try {
                return func.apply(t);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return null;
            }
        };
    }
}
