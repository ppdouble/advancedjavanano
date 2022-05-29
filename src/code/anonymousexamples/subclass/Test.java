package code.anonymousexamples.subclass;

public class Test {
    public static void main (String[] args) {
        ProgrammerInterview programmerInterview = new ProgrammerInterview();
        programmerInterview.read();
        System.out.println("------------");
        WebSite webSite = new WebSite();
        webSite.readIt();
    }
}
