package pl.java.scalatech.domain;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Builder
public class User extends AbstractEntity{

    private static final long serialVersionUID = -8920961125119379475L;
    private  String firstname;
    private  String email;
    private String login;
    private String password;
    
    
}
