package beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class GroupsFacade extends AbstractFacade<Groups> {

    @PersistenceContext(unitName = "MicroBlog")
    private EntityManager em3;

    @Override
    protected EntityManager getEntityManager() {
        return em3;
    }

    public GroupsFacade() {
        super(Groups.class);
    }
    
    @Override
    public Groups find(Object id) {
        return getEntityManager().find(Groups.class, id);
    }
   
}
