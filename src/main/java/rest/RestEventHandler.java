package rest;

import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.Skill;
import pl.java.scalatech.domain.User;

@RepositoryEventHandler
@Slf4j
public class RestEventHandler {

    @HandleBeforeSave
    public void handlePersonSave(User u) {
        log.info("++ handlePersonSave  {}", u);
    }

    @HandleBeforeSave
    public void handleSkillSave(Skill s) {
        log.info("++ handleSkillSave  {}", s);
    }
}
