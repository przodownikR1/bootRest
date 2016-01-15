import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RenameFile {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get(args[0]);
        Files.walk(path).filter(Files::isRegularFile).forEach(t -> {
            String newFileName = t.toFile().getName().replace('_', '-');
            t.toFile().renameTo(new File(path.toString() + "/" + newFileName));

        });

    }
}
