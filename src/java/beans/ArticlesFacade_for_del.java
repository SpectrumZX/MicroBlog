package beans;

import java.util.Date;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
@SessionScoped
public class ArticlesFacade_for_del extends AbstractFacade<Articles> {

   //@EJB UsersFacade usFacade;
    
   @Resource
   UserTransaction utx;

    
    @PersistenceContext(unitName = "MicroBlog")
    private EntityManager em;

    Articles current;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArticlesFacade_for_del() {
        super(Articles.class);
    }

    public String prepareEdit(Integer id) {
        current = new Articles();
        current = find(id);
        return "edit.xhtml";
    }

    public Articles getCurrent() {
        return current;
    }

    public void remove() {
// для удаления через JSF
        try {
            utx.begin();
            getEntityManager().remove(getEntityManager().merge(current));
            utx.commit();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
        }

    }

    public String edit() {

        try {
            utx.begin();
            getEntityManager().merge(current);
            utx.commit();

        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
        }
        return "index.xhtml";
    }


    public String add(Articles entity) {
        try {
            utx.begin();
            getEntityManager().persist(entity);
            utx.commit();
            return "index.xhtml";
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
        }
        return null;
    }

    public String addNew() {  
        add(current);
        return "index.xhtml";
    }

    public String getMaxId() {
        Query q = em.createNativeQuery("SELECT id FROM articles WHERE id=(SELECT max(id) FROM articles)");
        String max_id = q.getSingleResult().toString();
        return max_id;
    }

    public int findIdByName1(String name) {
        Query q = em.createNativeQuery("SELECT id FROM users WHERE login='" + name + "'");
        String str_id = q.getSingleResult().toString();
        int id = Integer.parseInt(str_id);
        return id;
    }
    
        public int findIdByName2(String name) {
         // этот метод в отличие от вар1 возвращает какой-то не такой формат
        String result = em.createNamedQuery("Users.findByLogin").setParameter("login", name).getSingleResult().toString();
        int id = Integer.parseInt(result);
        return id;
    }
    
    public String prepareAddNew(String login) {
   
       current = new Articles();
        // ищем id по логину
       int id = findIdByName1(login);
       // ищем по id и вставляем Users authorId
       // поиск почему-то не выдает результатов??? пока временно ID=1
       
       //Users authorId = usFacade.find(id);  // пробуем обойти инжекцию @EJB UsersFacade usFacade;
       Users authorId = getEntityManager().find(Users.class, id);
       current.setAuthorId(authorId);

       // вставляем сегодняшнюю дату
       current.setData(new Date(System.currentTimeMillis()));

       return "create.xhtml";
        
    }

    
    
}
