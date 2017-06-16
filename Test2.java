import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.DoubleFunction;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by v.kasimatis on 11/5/2017.
 */
public class Test2 {

    public static void testStreamOfAnything(){
        Stream<Integer> s = Stream.of(1,2,3,4,5);
        //Stream<String> s = Arrays.stream("asd","sd","das");
        Stream<String> ss = Arrays.asList("asdf","asdasd").parallelStream();


    }

    public static void testJoining(){
        List<String> list = Arrays.asList("a","b","c");
        System.out.println(
        list.parallelStream().collect(Collectors.joining(":")));
    }

    public static void testReduce(){
        double max =0.0;
        double min =Double.MAX_VALUE;
        List<Double> list = Arrays.asList(1.2,3.5,2.4,5.2);
        max = list.stream().reduce(0.0, Math::max);
        min = list.stream().mapToDouble(Number::doubleValue).min().getAsDouble();
        System.out.println("max:"+max+ "min="+min);
        double sum = list.stream().reduce(0D,(a, b) -> a + b);
        System.out.println("sum=" + sum);
        System.out.println("sum=" + list.stream().reduce(0D, Double::sum));


        Optional<Double> sum2 = Stream.of(1.2,3.45).reduce((a, b) -> a + b);
        Optional<Double> sum3 = Stream.of(1.2,3.45).limit(0).reduce((a, b) -> a + b);
        System.out.println(sum2);
        System.out.println(sum3);

    }
    public static void testReduceParallel(){
        long starttime = System.nanoTime();
        long result = Stream.iterate(1L, i->i+10).limit(1000000).parallel().reduce(1L,Long::sum);
        long time = System.nanoTime()-starttime;

        System.out.println("Parallel Time: " + time +" Result: " + result );
    }
public static void testReduceParallelClosed(){
        long starttime = System.nanoTime();
        long result = LongStream.rangeClosed(1L,1000000).parallel().reduce(1L,Long::sum);
        long time = System.nanoTime()-starttime;

        System.out.println("Parallel Time: " + time +" Result: " + result );
    }
public static void testReduceSeqClosed(){
        long starttime = System.nanoTime();
        long result = LongStream.rangeClosed(1L,1000000).reduce(1L,Long::sum);
        long time = System.nanoTime()-starttime;

        System.out.println("Parallel Time: " + time +" Result: " + result );
    }

    public static void testReduceSequntial(){
        long starttime = System.nanoTime();
        long result = Stream.iterate(1L, i->i+10).limit(1000000).reduce(1L,Long::sum);
        long time = System.nanoTime()-starttime;

        System.out.println("Sequential Time: " + time +" Result: " + result );
    }

    public static void testPattern(){
        Pattern patt = Pattern.compile(",");
        patt.splitAsStream("a,b,c")
        .forEach(System.out::print);

    }

    public static void testIterate(){

        Stream.iterate(1, i->i+1).limit(10).forEach(System.out::println);
        Stream.iterate(1, i-> i+1).limit(10).filter(Test2::myFilter).skip(1).peek(System.out::print).forEach(i-> System.out.print(i + " "));

    }

    public static boolean myFilter(int a){
        return a % 2 ==0;
    }


    public static double integrate (DoubleFunction<Double> f , double a, double b){
        return (f.apply(a)+f.apply(b))*(b-a)/2.0;
    }


    public static void main(String [] args){

        testIterate();
        testPattern();
        testReduce();
        testJoining();
        System.out.println(integrate(f->f+5,1,3));
        System.out.println(integrate(f->Math.cos(f),0,2*3.14));
        testReduceSequntial();
        testReduceParallel();

        System.out.println("Second try");
        testReduceParallelClosed();
        testReduceSeqClosed();


    }

}
