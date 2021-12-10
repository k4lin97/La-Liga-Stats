package json.save;

import java.util.List;

public interface SaveJSON<T> {
    void save(List<T> listToSave);
}
