
package  fi.uaemex.ejbs;

import  fi.uaemex.entities.Grupo;
import fi.uaemex.entities.GrupoPK;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class GrupoFacade extends AbstractFacade<Grupo> {
    @PersistenceContext(unitName = "SIPLAFI-WEB")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GrupoFacade() {
        super(Grupo.class);
    }
    
    public List<Grupo> findTraslapeLun(Date lunIni, Date lunFin,GrupoPK gpoPk,Integer semestre)
    {
        try
        {
            List<Grupo> lista = getEntityManager().createNamedQuery("BuscaTraslapeLunes",Grupo.class)
                    .setParameter("lunIni",lunIni).setParameter("lunFin", lunFin).setParameter("semestre", semestre).setParameter("cveMat", gpoPk.getClaveMateria())
                    .setParameter("period",gpoPk.getPeriodo()).setParameter("name",gpoPk.getNombre())
                    .getResultList();
            if(lista != null)
                return lista;
            else
                return null;
        }
        catch(Exception ex)
        {
            System.out.println(" ocurrio un error en la consulta para obtener traslape de lunes " + ex.toString());
            return null;   
        }
    }
     public List<Grupo> findTraslapeMar(Date marIni, Date marFin,GrupoPK gpoPk,Integer semestre)
    {
        try
        {
            List<Grupo> lista = getEntityManager().createNamedQuery("BuscaTraslapeMartes",Grupo.class)
                    .setParameter("marIni",marIni).setParameter("marFin", marFin).setParameter("semestre", semestre).setParameter("cveMat", gpoPk.getClaveMateria())
                    .setParameter("period",gpoPk.getPeriodo()).setParameter("name",gpoPk.getNombre())
                    .getResultList();
            if(lista != null)
                return lista;
            else
                return null;
        }
        catch(Exception ex)
        {
            System.out.println(" ocurrio un error en la consulta para obtener traslape de martes " + ex.toString());
            return null;   
        }
    }
    public List<Grupo> findTraslapeMie(Date mieIni, Date mieFin,GrupoPK gpoPk,Integer semestre)
    {
        try
        {
            List<Grupo> lista = getEntityManager().createNamedQuery("BuscaTraslapeMiercoles",Grupo.class)
                    .setParameter("mieIni",mieIni).setParameter("mieFin", mieFin).setParameter("semestre", semestre).setParameter("cveMat", gpoPk.getClaveMateria())
                    .setParameter("period",gpoPk.getPeriodo()).setParameter("name",gpoPk.getNombre())
                    .getResultList();
            if(lista != null)
                return lista;
            else
                return null;
        }
        catch(Exception ex)
        {
            System.out.println(" ocurrio un error en la consulta para obtener traslape de miercoles " + ex.toString());
            return null;   
        }
    }
    public List<Grupo> findTraslapeJue(Date jueIni, Date jueFin,GrupoPK gpoPk,Integer semestre)
    {
        try
        {
            List<Grupo> lista = getEntityManager().createNamedQuery("BuscaTraslapeJueves",Grupo.class)
                    .setParameter("jueIni",jueIni).setParameter("jueFin", jueFin).setParameter("semestre", semestre).setParameter("cveMat", gpoPk.getClaveMateria())
                    .setParameter("period",gpoPk.getPeriodo()).setParameter("name",gpoPk.getNombre())
                    .getResultList();
            if(lista != null)
                return lista;
            else
                return null;
        }
        catch(Exception ex)
        {
            System.out.println(" ocurrio un error en la consulta para obtener traslape de jueves " + ex.toString());
            return null;   
        }
    }
    public List<Grupo> findTraslapeVie(Date vieIni, Date vieFin,GrupoPK gpoPk,Integer semestre)
    {
        try
        {
            List<Grupo> lista = getEntityManager().createNamedQuery("BuscaTraslapeViernes",Grupo.class)
                    .setParameter("vieIni",vieIni).setParameter("vieFin", vieFin).setParameter("semestre", semestre).setParameter("cveMat", gpoPk.getClaveMateria())
                    .setParameter("period",gpoPk.getPeriodo()).setParameter("name",gpoPk.getNombre())
                    .getResultList();
            if(lista != null)
                return lista;
            else
                return null;
        }
        catch(Exception ex)
        {
            System.out.println(" ocurrio un error en la consulta para obtener traslape de viernes " + ex.toString());
            return null;   
        }
    }
    public List<Grupo> findTraslapeSab(Date sabIni, Date sabFin,GrupoPK gpoPk,Integer semestre)
    {
        try
        {
            List<Grupo> lista = getEntityManager().createNamedQuery("BuscaTraslapeSabado",Grupo.class)
                    .setParameter("sabIni",sabIni).setParameter("sabFin", sabFin).setParameter("semestre", semestre).setParameter("cveMat", gpoPk.getClaveMateria())
                    .setParameter("period",gpoPk.getPeriodo()).setParameter("name",gpoPk.getNombre())
                    .getResultList();
            if(lista != null)
                return lista;
            else
                return null;
        }
        catch(Exception ex)
        {
            System.out.println(" ocurrio un error en la consulta para obtener traslape de sabado " + ex.toString());
            return null;   
        }
    }
    
    public List<Grupo> findGrupoSemestre(Integer semester,GrupoPK gpoPk)
    {
        try
          {
            return (List<Grupo>) getEntityManager().createNamedQuery("Grupo.findGruposSemestre",Grupo.class)
                .setParameter("semestre", semester).setParameter("cveMat", gpoPk.getClaveMateria())
                .setParameter("period",gpoPk.getPeriodo()).setParameter("name",gpoPk.getNombre())
                    .getResultList();
          }
        catch(Exception ex)
        {
            System.out.println(" ocurrio un error en la consulta de grupo " + ex.toString());
            return null;
        }
    }
    
    public Double validaSumaHoras(Integer idGrupo, Integer semestre)
    {
        Double sum = getEntityManager().createNamedQuery("sumaHoras",Double.class)
                .setParameter("idGrupo", idGrupo).setParameter("semestre",semestre).getSingleResult();
        return sum;                 
    }
    
    public boolean hayGruposParaValidar(String rfc_prof)
    {
        String query = "SELECT COUNT(g) FROM Grupo g WHERE g.rfcProfesor.rfcProfesor = :rfc_prof AND g.validado = 0 AND g.periodos.actual = 1";
        
        Query q = getEntityManager().createQuery(query).setParameter("rfc_prof", rfc_prof);
        
        if((Long)q.getSingleResult() > 0)
            return true;
        else 
            return false;
    }
    
        public boolean todosAceptadosOConfirmados(String rfc_prof,int size)
    {
        String query = "SELECT COUNT(g) FROM Grupo g WHERE (g.validado = 1 OR g.validado = 3) AND g.rfcProfesor.rfcProfesor = :rfc_prof AND g.periodos.actual = 1";
        
        Query q = getEntityManager().createQuery(query).setParameter("rfc_prof", rfc_prof);
        
       
        if((Long)q.getSingleResult() == size)
            return true; // no todos estan confirmados o aceptados
        else 
            return false; // todos confirmados y/0 aceptados :)
        
    }
        public String getNombreDelNuevoGrupo(String cveMateria)
        {
        	List<Grupo> listGpo = getEntityManager().createNamedQuery("Grupo.findByClaveMateria").setParameter("claveMateria",cveMateria).getResultList();
        	String nuevoNombre = "CO01";
        	if(!listGpo.isEmpty())
        	{ // Si la lista de grupos de la misma materia no esta vacia (TOP)
        		if(listGpo.size() == 1)
        		{ // Si solo se encuentra un grupo en la lista de elementos (TOP)
        			String nombre = listGpo.get(0).getGrupoPK().getNombre().trim();
        			if(nombre.length() == 4)
        			{ // Si la longitud es igual a 4 (TOP)
            			int val = Integer.parseInt(nombre.substring(2,4));        				
        				if(val == 1)
        					nuevoNombre = "CO02";
        				else 
        					nuevoNombre = "CO01";
        			} // Si la longitud es igual a 4 (BOTTOM)
        			else
        			{
            			int val = Integer.parseInt(nombre.substring(1,2));        				
        				if(val == 1)
        					nuevoNombre = "O2";
        				else 
        					nuevoNombre = "O1";
        			}
        		} // Si solo se encuentra un grupo en la lista de elementos (BOTTOM) 
        		else
        		{ // Si la lista contiene mas de un grupo (TOP) 
        			for(int i=1; i< listGpo.size();i++)
        			{
        				String nombre = listGpo.get(i).getGrupoPK().getNombre().trim();
        				String nombreAnt = listGpo.get(i-1).getGrupoPK().getNombre().trim(); 
        				if(nombre.length() == 4)
        				{ // Si la longitud es igual a 4 (TOP)        					
        					int val = Integer.parseInt(nombre.substring(2,4));
        					int valAnt = Integer.parseInt(nombreAnt.substring(2,4));
        					if(val != (valAnt + 1))
        						return ((valAnt + 1) < 10 ? "CO0" : "C0") + valAnt +1;
        				} // Si la longitud es igual a 4 (BOTTOM)
        				else
        				{
        					int val = Integer.parseInt(nombre.substring(1,2));
        					int valAnt = Integer.parseInt(nombreAnt.substring(1,2));
        					if(val != (valAnt + 1))
        						return "O" + (valAnt + 1);        					        					
        				}
        			}
        			String nombre = listGpo.get(listGpo.size()-1).getGrupoPK().getNombre().trim();
        			if(nombre.length() == 4)
        			{
        				int val = Integer.parseInt(nombre.substring(2, 4));
        				nuevoNombre = ((val + 1) < 10 ? "CO0" : "CO") + (val + 1);
        			}
        			else
        			{
        				int val = Integer.parseInt(nombre.substring(1, 2));
        				nuevoNombre = ((val + 1) < 10 ? "CO0" : "CO") + (val + 1);
        			}
        		}// Si la lista contiene mas de un grupo (BOTTOM)         	
        	} // Si la lista de grupos de la misma materia no esta vacia (BOTTOM)	
        	return nuevoNombre;        	
        }
}
