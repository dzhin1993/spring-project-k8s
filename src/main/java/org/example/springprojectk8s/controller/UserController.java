package org.example.springprojectk8s.controller;

import lombok.AllArgsConstructor;
import org.example.springprojectk8s.model.User;
import org.example.springprojectk8s.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserRepository userRepository;

    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable("id") long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void create(@RequestBody User user) {
        userRepository.save(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable("id") long id, @RequestBody User user) {
        if (user.getId() == null) {
            user.setId(id);
        }
        if (user.getId() != id) {
            throw new ResponseStatusException(BAD_REQUEST);
        }
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND);
        }
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        if (userRepository.deleteById(id) == 0) {
            throw new ResponseStatusException(NOT_FOUND);
        }
    }
}
