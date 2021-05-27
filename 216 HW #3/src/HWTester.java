import java.util.*;
import java.util.function.Function;
public class HWTester {
    public static void main(String[] args){
        ArrayList<String> lol = new ArrayList<>();
        lol.add("fol");
        lol.add("Giannis");
        lol.add("Bronbet");
        lol.add("bih");
        lol.add("hi");
        boolean n = true;
        System.out.println(StreamUtils.capitalized(lol).toString());
        System.out.println(StreamUtils.longest(lol, n).toString());
        HashMap<String, String> hihi = new HashMap<>();
        hihi.put("lol", "cool");
        hihi.put("tf", "kool");
        hihi.put("frick", "hool");
        List<String> newList = StreamUtils.flatten(hihi);
        System.out.print(newList.toString());
        List<Double> a = Arrays.asList(1d, 1d, 3d, 0d, 4d);
        System.out.println(StreamUtils.least(lol,false));
        List<HigherOrderUtils.NamedBiFunction<Double, Double, Double>> bfs = Arrays.asList(HigherOrderUtils.add,
                HigherOrderUtils.mult, HigherOrderUtils.add, HigherOrderUtils.div);
        System.out.println(HigherOrderUtils.zip(a,bfs));
        Function<Character, String> first = b->b.toString()+b.toString();
        Function<String, Integer> second = c->c.length();
        HigherOrderUtils.FunctionComposition<Character, String, Integer> test = new HigherOrderUtils.FunctionComposition<>();
        Function<Character, Integer> g = test.composition.apply(first,second);
        System.out.println(g.apply('s'));
    }
}
