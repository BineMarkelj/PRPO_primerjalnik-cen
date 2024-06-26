package si.fri.prpo.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import org.jboss.logging.Logger;
import si.fri.prpo.entitete.Izdelek;
import si.fri.prpo.izjeme.NeveljavenIzdelekIzjema;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class IzdelkiZrno {

    private Logger log = Logger.getLogger(IzdelkiZrno.class.getName());

    @Inject UpravljanjeIzdelkovZrno upravljanjeIzdelkovZrno;

    //metoda PostConstruct, ki se izvede takoj po inicializaciji zrna
    @PostConstruct
    private void init() {
        UUID randomIDStart = UUID.randomUUID();
        log.info("Zrno IzdelkiZrno se je inicializiralo. Naključni začetni ID: " + randomIDStart);
    }

    //metoda PreDestroy, ki se izvede tik pred uničenjem zrna
    @PreDestroy
    private void destroy() {
        UUID randomIDEnd = UUID.randomUUID();
        log.info("Zrno IzdelkiZrno se bo uničilo. Naključni končni ID: " + randomIDEnd);
    }


    //entity manager
    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

    //metoda za pridobitev vseh izdelkov
    public List<Izdelek> getAllIzdelki() {
        Query q = em.createNamedQuery("Izdelek.getAll");
        List<Izdelek> izdelki =  (List<Izdelek>) q.getResultList();

        return izdelki;
    }

    //metoda za pridobitev vseh izdelkov - z queryParameters iz rest
    public List<Izdelek> getAllIzdelki(QueryParameters query) {
        List<Izdelek> izdelki = JPAUtils.queryEntities(em, Izdelek.class, query);

        return izdelki;
    }

    //metoda za pridobitev števila vrnjenih entitet s podanim querijem
    public long getAllIzdelkiCount(QueryParameters query) {
        long total_count = JPAUtils.queryEntitiesCount(em, Izdelek.class, query);

        return total_count;
    }

    public List<Izdelek> getAllIzdelkiCriteriaAPI() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Izdelek> cq = cb.createQuery(Izdelek.class);
        Root<Izdelek> root = cq.from(Izdelek.class);
        cq.select(root);

        Query query = em.createQuery(cq);
        List<Izdelek> izdelki = query.getResultList();

        return izdelki;
    }

    //metoda za pridobitev enega izdelka na podlagi IDja
    public Izdelek getIzdelek(int id) {
        Izdelek izdelek = em.find(Izdelek.class, id);

        return izdelek;
    }

    //metoda za dodajanje novega izdelka
    @Transactional
    public Izdelek createIzdelek(Izdelek izdelek) {
        if (izdelek != null) {
            if (izdelek.getCena() < 0) {
                throw new NeveljavenIzdelekIzjema("Napaka pri dodajanju novega izdelka: Cena izdelka ne sme biti negativna.");
            } else {
                upravljanjeIzdelkovZrno.ustvariIzdelek(izdelek);
            }
        }

        return izdelek;
    }

    //metoda za urejanje obstoječega izdelka na podlagi IDja
    @Transactional
    public Izdelek editIzdelek(int id, Izdelek izdelek) {
        Izdelek izdelekStar = em.find(Izdelek.class, id);

        if (izdelekStar != null) {
            if (izdelek.getCena() < 0) {
                throw new NeveljavenIzdelekIzjema("Napaka pri spreminjanju izdelka: Cena izdelka ne sme biti negativna.");
            } else {
                izdelek.setId(izdelekStar.getId());
                em.merge(izdelek);
            }
        } else {
            log.info("Izdelek s tem ID-jem ne obstaja.");
        }

        return izdelek;
    }

    //metoda za brisanje obstoječega izdelka na podlagi IDja
    @Transactional
    public boolean deleteIzdelek(int id) {
        Izdelek izdelek = em.find(Izdelek.class, id);

        if (izdelek != null) {
            em.remove(izdelek);
            return true;
        } else {
            return false;
        }
    }
}