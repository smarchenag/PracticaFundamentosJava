package ManejoExcepciones.Practice4;

import ManejoExcepciones.Practice1.EmptyFieldException;
import ManejoExcepciones.Practice1.InvalidFormatException;
import ManejoExcepciones.Practice1.OutOfRangeException;
import ManejoExcepciones.Practice2.InvalidAmountException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserService {
    private Map<Integer, String[]> users = new HashMap<>();
    private String logFile = "logs.txt";

    public void createUser(int id, String name, String email, int age) {
        if (id <= 0) throw new InvalidAmountException(id);
        if (name == null) throw new EmptyFieldException("name");
        if (!email.contains("@")) throw new InvalidFormatException("email", "Bad Email", email);
        if (age < 0 || age > 150) throw new OutOfRangeException("age", age, 0, 150);
        users.put(id, new String[]{name, email, String.valueOf(age)});
    }

    public Optional<String> getEmail(int id) {
        String[] user = users.get(id);
        if (user == null) return Optional.empty();
        return Optional.of(user[1]);
    }

    public void saveToFile(String data) {
        try (FileWriter fw = new FileWriter(logFile, true)) {
            fw.write(data);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getUserAge(int id) {
        String age = users.get(id)[2];
        return Integer.parseInt(age);
    }

    public boolean isAdult(int id) {
        return getUserAge(id) >= 18;
    }
}
