package pl.java.scalatech.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ReadCars {

    public Stream<Car> readCar(String filename) throws IOException {
        return Files.lines(Paths.get(filename)).map(l -> l.split(", ")).map(a -> new Car(a[0], a[1], Integer.parseInt(a[2])));
    }

}
