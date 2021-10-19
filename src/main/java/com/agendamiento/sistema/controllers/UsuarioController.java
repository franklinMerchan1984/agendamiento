package com.agendamiento.sistema.controllers;

import com.agendamiento.sistema.Utils.JWTUtil;
import com.agendamiento.sistema.dao.UsuarioDao;
import com.agendamiento.sistema.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;



@RestController
public class UsuarioController {

    @Autowired////inyeccion de dependencias hace que la clase usImp se cre un objeto y le guarda en la variable
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.GET)//por defecto ya utiliza este metodo
    public Usuario getUsuario(@PathVariable Long id){
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Frank");
        usuario.setApellido("Merchán");
        usuario.setEmail("franmerchan@hotmail.com");
        usuario.setTelefono("0988185515");
        return usuario;
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader(value = "Authorization") String token){
    if (!validarToken(token)){
        return  null;
    }
        return  usuarioDao.getUsuarios();
    }

    private boolean validarToken(String token){
        String usuarioId = jwtUtil.getKey(token);
        return usuarioId != null;
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public void registrarUsuarios(@RequestBody Usuario usuario){
    Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    String hash = argon2.hash(1,1024,1, usuario.getPassword());
    usuario.setPassword(hash);

          usuarioDao.registrar(usuario);
    }

    @RequestMapping(value = "usuario2")
    public Usuario editar(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Frank");
        usuario.setApellido("Merchán");
        usuario.setEmail("franmerchan@hotmail.com");
        usuario.setTelefono("0988185515");
        return usuario;
    }

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader(value = "Authorization") String token,
                         @PathVariable Long id){
        if (!validarToken(token)){
            return;
        }
        usuarioDao.eliminar(id);
    }

}
