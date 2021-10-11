package com.agendamiento.sistema.dao;

import com.agendamiento.sistema.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.GenerationType;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository//para acceder a la base de datos
@Transactional//armar consultas de sql a la base de datos
public class UsuarioDaoImp implements UsuarioDao {

    @PersistenceContext//referencia para guardar en la base de datos
    private EntityManager entityManager;//sirve para la conexion a la base de datos

    @Override
    public List<Usuario> getUsuarios() {
        String query = "FROM Usuario";//Objetos de la clase de java hace la consulta sobre hibernet
       return entityManager.createQuery(query).getResultList();

    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);//entityManager para consultar a la base de datos
        entityManager.remove(usuario);//despues lo eliminamos
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);//para guardar en la base de datos
    }

    @Override
    public Usuario optenerUsuarioPorCredenciales(Usuario usuario) {
        String query = "FROM Usuario WHERE email = :email";//Objetos de la clase de java hace la consulta sobre hibernet
        List<Usuario> lista = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();//recuperado el usuario de la db

        if (lista.isEmpty()){//si la lista de usuarios que busco en la base de datos esta vacia
            return null;
        }
        String passwordHashed = lista.get(0).getPassword();

        //verificar la contraseña
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(passwordHashed, usuario.getPassword())){//comprarar un hash con un password
            return lista.get(0);
        }
        return null;//si las credenciales no fueron correctas devuelve un null
    }
}
