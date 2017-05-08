/*
 * Copyright 2017 WSO2.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.charon.samples;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        IntStream.range(0, mylist.size()).forEach(idx -> {
            myMap.put(mylist.get(idx), idx);
        });
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
        //test_streams();

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
    
    
    
    
    

}
