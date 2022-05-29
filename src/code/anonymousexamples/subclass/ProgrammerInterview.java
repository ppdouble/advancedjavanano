package code.anonymousexamples.subclass;

/*
 * [anonymous-class-example](https://www.programmerinterview.com/java-questions/java-anonymous-class-example/)
 */
class ProgrammerInterview {
    public void read () {
        System.out.println("ProgrammerInterview");
        System.out.println(this.getClass().getName());
        System.out.println("super class of anonymous inner class");
    }
}

