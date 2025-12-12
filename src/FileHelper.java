import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class FileHelper {

    public static List<String> loadFile(String filename) throws IOException {
        return Files.readAllLines(Paths.get(filename));
    }

    public static void saveFile(List<String> list, String filename) throws IOException {
        Files.write(Paths.get(filename), list);
    }
}
