package gui;

import javax.swing.*;
import constants.commonConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import utils.Validator;
import utils.DataBaseHandler;

/**
 *
 * @author Willian Coral
 */
public class LoginFrame extends JFrame {

    //Declaracion global de los Fields
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JToggleButton togglePasswordButton;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public LoginFrame() {
        //CONFIGURACIÓN DEL FRAME
        this.setTitle("Login");
        this.setSize(520, 680);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        //INICIALIZAR COMPONENTES DE LA GUI
        initComponents();

        this.setVisible(true);
        DataBaseHandler.printAllUsers(); //MOSTRAR USUARIOS POR CONSOLA PARA EL TESTEO DEL LOGIN
    }

    private void initComponents() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Crear los paneles
        JPanel loginPanel = createLoginPanel();
        JPanel recoveryPanel = createRecoveryPanel();

        // Agregar paneles al cardPanel
        cardPanel.add(loginPanel, "LOGIN");
        cardPanel.add(recoveryPanel, "RECOVERY");

        // Mostrar el panel de login por defecto
        cardLayout.show(cardPanel, "LOGIN");

        // Añadir cardPanel al JFrame
        this.add(cardPanel);
    }

    //CREAR LOGIN PANEL
    private JPanel createLoginPanel() {
        //CREACION DEL PANEL DE LOGIN
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBackground(commonConstants.SECONDARY_COLOR);

        //creacion Label titulo
        JLabel titleLabel = new JLabel("Sistema");

        titleLabel.setForeground(commonConstants.TEXT_COLOR);
        titleLabel.setBounds(0, 25, 520, 100);
        titleLabel.setFont(new Font("Title", Font.BOLD, 50));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //creación label logo
        JLabel logoLabel = new JLabel();
        logoLabel.setBounds(0, 120, 520, 120);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon icon = loadImageIcon("resources/logo3.png");

        if (icon != null) {
            //Reescalar imagen
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledImg));
            this.setIconImage(loadImageIcon("resources/logo2.png").getImage());
        } else {
            logoLabel.setText("Logo no encontrado");
        }

        //creacion Label Nombre de Usuario
        JLabel userNameLabel = new JLabel("Nombre de Usuario");
        userNameLabel.setForeground(commonConstants.TEXT_COLOR);
        userNameLabel.setBounds(30, 265, 400, 25);
        userNameLabel.setFont(new Font("", Font.PLAIN, 18));

        //creación Textfield Nombre de usuario
        userNameField = new JTextField();
        userNameField.setBackground(commonConstants.PRIMARY_COLOR);
        userNameField.setForeground(commonConstants.TEXT_COLOR);
        userNameField.setFont(new Font("", Font.PLAIN, 24));
        userNameField.setBounds(30, 300, 450, 55);
        userNameField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        //creacion Label Contraseña
        JLabel passwordLabel = new JLabel("Contraseña");
        passwordLabel.setForeground(commonConstants.TEXT_COLOR);
        passwordLabel.setBounds(30, 385, 400, 25);
        passwordLabel.setFont(new Font("", Font.PLAIN, 18));

        // Panel contenedor para el password field y el botón
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BorderLayout());
        passwordPanel.setBackground(commonConstants.PRIMARY_COLOR);
        passwordPanel.setBounds(30, 420, 450, 55);
        passwordPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        //creación PasswordField Contraseña
        passwordField = new JPasswordField();
        passwordField.setBackground(commonConstants.PRIMARY_COLOR);
        passwordField.setForeground(commonConstants.TEXT_COLOR);
        passwordField.setFont(new Font("", Font.PLAIN, 24));
        passwordField.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        // Botón para mostrar/ocultar contraseña
        togglePasswordButton = new JToggleButton();
        togglePasswordButton.setPreferredSize(new Dimension(50, 55));
        togglePasswordButton.setBackground(commonConstants.PRIMARY_COLOR);
        togglePasswordButton.setBorder(BorderFactory.createEmptyBorder());
        togglePasswordButton.setFocusPainted(false);
        togglePasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        togglePasswordButton.setContentAreaFilled(false); 
        togglePasswordButton.setOpaque(true); 

        // Cargar iconos para mostrar/ocultar contraseña
        ImageIcon hideIcon = loadImageIcon("resources/hide_password.png");
        ImageIcon showIcon = loadImageIcon("resources/show_password.png");

        if (hideIcon != null && showIcon != null) {
            // Reescalar iconos
            Image hideImg = hideIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            Image showImg = showIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

            togglePasswordButton.setIcon(new ImageIcon(showImg));
            togglePasswordButton.setSelectedIcon(new ImageIcon(hideImg));

        } else {
            togglePasswordButton.setText("👁");
            togglePasswordButton.setFont(new Font("", Font.PLAIN, 16));
        }

        // Agregar ActionListener al botón de mostrar/ocultar
        togglePasswordButton.addActionListener(this::handleTogglePasswordAction);

        // Agregar componentes al panel de contraseña
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.add(togglePasswordButton, BorderLayout.EAST);

        //Creacion boton Login
        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.setFont(new Font("", Font.BOLD, 18));
        loginButton.setBackground(commonConstants.TEXT_COLOR);
        loginButton.setBounds(125, 520, 250, 55);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Agregar ActionListener usando método de referencia
        loginButton.addActionListener(this::handleLoginAction);

        //Creacion label recuperacion de contraseña
        JLabel recoverPasswordLabel = new JLabel("Recuperar Contraseña");
        recoverPasswordLabel.setForeground(commonConstants.LINK_COLOR);
        recoverPasswordLabel.setBounds(125, 600, 250, 25);
        recoverPasswordLabel.setFont(new Font("", Font.PLAIN, 14));
        recoverPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        recoverPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Agregar MouseListener para cambiar al panel de recuperación
        recoverPasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cardPanel, "RECOVERY");
            }
        });

        //Añadir componentes al panel
        loginPanel.add(titleLabel);
        loginPanel.add(logoLabel);
        loginPanel.add(userNameLabel);
        loginPanel.add(userNameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordPanel);
        loginPanel.add(loginButton);
        loginPanel.add(recoverPasswordLabel);

        return loginPanel;
    }

    //CREAR RECOVERY PANEL
    private JPanel createRecoveryPanel() {
        //CREACION DEL PANEL DE RECUPERACIÓN
        JPanel recoveryPanel = new JPanel();
        recoveryPanel.setLayout(null);
        recoveryPanel.setBackground(commonConstants.SECONDARY_COLOR);

        //creacion Label titulo
        JLabel titleLabel = new JLabel("Sistema");
        titleLabel.setForeground(commonConstants.TEXT_COLOR);
        titleLabel.setBounds(0, 25, 520, 100);
        titleLabel.setFont(new Font("Title", Font.BOLD, 50));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //creación label logo
        JLabel logoLabel = new JLabel();
        logoLabel.setBounds(0, 120, 520, 120);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon icon = loadImageIcon("resources/logo3.png");

        if (icon != null) {
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledImg));
        } else {
            logoLabel.setText("Logo no encontrado");
        }

        //creacion Label Usuario o Email
        JLabel userEmailLabel = new JLabel("Usuario o Email");
        userEmailLabel.setForeground(commonConstants.TEXT_COLOR);
        userEmailLabel.setBounds(30, 265, 400, 25);
        userEmailLabel.setFont(new Font("", Font.PLAIN, 18));

        //creación Textfield Usuario o Email
        JTextField userEmailField = new JTextField();
        userEmailField.setBackground(commonConstants.PRIMARY_COLOR);
        userEmailField.setForeground(commonConstants.TEXT_COLOR);
        userEmailField.setFont(new Font("", Font.PLAIN, 24));
        userEmailField.setBounds(30, 300, 450, 55);

        //Creacion boton Recuperar
        JButton recoverButton = new JButton("Recuperar Contraseña");
        recoverButton.setFont(new Font("", Font.BOLD, 18));
        recoverButton.setBackground(commonConstants.TEXT_COLOR);
        recoverButton.setBounds(125, 400, 250, 55);
        recoverButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Agregar ActionListener al botón de recuperación
        recoverButton.addActionListener(this::handleRecoveryAction);

        //Creacion label volver al login
        JLabel backToLoginLabel = new JLabel("Volver al Login");
        backToLoginLabel.setForeground(commonConstants.LINK_COLOR);
        backToLoginLabel.setBounds(125, 480, 250, 25);
        backToLoginLabel.setFont(new Font("", Font.PLAIN, 14));
        backToLoginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backToLoginLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Agregar MouseListener para volver al panel de login
        backToLoginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cardPanel, "LOGIN");
            }
        });

        //Añadir componentes al panel
        recoveryPanel.add(titleLabel);
        recoveryPanel.add(logoLabel);
        recoveryPanel.add(userEmailLabel);
        recoveryPanel.add(userEmailField);
        recoveryPanel.add(recoverButton);
        recoveryPanel.add(backToLoginLabel);

        return recoveryPanel;
    }

    // Método manejador del ActionListener para realizar el login
    private void handleLoginAction(ActionEvent e) {
        String username = userNameField.getText().trim();
        String password = new String(passwordField.getPassword());

        // Validar campos vacíos
        if (username.isEmpty() || password.isEmpty()) {
            showWarningMessage("Por favor, complete todos los campos", "Campos Incompletos");
            return;
        }

        // Validar formato del username
        String usernameError = Validator.validateUsername(username);
        if (usernameError != null) {
            showErrorMessage(usernameError, "Error en Usuario");
            return;
        }

        // Validar formato de la contraseña
        String passwordError = Validator.validatePassword(password);
        if (passwordError != null) {
            showErrorMessage(passwordError, "Error en Contraseña");
            return;
        }

        // Verificar si el usuario existe
        if (DataBaseHandler.userExists(username)) {

            // Validar credenciales
            if (DataBaseHandler.validateUser(username, password)) {
                showSuccessMessage("¡Inicio de sesión exitoso!", "Éxito");
                clearFields();

            } else {
                showErrorMessage("Contraseña incorrecta", "Error de Autenticación");
            }
        } else {
            showWarningMessage("El usuario no está registrado en el sistema", "Usuario No Encontrado");
        }
    }

    // Método manejador del ActionListener para recuperación
    private void handleRecoveryAction(ActionEvent e) {
        // Aquí implementarás la lógica de recuperación de contraseña
        showSuccessMessage("a", "Recuperación");
    }

    // Método para mostrar/ocultar contraseña
    private void handleTogglePasswordAction(ActionEvent e) {
        if (togglePasswordButton.isSelected()) {
            // Mostrar contraseña
            passwordField.setEchoChar((char) 0); // Carácter nulo para mostrar texto
        } else {
            // Ocultar contraseña
            passwordField.setEchoChar('•'); // Carácter de punto para ocultar
        }
    }

    // Método para limpiar los campos
    private void clearFields() {
        userNameField.setText("");
        passwordField.setText("");
    }

    // Métodos auxiliares para mostrar mensajes
    private void showWarningMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
    }

    private void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    //Cargar path relativo del logo
    private ImageIcon loadImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("No se pudo cargar la imagen: " + path);
            return null;
        }
    }

}
