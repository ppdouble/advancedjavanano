package code;

import java.util.List;

/*
 * Java Bean
 *
 * Each piece of information is stored in an instance field, with a getter and setter method.
 * The method names are prefixed by "get" and "set", respectively. followed by the name of
 * the property.
 * A data class that follows this naming convention is called a Java Bean.
 */
public class Client {
    private int id;
    private String name;
    private List<String> emails;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }
}
