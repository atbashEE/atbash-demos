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
package be.rubus.atbash.jsf.architecture.app.view;

import be.rubus.atbash.jsf.architecture.backend.exception.TodoNotFoundException;
import be.rubus.atbash.jsf.architecture.backend.model.Todo;
import be.rubus.atbash.jsf.architecture.backend.service.TodoService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Optional;

@Named
@RequestScoped
public class TodoEditBean {

    @Inject
    private TodoService todoService;

    private Todo editTodo = new Todo();

    public Todo getEditTodo() {
        return editTodo;
    }

    public String getActionMessage() {
        return editTodo.getId() == null ? "Create" : "Update";
    }

    public void save() {
        if (editTodo.getId() == null) {
            todoService.create(editTodo);
        } else {
            todoService.update(editTodo);
        }
    }

    public void edit(Long selectedId) {

        Optional<Todo> todoById = todoService.findTodoById(selectedId);
        if (todoById.isEmpty()) {
            // Should not happen
            throw new TodoNotFoundException(selectedId);
        }
        editTodo = todoById.get();
    }

    public void done(Long selectedId) {

        Optional<Todo> todoById = todoService.findTodoById(selectedId);
        if (todoById.isEmpty()) {
            // Should not happen
            throw new TodoNotFoundException(selectedId);
        }
        Todo todo = todoById.get();
        todo.setDone(true);
        todoService.update(todo);
    }
}
