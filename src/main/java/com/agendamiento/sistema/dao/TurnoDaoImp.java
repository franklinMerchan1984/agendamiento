package com.agendamiento.sistema.dao;

import com.agendamiento.sistema.models.Turnos;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository//para acceder a la base de datos
@Transactional//armar consultas de sql a la base de datos
public class TurnoDaoImp implements TurnosDao {

    @PersistenceContext//referencia para guardar en la base de datos
    private EntityManager entityManager;//sirve para la conexion a la base de datos





    @Override
    public List<Turnos> getTurnos() {
        String query = "FROM Turnos";//Objetos de la clase de java hace la consulta sobre hibernet
       return entityManager.createQuery(query).getResultList();

    }

    @Override
    public void eliminar(Long id) {
        Turnos turno = entityManager.find(Turnos.class, id);//entityManager para consultar a la base de datos
        entityManager.remove(turno);//despues lo eliminamos
    }

    @Override
    public void registrar(Turnos turno)   {

        entityManager.merge(turno);//para guardar en la base de datos
    }

    @Override
    public Turnos optenerTurnoPorCedula(Turnos turno) {
        String query = "FROM Turno WHERE cedula = :cedula";//Objetos de la clase de java hace la consulta sobre hibernet
        List<Turnos> lista = entityManager.createQuery(query)
                .setParameter("email", turno.getCedula())
                .getResultList();//recuperado la cedula de la db

        if (lista.isEmpty()){//si la lista de usuarios que busco en la base de datos esta vacia
            return null;
        }
        String passwordHashed = lista.get(0).getCedula();

        //verificar la contraseña
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(passwordHashed, turno.getCedula())){//comprarar un hash con un password
            return lista.get(0);
        }
        return null;//si las credenciales no fueron correctas devuelve un null
    }
}
