package org.example.Modelo;

public class Persona {
    private int codPersona;
    private String email;
    private String password;
    private String tipo;

    public Persona(int codPersona, String email, String password, String tipo) {
        this.codPersona = codPersona;
        this.email = email;
        this.password = password;
        this.tipo = tipo;
    }

    public int getCodPersona() {
        return codPersona;
    }

    public void setCodPersona(int codPersona) {
        this.codPersona = codPersona;
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
