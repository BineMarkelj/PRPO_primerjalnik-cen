package si.fri.prpo.entitete;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "izdelek")
@NamedQueries(value =
        {
                @NamedQuery(name = "Izdelek.getAll",
                        query = "SELECT i FROM izdelek i"),
                @NamedQuery(name = "Izdelek.getById",
                        query = "SELECT i FROM izdelek i WHERE i.id = :id"),
                @NamedQuery(name = "Izdelek.getByCenaLower",
                        query = "SELECT i FROM izdelek i WHERE i.cena <= :cena"),
                @NamedQuery(name = "Izdelek.getByCenaHigher",
                        query = "SELECT i FROM izdelek i WHERE i.cena >= :cena")
        })
public class Izdelek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ime;

    private String opis;

    private float cena;

    @ManyToOne
    @JoinColumn(name = "kategorija_id")
    private Kategorija kategorija;

    @ManyToOne
    @JoinColumn(name = "trgovina_id")
    private Trgovina trgovina;


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

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public float getCena() {
        return cena;
    }

    public void setCena(float cena) {
        this.cena = cena;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }

    public Trgovina getTrgovina() {
        return trgovina;
    }

    public void setTrgovina(Trgovina trgovina) {
        this.trgovina = trgovina;
    }
}