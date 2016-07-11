package pl.java.scalatech.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.Skill;
import pl.java.scalatech.repository.SkillRepository;

@RestController
@RequestMapping("/skils")
@Slf4j
public class SkillController extends GenericController<Skill> {
    @Autowired
    public SkillController(SkillRepository repo) {
        super(repo);
    }

    @Override
    Class<?> getDomainClass() {
        return Skill.class;
    }

}
