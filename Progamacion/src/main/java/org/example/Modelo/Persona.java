package org.example.Modelo;

public class Persona {
    private int id;
    private String email;
    private String password;
    private String tipo;

    public Persona(int id, String email, String password, String tipo) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.tipo = tipo;
    }

    public Persona() {
    }

    public int getId() {
        return id;
    }

    public void setCodPersona(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
