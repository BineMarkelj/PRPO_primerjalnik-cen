package si.fri.prpo.zrna;

import si.fri.prpo.entitete.Izdelek;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class IzdelkiZrno {

    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

    public List<Izdelek> getIzdelki() {
        Query q = em.createNamedQuery("Izdelek.getAll");
        List<Izdelek> izdelki =  (List<Izdelek>) q.getResultList();
        return izdelki;
    }
}