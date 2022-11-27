package si.fri.prpo.dtos;

import si.fri.prpo.entitete.Kategorija;
import si.fri.prpo.entitete.Trgovina;

public class UporabnikDto {

    private Integer id;
    private String ime;
    private String priimek;
    private String uporabniksoIme;
    private String email;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public String getUporabniksoIme() {
        return uporabniksoIme;
    }

    public void setUporabniksoIme(String uporabniksoIme) {
        this.uporabniksoIme = uporabniksoIme;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
