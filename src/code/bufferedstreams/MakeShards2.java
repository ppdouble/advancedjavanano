package code.bufferedstreams;

import java.io.BufferedReader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public final class MakeShards2 {
    private static final int SHARD_SIZE = 100;

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage: MakeShards2 [input file] [output folder]");
            return;
        }

        Path inputFile = Path.of(args[0]);
        Path outputFolder = Files.createDirectory(Path.of(args[1]));

        try (BufferedReader reader = Files.newBufferedReader(inputFile)) {
            int shardNum = 0;
            String word = reader.readLine();
            while (word != null) {
                List<String> shard = new ArrayList<>(SHARD_SIZE);
                while (shard.size() < SHARD_SIZE && word != null) {
                    shard.add(word);
                    word = reader.readLine();
                }
                shard.sort(String::compareTo);
                Path output = Path.of(outputFolder.toString(), getOutputFileName(shardNum));
                try (Writer writer = Files.newBufferedWriter(output)) {
                    for (int i = 0; i < shard.size(); i++) {
                        writer.write(shard.get(i));
                        if (i < shard.size() - 1) {
                            writer.write(System.lineSeparator());
                        }
                    }
                }
                shardNum++;
            }
        }

        List<Path> inputs = Files.walk(Path.of(inputFile.toString()), 1).skip(1).collect(Collectors.toList());
        List<BufferedReader> readers = new ArrayList<>(inputs.size());
        Path outputPath = Path.of(args[1]);

        try {
            for (Path input : inputs) {
                readers.add(Files.newBufferedReader(input));
            }
            PriorityQueue<WordEntry> words = new PriorityQueue<>();
            for (BufferedReader reader : readers) {
                String word = reader.readLine();
                if (word != null) {
                    words.add(new WordEntry(word, reader));
                }
            }

            try (Writer writer = Files.newBufferedWriter(outputPath)) {
                while (!words.isEmpty()) {
                    WordEntry entry = words.poll();
                    writer.write(entry.word);
                    writer.write(System.lineSeparator());
                    String word = entry.reader.readLine();
                    if (word != null) {
                        words.add(new WordEntry(word, entry.reader));
                    }
                }
            }
        } finally {
            for (BufferedReader reader : readers) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static final class WordEntry implements Comparable<WordEntry> {
        private final String word;
        private final BufferedReader reader;

        private WordEntry(String word, BufferedReader reader) {
            this.word = Objects.requireNonNull(word);
            this.reader = Objects.requireNonNull(reader);
        }

        @Override
        public int compareTo(WordEntry other) {
            return word.compareTo(other.word);
        }
    }

    private static String getOutputFileName(int shardNum) {
        return String.format("shard%02d.txt", shardNum);
    }
}