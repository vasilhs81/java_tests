//package org.wso2.charon.samples;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author v.kasimatis
 */
public class Test {

    public static Map<String, Integer> myMap = new HashMap<>();

    public static void test_streams2() {
        List<String> mylist = Arrays.asList("a", "b", "c", "1", "2");
        mylist.stream().filter((String s)
            -> Character.isDigit(s.charAt(0)))
            .map(String::toUpperCase).
            sorted().
            forEach(System.out::println);

        IntStream.range(0, mylist.size()).forEach(idx ->
            myMap.put(mylist.get(idx), idx)
        );
        myMap.forEach((k, v) -> System.out.format("This is key: '%s' with value: %d\n", k, v));
        //mylist.stream().forEach(item->{
        //  myMap.put(item, item.)
        //});

    }

    public static void test_streams() {
        File[] hiddenFiles = new File(".").listFiles(File::isHidden);
        Arrays.stream(hiddenFiles).forEach(System.out::println);

        //Arrays.stream(values).mapToObj(i -> Integer.toUnsignedString(i, 16)).forEach(System.out::println);
    }

    public static boolean startsWithSomething(String s) {
        return s.startsWith("pre");

    }

    public static boolean hasSomething(String s) {
        return s.indexOf("rrr") > 0;
    }

    public interface PlayWithString<T> {

        boolean play(T t);
    }

    public static void test_passing_code(String ss, PlayWithString<String> p) {
        if (p.play(ss)) {
            System.out.println("Is true");
        } else {
            System.out.println("Is false");
        }
    }

    public static <T> List<T> filter(List<T> list, PlayWithString<T> p) {
        List<T> result = new ArrayList<>();
        list.stream().forEach(item -> {
            if (p.play(item)) {
                result.add(item);
            }
        });
        return result;
    }

    public static void main(String[] args) {
        test_streams();

        test_passing_code("peaaaarrrrcorr", Test::startsWithSomething);
        test_passing_code("peaaaarrrrcorrgb", (String s) -> s.endsWith("gab"));
        test_passing_code("peaaaarrrrcorrgb",
            new PlayWithString() {
            @Override
            public boolean play(Object t) {
                return !((String) t).isEmpty();
            }
        });
        test_streams2();

        List<Integer> firstTenIntegers = filter(Arrays.asList(1, 2, 3, 4, 5), (Integer i) -> i > 2);
        firstTenIntegers.stream().forEach(System.out::println);

        //firstTenIntegers.sort(Integer::compareTo);
        
        filter(Arrays.asList(0.8F, 0.6F, 0.9F, 0.1F, -0.4F), (Float i) -> i >= 0.1F).stream().
            sorted(Float::compareTo).
            sorted((Float a, Float b)->b.compareTo(a)).
            forEach(System.out::println);

        Runnable r = () -> System.out.print("Hi\n");
        Runnable r2 = new Runnable() {
            public void run() {
                System.out.println("ssadfa");
            }
        };
        
        
        forEach(Arrays.asList(1,2,3,4,5),
            (Integer i)->System.out.println(i));
            
        List<Integer> LL = map(Arrays.asList("aaa","bbb","asdasdfasd"),
            (String s)->s.length()
            );
        forEach(LL,(Integer a)->System.out.println(a));
        //LL.stream().forEach(action);

        createMap(Arrays.asList("aaa","bbb","asdasdfasd"),
            (String s)->s.length()+ ":" + s
            ).forEach((a,k)-> System.out.format("key: %s, value: %s\n",a,k));


        collectTest();

        testMap();
        //////////////////End of Main
        
    }
    
    
    @FunctionalInterface
    public interface Consumer<T>{
        void accept(T t);
    }
    
    public static <T> void forEach(List<T> list, Consumer <T> c){
        list.stream().forEach(item->c.accept(item));
    }
    
    
    
    @FunctionalInterface
    public interface MapFunction<T,R>{
        R apply(T t);
    }
    
    @FunctionalInterface
    public interface MappingFunction<T,R>{
        R apply(T t);
    }
    
    
    public static <T,R> List<R> map(List<T> list, MapFunction<T,R> f){
        List<R> result = new ArrayList<>();
        list.stream().forEach(item->result.add(f.apply(item)));
        return result;
    }
    
    public static <T,R> Map<T,R> createMap(List<T> list, MapFunction<T,R> f){
        Map<T,R> result = new HashMap<>();
        list.stream().forEach(item->result.put(item,f.apply(item)));
        return result;
    }


    public  static void collectTest(){

        List<String> aa = Arrays.asList("a","b","d","c","d","asdfsa");
        System.out.println(
        aa.stream().map(String::toLowerCase).collect(Collectors.joining())); //collects just reduces the list by operation


        System.out.println(
        aa.stream().filter(d->d.length()==1).distinct().limit(4).count());

        aa.stream().filter(d->d.length()==1).distinct().limit(4).forEach(System.out::println);


        List<String> bb = aa.stream().filter(d->d.length()==1).distinct().limit(4).skip(2).collect(Collectors.toList());

        forEach(bb, s -> System.out.print(s));

        List<Integer> listOfIntegers = aa.stream().filter(d->d.length()==1).distinct().limit(4).skip(2).map(String::length)
                .collect(Collectors.toList());

        forEach(listOfIntegers, s -> System.out.print(s));


    }


    public static void testMap(){
        List<String> aa = Arrays.asList("a","b","d","a","c","d","wwww");

        //1. filter out duplicate list entries, with distinct.
        //2. The unique now elements are being converted into a map, using collect() and Collectors.toMap
        Map<String, String> map = aa.stream().distinct().collect(Collectors.toMap(v->String.valueOf(v.charAt(0)),item->item.toUpperCase()));

        System.out.println();
        map.forEach((s,v)->{
            System.out.println("Key: " + s + " value: " + v);
        });

        // the same as above, but avoid distinct() method, using 3rd argument in collect()
        map = aa.stream().collect(Collectors.toMap(v->String.valueOf(v.charAt(0)),item->item.toUpperCase(),(oldV, newV)->(newV)));
        map.forEach((s,v)->{
            System.out.println("Key: " + s + " value: " + v);
        });


        // check if any value of string has length>1
        System.out.println(
            aa.stream().anyMatch(t->t.length()>1)
        );

        //1. filter out every element with length >1
        //2. convert the resulting stream of strings into a stream of Int - length (using mapToInt)
        //3. Sum all integers lengths into one integer value, using reduce()
        System.out.println(
            aa.stream().filter(t->t.length()==1).mapToInt(a->a.length()).reduce(0,(a,b)-> a + b)
        );


        //reduce voleuei na a8roizei integers..
    }


    //Using flatMap
    
    
    

}
