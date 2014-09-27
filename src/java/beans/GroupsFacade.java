package beans;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

@Stateless
public class GroupsFacade extends AbstractFacade<Groups> {

    
    @PersistenceContext(unitName = "MicroBlog")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GroupsFacade() {
        super(Groups.class);
    }
    
    @Override
    public Groups find(Object id) {
        return getEntityManager().find(Groups.class, id);
    }
 
   
}
