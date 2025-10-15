package gui;

import javax.swing.*;
import constants.commonConstants;
import java.awt.*;

/**
 *
 * @author Willian Coral
 */
public class LoginFrame extends JFrame {

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
    }

    private void initComponents() {

        //CREACION DEL PANEL 
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(commonConstants.SECONDARY_COLOR);

        //creacion Label titulo
        JLabel titleLabel = new JLabel("Login");

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
        } else {
            logoLabel.setText("Logo no encontrado");
        }

        //creacion Label Nombre de Usuario
        JLabel userNameLabel = new JLabel("Nombre de Usuario");
        userNameLabel.setForeground(commonConstants.TEXT_COLOR);
        userNameLabel.setBounds(30, 265, 400, 25);
        userNameLabel.setFont(new Font("", Font.PLAIN, 18));

        //creación Textfield Nombre de usuario
        JTextField userNameField = new JTextField();
        userNameField.setBackground(commonConstants.PRIMARY_COLOR);
        userNameField.setForeground(commonConstants.TEXT_COLOR);
        userNameField.setFont(new Font("", Font.PLAIN, 24));
        userNameField.setBounds(30, 300, 450, 55);
        
        //creacion Label Contraseña
        JLabel passwordLabel = new JLabel("Contraseña");
        passwordLabel.setForeground(commonConstants.TEXT_COLOR);
        passwordLabel.setBounds(30, 385, 400, 25);
        passwordLabel.setFont(new Font("", Font.PLAIN, 18));

        //creación PasswordField Contraseña
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBackground(commonConstants.PRIMARY_COLOR);
        passwordField.setForeground(commonConstants.TEXT_COLOR);
        passwordField.setFont(new Font("", Font.PLAIN, 24));
        passwordField.setBounds(30, 420, 450, 55);
        
        //Creacion boton Login
        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.setFont(new Font("", Font.BOLD, 18));
        loginButton.setBackground(commonConstants.TEXT_COLOR);
        loginButton.setBounds(125, 520, 250, 55);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        //Creacion label recuperacion de contraseña
        JLabel recoverPasswordLabel = new JLabel("Recuperar Contraseña");
        recoverPasswordLabel.setForeground(commonConstants.LINK_COLOR);
        recoverPasswordLabel.setBounds(125, 600, 250, 25);
        recoverPasswordLabel.setFont(new Font("", Font.PLAIN, 14));
        recoverPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        recoverPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //Añadir componentes al panel
        mainPanel.add(titleLabel);
        mainPanel.add(logoLabel);
        mainPanel.add(userNameLabel);
        mainPanel.add(userNameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(loginButton);
        mainPanel.add(recoverPasswordLabel);

        //Añadir panel al JFrame
        this.add(mainPanel);
    }

    //Cargar path relativo de la imagen
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
