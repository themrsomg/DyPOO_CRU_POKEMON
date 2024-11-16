package gui;

import DAO.UserDAO;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.condition.*;
import org.mockito.Mockito;
import javax.swing.*;
import java.awt.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import DAO.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginFormTest {

    private LoginForm loginForm;
    private UserDAO mockUserDAO;

    @Before
    public void setUp() {
        mockUserDAO = mock(UserDAO.class);
        loginForm = new LoginForm() {
            {
                this.userDAO = mockUserDAO;
            }
        };
        loginForm.setVisible(false); // No need to display the form during tests
    }

    @Test
    public void testSuccessfulLogin() {
        when(mockUserDAO.authenticate("validUser", "validPass")).thenReturn(true);

        loginForm.usernameField.setText("validUser");
        loginForm.passwordField.setText("validPass");

        loginForm.new LoginAction().actionPerformed(null);

        assertEquals("Inicio de sesión exitoso", loginForm.messageLabel.getText());
        verify(mockUserDAO, times(1)).authenticate("validUser", "validPass");
    }

    @Test
    public void testUnsuccessfulLogin() {
        when(mockUserDAO.authenticate("invalidUser", "invalidPass")).thenReturn(false);

        loginForm.usernameField.setText("invalidUser");
        loginForm.passwordField.setText("invalidPass");

        loginForm.new LoginAction().actionPerformed(null);

        assertEquals("Usuario o contraseña incorrectos", loginForm.messageLabel.getText());
        verify(mockUserDAO, times(1)).authenticate("invalidUser", "invalidPass");
    }

    @Test
    public void testEmptyFields() {
        loginForm.usernameField.setText("");
        loginForm.passwordField.setText("");

        loginForm.new LoginAction().actionPerformed(null);

        assertEquals("Usuario o contraseña incorrectos", loginForm.messageLabel.getText());
        verify(mockUserDAO, times(1)).authenticate("", "");
    }
}
