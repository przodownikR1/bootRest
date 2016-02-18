package pl.java.scalatech.web.matrix;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
@RestController
@RequestMapping("/matrixSec")
@Slf4j
public class MatrixSecondController {
    // GET /users/42;q=11;r=22
    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> findUser(@PathVariable String userId, @MatrixVariable int q) {
         log.info("{}",q);
        // user == 42
        // q == 11
     return ResponseEntity.ok(q);
    }
    
 // GET /owners/42;q=11/pets/21;q=22

    @RequestMapping(path = "/owners/{ownerId}/pets/{petId}", method = RequestMethod.GET)
    public ResponseEntity<?> findOwner(
            @MatrixVariable(name="q", pathVar="ownerId") int q1,
            @MatrixVariable(name="q", pathVar="petId") int q2) {

        // q1 == 11
        // q2 == 22
        return ResponseEntity.ok(q1 + "  "  + q2);
    }
    
 // GET /owners/42;q=11;r=12/pets/21;q=22;s=23

    @RequestMapping(path = "/owners2/{ownerId}/pets/{petId}", method = RequestMethod.GET)
    public void findUser(
            @MatrixVariable Map<String, String> matrixVars,
            @MatrixVariable(pathVar="petId") Map<String, String> petMatrixVars) {
           log.info("petVariable {}",petMatrixVars);
           log.info("matrixVars  {}",matrixVars);
        // matrixVars: ["q" : [11,22], "r" : 12, "s" : 23]
        // petMatrixVars: ["q" : 11, "s" : 23]

    }
}
