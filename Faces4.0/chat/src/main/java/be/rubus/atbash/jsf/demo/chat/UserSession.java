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
package be.rubus.atbash.jsf.demo.chat;

import be.rubus.atbash.jsf.demo.chat.model.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class UserSession implements Serializable {

    private User user;

    public void startSessionFor(User user) {
        this.user = user;
    }

    public void endSession() {
        user = null;
    }

    public String getName() {
        if (user == null)
            return "Anonymous";
        return user.getName();
    }

    public String getNickname() {
        if (user == null)
            return "Anonymous";
        return user.getNickname();
    }

    public boolean isLogged() {
        return user != null;
    }

}
