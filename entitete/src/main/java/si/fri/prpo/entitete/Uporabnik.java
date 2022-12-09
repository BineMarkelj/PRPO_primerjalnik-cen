package si.fri.prpo.entitete;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "uporabnik")
@NamedQueries(value =
        {
                @NamedQuery(name = "Uporabnik.getAll",
                        query = "SELECT u FROM uporabnik u"),
                @NamedQuery(name = "Uporabnik.getById",
                        query = "SELECT u FROM uporabnik u WHERE u.id = :id"),
                @NamedQuery(name = "Uporabnik.getByUporabniskoIme",
                        query = "SELECT u FROM uporabnik u WHERE u.uporabniskoIme = :uporabniskoIme"),
                @NamedQuery(name = "Uporabnik.getByImeinPriimek",
                        query = "SELECT u FROM uporabnik u WHERE (u.ime = :ime AND u.priimek = :priimek)")
        })
public class Uporabnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ime;

    private String priimek;

    private String email;

    @Column(name = "uporabnisko_ime")
    private String uporabniskoIme;


    // getter in setter metode

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUporabniskoIme() {
        return uporabniskoIme;
    }

    public void setUporabniskoIme(String uporabniskoIme) {
        this.uporabniskoIme = uporabniskoIme;
    }
}