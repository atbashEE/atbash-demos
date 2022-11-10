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
package be.rubus.atbash.jsf.architecture.app.exception;


import be.rubus.atbash.jsf.architecture.backend.exception.TodoApplicationException;
import jakarta.faces.FacesException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerWrapper;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ExceptionQueuedEvent;
import jakarta.faces.event.ExceptionQueuedEventContext;

import java.util.Iterator;

class BusinessExceptionHandler extends ExceptionHandlerWrapper {

    BusinessExceptionHandler(ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
    }

    @Override
    public void handle() throws FacesException {

        Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
        while (i.hasNext()) {
            ExceptionQueuedEvent event = i.next();
            Throwable t = getThrowable(event);

            TodoApplicationException exception = getBusinessException(t);
            if (exception != null) {

                handleMessage(exception);

                //remove it from queue
                i.remove();

            }
        }

        //parent handle
        getWrapped().handle();

    }

    private TodoApplicationException getBusinessException(Throwable t) {
        TodoApplicationException result = null;
        Throwable stack = t;
        do {
            if (stack instanceof TodoApplicationException) {
                result = (TodoApplicationException) stack;
            } else {
                stack = stack.getCause();
            }
        } while (result == null && stack != null);
        return result;
    }

    private void handleMessage(TodoApplicationException businessException) {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        externalContext.getFlash().setKeepMessages(true);

        facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, businessException.getMessage(), businessException.getMessage()));

    }

    private Throwable getThrowable(ExceptionQueuedEvent event) {
        ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

        return context.getException();
    }

}