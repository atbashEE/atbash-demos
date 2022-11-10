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
package be.rubus.atbash.jsf.architecture.rest;

import be.rubus.atbash.jsf.architecture.backend.model.Todo;
import be.rubus.atbash.jsf.architecture.backend.service.TodoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

@Path("/todo")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {

    @Inject
    private TodoService todoService;

    @GET
    public List<Todo> getAll() {
        return todoService.allTodos();
    }

    @GET
    @Path("/{id}")
    public Todo getById(@PathParam("id") Long id) {
        return todoService.findTodoById(id).orElse(null);
    }

    @POST
    public Response createTodo(@Valid Todo todo, @Context UriInfo uriInfo) {
        Todo savedTodo = todoService.create(todo);
        URI uri = uriInfo.getRequestUriBuilder().path(savedTodo.getId().toString()).build();
        return Response.created(uri).build();
    }

}
