
package DAO;

import static org.junit.jupiter.api.Assertions.*;
import DAO.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDAOTest {

    private UserDAO userDAO;

    @BeforeEach
    public void setUp() {
        userDAO = mock(UserDAO.class);
    }
    @Test
    public void testAuthenticateValidUser() {
        when(userDAO.authenticate("admin", "1234")).thenReturn(true);
        assertTrue(userDAO.authenticate("admin", "1234"));
        verify(userDAO, times(1)).authenticate("admin", "1234");
    }

    @Test
    public void testAuthenticateInvalidUser() {
        when(userDAO.authenticate("user", "wrongpassword")).thenReturn(false);
        assertFalse(userDAO.authenticate("user", "wrongpassword"));
        verify(userDAO, times(1)).authenticate("user", "wrongpassword");
    }
}