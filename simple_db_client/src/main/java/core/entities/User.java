package core.entities;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

/**
 * Created by user on 20.02.15.
 */
@Entity
@Table(name = "sb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    public String name;
    public String alias;

    public User() {
    }

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }
}