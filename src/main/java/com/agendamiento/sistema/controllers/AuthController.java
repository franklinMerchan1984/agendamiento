package com.agendamiento.sistema.controllers;

import com.agendamiento.sistema.Utils.JWTUtil;
import com.agendamiento.sistema.dao.PacienteDao;
import com.agendamiento.sistema.dao.UsuarioDao;
import com.agendamiento.sistema.models.Paciente;
import com.agendamiento.sistema.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired////inyeccion de dependencias hace que la clase usImp se cree un objeto y le guarda en la variable
    private UsuarioDao usuarioDao;

    @Autowired
    private PacienteDao pacienteDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login(@RequestBody Usuario usuario,Paciente paciente){//recibo el email y el password

        Usuario usuarioLogueado= usuarioDao.optenerUsuarioPorCredenciales(usuario);
        if (usuarioLogueado != null){
            String tokenJwt = jwtUtil.create(String.valueOf(usuarioLogueado.getId()), usuarioLogueado.getEmail());
            return tokenJwt;
        }
        return "FAIL";
    }

    @RequestMapping(value = "api/loginpaciente", method = RequestMethod.POST)
    public String login(@RequestBody Paciente paciente){//recibo el email y el password

        Paciente pacienteLogueado= pacienteDao.optenerPacientePorCredenciales(paciente);
        if (pacienteLogueado != null){
            String tokenJwt = jwtUtil.create(String.valueOf(pacienteLogueado.getId()), pacienteLogueado.getEmail());
            return tokenJwt;
        }
        return "FAIL";
    }
}
