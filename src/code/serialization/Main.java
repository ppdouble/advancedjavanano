package code.serialization;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Main {
    // javac -classpath "lib/*" DongSearchClient.java Main.java
    // java -classpath ".:lib/*" Main
    // [correct-way-to-add-external-jars-lib-jar-to-an-intellij-idea-project](https://stackoverflow.com/questions/1051640/correct-way-to-add-external-jars-lib-jar-to-an-intellij-idea-project#1051705)
    public static void main (String[] args) {

        // create an instance of the object
        DongSearchClient dongSearchClient = new DongSearchClient("John", 896706,
                10000, 50, ZoneId.of("Asia/Jakarta"),
                Instant.now(), Duration.ofDays(365), "Jakarta");

        // TO FILE
        // serialize (persistent, storing to disk)
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Path.of("client.bin")))) {
            objectOutputStream.writeObject(dongSearchClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Wrote to " + Path.of("client.bin").toAbsolutePath());

        try(ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Path.of("client.bin")))) {
            DongSearchClient deserializedDongSearchClient = (DongSearchClient) objectInputStream.readObject();
            // The object will be printed as human-readable using toString().
            System.out.println("Deserialized " + deserializedDongSearchClient);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // TO JSON
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        objectMapper.registerModule(javaTimeModule);
        try {
            // serialize. write to JSON
            objectMapper.writeValue(Files.newBufferedWriter(Path.of("client.json")), dongSearchClient);
            System.out.println("wrote to JSON file" + Path.of("client.json").toAbsolutePath());

            // deserialize. read from JSON. Reflection
            DongSearchClient deserializedFromJSONDongSearchClient = objectMapper.readValue(
                    Files.newBufferedReader(Path.of("client.json")),
                    DongSearchClient.class); // Reflection. Need No arguments constructor.
            System.out.println("Deserialized from JSON: " + deserializedFromJSONDongSearchClient);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
