package code.serialization;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

public class DongSearchClient implements Serializable {
    private String name = "";
    private long id = 0;
    private int quarterlyBudget = 0;
    private int numEmployees = 0;
    private ZoneId timeZone = ZoneId.of("UTC");
    private Instant contractStart = Instant.EPOCH;
    private Duration contractLength = Duration.ZERO;
    private String billingAddress = "";

    public DongSearchClient() {}

    public DongSearchClient(String name, long id,int quarterlyBudget, int numEmployees,
                            ZoneId timeZone, Instant contractStart, Duration contractLength,
                            String billingAddress) {
        this.name = name;
        this.id = id;
        this.quarterlyBudget = quarterlyBudget;
        this.numEmployees = numEmployees;
        this.timeZone = timeZone;
        this.contractStart = contractStart;
        this.contractLength = contractLength;
        this.billingAddress = billingAddress;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public int getQuarterlyBudget() {
        return quarterlyBudget;
    }

    public int getNumEmployees() {
        return numEmployees;
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

    public Instant getContractStart() {
        return contractStart;
    }

    public Duration getContractLength() {
        return contractLength;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    @Override
    public String toString() {
        return "DongSearchClient{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", quarterlyBudget=" + quarterlyBudget +
                ", numEmployees=" + numEmployees +
                ", timeZone=" + timeZone +
                ", contractStart=" + contractStart +
                ", contractLength=" + contractLength +
                ", billingAddress='" + billingAddress + '\'' +
                '}';
    }
}
