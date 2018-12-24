/*
 * Copyright 2018 Rudy De Busscher (https://www.atbash.be)
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
package be.atbash.demo.utils.resource_api;

import be.atbash.util.resource.ResourceUtil;
import org.eclipse.microprofile.config.ConfigProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Tester {

    public static void main(String[] args) throws IOException {
        String configFile = ConfigProvider.getConfig().getValue("config.file", String.class);
        ResourceUtil resourceUtil = ResourceUtil.getInstance();
        if (resourceUtil.resourceExists(configFile)) {
            Properties props = new Properties();
            InputStream inputStream = resourceUtil.getStream(configFile);
            props.load(inputStream);
            inputStream.close();
            System.out.println(props);
        } else {
            System.out.println(String.format("Config Location [%s] not found", configFile));
        }
    }
}
