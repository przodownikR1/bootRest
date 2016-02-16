package pl.java.scalatech.domain;

import org.springframework.data.rest.core.config.Projection;

import pl.java.scalatech.domain.User;

@Projection(name = "noSkills", types = { User.class }) 
interface NoSkills { 

  String getLogin(); 

  String getEmail(); 
}

