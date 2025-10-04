package e45tm3d.pit.utils.functions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClassFunction extends ClassLoader {
    public Class<?> loadClassFromFile(String className, String filePath) {
        try {

            byte[] classData = Files.readAllBytes(Paths.get(filePath));

            return defineClass(className, classData, 0, classData.length);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}