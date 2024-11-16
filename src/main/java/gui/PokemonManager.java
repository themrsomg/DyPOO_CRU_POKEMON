package gui;

import DAO.PokemonDAO;
import gui.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PokemonManager extends JFrame {

    private DefaultListModel<String> pokemonListModel;
    JList<String> pokemonList;
    JTextField searchField;
    PokemonDAO pokemonDAO;

    public PokemonManager() {
        pokemonDAO = new PokemonDAO();
        setTitle("Gestión de Pokémon");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pokemonListModel = new DefaultListModel<>();
        pokemonList = new JList<>(pokemonListModel);
        searchField = new JTextField();

        initComponents();
        loadInitialPokemons();
    }

    private void initComponents() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Buscar Pokémon:"), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);

        JButton addButton = new JButton("Agregar");
        JButton updateButton = new JButton("Actualizar");
        JButton deleteButton = new JButton("Eliminar");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(pokemonList), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        searchField.addActionListener(e -> filterPokemonList());
        addButton.addActionListener(e -> addPokemon());
        updateButton.addActionListener(e -> updatePokemon());
        deleteButton.addActionListener(e -> deletePokemon());
    }

    void loadInitialPokemons() {
        List<String> pokemons = pokemonDAO.getAllPokemons();
        pokemonListModel.clear();
        pokemons.forEach(pokemonListModel::addElement);
    }

    void filterPokemonList() {
        String filterText = searchField.getText().trim().toLowerCase();
        pokemonListModel.clear();
        List<String> allPokemons = pokemonDAO.getAllPokemons();

        for (String pokemon : allPokemons) {
            if (pokemon.toLowerCase().contains(filterText)) {
                pokemonListModel.addElement(pokemon);
            }
        }
        if (pokemonListModel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron Pokémon con ese criterio.",
                    "Filtro vacío", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    void addPokemon() {
        String newPokemon = JOptionPane.showInputDialog(this, "Ingrese el nombre del nuevo Pokémon:");
        if (newPokemon != null && !newPokemon.trim().isEmpty()) {
            pokemonDAO.addPokemon(newPokemon.trim());
            loadInitialPokemons();
        }
    }

    void updatePokemon() {
        String selectedPokemon = pokemonList.getSelectedValue();
        if (selectedPokemon != null) {
            String updatedPokemon = JOptionPane.showInputDialog(this, "Actualizar nombre de Pokémon:", selectedPokemon);
            if (updatedPokemon != null && !updatedPokemon.trim().isEmpty()) {
                pokemonDAO.updatePokemon(selectedPokemon, updatedPokemon.trim());
                loadInitialPokemons();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un Pokémon para actualizar.");
        }
    }

    void deletePokemon() {
        String selectedPokemon = pokemonList.getSelectedValue();
        if (selectedPokemon != null) {
            pokemonDAO.deletePokemon(selectedPokemon);
            loadInitialPokemons();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un Pokémon para eliminar.");
        }
    }
}

