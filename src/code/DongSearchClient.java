package code;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class DongSearchClient {
    private int quarterlyBudget;
    private long id;
    private List<ZoneId> timeZones;
    private Instant contractStart;
    private Instant contractEnd;

    public DongSearchClient (int quarterlyBudget, long id, List<ZoneId> timeZones, Instant contractStart, Instant contractEnd) {
        this.quarterlyBudget = quarterlyBudget;
        this.id = id;
        this.timeZones = timeZones;
        this.contractStart = contractStart;
        this.contractEnd = contractEnd;
    }

    public int getQuarterlyBudget() {
        return quarterlyBudget;
    }

    public long getId() {
        return id;
    }

    public Instant getContractStart() {
        return contractStart;
    }

    public Instant getContractEnd() {
        return contractEnd;
    }

    public List<ZoneId> getTimeZones() {
        return timeZones;
    }
}
