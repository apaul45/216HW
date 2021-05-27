import java.util.function.BiFunction;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class HigherOrderUtils {
    public static interface NamedBiFunction<T,T1,T2> extends BiFunction<T,T,T> {
        String name();
    }
    static NamedBiFunction<Double, Double, Double> add = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "add";
        }

        @Override
        public Double apply(Double aDouble, Double aDouble2) {
            return aDouble+aDouble2;
        }
    };
    static NamedBiFunction<Double, Double, Double> diff = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "diff";
        }

        @Override
        public Double apply(Double aDouble, Double aDouble2) {
            return aDouble - aDouble2;
        }
    };
    static NamedBiFunction<Double, Double, Double> mult = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "mult";
        }

        @Override
        public Double apply(Double aDouble, Double aDouble2) {
            return aDouble*aDouble2;
        }
    };
    static NamedBiFunction<Double, Double, Double> div = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "div";
        }

        @Override
        public Double apply(Double aDouble, Double aDouble2) throws ArithmeticException{
            if (aDouble2==0.0){
                throw new ArithmeticException();
            }
            Double a = aDouble/aDouble2;
            return (double)(a.intValue());
        }
    };
/**
 * Applies a given list of bifunctions -- functions that take two arguments of a certain type
 * and produce a single instance of that type -- to a list of arguments of that type. The
 * functions are applied in an iterative manner, and the result of each function is stored in
 * the list in an iterative manner as well, to be used by the next bifunction in the next
 * iteration. For example, given
 * List<Double> args = Arrays.asList(1d, 1d, 3d, 0d, 4d), and
 * List<NamedBiFunction<Double, Double, Double>> bfs = [add, multiply, add, divide],
 * * <code>zip(args, bfs)</code> will proceed iteratively as follows:
 * * - index 0: the result of add(1,1) is stored in args[1] to yield args = [1,2,3,0,4]
 * * - index 1: the result of multiply(2,3) is stored in args[2] to yield args = [1,2,6,0,4]
 * * - index 2: the result of add(6,0) is stored in args[3] to yield args = [1,2,6,6,4]
 * * - index 3: the result of divide(6,4) is stored in args[4] to yield args = [1,2,6,6,1]
 * *
 * * @param args: the arguments over which <code>bifunctions</code> will be applied.
 * * @param bifunctions: the list of bifunctions that will be applied on <code>args</code>.
 * * @param <T>: the type parameter of the arguments (e.g., Integer, Double)
 * * @return the item in the last index of <code>args</code>, which has the final
 * * result of all the bifunctions being applied in sequence.
 * */
    public static <T> T zip(List<T> args, List<NamedBiFunction<T, T, T>> bifunctions) throws IllegalArgumentException{
        if ((args.size() == 0 || bifunctions.size() == 0) || (bifunctions.size()!=(args.size()-1))){
            throw new IllegalArgumentException();
        }
        IntStream zipStream = IntStream.range(0, args.size()-1);
        zipStream.forEach(num -> args.set(num+1, bifunctions.get(num).apply(args.get(num), args.get(num+1))));
        return args.get(args.size()-1);
    }
    public static class FunctionComposition<T,T1,T2>{
        BiFunction<Function<T,T1>,Function<T1,T2>,Function<T,T2>> composition = new BiFunction<Function<T, T1>, Function<T1, T2>, Function<T, T2>>() {

            public String name(){
                return "composition";
            }
            @Override
            public Function<T, T2> apply(Function<T, T1> tt1Function, Function<T1, T2> t1T2Function) {
                return t1T2Function.compose(tt1Function);
            }
        };
    }
}
