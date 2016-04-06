
package edu.wctc.jcb.bookwebapp.ejb;

import edu.wctc.jcb.bookwebapp.model.Author;
import edu.wctc.jcb.bookwebapp.model.Book;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author joshuabrown
 */
@Stateless
public class BookFacade extends AbstractFacade<Book> {

    @PersistenceContext(unitName = "edu.wctc.jcb_bookwebapp_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookFacade() {
        super(Book.class);
    }
    
    public List<Book> findByTitle(String title) {
        String jpql = "select b from Book b where b.title = ?1";
        TypedQuery<Book> q = getEntityManager().createQuery(jpql, Book.class);
        q.setParameter(1, title);
        return q.getResultList();
    }
   
    
}
