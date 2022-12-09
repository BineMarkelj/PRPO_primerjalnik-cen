package si.fri.prpo.zrna;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BelezenjeKlicevZrno {

    private Logger log = Logger.getLogger(BelezenjeKlicevZrno.class.getName());

    private int stevec_vrniIzdelke = 0;
    private int stevec_vrniIzdelek = 0;
    private int stevec_dodajIzdelek = 0;
    private int stevec_urediIzdelek = 0;
    private int stevec_izbrisiIzdelek = 0;

    public void pristej_klic_vrniIzdelke(String metoda) {
        stevec_vrniIzdelke++;

        System.out.println("Števec metode " + metoda + ": " + stevec_vrniIzdelke);
    }

    public void pristej_klic_vrniIzdelek(String metoda) {
        stevec_vrniIzdelek++;

        System.out.println("Števec metode " + metoda + ": " + stevec_vrniIzdelek);
    }

    public void pristej_klic_dodajIzdelek(String metoda) {
        stevec_dodajIzdelek++;

        System.out.println("Števec metode " + metoda + ": " + stevec_dodajIzdelek);
    }

    public void pristej_klic_urediIzdelek(String metoda) {
        stevec_urediIzdelek++;

        System.out.println("Števec metode " + metoda + ": " + stevec_urediIzdelek);
    }

    public void pristej_klic_izbrisiIzdelek(String metoda) {
        stevec_izbrisiIzdelek++;

        System.out.println("Števec metode " + metoda + ": " + stevec_izbrisiIzdelek);
    }
}
