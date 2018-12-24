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

import java.util.HashMap;
import java.util.Map;

public class MapData {

    public static Map<String, String> getMapData() {
        Map<String, String> result = new HashMap<>();
        result.put("v1", "prop1=1-value1\nprop2=1-value2");
        result.put("v2", "prop1=2-value1\nprop2=2-value2");
        return result;

    }
}
