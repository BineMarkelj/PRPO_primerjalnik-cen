package si.fri.prpo.zrna;

import org.jboss.logging.Logger;
import si.fri.prpo.entitete.Trgovina;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class TrgovineZrno {

    private Logger log = Logger.getLogger(TrgovineZrno.class.getName());

    //metoda PostConstruct, ki se izvede takoj po inicializaciji zrna
    @PostConstruct
    private void init() {
        log.info("Zrno TrgovineZrno se je inicializiralo.");
    }

    //metoda PreDestroy, ki se izvede tik pred uničenjem zrna
    @PreDestroy
    private void destroy() {
        log.info("Zrno TrgovineZrno se bo uničilo.");
    }


    //entity manager
    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

    //metoda za pridobitev vseh trgovin
    public List<Trgovina> getAllTrgovine() {
        Query q = em.createNamedQuery("Trgovina.getAll");
        List<Trgovina> trgovine = q.getResultList();

        return trgovine;
    }

    //metoda za pridobitev ene trgovine na podlagi IDja
    public Trgovina getTrgovina(int id) {
        Trgovina trgovina = em.find(Trgovina.class, id);

        return trgovina;
    }

    //metoda za dodajanje nove trgovine
    @Transactional
    public Trgovina createTrgovina(Trgovina trgovina) {
        if (trgovina != null) {
            em.persist(trgovina);
        }

        return trgovina;
    }

    //metoda za urejanje obstoječe trgovine na podlagi IDja
    @Transactional
    public Trgovina editTrgovina(int id, Trgovina trgovina) {
        Trgovina trgovinaStara = em.find(Trgovina.class, id);
        trgovina.setId(trgovinaStara.getId());
        em.merge(trgovina);

        return trgovina;
    }

    //metoda za brisanje obstoječe trgovine na podlagi IDja
    @Transactional
    public boolean deleteTrgovina(int id) {
        Trgovina trgovina = em.find(Trgovina.class, id);

        if (trgovina != null) {
            em.remove(trgovina);
            return true;
        } else {
            return false;
        }
    }
}
