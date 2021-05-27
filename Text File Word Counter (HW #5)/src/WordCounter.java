import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class WordCounter{
    // The following are the ONLY variables we will modify for grading.
    // The rest of your code must run with no changes.
    public static final Path FOLDER_OF_TEXT_FILES  = Paths.get("/Users/apaul23/216HW5testfiles"); // path to the folder where input text files are located
    public static final Path WORD_COUNT_TABLE_FILE = Paths.get("/Users/apaul23"); // path to the output plain-text (.txt) file
    public static final int  NUMBER_OF_THREADS     = 1;// max. number of threads to spawn
    /* The following Hashmap will serve as the data structure that contains all of the words from every file:
    The key for each Treemap (to sort alphabetically) in the larger hashmap will be the file name,
    as a way to indicate what files have what words
     */
    public static final ConcurrentHashMap<String, TreeMap<String, Integer>> wordHolder = new ConcurrentHashMap<>(); //Fr storing the words associated with each file
    public static final TreeMap<String, Integer> wordSorter = new TreeMap<>(); //For storing the total amount of each word
    public static final ArrayList<File> textFiles = new ArrayList<>(Arrays.asList(FOLDER_OF_TEXT_FILES.toFile().listFiles()));
    public static File outputFile = WORD_COUNT_TABLE_FILE.toFile();
    public static void main(String[] args) throws IOException{
        ExecutorService fileExec = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        try {
            for (File f : textFiles) {
                String[] h = readFromFile(f);
                fileExec.execute(new CounterHelper(wordHolder, f.getName(), h));
            }
        }
        finally{
            fileExec.shutdown();
        }
        getTotalNum();
        outputTable();
    }
    public static void getTotalNum(){
        boolean forFirst = true; //Flag to represent that only the words of the First file encountered in textfiles should
        //be  automatically put into the empty wordSorter map that will hold the total number of times that every word appears in every file
        for (File f: textFiles){
            if (forFirst == true){
                for (String g:wordHolder.get(f.getName()).keySet()){
                    wordSorter.put(g, wordHolder.get(f.getName()).get(g));
                }
                forFirst = false;
            }
            else{
                for (String g:wordHolder.get(f.getName()).keySet()){
                    if (wordSorter.containsKey(g)){
                        wordSorter.put(g, wordSorter.get(g)+wordHolder.get(f.getName()).get(g));
                    }
                    else{
                        wordSorter.put(g, wordHolder.get(f.getName()).get(g));
                    }
                }
            }
        }
    }

    public static String[] readFromFile(File f) throws FileNotFoundException {
        Scanner reader = new Scanner(f);
        String[] words = reader.nextLine().split(" ");
        return words;
    }
    public static void outputTable() throws IOException {
        try{
            boolean result = outputFile.createNewFile();
        }
        catch (IOException e){

        }
        Comparator<String> byLength = Comparator.comparingInt(String::length);
        int columnWidth = wordSorter.keySet().stream().max(byLength).get().length() + 1;
        String space = String.format("%" + columnWidth + "s", "");
        StringBuilder wordBuilder = new StringBuilder(space);
        Collections.sort(textFiles);
        for (File s: textFiles){
            wordBuilder.append(s.getName().substring(0, s.getName().indexOf(".txt")));
            wordBuilder.append(String.format("%4s", ""));
        }
        wordBuilder.append("Total\n");
        TreeMap<String, TreeMap<String, Integer>> s = new TreeMap<>();
        s.putAll(wordHolder);
        for (String key: wordSorter.keySet()){
            wordBuilder.append(key);
            int rowWidth = columnWidth-key.length();
            wordBuilder.append(String.format("%" + rowWidth + "s", ""));
            int i = 0;
            for (String file:s.keySet()){
                if (s.get(file).containsKey(key)){
                    wordBuilder.append(s.get(file).get(key));
                    int spacingLength = textFiles.get(i).getName().substring(0,textFiles.get(i).getName().indexOf(".txt")).length() + 3;
                    wordBuilder.append(String.format("%" + spacingLength + "s", ""));
                    i+=1;
                }
                else{
                    wordBuilder.append(0);
                    int spacingLength = textFiles.get(i).getName().substring(0,textFiles.get(i).getName().indexOf(".txt")).length() + 3;
                    wordBuilder.append(String.format("%" + spacingLength + "s", ""));
                    i+=1;
                }
            }
            wordBuilder.append(wordSorter.get(key));
            wordBuilder.append("\n");
        }
        FileWriter writer = new FileWriter(outputFile);
        BufferedWriter outputWriter = new BufferedWriter(writer);
        outputWriter.write(wordBuilder.toString());
        outputWriter.close();
    }
}
