package json.read;

import java.util.List;

public interface ReadJSON<T> {
    List<T> read(String pathToFile);
}
