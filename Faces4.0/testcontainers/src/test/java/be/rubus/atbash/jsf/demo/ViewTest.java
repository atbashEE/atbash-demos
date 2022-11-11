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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.VncRecordingContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;

@Testcontainers
public class ViewTest {

    private static final MountableFile WAR_FILE = MountableFile.forHostPath(Paths.get("target/hello.war").toAbsolutePath(), 0777);

    @Container
    public static PayaraMicroContainer payaraMicro = new PayaraMicroContainer(WAR_FILE);

    @Container
    public static BrowserWebDriverContainer<?> selenium = new BrowserWebDriverContainer<>()
            .withCapabilities(new FirefoxOptions())
            .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.RECORD_FAILING, new File("./target/"), VncRecordingContainer.VncRecordingFormat.MP4)
            .withNetwork(Network.SHARED);

    @Test
    void checkPage() {
        RemoteWebDriver webDriver = selenium.getWebDriver();

        webDriver.get("http://app:8080/test.xhtml");
        webDriver.findElement(By.id("frm:name")).sendKeys("Vienna");
        webDriver.findElement(By.id("frm:hiBtn")).click();

        waitForAjax(webDriver);
        Assertions.assertThat(webDriver.findElement(By.id("response")).getText()).isEqualTo("Hello Vienna");
        //webDriver.findElement(By.tagName("body")).getAttribute("innerHTML");
    }

    private void waitForAjax(RemoteWebDriver webDriver) {
        var wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(wd -> ((JavascriptExecutor) wd).executeScript("return jQuery.active == 0"));
    }
}
