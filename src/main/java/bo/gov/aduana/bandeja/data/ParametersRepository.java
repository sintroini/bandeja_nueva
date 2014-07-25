package bo.gov.aduana.bandeja.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import bo.gov.aduana.bandeja.model.Parameter;
import bo.gov.aduana.bandeja.model.Parameter_;

@ApplicationScoped
public class ParametersRepository {

    @Inject
    private EntityManager em;

    public Parameter findById(Long id) {
        return em.find(Parameter.class, id);
    }

    public List<Parameter> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Parameter> criteria = cb.createQuery(Parameter.class);
        Root<Parameter> parameter = criteria.from(Parameter.class);

        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        criteria.select(parameter).orderBy(cb.asc(parameter.get(Parameter_.name)));

        return em.createQuery(criteria).getResultList();
    }
}
