package si.fri.rso.samples.feedback.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.samples.feedback.entities.Feedback;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class FeedbackBean {

    private Logger log = Logger.getLogger(FeedbackBean.class.getName());

    @Inject
    private EntityManager em;

    public List<Feedback> getFeedback(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery())
                .defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, Feedback.class, queryParameters);

    }
    public List<Feedback> getFeedback() {

        TypedQuery<Feedback> query = em.createNamedQuery("Feedback.getAll", Feedback.class);

        return query.getResultList();

    }

    public Feedback getFeedback(Integer feedbackId) {

        Feedback feedback = em.find(Feedback.class, feedbackId);

        if (feedback == null) {
            throw new NotFoundException();
        }

        return feedback;
    }

    public Feedback createFeedback(Feedback feedback) {

        try {
            beginTx();
            em.persist(feedback);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return feedback;
    }

    public Feedback putFeedback(Integer feedbackId, Feedback feedback) {

        Feedback c = em.find(Feedback.class, feedbackId);

        if (c == null) {
            return null;
        }

        try {
            beginTx();
            feedback.setId(c.getId());
            feedback = em.merge(feedback);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return feedback;
    }


    public boolean deleteFeedback(String feedbackId) {

        Feedback feedback = em.find(Feedback.class, feedbackId);

        if (feedback != null) {
            try {
                beginTx();
                em.remove(feedback);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else
            return false;

        return true;
    }



    private void beginTx() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    private void commitTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }
}
