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
package be.rubus.atbash.jsf.demo;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@RequestScoped
@Named
public class ImageBean {

    private static final String IMAGE_PREFIX = "data:image/jpeg;base64,";

    private String imageContent;

    public String getImageContent() {
        return imageContent;
    }

    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }

    public void saveImage() {
        if (imageContent.startsWith(IMAGE_PREFIX)) {
            byte[] bytes = Base64.getDecoder().decode(imageContent.substring(IMAGE_PREFIX.length()));
            try {
                Files.write(Path.of("camera.jpeg"), bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
