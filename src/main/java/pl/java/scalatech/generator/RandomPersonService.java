package pl.java.scalatech.generator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.Gender;
import pl.java.scalatech.domain.User;
@Slf4j
public class RandomPersonService implements Generator {
  Random random = new Random();
    
  private List<String> lastName = Stream.of(
          "Smith","Johnson", "Wong", "Lu", "Cobain", "Rockerfella", "Armstrong",
          "Alderan", "O'Connel", "Williams", "Brown","Jones","Miller","Garcia",
          "Martin","Moore","White","Jackson","Taylor","Lee","Harris", "Clark",
          "Robinson","Young","King","Scott","Green","Baker","Hill","Edwards"
  ).distinct().collect(Collectors.toList());

  
  

  public Supplier<User> personSupplier(){
    return () -> generate();
  }

  public User generate(){
    User toReturn = new User();
    Path  pathFemale = Paths.get("src/main/resources/female_names.txt");
    Path  pathMale = Paths.get("src/main/resources/male_names.txt");
    
    
    toReturn.setGender( random.nextBoolean() ? Gender.FEMALE : Gender.MALE);
   // toReturn.setFirstname(toReturn.getGender() == Gender.FEMALE ? random(femaleName) : random(maleName));
    toReturn.setLastName(random(lastName));
    toReturn.setBirthDate(generateBirthDate());  
    return toReturn;
  }

  public Stream<User> generate(Integer count){
    return generate(count.longValue());
  }

  public Stream<User> generate(Long count)
  {
    return LongStream.range(0,count).mapToObj(x->generate());
  }

  public LocalDate generateBirthDate(){
    return LocalDate.now()
            .minus(16l, ChronoUnit.YEARS )
            .minus(random.nextInt( 365 * 50),ChronoUnit.DAYS);
  }

  public String random(List<String> list){
    return list.get(random.nextInt(list.size()));
  }
  @Test
  public void test(){
      RandomPersonService rps= new RandomPersonService();
      List<User> users = rps.generate(20).collect(Collectors.toList());
      log.info(".... {}",users);
  }

@Override
public void generateValue() {
    // TODO Auto-generated method stub
    
}
}