package facade;

public class LoggingAspect {
    public void log(String message) {
        System.out.println("LOG: " + message);
    }
}
