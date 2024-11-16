package DAO;

import static org.junit.jupiter.api.Assertions.*;
import DAO.PokemonDAO;
import static org.junit.jupiter.api.Assertions.*;
import DatabaseConnection.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import static org.mockito.Mockito .*;

public class PokemonDAOTest {

    private PokemonDAO pokemonDAO;

    @Mock
    private DatabaseConnection databaseConnection;

    @Mock
    private Connection connection;

    @BeforeEach
    public void setUp() throws Exception {
        databaseConnection = mock(DatabaseConnection.class);
        connection = mock(Connection.class);
        when(databaseConnection.getConnection()).thenReturn(connection);
        pokemonDAO = new PokemonDAO();
    }

    @Test
    public void testGetAllPokemons() throws Exception {
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery("SELECT nombre FROM pokemones")).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false); // Simula 2 registros
        when(resultSet.getString("nombre")).thenReturn("Pikachu", "Charmander");

        List<String> pokemons = pokemonDAO.getAllPokemons();
        assertEquals(2, pokemons.size());
        assertEquals("Pikachu", pokemons.get(0));
        assertEquals("Charmander", pokemons.get(1));
    }
    @Test
    public void testAddPokemon() throws Exception {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        when(connection.prepareStatement("INSERT INTO pokemones (nombre) VALUES (?)")).thenReturn(preparedStatement);

        pokemonDAO.addPokemon("Bulbasaur");

        verify(preparedStatement, times(1)).setString(1, "Bulbasaur");
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testUpdatePokemon() throws Exception {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        when(connection.prepareStatement("UPDATE pokemones SET nombre = ? WHERE nombre = ?")).thenReturn(preparedStatement);

        pokemonDAO.updatePokemon("Pikachu", "Raichu");

        verify(preparedStatement, times(1)).setString(1, "Raichu");
        verify(preparedStatement, times(1)).setString(2, "Pikachu");
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testDeletePokemon() throws Exception {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        when(connection.prepareStatement("DELETE FROM pokemones WHERE nombre = ?")).thenReturn(preparedStatement);

        pokemonDAO.deletePokemon("Charmander");

        verify(preparedStatement, times(1)).setString(1, "Charmander");
        verify(preparedStatement, times(1)).executeUpdate();
    }
}
