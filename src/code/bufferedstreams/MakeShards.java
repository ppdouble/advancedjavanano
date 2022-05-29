package code.bufferedstreams;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/*
 *
 *  shard files
 *
 */
public class MakeShards {

    public static void main (String[] args) {

        // prepareData ();
        // sharding the large file
        // Path input = Path.of(args[0]);
        // Path outputFolder = Files.createDirectory(Path.of(args[1]));
        shardingLargeFile();
        sortingShardFiles();
        mergeShardFiles();


    }

    public static void prepareData () {
        int num = 10000;
        String dataFile = "data.txt";
        String localFile = "dictionary.txt";
        String remoteFile = "http://web.stanford.edu/class/archive/cs/cs106l/cs106l.1102/assignments/dictionary.txt";
        Integer max = countLines(downloadData(localFile, remoteFile));
        System.out.println(max);
        Random random = new Random();
        Set<Integer> iSet = new HashSet<Integer>(); // unsorted
        HashMap<Integer, String> wordsHashMap = new HashMap<Integer, String>();

        while (iSet.size() < num) {
            int tmp = random.nextInt(max - 1) + 1;
            iSet.add(tmp);
            wordsHashMap.put(tmp, "");
        }
        List<Integer> iList = new ArrayList<Integer>(iSet) ;
        Collections.sort(iList);    // sorted

        try(LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(localFile))) {
            int j = 0;
            int count = 0;
            for (Integer i : iList) {
                String tmp = "";
                while(true) {
                    tmp = lineNumberReader.readLine();
                    if (i == lineNumberReader.getLineNumber()) {
                        wordsHashMap.put(i, tmp);
                        count++;
                        break;
                    }
                }
            }

            BufferedWriter outputWriter = null;
            outputWriter = new BufferedWriter(new FileWriter(dataFile));
            count = 0;
            for (Integer n : iSet) {
                outputWriter.write(wordsHashMap.get(n));
                if (count < num-1) {
                    outputWriter.newLine();
                }
                count++;

            }
            outputWriter.flush();
            outputWriter.close();

        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public static int countLines(String fileName) {
        int lines = 0;

        File file = new File(fileName);
        try (LineNumberReader lnr = new LineNumberReader(new FileReader(file))) {
            while (lnr.readLine() != null) ;
            lines = lnr.getLineNumber();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public static String downloadData(String fileName, String url) {

        try {
            URL fetchWebsite = new URL(url);
            ReadableByteChannel readableByteChannel = Channels.newChannel(fetchWebsite.openStream());

            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                fos.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return fileName;
    }

    public static void shardingLargeFile () {
        Integer SHARD_SIZE = 800;
        Integer dataSize = 10000;
        Integer numOfFiles = (int) Math.ceil((double)dataSize/SHARD_SIZE);
        try (BufferedReader reader = Files.newBufferedReader(Path.of("data.txt"), StandardCharsets.UTF_8)){

            String line;
            //int i = 1;
            int count = 0;
            //useString(line);
            for (int shardNum=1; shardNum<=numOfFiles; shardNum++) {
                try (BufferedWriter writer = Files.newBufferedWriter(getOutputFileName(shardNum), StandardCharsets.UTF_8)) {
                    while (null != (line = reader.readLine())) {
                        writer.write(line);
                        count++;
                        if (0 == count%SHARD_SIZE) {
                            break;
                        } else if (count != dataSize){
                            writer.newLine();
                        }

                    }
                } catch (IOException e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }

        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public static void sortingShardFiles () {

            String line;
            //int i = 1;
            int count = 0;
            int numOfFiles = 13;
            Path outputFolder = null;

            for (int shardNum=1; shardNum<=numOfFiles; shardNum++) {
                List<String> list = new ArrayList<String>();
                try (BufferedReader reader = Files.newBufferedReader(getOutputFileName(shardNum), StandardCharsets.UTF_8)){
                    while (null != (line = reader.readLine())) {
                        list.add(line);
                    }
                } catch (IOException e) {
                    System.out.println(e.getLocalizedMessage());
                }
                // sorting
                List<String> sortedList = list.stream().sorted().collect(Collectors.toList());

                try (BufferedWriter writer = Files.newBufferedWriter(getOutputFileName(shardNum), StandardCharsets.UTF_8)) {
                    for (String s : sortedList) {
                        writer.write(s);
                        count++;
                        if (count < sortedList.size()) {
                            writer.newLine();
                        } else {
                            count = 0;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    public static Path getOutputFileName(int shardNum) {
        Path p = null;
        try {
            p = Files.createDirectory(Path.of("outputfolder"));
        } catch (IOException e) {
            if ("FileAlreadyExistsException".equals(e.getClass().getSimpleName())) {
                p = Path.of("outputfolder");
            } else {
                e.printStackTrace();
            }
        }

        return Path.of(p.toString(), String.format("shard%02d.txt", shardNum));
        //return String.format(, shardNum);
    }

    public static void mergeShardFiles () {
        int numOfFiles = 13;

        List<BufferedReader> bufferedReaders = null;

        // An unbounded priority queue based on a priority heap.
        // The elements of the priority queue are ordered according to their natural ordering,
        // or by a Comparator provided at queue construction time,
        // depending on which constructor is used. A priority queue does not permit null elements.
        // A priority queue relying on natural ordering also does not permit insertion of
        // non-comparable objects (doing so may result in ClassCastException).
        // https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html
        PriorityQueue<WordEntry> words = new PriorityQueue<WordEntry>();
        try {
            List<Path> inputs = Files.walk(Path.of("outputfolder"), 1).skip(1).collect(Collectors.toList());
            bufferedReaders = new ArrayList<BufferedReader>(inputs.size());
            for (Path input : inputs) {
                bufferedReaders.add(Files.newBufferedReader(input, StandardCharsets.UTF_8));
            }
            // put the first word of each shard file into memory(PriorityQueue),
            // and record the reader to the queue either
            // and record the reader to the queue either
            for (BufferedReader bufferedReader : bufferedReaders) {
                String word;
                if (null != (word = bufferedReader.readLine())) {
                    words.add(new WordEntry(word, bufferedReader));
                }
            }
            try (BufferedWriter writer = Files.newBufferedWriter(Path.of("sorted.txt"), StandardCharsets.UTF_8)) {
                while (!words.isEmpty()) {
                    WordEntry wordEntry = words.poll(); // fetch the smallest word
                    writer.write(wordEntry.word);       // store the smallest word into a file
                    writer.newLine();

                    // fetch a new word from the file which including the smallest word
                    // record the reader
                    // the queue will be ordered again, during the *add* action
                    String word;
                    if (null != (word = wordEntry.reader.readLine())) {
                        words.add(new WordEntry(word, wordEntry.reader));
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            } finally {
                for (BufferedReader bufferedReader : bufferedReaders) {
                    try {
                        bufferedReader.close();
                    } catch (IOException ex) {
                        System.out.println(ex.getLocalizedMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }


    /*
     * Record the reader
     */
    private static final class WordEntry implements Comparable<MakeShards.WordEntry> {
        private final String word;
        private final BufferedReader reader;

        private WordEntry(String word, BufferedReader reader) {
            this.word = Objects.requireNonNull(word);
            this.reader = Objects.requireNonNull(reader);
        }

        @Override
        public int compareTo(MakeShards.WordEntry other) {
            return word.compareTo(other.word);
        }
    }
}
