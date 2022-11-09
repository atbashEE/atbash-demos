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
package be.rubus.atbash.jsf.demo.renderer;

import be.rubus.atbash.jsf.demo.component.CameraCaptureComponent;
import jakarta.el.ValueExpression;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;
import jakarta.faces.event.ActionEvent;
import org.primefaces.renderkit.CoreRenderer;

import java.io.IOException;

public class CameraCaptureRenderer extends CoreRenderer {

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter responseWriter = context.getResponseWriter();

        responseWriter.startElement("div", null);
        responseWriter.writeAttribute("id", "cameraCapture", null);
        responseWriter.writeAttribute("class", "center", null);

        writeButton(responseWriter, "start-camera", "Start Camera", false);


        responseWriter.startElement("video", null);
        responseWriter.writeAttribute("id", "video", null);
        responseWriter.writeAttribute("width", "320", null);
        responseWriter.writeAttribute("height", "240", null);
        responseWriter.writeAttribute("class", "child", null);
        responseWriter.endElement("video");


        writeButton(responseWriter, "click-photo", "Take Photo", false);

        responseWriter.startElement("canvas", null);
        responseWriter.writeAttribute("id", "canvas", null);
        responseWriter.writeAttribute("width", "320", null);
        responseWriter.writeAttribute("class", "child dnone", null);
        responseWriter.writeAttribute("height", "240", null);
        responseWriter.endElement("canvas");

        writeButton(responseWriter, "send-photo", "Submit", true);

        responseWriter.startElement("input", component);
        responseWriter.writeAttribute("id", component.getClientId(), null);
        responseWriter.writeAttribute("name", component.getClientId(), null);
        responseWriter.writeAttribute("type", "hidden", null);
        responseWriter.writeAttribute("class", "cameraCapture", null);
        responseWriter.endElement("canvas");

        responseWriter.endElement("div");
    }

    private static void writeButton(ResponseWriter responseWriter, String id, String text, boolean doesSubmit) throws IOException {
        responseWriter.startElement("button", null);
        responseWriter.writeAttribute("id", id, null);
        if (!doesSubmit) {
            responseWriter.writeAttribute("type", "button", null);
        }
        responseWriter.writeAttribute("class", "child ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only", null);
        writeLabel(responseWriter, text);
        responseWriter.endElement("button");
    }

    private static void writeLabel(ResponseWriter responseWriter, String text) throws IOException {
        responseWriter.startElement("span", null);
        responseWriter.writeAttribute("class", "ui-button-text ui-c", null);
        responseWriter.writeText(text, null);
        responseWriter.endElement("span");
    }

    @Override
    public void decode(FacesContext context, UIComponent component) {
        CameraCaptureComponent cameraCaptureComponent = (CameraCaptureComponent) component;

        String clientId = cameraCaptureComponent.getClientId(context);

        String submittedValue = context.getExternalContext().getRequestParameterMap().get(clientId);

        ValueExpression valueExpression = cameraCaptureComponent.getValueExpression("value");

        valueExpression.setValue(context.getELContext(), submittedValue);

        component.queueEvent(new ActionEvent(component));
        decodeBehaviors(context, component);
    }
}
