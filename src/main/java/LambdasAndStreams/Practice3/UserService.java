package LambdasAndStreams.Practice3;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserService {
    Map<Integer,User> users = new HashMap<>();

    public UserService(Map<Integer, User> users) {
        this.users = users;
    }

    // Malo — retorna null si no existe
    public Optional<User> findById(int id){
        return Optional.ofNullable(users.get(id));
    }

    // Malo — retorna null si no tiene dirección
    public Optional<String> getCity(int userId) {
        Optional<User> user = findById(userId);
        return user.map(User::getAddress).map(Address::getCity);
    }

    // Malo — retorna null si email no contiene @
    public Optional<String> getEmailDomain(int userId) {
        Optional<User> user = findById(userId);
        return user.filter(u -> u.getEmail() != null && u.getEmail().contains("@")).map(u1 -> u1.getEmail().split("@")[1]);
    }

    public String getDisplayName (int userId) {
        Optional<User> user = findById(userId);
        return user.map(u -> u.getName() + " (" + u.getEmail() + ")").orElse("Usuario Desconocido");
    }
}
