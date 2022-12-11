package si.fri.prpo.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import org.jboss.logging.Logger;
import si.fri.prpo.entitete.Izdelek;
import si.fri.prpo.entitete.Kategorija;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class KategorijeZrno {

    private Logger log = Logger.getLogger(KategorijeZrno.class.getName());

    //metoda PostConstruct, ki se izvede takoj po inicializaciji zrna
    @PostConstruct
    private void init() {
        log.info("Zrno KategorijeZrno se je inicializiralo.");
    }

    //metoda PreDestroy, ki se izvede tik pred uničenjem zrna
    @PreDestroy
    private void destroy() {
        log.info("Zrno KategorijeZrno se bo uničilo.");
    }


    //entity manager
    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

    //metoda za pridobitev vseh kategorij
    public List<Kategorija> getAllKategorije() {
        Query q = em.createNamedQuery("Kategorija.getAll");
        List<Kategorija> kategorije = q.getResultList();

        return kategorije;
    }

    //metoda za pridobitev vseh kategorij - z queryParameters iz rest
    public List<Kategorija> getAllKategorije(QueryParameters query) {
        List<Kategorija> kategorije = JPAUtils.queryEntities(em, Kategorija.class, query);

        return kategorije;
    }

    //metoda za pridobitev števila vrnjenih entitet s podanim querijem
    public long getAllKategorijeCount(QueryParameters query) {
        long total_count = JPAUtils.queryEntitiesCount(em, Kategorija.class, query);

        return total_count;
    }

    //metoda za pridobitev ene kategorije na podlagi IDja
    public Kategorija getKategorija(int id) {
        Kategorija kategorija = em.find(Kategorija.class, id);

        return kategorija;
    }

    //metoda za dodajanje nove kategorije
    @Transactional
    public Kategorija createKategorija(Kategorija kategorija) {
        if (kategorija != null) {
            em.persist(kategorija);
        }

        return kategorija;
    }

    //metoda za urejanje obstoječe kategorije na podlagi IDja
    @Transactional
    public Kategorija editKategorija(int id, Kategorija kategorija) {
        Kategorija kategorijaStara = em.find(Kategorija.class, id);

        if (kategorijaStara != null) {
            kategorija.setId(kategorijaStara.getId());
            em.merge(kategorija);
        } else {
            log.info("Kategorija s tem ID-jem ne obstaja.");
        }

        return kategorija;
    }

    //metoda za brisanje obstoječe kategorije na podlagi IDja
    @Transactional
    public boolean deleteKategorija(int id) {
        Kategorija kategorija = em.find(Kategorija.class, id);

        if (kategorija != null) {
            em.remove(kategorija);
            return true;
        } else {
            return false;
        }
    }
}
