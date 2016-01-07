/*
 * Copyright 2016 the original author or authors.
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
package pl.java.scalatech.web;

import java.io.IOException;
import java.util.Properties;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.VersionInfo;

@RestController
@Slf4j
public class VersionInfoController {

    private VersionInfo versionInfo;

    @RequestMapping(value = "/versionInfo", method = RequestMethod.GET)
    public VersionInfo getGitRepositoryState() throws IOException {
        log.info("++++ ver repo");
        if (versionInfo == null) {
            log.info("++++ ver repo null");
            Properties gitProperties = new Properties();
            gitProperties.load(getClass().getClassLoader().getResourceAsStream("git.properties"));

            Properties buildProperties = new Properties();
            buildProperties.load(getClass().getClassLoader().getResourceAsStream("buildVersion.properties"));

            buildProperties.putAll(gitProperties);
            versionInfo = new VersionInfo(buildProperties);

        }
        return versionInfo;
    }

}