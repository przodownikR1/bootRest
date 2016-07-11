package pl.java.scalatech.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.Team;
import pl.java.scalatech.repository.TeamRepository;

@RestController
@RequestMapping("/teams")
@Slf4j
public class TeamController extends GenericController<Team> {

    @Autowired
    public TeamController(TeamRepository repo) {
        super(repo);
    }

    @Override
    Class<?> getDomainClass() {
        return Team.class;
    }

}
