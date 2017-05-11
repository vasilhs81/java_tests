import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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


    public static void main(String [] args){

        testIterate();
        testPattern();
        testReduce();
        testJoining();

    }

}
