package Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ib.Tim25_IB.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<User> getAllUsers() throws IOException {
        File file = new File("users.json");
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(file, new TypeReference<List<User>>() {});
    }

    public void save(List<User> users) throws IOException {
        File file = new File("users.json");
        objectMapper.writeValue(file, users);
    }
}
