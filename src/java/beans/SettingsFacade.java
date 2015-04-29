package beans;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@ManagedBean
@SessionScoped
public class SettingsFacade extends AbstractFacade<Settings> {
    @PersistenceContext(unitName = "MicroBlog")
    private EntityManager em;

    @Resource
    UserTransaction utx;
    
    private Settings currentSettings;
    
    public void updateSet(){
    currentSettings = em.find(Settings.class, 1);
    
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SettingsFacade() {
        super(Settings.class);
    }
    
    public Settings getSettings() {
        
        return currentSettings;
    }
    
     public String saveSettings() {
        try {
            utx.begin();
            getEntityManager().merge(currentSettings);
            utx.commit();

        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
        }
        return "index.xhtml";
    }
    
}
