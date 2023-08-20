package co.edu.uniempresarial.bdsqllite.classes;

public class User {

    private int document;
    private String user;
    private String names;
    private String lastNames;
    private String pass;
    private int status;

    public User() {
    }

    public User(int document, String user, String names, String lastNames, String pass) {
        this.document = document;
        this.user = user;
        this.names = names;
        this.lastNames = lastNames;
        this.pass = pass;
        this.status = 1;
    }

    public int getDocument() {
        return document;
    }

    public void setDocument(int document) {
        this.document = document;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastNames() {
        return lastNames;
    }

    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void delete() {
        this.status = 0;
    }

    public void restore() {
        this.status = 1;
    }

    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    @Override
    public String toString() {
        return capitalizeFirstLetter(names) + " " + capitalizeFirstLetter(lastNames) + ", Usuario: " + user
                + ", Documento: " + document + ", Estado: " + (status == 1 ? "Activo" : "Inactivo");
    }
}
