package si.fri.prpo.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import org.jboss.logging.Logger;
import si.fri.prpo.entitete.Kategorija;
import si.fri.prpo.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UporabnikiZrno {

    private Logger log = Logger.getLogger(UporabnikiZrno.class.getName());

    @Inject
    UpravljanjeIzdelkovZrno upravljanjeIzdelkovZrno;

    //metoda PostConstruct, ki se izvede takoj po inicializaciji zrna
    @PostConstruct
    private void init() {
        log.info("Zrno UporabnikiZrno se je inicializiralo.");
    }

    //metoda PreDestroy, ki se izvede tik pred uničenjem zrna
    @PreDestroy
    private void destroy() {
        log.info("Zrno UporabnikiZrno se bo uničilo.");
    }


    //entity manager
    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

    //metoda za pridobitev vseh uporabnikov
    public List<Uporabnik> getAllUporabniki() {
        Query q = em.createNamedQuery("Uporabnik.getAll");
        List<Uporabnik> uporabniki = q.getResultList();

        return uporabniki;
    }

    //metoda za pridobitev vseh uporabnikov - z queryParameters iz rest
    public List<Uporabnik> getAllUporabniki(QueryParameters query) {
        List<Uporabnik> uporabniki = JPAUtils.queryEntities(em, Uporabnik.class, query);

        return uporabniki;
    }

    //metoda za pridobitev števila vrnjenih entitet s podanim querijem
    public long getAllUporabnikiCount(QueryParameters query) {
        long total_count = JPAUtils.queryEntitiesCount(em, Uporabnik.class, query);

        return total_count;
    }

    //metoda za pridobitev enega upoarbnika na podlagi IDja
    public Uporabnik getUporabnik(int id) {
        Uporabnik uporabnik = em.find(Uporabnik.class, id);

        return uporabnik;
    }

    //metoda za dodajanje novega upoarbnika
    @Transactional
    public Uporabnik createUporabnik(Uporabnik uporabnik) {
        upravljanjeIzdelkovZrno.ustvariUporabnika(uporabnik);

        return uporabnik;
    }

    //metoda za urejanje obstoječega upoarbnika na podlagi IDja
    @Transactional
    public Uporabnik editUporabnik(int id, Uporabnik uporabnik) {
        Uporabnik uporabnikStari = em.find(Uporabnik.class, id);

        if (uporabnikStari != null) {
            uporabnik.setId(uporabnikStari.getId());
            em.merge(uporabnik);
        } else {
            log.info("Uporabnik s tem ID-jem ne obstaja.");
        }

        return uporabnik;
    }

    //metoda za brisanje obstoječega upoarbnika na podlagi IDja
    @Transactional
    public boolean deleteUporabnik(int id) {
        Uporabnik uporabnik = em.find(Uporabnik.class, id);

        if (uporabnik != null) {
            em.remove(uporabnik);
            return true;
        } else {
            return false;
        }
    }
}
