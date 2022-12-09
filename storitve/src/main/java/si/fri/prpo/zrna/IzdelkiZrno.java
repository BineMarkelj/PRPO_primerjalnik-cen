package si.fri.prpo.zrna;

import org.jboss.logging.Logger;
import si.fri.prpo.entitete.Izdelek;

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
            upravljanjeIzdelkovZrno.ustvariIzdelek(izdelek);
        }

        return izdelek;
    }

    //metoda za urejanje obstoječega izdelka na podlagi IDja
    @Transactional
    public Izdelek editIzdelek(int id, Izdelek izdelek) {
        Izdelek izdelekStar = em.find(Izdelek.class, id);

        //if (izdelekStar != null) {
            izdelek.setId(izdelekStar.getId());
            em.merge(izdelek);
        //}

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