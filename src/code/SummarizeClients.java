package code;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public final class SummarizeClients {

    public static void main (String[] args) {

        //List<DongSearchClient> clients = ClientStore.getClients();
        List<DongSearchClient> clients = new ArrayList<DongSearchClient>();
        clients.add(new DongSearchClient(10, 896706,
                Arrays.asList(
                        ZoneId.of("Asia/Jakarta"),
                        ZoneId.of("America/Chicago")
                ),
                Instant.parse("2031-12-03T10:15:30.00Z"), Instant.parse("2032-12-03T10:15:30.00Z")));
        clients.add(new DongSearchClient(10, 896707,
                Arrays.asList(
                        ZoneId.of("Asia/Jakarta"),
                        ZoneId.of("America/Chicago")
                ),
                Instant.parse("2031-12-03T10:15:30.00Z"), Instant.parse("2033-12-03T10:15:30.00Z")));
        clients.add(new DongSearchClient(10, 896708,
                Arrays.asList(
                        ZoneId.of("Asia/Jakarta"),
                        ZoneId.of("America/Chicago")
                ),
                Instant.parse("2032-12-03T10:15:30.00Z"), Instant.parse("2033-12-03T10:15:30.00Z")));
        clients.add(new DongSearchClient(20, 896709,
                Arrays.asList(
                        ZoneId.of("Asia/Jakarta"),
                        ZoneId.of("America/Chicago")
                ),
                Instant.parse("2022-03-03T10:15:30.00Z"), Instant.parse("2022-12-03T10:15:30.00Z")));
        int numClients = clients.size();

        int totalQuarterlySpend =
                clients
                        .stream()
                        .mapToInt(DongSearchClient::getQuarterlyBudget)
                        .sum();


        double averageBudget =
                clients
                        .stream()
                        .mapToDouble(DongSearchClient::getQuarterlyBudget)
                        .average()
                        .orElse(0);


        long nextExpiration =
                clients
                        .stream()
                        .min(Comparator.comparing(DongSearchClient::getContractEnd))
                        .map(DongSearchClient::getId)
                        .orElse(-1L);


        List<ZoneId> representedZoneIds =
                clients
                        .stream()
                        .flatMap(c -> c.getTimeZones().stream())
                        .distinct()
                        .collect(Collectors.toList());


        Map<Year, Long> contractsPerYear =
                clients
                        .stream()
                        .collect(Collectors.groupingBy(SummarizeClients::getContractYear, Collectors.counting()));

        System.out.println("Num Clients: " + numClients);
        System.out.println("Total quarterly spend: " + totalQuarterlySpend);
        System.out.println("Average budget: " + averageBudget);
        System.out.println("ID of next expiring contract: " + nextExpiration);
        System.out.println("Client time zones: " + representedZoneIds);
        System.out.println("Contracts per year: " + contractsPerYear);
    }

    public static Year getContractYear(DongSearchClient client) {
        LocalDate contractDate =
                LocalDate.ofInstant(client.getContractStart(), client.getTimeZones().get(0));
        return Year.of(contractDate.getYear());
    }
}
