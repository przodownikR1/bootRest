package pl.java.scalatech;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.Test;

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
    
}
