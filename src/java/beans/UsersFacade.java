package beans;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;


@ManagedBean
@Stateless
public class UsersFacade extends AbstractFacade<Users> {
    @EJB private Utils utils;
    @EJB private GroupsFacade groupsFacade;
    
    @PersistenceContext(unitName = "MicroBlog")
    private EntityManager em;
// ArticleFacade_for_del  не может создать stateless EJB из-за  @Resource в UsersFacade
    @Resource
    UserTransaction utx;
    

Users current_usr = new Users();
    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public UsersFacade() {
        super(Users.class);
    }
    
    public Users getCurrentUsr() {
        return current_usr;
    }
    
  
    public String prepareReg() {
      current_usr = new Users();
      
      return "registration.xhtml";
    }
    
    public String registration() {
        //получаем хеш пароля и пишем его в entity
        String hash = utils.md5(current_usr.getPassword());
        current_usr.setPassword(hash);
        
        try {
            utx.begin();
            getEntityManager().persist(current_usr);
            utx.commit();
          //  return "reg_finish.xhtml";
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {}
         
        int user_id = findIdByName(current_usr.getLogin());
        current_usr = this.find(user_id);
        Groups groups = groupsFacade.find(1); // id 1 - admins group
        groups.getUsersCollection().add(current_usr);
        
    try {
            utx.begin();
            getEntityManager().merge(groups);
            utx.commit();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {}
     return "reg_finish.xhtml";
     
    }
    
        public int findIdByName(String name) {
        Query q = em.createNativeQuery("SELECT id FROM users WHERE login='" + name + "'");
        String str_id = q.getSingleResult().toString();
        int id = Integer.parseInt(str_id);
        return id;
    }
        
     
 
}
