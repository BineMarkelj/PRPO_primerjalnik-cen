package si.fri.prpo.zrna;

import si.fri.prpo.entitete.Izdelek;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

    public List<Izdelek> getIzdelkiCriteriaAPI() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Izdelek> cq = cb.createQuery(Izdelek.class);
        Root<Izdelek> root = cq.from(Izdelek.class);
        cq.select(root);

        Query query = em.createQuery(cq);
        List<Izdelek> izdelki = query.getResultList();

        return izdelki;
    }
}