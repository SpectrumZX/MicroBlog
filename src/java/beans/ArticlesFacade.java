package beans;

import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ManagedBean
@Stateless
public class ArticlesFacade extends AbstractFacade<Articles> {
    @PersistenceContext(unitName = "MicroBlog")
    private EntityManager em1;

    @Override
    protected EntityManager getEntityManager() {
        return em1;
    }
    
    

    public ArticlesFacade() {
        super(Articles.class);
    }
    
}
