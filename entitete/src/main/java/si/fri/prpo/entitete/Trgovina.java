package si.fri.prpo.entitete;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "trgovina")
@NamedQueries(value =
        {
                @NamedQuery(name = "Trgovina.getAll",
                        query = "SELECT t FROM trgovina t"),
                @NamedQuery(name = "Trgovina.getById",
                        query = "SELECT t FROM trgovina t WHERE t.id = :id"),
                @NamedQuery(name = "Trgovina.getByIme",
                        query = "SELECT t FROM trgovina t WHERE t.ime = :ime"),
                @NamedQuery(name = "Trgovina.getIfIncludesIzdelek",
                        query = "SELECT t FROM trgovina t WHERE t.ime LIKE :niz")
        })
public class Trgovina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ime;

    @JsonbTransient
    @OneToMany(mappedBy = "trgovina", cascade = CascadeType.ALL)
    private List<Izdelek> izdelki;


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

    public List<Izdelek> getIzdelki() {
        return izdelki;
    }

    public void setIzdelki(List<Izdelek> izdelki) {
        this.izdelki = izdelki;
    }
}
