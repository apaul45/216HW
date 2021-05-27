import java.util.Comparator;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class CounterHelper implements Runnable{
    private ConcurrentHashMap<String, TreeMap<String, Integer>> wordHolder;
    private String filename;
    String[] words;

    public CounterHelper(ConcurrentHashMap<String, TreeMap<String, Integer>> p, String f, String[] g){
        wordHolder = p;
        filename = f;
        words = g;
    }

    public void parseWordsInFile(){
        Comparator<String> c = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };
        TreeMap<String, Integer> parsedWords = new TreeMap<>(c);
        for (String w: words){
            if ((w.endsWith(".")) || (w.endsWith(",")) ||
                    (w.endsWith(":")) || (w.endsWith(";")) ||
                    (w.endsWith("!")) || (w.endsWith("?"))){
                String p = w.substring(0, w.length()-1).toLowerCase();
                if (parsedWords.containsKey(p)){
                    parsedWords.put(p, parsedWords.get(p)+1);
                }
                else {
                    parsedWords.put(p, 1);
                }
            }
            else{
                String r = w.toLowerCase();
                if (parsedWords.containsKey(r)){
                    parsedWords.put(r, parsedWords.get(r)+1);
                }
                else {
                    parsedWords.put(r, 1);
                }
            }
        }
        wordHolder.put(filename, parsedWords);
    }

    @Override
    public void run() {
        parseWordsInFile();
    }
}
