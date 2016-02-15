package pl.java.scalatech;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.Test;
import org.neo4j.cypher.internal.compiler.v2_2.ast.conditions.collectNodesOfType;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class FunctionTest {
    Function<Integer, Integer> square = x -> x * x;
    Function<Integer, Integer> addOne = x -> x + 1;
    static class Math{

        public static final BiFunction<Integer, Integer, Integer> ADDITION = (a, b) -> a + b;

        public static final BiFunction<Integer, Integer, Integer> SUBTRACTION = (a, b) -> a - b;

        public static  final BiFunction<Integer, Integer, Integer> MULTIPLICATION = (a, b) -> a * b;

        public static final BiFunction<Integer, Integer, Integer> DIVISION = (a, b) -> a / b;
}
    @Test    
    public void shouldComposeWork(){
        Function<Integer, Integer> addOneSquare = square.compose(addOne);
        Function<Integer, Integer> squareAddOne = square.andThen(addOne);
        //TODO
        BiFunction<Integer, Integer,Integer> addThenMulti = Math.ADDITION;
        
        log.info(" compose {}",addOneSquare.apply(3));
        log.info(" andThen {}",squareAddOne.apply(3));
        
        
        
        
            
    }
    @Test
    public void s(){
        List<String> numberString = Arrays.asList("12", "34", "82");
        List<Integer> doubleNumbers = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();
        Function<List<String>, List<Integer>> singleFunction = s -> {
            s.stream()
            .forEach(t -> numbers.add(Integer.parseInt(t)));
            return numbers;
            };
            Function<List<String>, List<Integer>> doubleFunction = s -> {
                s.stream()
                .forEach(t -> doubleNumbers.add(
                Integer.parseInt(t) * 2));
                return doubleNumbers;
                };
            log.info("{}",singleFunction.apply(numberString));
           
    }
    
}
