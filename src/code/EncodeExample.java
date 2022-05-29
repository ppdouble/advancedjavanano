package code;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class EncodeExample {
    public static void main (String[] args) {
        try(Writer writer = Files.newBufferedWriter(Path.of("test_utf8.txt"),
                StandardCharsets.UTF_8)) {
            writer.write("hello, world");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Writer writer = Files.newBufferedWriter(Path.of("test_utf16.txt"))){
            writer.write("hello, world");
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
