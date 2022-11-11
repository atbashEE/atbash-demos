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

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

/**
 * Specialised Generic Container for Payara Micro.  You do not need to define all the configuration
 * options in the test itself.
 */
public class PayaraMicroContainer extends GenericContainer<PayaraMicroContainer> {

    public PayaraMicroContainer(MountableFile warFile) {
        super(DockerImageName.parse("payara/micro:6.2022.1"));
        withExposedPorts(8080)
                .withNetwork(Network.SHARED)
                .withNetworkAliases("app")
                .withCopyFileToContainer(warFile, "/opt/payara/deployments/app.war")
                // Health point of Payara Micro based on MicroProfile Health
                .waitingFor(Wait.forHttp("/health"))
                .withCommand("--deploy /opt/payara/deployments/app.war --noCluster --contextRoot /");
        // Deploy app, no clustering = faster and define context root.

    }
}
