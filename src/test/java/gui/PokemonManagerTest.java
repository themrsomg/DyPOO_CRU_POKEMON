package gui;

import DAO.PokemonDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import javax.swing.*;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PokemonManagerTest {

    private PokemonManager pokemonManager;
    private PokemonDAO mockPokemonDAO;

    @BeforeEach
    public void setUp() {
        mockPokemonDAO = mock(PokemonDAO.class);
        pokemonManager = new PokemonManager() {
            {
                this.pokemonDAO = mockPokemonDAO;
            }
        };
    }

    @Test
    public void testLoadInitialPokemons() {
        when(mockPokemonDAO.getAllPokemons()).thenReturn(Arrays.asList("Pikachu", "Charmander", "Bulbasaur"));
        pokemonManager.loadInitialPokemons();
        DefaultListModel<String> model = (DefaultListModel<String>) pokemonManager.pokemonList.getModel();
        assertEquals(3, model.size());
        assertEquals("Pikachu", model.getElementAt(0));
        assertEquals("Charmander", model.getElementAt(1));
        assertEquals("Bulbasaur", model.getElementAt(2));
    }

    @Test
    public void testFilterPokemonList() {
        when(mockPokemonDAO.getAllPokemons()).thenReturn(Arrays.asList("Pikachu", "Charmander", "Bulbasaur"));
        pokemonManager.loadInitialPokemons();
        pokemonManager.searchField.setText("char");
        pokemonManager.filterPokemonList();
        DefaultListModel<String> model = (DefaultListModel<String>) pokemonManager.pokemonList.getModel();
        assertEquals(1, model.size());
        assertEquals("Charmander", model.getElementAt(0));
    }

    @Test
    public void testAddPokemon() {
        when(mockPokemonDAO.getAllPokemons()).thenReturn(Arrays.asList("Pikachu", "Charmander"));
        doAnswer(invocation -> {
            pokemonManager.loadInitialPokemons();
            return null;
        }).when(mockPokemonDAO).addPokemon(anyString());

        pokemonManager.addPokemon();

        verify(mockPokemonDAO, times(1)).addPokemon(anyString());
    }

    @Test
    public void testUpdatePokemon() {
        when(mockPokemonDAO.getAllPokemons()).thenReturn(Arrays.asList("Pikachu", "Charmander"));
        doAnswer(invocation -> {
            pokemonManager.loadInitialPokemons();
            return null;
        }).when(mockPokemonDAO).updatePokemon(anyString(), anyString());

        pokemonManager.pokemonList.setSelectedIndex(0);
        pokemonManager.updatePokemon();

        verify(mockPokemonDAO, times(1)).updatePokemon(anyString(), anyString());
    }

    @Test
    public void testDeletePokemon() {
        when(mockPokemonDAO.getAllPokemons()).thenReturn(Arrays.asList("Pikachu", "Charmander"));
        doAnswer(invocation -> {
            pokemonManager.loadInitialPokemons();
            return null;
        }).when(mockPokemonDAO).deletePokemon(anyString());

        pokemonManager.pokemonList.setSelectedIndex(0);
        pokemonManager.deletePokemon();

        verify(mockPokemonDAO, times(1)).deletePokemon(anyString());
    }
}
