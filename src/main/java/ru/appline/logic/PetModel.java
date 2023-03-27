package ru.appline.logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PetModel implements Serializable {

    private static final PetModel instance = new PetModel();
    private final Map<Integer, Pet> model;

    public PetModel() {
        model = new HashMap<Integer, Pet>();
        model.put(1, new Pet("Jack", "Dog", 12));
        model.put(2, new Pet("Max", "Dog", 2));
    }

    public static PetModel getInstance() {
        return instance;
    }

    public void add(int id, Pet pet) {
        model.put(id, pet);
    }

    public Pet getFromList(int id) {
        return model.get(id);
    }

    public Map<Integer, Pet> getAll() {
        return model;
    }

    public void replacePet(int id, Pet pet) {
        model.replace(id, pet);
    }

    public void removePet(int id) {
        model.remove(id);
    }
}
