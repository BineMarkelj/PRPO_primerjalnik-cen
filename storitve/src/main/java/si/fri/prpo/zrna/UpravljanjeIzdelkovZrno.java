package si.fri.prpo.zrna;

import org.eclipse.persistence.internal.libraries.antlr.runtime.misc.IntArray;
import org.jboss.logging.Logger;
import si.fri.prpo.dtos.IzdelekDto;
import si.fri.prpo.dtos.KategorijaDto;
import si.fri.prpo.dtos.TrgovinaDto;
import si.fri.prpo.dtos.UporabnikDto;
import si.fri.prpo.entitete.Izdelek;
import si.fri.prpo.entitete.Kategorija;
import si.fri.prpo.entitete.Trgovina;
import si.fri.prpo.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UpravljanjeIzdelkovZrno {

    private Logger log = Logger.getLogger(UpravljanjeIzdelkovZrno.class.getName());

    @Inject
    private IzdelkiZrno izdelkiZrno;

    @Inject
    private KategorijeZrno kategorijeZrno;

    @Inject
    private TrgovineZrno trgovineZrno;

    @Inject
    private UporabnikiZrno uporabnikiZrno;


    //metoda PostConstruct, ki se izvede takoj po inicializaciji zrna
    @PostConstruct
    private void init() {
        log.info("Zrno UpravljalecIzdelkovZrno se je inicializiralo.");
    }

    //metoda PreDestroy, ki se izvede tik pred uničenjem zrna
    @PreDestroy
    private void destroy() {
        log.info("Zrno UpravljalecIzdelkovZrno se bo uničilo.");
    }


    //entity manager
    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;


    @Transactional
    public Izdelek ustvariIzdelek(IzdelekDto izdelekDto) {

        Izdelek izdelek = new Izdelek();
        if (izdelekDto != null) {
            izdelek.setIme(izdelekDto.getIme());
            izdelek.setOpis(izdelekDto.getOpis());
            izdelek.setCena(izdelekDto.getCena());
            izdelek.setKategorija(izdelekDto.getKategorija());
            izdelek.setTrgovina(izdelekDto.getTrgovina());

            if (izdelek != null) {
                em.persist(izdelek);
            } else {
                log.info("Izdelka ni bilo mogoče shraniti v podatkovno bazo.");
            }
        }

        return izdelek;
    }


    @Transactional
    public Uporabnik ustvariUporabnika(UporabnikDto uporabnikDto) {

        Uporabnik uporabnik = new Uporabnik();
        if (uporabnikDto != null) {
            uporabnik.setIme(uporabnikDto.getIme());
            uporabnik.setPriimek(uporabnikDto.getPriimek());
            uporabnik.setUporabniskoIme(uporabnikDto.getUporabniksoIme());
            uporabnik.setEmail(uporabnikDto.getEmail());

            if (uporabnik != null) {
                em.persist(uporabnik);
            } else {
                log.info("Uporabnika ni bilo mogoče shraniti v podatkovno bazo.");
            }
        }

        return uporabnik;
    }


    public float vrniRazlikoCen(IzdelekDto izdelekDto1, IzdelekDto izdelekDto2) {
        float cena1 = izdelekDto1.getCena();
        float cena2 = izdelekDto2.getCena();

        float razlikaCen = cena2 - cena1;
        return razlikaCen;
    }


    public float povpracnaCenaIzdelkovSKategorijo(KategorijaDto kategorijaDto) {
        Integer kategorijaId = kategorijaDto.getId();
        //String kategorijaIme = kategorijaDto.getIme();
        Kategorija kategorija = kategorijeZrno.getKategorija(kategorijaId);
        kategorijaId = kategorija.getId();
        String kategorijaIme = kategorija.getIme();
        List<Izdelek> vsiIzdelki = izdelkiZrno.getAllIzdelki();

        float vsotaCen = 0;
        int steviloIzdelkov = 0;
        for (int i = 0; i < vsiIzdelki.size(); i++) {
            Izdelek tmp_izdelek = vsiIzdelki.get(i);
            float tmp_cena = tmp_izdelek.getCena();
            Kategorija tmp_kategorija = tmp_izdelek.getKategorija();
            Integer tmp_kategorija_id = tmp_kategorija.getId();
            String tmp_kategorija_ime = tmp_kategorija.getIme();

            if ((tmp_kategorija_id == kategorijaId) && (tmp_kategorija_ime == kategorijaIme)) {
                steviloIzdelkov++;
                vsotaCen = vsotaCen + tmp_cena;
            }
        }

        float povpracnaCenaKategorije = vsotaCen / steviloIzdelkov;
        return povpracnaCenaKategorije;
    }


    public float povpracnaCenaIzdelkovVTrgovini(TrgovinaDto trgovinaDto) {
        Integer trgovinaId = trgovinaDto.getId();
        //String trgovinaIme = trgovinaDto.getIme();
        Trgovina trgovina = trgovineZrno.getTrgovina(trgovinaId);
        String trgovinaIme = trgovina.getIme();
        List<Izdelek> vsiIzdelki = izdelkiZrno.getAllIzdelki();

        float vsotaCen = 0;
        int steviloIzdelkov = 0;
        for (int i = 0; i < vsiIzdelki.size(); i++) {
            Izdelek tmp_izdelek = vsiIzdelki.get(i);
            float tmp_cena = tmp_izdelek.getCena();
            Trgovina tmp_trgovina = tmp_izdelek.getTrgovina();
            Integer tmp_trgovina_id = tmp_trgovina.getId();
            String tmp_trgovina_ime = tmp_trgovina.getIme();

            if ((tmp_trgovina_id == trgovinaId) && (tmp_trgovina_ime == trgovinaIme)) {
                steviloIzdelkov++;
                vsotaCen = vsotaCen + tmp_cena;
            }
        }

        float povpracnaCenaTrgovine = vsotaCen / steviloIzdelkov;
        return povpracnaCenaTrgovine;
    }
}
