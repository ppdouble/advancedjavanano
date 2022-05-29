package code.anonymousexamples.implinterface;

class WebSite {
    ProgrammerInterview programmerInterview = new ProgrammerInterview() {
        @Override
        public void read() {
            System.out.println("the anonymous inner class: " + this.getClass().getName() +
                " implements the interface: " + this.getClass().getInterfaces()[0].getName());
        }
    };

    public void readIt () {
        programmerInterview.read();
    }
}
