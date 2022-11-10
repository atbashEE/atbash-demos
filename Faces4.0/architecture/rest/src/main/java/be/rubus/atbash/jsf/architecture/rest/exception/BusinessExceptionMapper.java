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
package be.rubus.atbash.jsf.architecture.rest.exception;

import be.rubus.atbash.jsf.architecture.backend.exception.TodoApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class BusinessExceptionMapper implements ExceptionMapper<TodoApplicationException> {
    @Override
    public Response toResponse(TodoApplicationException exception) {
        Map<String, String> data = new HashMap<>();
        data.put("code", exception.getCode());
        data.put("message", exception.getMessage());
        return Response.status(Response.Status.PRECONDITION_FAILED)
                .entity(data)
                .build();
    }
}
