package ManejoExcepciones.Practice1;

public class FormValidator {
    public static void validateUsername(String username){
        if (username == null || username.isBlank()){
            throw new EmptyFieldException("username");
        }
        if (username.contains(" ")){
            throw new InvalidFormatException(username,String.format("El campo %s no puede contener espacios",username),username);
        }
        if (username.length() < 3){
            throw new InvalidFormatException(username,String.format("El campo %s debe contener mas de 3 caracteres",username),username);
        }
    }

    public static void validateEmail(String email){
        if (email.isEmpty()){
            throw new EmptyFieldException("email");
        }
        if (!email.contains("@") || !email.contains(".")){
            throw new InvalidFormatException(email,String.format("El campo %s debe contener @ y .", email),email);
        }
    }

    public static void validateAge (int age){
        if (age < 18 || age > 120){
            throw new OutOfRangeException("Age",age,18,120);
        }
    }

    public static void validatePassword (String password){
        if (password.isEmpty()){
            throw new EmptyFieldException("password");
        }
        if (password.length() < 8) {
            throw new InvalidFormatException("password", "Debe tener al menos 8 caracteres", password);
        }
        if (password.chars().noneMatch(Character::isDigit)) {
            throw new InvalidFormatException("password", "Debe contener al menos un número", password);
        }
    }

    public static UserForm validateForm (String username, String email, int age, String password) {
        validateUsername(username);
        validateEmail(email);
        validateAge(age);
        validatePassword(password);
        return new UserForm(username,email,age,password);
    }
}
