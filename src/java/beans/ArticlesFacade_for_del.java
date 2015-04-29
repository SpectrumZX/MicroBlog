package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

   UsersFacade usFacade;
   
   @Resource
   UserTransaction utx;

    @PersistenceContext(unitName = "MicroBlog")
    private EntityManager em;

    private Articles current;
    private Integer author_id;

    public Integer getAuthor_id() {
        author_id = current.getAuthorId().getId();
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
    public ArticlesFacade_for_del() {
        super(Articles.class);
    }
    public int getMaximumRange(){
    return maximum_range;
    }
    
    private int maximum_range = 5;  // количество статей на странице getRanges()
    
    public List<Ranges> getRanges(){  // возвращает лист диапазонов страниц 1-10, 11-20, 21-30... 
       
        List<Ranges> rangesList = new ArrayList<>();
        
        int articles_count = count();  // количество статей всего
        
        int last;
    
        for (int first = 1; first < articles_count;) {
            last = first + maximum_range-1;
            Ranges range = new Ranges(first, last); // добавляем новый диапазон в лист диапазонов. первая и последняя страница
            rangesList.add(range);
            first = last+1;
        }
   
    return rangesList;
    }
    
//    public int[] getArticlesRanges(){
//        findRange()
//    return ;
//    }

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
        setObjAuthorId_to_article();
        try {
            utx.begin();
            getEntityManager().merge(current);
            utx.commit();

        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
        }
        return "index.xhtml";
    }

    public String addNew() { 
        setObjAuthorId_to_article();  // перед комитом прикрепляем Users author_id к Articles current
         try {
            utx.begin();
            getEntityManager().persist(current);
            utx.commit();
        
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) { }
        
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
     
    public void setObjAuthorId_to_article(){
        // по id прикрепляем объект authorId в Articles current
    Users authorId = getEntityManager().find(Users.class, author_id);
    current.setAuthorId(authorId);
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
