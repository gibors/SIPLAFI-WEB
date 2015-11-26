
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
                    .setParameter("lunIni",lunIni).setParameter("lunFin", lunFin).setParameter("semestre", semestre).setParameter("cveMat", gpoPk.getClaveMateria()).setParameter("rfcPro",gpoPk.getRfcProfesor())
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
                    .setParameter("marIni",marIni).setParameter("marFin", marFin).setParameter("semestre", semestre).setParameter("cveMat", gpoPk.getClaveMateria()).setParameter("rfcPro",gpoPk.getRfcProfesor())
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
                    .setParameter("mieIni",mieIni).setParameter("mieFin", mieFin).setParameter("semestre", semestre).setParameter("cveMat", gpoPk.getClaveMateria()).setParameter("rfcPro",gpoPk.getRfcProfesor())
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
                    .setParameter("jueIni",jueIni).setParameter("jueFin", jueFin).setParameter("semestre", semestre).setParameter("cveMat", gpoPk.getClaveMateria()).setParameter("rfcPro",gpoPk.getRfcProfesor())
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
                    .setParameter("vieIni",vieIni).setParameter("vieFin", vieFin).setParameter("semestre", semestre).setParameter("cveMat", gpoPk.getClaveMateria()).setParameter("rfcPro",gpoPk.getRfcProfesor())
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
                    .setParameter("sabIni",sabIni).setParameter("sabFin", sabFin).setParameter("semestre", semestre).setParameter("cveMat", gpoPk.getClaveMateria()).setParameter("rfcPro",gpoPk.getRfcProfesor())
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
                .setParameter("semestre", semester).setParameter("cveMat", gpoPk.getClaveMateria()).setParameter("rfcPro",gpoPk.getRfcProfesor())
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
        String query = "SELECT COUNT(g) FROM Grupo g WHERE g.profesor.rfcProfesor = :rfc_prof AND g.validado = 0 AND g.periodos.actual = 1";
        
        Query q = getEntityManager().createQuery(query).setParameter("rfc_prof", rfc_prof);
        
        if((Long)q.getSingleResult() > 0)
            return true;
        else 
            return false;
    }
    
        public boolean todosAceptadosOConfirmados(String rfc_prof,int size)
    {
        String query = "SELECT COUNT(g) FROM Grupo g WHERE (g.validado = 1 OR g.validado = 3) AND g.profesor.rfcProfesor = :rfc_prof AND g.periodos.actual = 1";
        
        Query q = getEntityManager().createQuery(query).setParameter("rfc_prof", rfc_prof);
        
       
        if((Long)q.getSingleResult() == size)
            return true; // no todos estan confirmados o aceptados
        else 
            return false; // todos confirmados y/0 aceptados :)
        
    }
}
