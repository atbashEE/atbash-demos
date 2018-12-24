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
package be.atbash.demo.utils.resource_api.spi;

import be.atbash.util.ordered.Order;
import be.atbash.util.resource.ResourceReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Order(50)
public class MapBasedResourceReader implements ResourceReader {

    private static final String PREFIX = "myData:";
    private Map<String, String> data;

    public MapBasedResourceReader() {
        data = MapData.getMapData();
    }

    @Override
    public boolean canRead(String resourcePath, Object context) {
        return resourcePath.startsWith(PREFIX);
    }

    private String getKey(String resourcePath) {
        return resourcePath.substring(PREFIX.length());
    }

    @Override
    public boolean exists(String resourcePath, Object context) {
        boolean result = canRead(resourcePath, context);
        if (result) {
            String key = getKey(resourcePath);
            result = data.containsKey(key);
        }
        return result;
    }

    @Override
    public InputStream load(String resourcePath, Object context) throws IOException {
        if (exists(resourcePath, context)) {
            String key = getKey(resourcePath);
            return new ByteArrayInputStream(data.get(key).getBytes());
        }
        return null;
    }
}
