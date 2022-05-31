package ru.appline.controller;

import org.springframework.web.bind.annotation.*;
import ru.appline.logic.Pet;
import ru.appline.logic.PetModel;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {
    private static final PetModel petModel = PetModel.getInstance();
    private static final AtomicInteger newId = new AtomicInteger(3);

    @PostMapping(value = "/createPet", consumes = "application/json", produces = "application/json")
    public Map createPet(@RequestBody Pet pet) {
        petModel.add(newId.getAndIncrement(), pet);
        if(petModel.getAll().size() == 1) {
           return Collections.singletonMap("response", "Вы создали своего первого питомца!");
        } else return Collections.singletonMap("response", "Вы создали нового питомца");
    }

    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer, Pet> getAll() {
        return petModel.getAll();
    }

    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
    public Pet getPet(@RequestBody Map<String, Integer> id) {
        return petModel.getFromList(id.get("id"));
    }

    @DeleteMapping (value = "/deletePet", consumes = "application/json", produces = "application/json")
    public Map deletePet(@RequestBody Map<String, Integer> id) {
        petModel.removePet(id.get("id"));
        return Collections.singletonMap("response", "Вы успешно удалили питомца");
    }

    @PutMapping (value = "/replacePet", consumes = "application/json", produces = "application/json")
    public Map replacePet(@RequestBody Map<String, String> map) {
        int id = Integer.parseInt(map.get("id"));
        if(id <= petModel.getAll().size()) {
            String name = map.get("name");
            String type = map.get("type");
            int age = Integer.parseInt(map.get("age"));
            petModel.replacePet(id, name, type, age);
            return Collections.singletonMap("response", "Вы успешно заменили питомца");
        } else return Collections.singletonMap("response", "Такого ID в базе нет :(");
    }
}
