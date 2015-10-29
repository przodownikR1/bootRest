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