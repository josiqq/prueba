package omoikane.repository;

import omoikane.entities.Caja;
import org.synyx.hades.dao.GenericDao;
import org.synyx.hades.dao.Query;
import org.synyx.hades.domain.Pageable;

/**
 * Created with IntelliJ IDEA.
 * User: octavioruizcastillo
 * Date: 17/11/12
 * Time: 04:20
 * To change this template use File | Settings | File Templates.
 */
public interface CajaRepo extends GenericDao<Caja, Long> {
    @Query("UPDATE Caja SET abierta = 1, horaAbierta = CURRENT_TIMESTAMP WHERE id_caja = ?1")
    public void abrirCaja(Long id);
}
