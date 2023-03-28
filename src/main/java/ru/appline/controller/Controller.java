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
    public Map getAll() {
        System.out.println(petModel.getAll() + " " + petModel.getAll().size());
        if(petModel.getAll().size() == 0) {
            return Collections.singletonMap("response", "У вас нет ни одного питомца!");
        } else
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

    @PutMapping (value = "/replacePet/{id}", consumes = "application/json", produces = "application/json")
    public Map replacePet(@PathVariable int id, @RequestBody Pet pet) {
        petModel.replacePet(id, pet);
        return Collections.singletonMap("response", "Вы успешно заменили питомца");
    }
}
