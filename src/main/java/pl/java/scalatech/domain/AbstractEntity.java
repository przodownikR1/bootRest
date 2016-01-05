package pl.java.scalatech.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable{
    private static final long serialVersionUID = 1764429777262538648L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    protected Long id;
}
