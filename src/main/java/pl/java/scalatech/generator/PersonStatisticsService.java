package pl.java.scalatech.generator;

import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pl.java.scalatech.domain.Gender;
import pl.java.scalatech.domain.User;

public class PersonStatisticsService {

    public Map<Integer, Long> ageDistribution(Stream<User> personStream) {
        return personStream.map(User.age()).collect(groupingBy(x -> x, counting()));
    }

    public Map<Gender, Long> genderDistribution(Stream<User> personStream) {
        return personStream.collect(groupingBy(User::getGender, counting()));
    }

    public Map<Gender, LocalDate> averageBirthDateByGender(Stream<User> person) {
        return person.collect(groupingBy(User::getGender, averagingInt(x -> User.daysSinceBirth().apply(x)))).entrySet().stream()
                .collect(Collectors.toMap(x -> x.getKey(), x -> LocalDate.now().minus(x.getValue().longValue(), ChronoUnit.DAYS)));
    }

    public LocalDate averageBirthdate(Stream<User> persons) {
        Double avg = persons.map(User.daysSinceBirth()).mapToInt(x -> x).average().getAsDouble();
        return LocalDate.now().minus(avg.longValue(), ChronoUnit.DAYS);
    }

}