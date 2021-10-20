package com.agendamiento.sistema.controllers;

import com.agendamiento.sistema.Utils.JWTUtil;
import com.agendamiento.sistema.dao.TurnosDao;
import com.agendamiento.sistema.models.Turnos;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
public class TurnosController {

    @Autowired////inyeccion de dependencias hace que la clase usImp se cre un objeto y le guarda en la variable
    private TurnosDao turnosDao;

    @Autowired
    private JWTUtil jwtUtil;


    @RequestMapping(value = "api/turnos/{id}", method = RequestMethod.GET)//por defecto ya utiliza este metodo
    public Turnos getTurno(@PathVariable Long id){
        Turnos turno = new Turnos();
        turno.setId(id);
        turno.getCedula();
        turno.setEspecialidad("Medicina General");
        turno.setFecha("25/10/2021");
        turno.setHora("10:00");
        return turno;
    }

    @RequestMapping(value = "api/turnos", method = RequestMethod.GET)
    public List<Turnos> getTurnos(@RequestHeader(value = "Authorization") String token){
    if (!validarToken(token)){
        return  null;
    }
        return  turnosDao.getTurnos();
    }

    private boolean validarToken(String token){
        String turnoId = jwtUtil.getKey(token);
        return turnoId != null;
    }

    @RequestMapping(value = "api/turnopost", method = RequestMethod.POST)
    public void registrarTurnos(@RequestBody Turnos turno, RedirectAttributes attribute)  {
    String hash = (turno.getCedula());
        turno.setCedula(hash);

        turnosDao.registrar(turno);
        attribute.addFlashAttribute("success", "Turno guardado con exito!");
    }

    @RequestMapping(value = "turno2")
    public Turnos editar(){
        Turnos  turno= new Turnos();
        turno.setCedula("0104032206");
        turno.setEspecialidad("Medicina General");
        turno.setFecha("21/10/2021");
        turno.setHora("10:00");
        return turno;
    }

    @RequestMapping(value = "api/turno/{id}", method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader(value = "Authorization") String token,
                         @PathVariable Long id){
        if (!validarToken(token)){
            return;
        }
        turnosDao.eliminar(id);
    }

}
