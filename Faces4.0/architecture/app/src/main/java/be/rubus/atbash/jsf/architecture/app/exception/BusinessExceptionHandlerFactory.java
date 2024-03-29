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

import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerFactory;

public class BusinessExceptionHandlerFactory extends ExceptionHandlerFactory {

    // this injection handles jsf
    public BusinessExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        super(parent);

    }

    @Override
    public ExceptionHandler getExceptionHandler() {

        return new BusinessExceptionHandler(getWrapped().getExceptionHandler());
    }

}