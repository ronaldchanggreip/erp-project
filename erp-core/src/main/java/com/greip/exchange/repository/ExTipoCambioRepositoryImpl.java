package com.greip.exchange.repository;

import com.greip.exchange.dto.ExTipoCambioDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrador on 03/02/2017.
 */
public class ExTipoCambioRepositoryImpl implements ExTipoCambioRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<BigInteger> procedureTiposCambioDelDia(Date fecha){
        //System.out.println("EN EL METODO!" );
        List<BigInteger> lst = null;
        StoredProcedureQuery tcDiaProcedure =
                em.createNamedStoredProcedureQuery("obtenerTiposCambioDelDia");
        StoredProcedureQuery storedProcedure = tcDiaProcedure
                .setParameter("P_FECHA", fecha);
        List<Object> result = storedProcedure.getResultList();
        if(result!=null){
            lst = new ArrayList<>();
            for (int i = 0; i< result.size(); i++) {
                BigInteger o  = (BigInteger)result.get(i);
                lst.add(o);
            }
        }
        return lst;
    }

    @Override
    public boolean inactivarOtrosTiposCambios(BigInteger id){

        List<BigInteger> lst = null;
        StoredProcedureQuery sp =
                em.createNamedStoredProcedureQuery("inactivarOtrosTiposCambios");
        StoredProcedureQuery storedProcedure = sp
                .setParameter("P_ID", id);

        boolean rpta = sp.execute();

        return rpta;
    }

    @Override
    public List<ExTipoCambioDto> getsPorFecha(Date fecha, BigInteger moneda){

        try{
            StoredProcedureQuery sp =
                    em.createNamedStoredProcedureQuery("obtenerTipoCambioPorFecha");
            StoredProcedureQuery storedProcedure = sp
                    .setParameter("P_FECHA", fecha)
                    .setParameter("P_MONEDA", moneda);
            List<ExTipoCambioDto> result = storedProcedure.getResultList();



            return result;
        }catch (Exception e) {
            //System.out.println(e);
            return null;
        }

    }

}
