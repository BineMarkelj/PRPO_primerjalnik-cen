package si.fri.prpo.dtos;

public class IzdelkaCenaDto {

    private Integer id1;
    private Integer id2;
    private String ime1;
    private String ime2;

    private float cena1;
    private float cena2;

    public Integer getId1() {
        return id1;
    }

    public void setId1(Integer id1) {
        this.id1 = id1;
    }

    public Integer getId2() {
        return id2;
    }

    public void setId2(Integer id2) {
        this.id2 = id2;
    }

    public String getIme1() {
        return ime1;
    }

    public void setIme1(String ime1) {
        this.ime1 = ime1;
    }

    public String getIme2() {
        return ime2;
    }

    public void setIme2(String ime2) {
        this.ime2 = ime2;
    }

    public float getCena1() {
        return cena1;
    }

    public void setCena1(float cena1) {
        this.cena1 = cena1;
    }

    public float getCena2() {
        return cena2;
    }

    public void setCena2(float cena2) {
        this.cena2 = cena2;
    }
}
