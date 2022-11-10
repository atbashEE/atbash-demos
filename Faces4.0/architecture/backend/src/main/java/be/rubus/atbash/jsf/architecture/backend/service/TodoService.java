/*
 * Copyright 2022 Rudy De Busscher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.rubus.atbash.jsf.architecture.backend.service;

import be.rubus.atbash.jsf.architecture.backend.boundary.TodoBoundary;
import be.rubus.atbash.jsf.architecture.backend.exception.TodoAlreadyExistsException;
import be.rubus.atbash.jsf.architecture.backend.exception.TodoNotFoundException;
import be.rubus.atbash.jsf.architecture.backend.model.Todo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TodoService {

    @Inject
    private TodoBoundary todoBoundary;

    public Todo create(Todo todo) {

        todo.setId(null);
        Optional<Todo> todoByTitle = findTodoByTitle(todo.getTitle());
        if (todoByTitle.isPresent()) {
            throw new TodoAlreadyExistsException(todo.getTitle());
        }
        return todoBoundary.save(todo);
    }

    public void delete(Todo todo) {
        todoBoundary.delete(todo.getId());
    }

    public void update(Todo todo) {
        Optional<Todo> todoById = findTodoById(todo.getId());
        if (todoById.isEmpty()) {
            throw new TodoNotFoundException(todo.getId());
        }
        Optional<Todo> todoByTitle = findTodoByTitle(todo.getTitle());
        if (todoByTitle.isPresent() && !todo.getId().equals(todoByTitle.get().getId())) {
            // Todo with other Id and with same title exists
            throw new TodoAlreadyExistsException(todo.getTitle());
        }
        todoBoundary.update(todo);
    }


    public List<Todo> allTodos() {
        return todoBoundary.allTodos();
    }

    public Optional<Todo> findTodoById(Long id) {
        return todoBoundary.findTodos(t -> t.getId().equals(id))
                .stream().findAny();
    }

    public Optional<Todo> findTodoByTitle(String title) {
        return todoBoundary.findTodos(t -> t.getTitle().equals(title))
                .stream().findAny();
    }

}
