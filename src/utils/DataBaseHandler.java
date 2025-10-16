package utils;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author Willian Coral
 */
public class DataBaseHandler {

    private static final String DB_FILE = "users.json";

    //USUARIO POR DEFECTO
    private static final String DEFAULT_USERNAME = "Admin123";
    private static final String DEFAULT_PASSWORD = "Admin123.";
    private static final String DEFAULT_EMAIL = "admin@gmail.com";
    private static final String DEFAULT_RECOVERY_CODE = "REC123456"; // Código por defecto

    // Inicializar el archivo JSON si no existe
    static {
        initializeDatabase();
    }

    private static void initializeDatabase() {
        try {
            File file = new File(DB_FILE);

            if (!file.exists()) {
                // Crear el directorio padre si no existe
                File parentDir = file.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }

                // Crear un archivo JSON con el usuario base
                JSONArray usersArray = new JSONArray();

                // Crear usuario base
                JSONObject defaultUser = new JSONObject();
                defaultUser.put("username", DEFAULT_USERNAME);
                defaultUser.put("password", DEFAULT_PASSWORD);
                defaultUser.put("email", DEFAULT_EMAIL);
                defaultUser.put("recoveryCode", DEFAULT_RECOVERY_CODE);
                defaultUser.put("isDefault", true);

                usersArray.put(defaultUser);

                Files.write(Paths.get(DB_FILE), usersArray.toString().getBytes(), StandardOpenOption.CREATE);
            } else {
                // Verificar si el archivo existe pero está vacío o corrupto
                ensureDefaultUserExists();
            }

        } catch (IOException e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }

    // Asegurar que el usuario base siempre exista
    private static void ensureDefaultUserExists() {
        try {
            JSONArray users = readUsers();
            boolean defaultUserExists = false;

            // Buscar si el usuario base existe
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                if (user.has("isDefault") && user.getBoolean("isDefault")) {
                    defaultUserExists = true;
                    // Asegurar que tenga recoveryCode
                    if (!user.has("recoveryCode")) {
                        user.put("recoveryCode", DEFAULT_RECOVERY_CODE);
                        writeUsers(users);
                    }
                    break;
                }
            }

            // Si no existe el usuario base, crearlo
            if (!defaultUserExists) {
                JSONObject defaultUser = new JSONObject();
                defaultUser.put("username", DEFAULT_USERNAME);
                defaultUser.put("password", DEFAULT_PASSWORD);
                defaultUser.put("email", DEFAULT_EMAIL);
                defaultUser.put("recoveryCode", DEFAULT_RECOVERY_CODE);
                defaultUser.put("isDefault", true);

                users.put(defaultUser);
                writeUsers(users);
            }

        } catch (Exception e) {
            System.err.println("Error al verificar usuario base: " + e.getMessage());
        }
    }

    // Leer todos los usuarios del archivo JSON
    private static JSONArray readUsers() {
        try {
            File file = new File(DB_FILE);
            if (!file.exists()) {
                initializeDatabase();
            }

            String content = new String(Files.readAllBytes(Paths.get(DB_FILE)));

            if (content.trim().isEmpty()) {
                initializeDatabase();
                content = new String(Files.readAllBytes(Paths.get(DB_FILE)));
            }

            return new JSONArray(content);
        } catch (IOException e) {
            System.err.println("Error al leer usuarios: " + e.getMessage());
            return new JSONArray();
        } catch (Exception e) {
            System.err.println("Error inesperado al leer JSON: " + e.getMessage());
            try {
                initializeDatabase();
                return new JSONArray();
            } catch (Exception ex) {
                return new JSONArray();
            }
        }
    }

    // Escribir usuarios al archivo JSON
    private static void writeUsers(JSONArray users) {
        try {
            Files.write(Paths.get(DB_FILE), users.toString(4).getBytes(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("Error al escribir usuarios: " + e.getMessage());
        }
    }

    // Verificar si un usuario existe
    public static boolean userExists(String username) {
        JSONArray users = readUsers();
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.getString("username").equals(username)) {
                return true;
            }
        }
        return false;
    }

    // Obtener email de un usuario
    public static String getUserEmail(String username) {
        JSONArray users = readUsers();
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.getString("username").equals(username)) {
                return user.getString("email");
            }
        }
        return null;
    }

    // Obtener código de recuperación de un usuario
    public static String getRecoveryCode(String username) {
        JSONArray users = readUsers();
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.getString("username").equals(username)) {
                return user.getString("recoveryCode");
            }
        }
        return null;
    }

    // Validar código de recuperación
    public static boolean validateRecoveryCode(String username, String recoveryCode) {
        JSONArray users = readUsers();
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.getString("username").equals(username)
                    && user.getString("recoveryCode").equals(recoveryCode)) {
                return true;
            }
        }
        return false;
    }

    // Validar credenciales de usuario
    public static boolean validateUser(String username, String password) {
        JSONArray users = readUsers();
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.getString("username").equals(username)
                    && user.getString("password").equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Obtener credenciales del usuario base
    public static String[] getDefaultCredentials() {
        return new String[]{DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_EMAIL, DEFAULT_RECOVERY_CODE};
    }

    // Cambiar contraseña de un usuario
    public static boolean changePassword(String username, String newPassword) {
        try {
            JSONArray users = readUsers();
            boolean userFound = false;

            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                if (user.getString("username").equals(username)) {
                    // Actualizar la contraseña
                    user.put("password", newPassword);
                    userFound = true;
                    break;
                }
            }

            if (userFound) {
                writeUsers(users);
                return true;
            }

            return false; // Usuario no encontrado

        } catch (Exception e) {
            System.err.println("Error al cambiar contraseña: " + e.getMessage());
            return false;
        }
    }

    // Imprimir todos los usuarios (Para el testeo del login)
    public static void printAllUsers() {
        JSONArray users = readUsers();
        System.out.println("=== USUARIOS REGISTRADOS ===");
        System.out.println("Total de usuarios: " + users.length());

        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            System.out.println((i + 1) + ". " + " Usuario: " + user.getString("username")
                    + ", Email: " + user.getString("email"));
        }
        System.out.println("============================");
    }
}
