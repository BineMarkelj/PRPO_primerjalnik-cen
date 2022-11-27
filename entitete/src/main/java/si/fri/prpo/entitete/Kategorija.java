package si.fri.prpo.entitete;

import javax.persistence.*;
import java.util.List;

@Entity(name = "kategorija")
@NamedQueries(value =
        {
                @NamedQuery(name = "Kategorija.getAll",
                        query = "SELECT k FROM kategorija k"),
                @NamedQuery(name = "Kategorija.getById",
                        query = "SELECT k FROM kategorija k WHERE k.id = :id"),
                @NamedQuery(name = "Kategorija.getByIme",
                        query = "SELECT k FROM kategorija k WHERE k.id < :id1 AND k.id > :id2"),
                @NamedQuery(name = "Kategorija.getIfIncludesIzdlelek",
                        query = "SELECT k FROM kategorija k WHERE k.ime LIKE :niz")
        })
public class Kategorija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ime;

    @OneToMany(mappedBy = "kategorija", cascade = CascadeType.ALL)
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