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
package be.rubus.atbash.jsf.architecture.backend.boundary;

import be.rubus.atbash.jsf.architecture.backend.model.Todo;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Stateless
public class TodoBoundary {

    @PersistenceContext
    private EntityManager em;

    public Todo save(Todo todo) {
        em.persist(todo);
        return todo;
    }

    public void delete(Long todoId) {
        Todo t = em.getReference(Todo.class, todoId);
        em.remove(t);
    }

    public void update(Todo todo) {
        em.merge(todo);
    }

    public List<Todo> allTodos() {
        return (List<Todo>) em.createQuery("select t from Todo t")
                .getResultList();
    }

    public List<Todo> findTodos(Predicate<Todo> filterForTodo) {
        return allTodos().stream().filter(filterForTodo).collect(Collectors.toList());
    }

}
