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
package be.atbash.jakarta.jsf.example.view;


import be.atbash.jakarta.jsf.example.service.HelloService;
import be.atbash.jakarta.jsf.example.util.ComponentUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.annotation.View;
import jakarta.faces.application.StateManager;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIOutput;
import jakarta.faces.component.html.*;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.facelets.Facelet;
import jakarta.inject.Inject;

import java.io.IOException;
import java.util.List;

@View("/demo.xhtml")
@ApplicationScoped
public class DemoFacelet extends Facelet {

    @Inject
    private HelloService helloService;

    @Override
    public void apply(FacesContext facesContext, UIComponent root) throws IOException {
        if (!facesContext.getAttributes().containsKey(StateManager.IS_BUILDING_INITIAL_STATE)) {
            return;
        }
        ComponentUtil components = new ComponentUtil(facesContext);

        List<UIComponent> rootChildren = root.getChildren();

        UIOutput output = new UIOutput();
        output.setValue("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        rootChildren.add(output);

        HtmlBody body = components.create(HtmlBody.COMPONENT_TYPE);
        rootChildren.add(body);

        HtmlForm form = components.create(HtmlForm.COMPONENT_TYPE);
        form.setId("frm");
        body.getChildren().add(form);

        HtmlOutputLabel label = components.create(HtmlOutputLabel.COMPONENT_TYPE);
        label.setValue("Enter your name");
        form.getChildren().add(label);

        HtmlInputText message = components.create(HtmlInputText.COMPONENT_TYPE);
        message.setId("message");
        form.getChildren().add(message);

        form.getChildren().add(newHtmlBreak(components));

        HtmlOutputText text = components.create(HtmlOutputText.COMPONENT_TYPE);
        form.getChildren().add(text);

        form.getChildren().add(newHtmlBreak(components));

        HtmlCommandButton actionButton = components.create(HtmlCommandButton.COMPONENT_TYPE);
        actionButton.setId("btn");
        actionButton.addActionListener(e -> text.setValue(helloService.doGreet(message.getValue().toString())));
        actionButton.setValue("Greet");
        form.getChildren().add(actionButton);

        output = new UIOutput();
        output.setValue("</html>");
        rootChildren.add(output);
    }

    private UIComponent newHtmlBreak(ComponentUtil components) {
        HtmlOutputLabel result =  components.create(HtmlOutputLabel.COMPONENT_TYPE);
        result.setValue("<br/>");
        result.setEscape(false);
        return result;
    }
}
