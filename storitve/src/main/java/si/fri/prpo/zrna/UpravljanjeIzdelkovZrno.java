package si.fri.prpo.zrna;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;
import si.fri.prpo.dtos.*;
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
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;
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
    public IzdelekDto ustvariIzdelek(Izdelek izdelek) {

        IzdelekDto izdelekDto = new IzdelekDto();
        boolean napaka = false;

        if (izdelek != null) {

            //ime
            if (izdelek.getIme() == null || izdelek.getIme().isEmpty()) {
                log.info("Atribut ime podanega izdelka je prazen.");
                napaka = true;
            } else {
                izdelekDto.setIme(izdelek.getIme());
            }

            //opis
            if (izdelek.getOpis() == null || izdelek.getOpis().isEmpty()) {
                log.info("Atribut opis podanega izdelka je prazen.");
                napaka = true;
            } else {
                izdelekDto.setOpis(izdelek.getOpis());
            }

            //cena
            if (izdelek.getCena() == 0.0f) {
                log.info("Atribut cena podanega izdelka je prazen.");
                napaka = true;
            } else {
                izdelekDto.setCena(izdelek.getCena());
            }

            //id
            if (izdelek.getId() == null) {
                log.info("Atribut id podanega izdelka je prazen.");
                napaka = true;
            } else {
                izdelekDto.setId(izdelek.getId());
            }

            //kategorija
            if (izdelek.getKategorija() == null) {
                log.info("Atribut kategorija podanega izdelka je prazen.");
                napaka = true;
            } else {
                izdelekDto.setKategorija(izdelek.getKategorija());
            }

            //trgovina
            if (izdelek.getTrgovina() == null) {
                log.info("Atribut trgovina podanega izdelka je prazen.");
                napaka = true;
            } else {
                izdelekDto.setTrgovina(izdelek.getTrgovina());
            }

            //preveri če kategorija obstaja / ustvari novo
            if (izdelek.getKategorija() != null) {
                Kategorija kategorija = izdelek.getKategorija();
                Integer id = kategorija.getId();
                Kategorija kategorijaObstaja = kategorijeZrno.getKategorija(id);

                if (kategorijaObstaja == null) {
                    kategorijeZrno.createKategorija(kategorija);
                    log.info("Ustvarjena nova kategorija.");
                }
            }

            //preveri če trgovina obstaja / ustvari novo
            if (izdelek.getTrgovina() != null) {
                Trgovina trgovina = izdelek.getTrgovina();
                Integer id = trgovina.getId();
                Trgovina trgovinaObstaja = trgovineZrno.getTrgovina(id);

                if (trgovinaObstaja == null) {
                    trgovineZrno.createTrgovina(trgovina);
                    log.info("Ustvarjena nova trgovina.");
                }
            }


            if (!napaka) {
                em.persist(izdelek);
            } else {
                log.info("Izdelka ni bilo mogoče shraniti v bazo podatkov.");
            }

        } else {
            log.info("Atributi podanega izdelka so prazni.");
        }

        return izdelekDto;
    }


    @Transactional
    public UporabnikDto ustvariUporabnika(Uporabnik uporabnik) {

        UporabnikDto uporabnikDto = new UporabnikDto();
        boolean napaka = false;
        if (uporabnik != null) {

            //ime
            if (uporabnik.getIme() == null || uporabnik.getIme().isEmpty()) {
                log.info("Atribut podanega uporabnika je prazen.");
                napaka = true;
            } else {
                uporabnikDto.setIme(uporabnik.getIme());
            }

            //priimek
            if (uporabnik.getPriimek() == null || uporabnik.getPriimek().isEmpty()) {
                log.info("Atribut podanega uporabnika je prazen.");
                napaka = true;
            } else {
                uporabnikDto.setPriimek(uporabnik.getPriimek());
            }

            //uporabniska ime
            if (uporabnik.getUporabniskoIme() == null || uporabnik.getUporabniskoIme().isEmpty()) {
                log.info("Atribut podanega uporabnika je prazen.");
                napaka = true;
            } else {
                uporabnikDto.setUporabniksoIme(uporabnik.getUporabniskoIme());
            }

            //email
            if (uporabnik.getEmail() == null || uporabnik.getEmail().isEmpty()) {
                log.info("Atribut podanega uporabnika je prazen.");
                napaka = true;
            } else {
                uporabnikDto.setEmail(uporabnik.getEmail());
            }

            //id
            if (uporabnik.getId() == null) {
                log.info("Atribut podanega uporabnika je prazen.");
                napaka = true;
            } else {
                uporabnikDto.setId(uporabnik.getId());
            }


            if (!napaka) {
                em.persist(uporabnik);
            } else {
                log.info("Izdelka ni bilo mogoče shraniti v bazo podatkov.");
            }
        } else {
            log.info("Atributi podanega uporabnika so prazni.");
        }

        return uporabnikDto;
    }


    public IzdelkaCenaRazlikaDto vrniRazlikoCen(IzdelkaCenaDto izdelkaCenaDto) {
        float cena1 = izdelkaCenaDto.getCena1();
        float cena2 = izdelkaCenaDto.getCena2();

        float razlikaCen = Math.abs(cena2 - cena1);
        IzdelkaCenaRazlikaDto izdelkaCenaRazlikaDto = new IzdelkaCenaRazlikaDto();
        izdelkaCenaRazlikaDto.setRazlika(razlikaCen);
        return izdelkaCenaRazlikaDto;
    }


    public KategorijaPovprecjeCenDto povpracnaCenaIzdelkovSKategorijo(KategorijaCenaDto kategorijaCenaDto) {
        Integer kategorijaId = kategorijaCenaDto.getId();
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

        float povpracnaCenaKategorije = 0;
        if (steviloIzdelkov != 0) {
            povpracnaCenaKategorije = vsotaCen / steviloIzdelkov;
        }

        KategorijaPovprecjeCenDto kategorijaPovprecjeCenDto = new KategorijaPovprecjeCenDto();
        kategorijaPovprecjeCenDto.setPovpracje(povpracnaCenaKategorije);
        return kategorijaPovprecjeCenDto;
    }


    public TrgovinaPovprecjeCenDto povpracnaCenaIzdelkovVTrgovini(TrgovinaCeneDto trgovinaCeneDto) {
        Integer trgovinaId = trgovinaCeneDto.getId();
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

        float povpracnaCenaTrgovine = 0;
        if (steviloIzdelkov != 0) {
            povpracnaCenaTrgovine = vsotaCen / steviloIzdelkov;
        }

        TrgovinaPovprecjeCenDto trgovinaPovprecjeCenDto = new TrgovinaPovprecjeCenDto();
        trgovinaPovprecjeCenDto.setPovprecje(povpracnaCenaTrgovine);
        return trgovinaPovprecjeCenDto;
    }
}
