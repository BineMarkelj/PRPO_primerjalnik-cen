package si.fri.prpo.dtos;

import si.fri.prpo.entitete.Izdelek;

import java.util.List;

public class TrgovinaDto {

    private Integer id;
    private String ime;
    private List<Izdelek> izdelki;


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
