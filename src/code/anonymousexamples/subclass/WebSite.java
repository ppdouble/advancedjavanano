package code.anonymousexamples.subclass;

class WebSite {
    ProgrammerInterview programmerInterview = new ProgrammerInterview() {
        public void read () {
            System.out.print("anonymous ProgrammerInterview: ");
            System.out.println(this.getClass().getName());
            System.out.print("subclass of normal class: ");
            System.out.println(this.getClass().getSuperclass());
        }
        public void learn () {
            System.out.println("anonymous, learn ProgrammerInterview");
        }
    };

    public void readIt () {
        programmerInterview.read();
        /*
        // Illegal
        programmerInterview.learn();
         */
    }
}
