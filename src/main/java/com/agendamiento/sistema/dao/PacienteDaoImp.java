package com.agendamiento.sistema.dao;

import com.agendamiento.sistema.models.Paciente;
import com.agendamiento.sistema.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository//para acceder a la base de datos
@Transactional//armar consultas de sql a la base de datos
public class PacienteDaoImp implements PacienteDao {

    @PersistenceContext//referencia para guardar en la base de datos
    private EntityManager entityManager;//sirve para la conexion a la base de datos





    @Override
    public List<Paciente> getPacientes() {
        String query = "FROM Paciente";//Objetos de la clase de java hace la consulta sobre hibernet
       return entityManager.createQuery(query).getResultList();

    }

    @Override
    public void eliminar(Long id) {
        Paciente paciente = entityManager.find(Paciente.class, id);//entityManager para consultar a la base de datos
        entityManager.remove(paciente);//despues lo eliminamos
    }

    @Override
    public void registrar(Paciente paciente) {
        entityManager.merge(paciente);//para guardar en la base de datos
    }

    @Override
    public Paciente optenerPacientePorCredenciales(Paciente paciente) {
        String query = "FROM Paciente WHERE email = :email";//Objetos de la clase de java hace la consulta sobre hibernet
        List<Paciente> lista = entityManager.createQuery(query)
                .setParameter("email", paciente.getEmail())
                .getResultList();//recuperado el usuario de la db

        if (lista.isEmpty()){//si la lista de usuarios que busco en la base de datos esta vacia
            return null;
        }
        String passwordHashed = lista.get(0).getPassword();

        //verificar la contraseña
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(passwordHashed, paciente.getPassword())){//comprarar un hash con un password
            return lista.get(0);
        }
        return null;//si las credenciales no fueron correctas devuelve un null
    }
}
