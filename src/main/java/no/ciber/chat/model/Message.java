package no.ciber.chat.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * User: Michael Johansen
 * Date: 07.11.13
 * Time: 15:00
 */
@Entity
public class Message {

    @Id
    @GeneratedValue
    private Long id;
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
