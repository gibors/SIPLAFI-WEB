//package fi.uaemex.ejbs;
//
//import fi.uaemex.entities.Apreciacion;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//import javax.ejb.EJBContext;
//import javax.ejb.Stateless;
//import javax.ejb.TransactionManagement;
//import javax.ejb.TransactionManagementType;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.transaction.SystemException;
//import javax.transaction.UserTransaction;
//
//@Stateless
//@TransactionManagement(TransactionManagementType.BEAN)
//public class ApreciacionFacade extends AbstractFacade<Apreciacion> 
//{
//    @PersistenceContext(unitName = "SIPLAFI-WEB")
//    private EntityManager em;
//    @Resource
//    EJBContext contextEjb;
//    
//    @Override
//    protected EntityManager getEntityManager() 
//    {
//        return em;
//    }
//
//    public ApreciacionFacade() 
//    {
//        super(Apreciacion.class);
//    }
//    
//    
//    public Apreciacion updateManyAprec(List<Apreciacion> listApr)
//    {
//    	UserTransaction utx = contextEjb.getUserTransaction();
//    	try
//    	{
//    		utx.begin();    	
//    		for(Apreciacion ap:listApr)
//    		{
//    			if(ap.getCalificacion() != null && (ap.getCalificacion() >= 1 && ap.getCalificacion() <= 10))
//    			{
//    				em.merge(ap);
//    			}
//    			else if(ap.getCalificacion() != null &&(ap.getCalificacion() < 0 || ap.getCalificacion() > 10))
//    			{
//    	    		utx.rollback();    				
//    				return ap;
//    			}
//    		}
//    		utx.commit();    			
//    	}
//    	catch(Exception ex)
//    	{
//    		try 
//    		{
//				utx.rollback();
//			} catch (IllegalStateException e) 
//    		{
//				e.printStackTrace();
//			} catch (SecurityException e) 
//    		{
//				e.printStackTrace();
//			} catch (SystemException e) 
//    		{
//				e.printStackTrace();
//			}
//    		System.out.println(">>>Ocurrio un error al acutalizar la apreciacion " +ex.toString());
//    	}
//    	return null;
//    }
//    
//}
