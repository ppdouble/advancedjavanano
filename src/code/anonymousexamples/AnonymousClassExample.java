package code.anonymousexamples;

public class AnonymousClassExample {

    private final Runnable withLambda =
            () -> System.out.println("From lambda: " + this.getClass());

    private final Runnable withSubClass = new Runnable() {
        @Override
        public void run() {
            System.out.println("From anonymous subclass: " + this.getClass());
        }
    };

    public static void main (String[] args) {
        AnonymousClassExample anonymousClassExample = new AnonymousClassExample();
        anonymousClassExample.withLambda.run();
        anonymousClassExample.withSubClass.run();
    }
}
