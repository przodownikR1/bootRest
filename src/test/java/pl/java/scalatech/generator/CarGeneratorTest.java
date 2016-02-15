package pl.java.scalatech.generator;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class CarGeneratorTest {
    @Test
    @SneakyThrows    
    public void readline(){
        Path  path= Paths.get("src/main/resources/car.txt");
        log.info("{}",path.toFile().getAbsolutePath());
        log.info("{}",path.toFile().length());
         Generator carGenerator = new CarGenerator();
         carGenerator.readLineByLine(path, s-> s.length()>1,log, l-> log.info("{}",l)); 
    }
}
