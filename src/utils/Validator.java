package utils;

import java.util.regex.Pattern;

/**
 *
 * @author Willian Coral
 */
public class Validator {
    
    // Validar nombre de usuario: Mínimo 8 caracteres, máximo 20, al menos 1 mayúscula
    public static String validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return "El nombre de usuario no puede estar vacío";
        }
        
        if (username.length() < 8) {
            return "El nombre de usuario debe tener al menos 8 caracteres";
        }
        
        if (username.length() > 20) {
            return "El nombre de usuario no puede tener más de 20 caracteres";
        }
        
        if (!Pattern.compile("[A-Z]").matcher(username).find()) {
            return "El nombre de usuario debe contener al menos 1 letra mayúscula";
        }
        
        return null; // No hay errores
    }
    
    // Validar contraseña: Mínimo 1 dígito, 1 mayúscula, 1 minúscula, 1 carácter especial
    public static String validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return "La contraseña no puede estar vacía";
        }
        
        if (password.length() < 8) {
            return "La contraseña debe tener al menos 8 caracteres";
        }
        
        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            return "La contraseña debe contener al menos 1 dígito numérico (0-9)";
        }
        
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            return "La contraseña debe contener al menos 1 letra mayúscula (A-Z)";
        }
        
        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            return "La contraseña debe contener al menos 1 letra minúscula (a-z)";
        }
        
        if (!Pattern.compile("[@#$%.-]").matcher(password).find()) {
            return "La contraseña debe contener al menos 1 carácter especial (@ # $ % . -)";
        }
        
        return null; // No hay errores
    }
}
