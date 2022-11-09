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
package be.rubus.atbash.jsf.demo.component;

import jakarta.el.MethodExpression;
import jakarta.faces.application.ResourceDependencies;
import jakarta.faces.application.ResourceDependency;
import jakarta.faces.component.ActionSource2;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIComponentBase;
import jakarta.faces.event.ActionListener;

@FacesComponent("atbash.CameraCapture")
@ResourceDependencies({
        @ResourceDependency(
                library = "atbash",
                name = "cameraCapture.js"
        ),
        @ResourceDependency(
                library = "atbash",
                name = "cameraCapture.css"
        )
})
public class CameraCaptureComponent extends UIComponentBase implements ActionSource2 {

    enum PropertyKeys {
        value, actionExpression
    }

    private String value;

    public CameraCaptureComponent() {
        setRendererType("atbash.CameraCaptureRenderer");
    }

    @Override
    public String getFamily() {
        return "atbash";
    }

    public String getValue() {
        return (String) getStateHelper().eval(PropertyKeys.value);
    }

    public void setValue(String value) {
        getStateHelper().put(PropertyKeys.value, value);
    }

    @Override
    public boolean isImmediate() {
        return false;
    }

    @Override
    public void setImmediate(boolean immediate) {
        // Ignored, Not needed
    }

    @Override
    public void addActionListener(ActionListener listener) {
        addFacesListener(listener);
    }

    @Override
    public ActionListener[] getActionListeners() {
        return (ActionListener[]) getFacesListeners(ActionListener.class);
    }

    @Override
    public void removeActionListener(ActionListener listener) {
        removeFacesListener(listener);
    }

    @Override
    public MethodExpression getActionExpression() {
        return (MethodExpression) getStateHelper().get(PropertyKeys.actionExpression);
    }

    @Override
    public void setActionExpression(MethodExpression action) {
        getStateHelper().put(PropertyKeys.actionExpression, action);
    }
}
