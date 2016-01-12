package pl.java.scalatech.refactoringSample;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Random;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class SimpleConstantTest {
    private static final int MAX_ITER = 4;
    private static final List<String> people = newArrayList("slawke","tomek","gosia","kasia","opla");
    final Random r = new Random();
    @Test
    public void shouldConstantRef() {


        for (int iter = 0; iter < MAX_ITER; iter++) {
            log.info("{}",iter);
        }
        String person = people.get(getPerson());
    }
    private int getPerson() {
        int nextInt = r.nextInt(people.size()-1);
        return nextInt;
    }

}
