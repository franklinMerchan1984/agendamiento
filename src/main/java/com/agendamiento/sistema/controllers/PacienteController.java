package com.agendamiento.sistema.controllers;

import com.agendamiento.sistema.Utils.JWTUtil;
import com.agendamiento.sistema.dao.PacienteDao;
import com.agendamiento.sistema.dao.UsuarioDao;
import com.agendamiento.sistema.models.Paciente;
import com.agendamiento.sistema.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PacienteController {

    @Autowired////inyeccion de dependencias hace que la clase usImp se cre un objeto y le guarda en la variable
    private PacienteDao pacienteDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/pacientes/{id}", method = RequestMethod.GET)//por defecto ya utiliza este metodo
    public Paciente getPaciente(@PathVariable Long id){
        Paciente paciente = new Paciente();
        paciente.setId(id);
        paciente.setNombre("Frank");
        paciente.setApellido("Merchán");
        paciente.setEmail("franmerchan@hotmail.com");
        paciente.setTelefono("0988185515");
        return paciente;
    }

    @RequestMapping(value = "api/pacientes", method = RequestMethod.GET)
    public List<Paciente> getPacientes(@RequestHeader(value = "Authorization") String token){
    if (!validarToken(token)){
        return  null;
    }
        return  pacienteDao.getPacientes();
    }

    private boolean validarToken(String token){
        String pacienteId = jwtUtil.getKey(token);
        return pacienteId != null;
    }

    @RequestMapping(value = "api/pacientes", method = RequestMethod.POST)
    public void registrarPacientes(@RequestBody Paciente paciente){
    Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    String hash = argon2.hash(1,1024,1, paciente.getPassword());
    paciente.setPassword(hash);

          pacienteDao.registrar(paciente);
    }

    @RequestMapping(value = "paciente2")
    public Paciente editar(){
        Paciente paciente = new Paciente();
        paciente.setNombre("Frank");
        paciente.setApellido("Merchán");
        paciente.setEmail("franmerchan@hotmail.com");
        paciente.setTelefono("0988185515");
        return paciente;
    }

    @RequestMapping(value = "api/pacientes/{id}", method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader(value = "Authorization") String token,
                         @PathVariable Long id){
        if (!validarToken(token)){
            return;
        }
        pacienteDao.eliminar(id);
    }

}
