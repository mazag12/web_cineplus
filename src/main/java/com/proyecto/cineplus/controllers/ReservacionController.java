package com.proyecto.cineplus.controllers;

import com.proyecto.cineplus.models.Reservacion;
import com.proyecto.cineplus.repository.*;
import com.proyecto.cineplus.service.IUsuarioService;
import com.proyecto.cineplus.service.PeliculaService;
import com.proyecto.cineplus.service.ReservacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/reservacion")
public class ReservacionController {

    @Autowired
    private IReservavionRepository reporeserv;

    @Autowired
    private IClienteRepository repocliente;

    @Autowired
    private ITiporeservacionRespository repotiporeserv;

    @Autowired
    private PeliculaService repopeli;

    @Autowired
    private IComestibleRepository repocomes;

    @GetMapping("/cargar")
    public String abrirPagina(Model model){
        model.addAttribute("reservacion", new Reservacion());
        model.addAttribute("listadoPeliculas", repopeli.findAll());
        model.addAttribute("listadoComestibles", repocomes.findAll());
        model.addAttribute("listadoCliente", repocliente.findAll());
        model.addAttribute("listadoTipoReserv", repotiporeserv.findAll());
        model.addAttribute("listadoReservacion",reporeserv.findAll());
        return "MReservacion";
    }

    @GetMapping("/listado")
    public String listadoReservacion(Model model){
        //model.addAttribute("reservacion", new Reservacion());
        model.addAttribute("listadoCliente", repocliente.findAll());
        model.addAttribute("listadoTipoReserv", repotiporeserv.findAll());
        model.addAttribute("listadoReservacion", reporeserv.findAll());
        model.addAttribute("listadoPeliculas", repopeli.findAll());
        model.addAttribute("listadoComestibles", repocomes.findAll());
        return "ListReservacion";
    }

    @PostMapping("/guardar")
    public String guardarReservacion(@ModelAttribute(name = "reservacion")Reservacion reservacion, Model model){

        if(reservacion != null){


        /*    if (reservacion.getIdreserva() == -1) {
                // model.addAttribute("validacion", "Selecciona una Reservacion");
                model.addAttribute("listadoCliente", repocliente.findAll());
                model.addAttribute("listadoTipoReserv", repotiporeserv.findAll());
                model.addAttribute("listadoReservacion", reporeserv.findAll());
                model.addAttribute("listadoPeliculas", repopeli.findAll());
                model.addAttribute("listadoComestibles", repocomes.findAll());
                return "ListReservacion";
            }*/

            reporeserv.save(reservacion);
            model.addAttribute("listadoPeliculas", repopeli.findAll());
            model.addAttribute("listadoComestibles", repocomes.findAll());
            model.addAttribute("listadoCliente", repocliente.findAll());
            model.addAttribute("listadoTipoReserv", repotiporeserv.findAll());
            model.addAttribute("listadoReservacion", reporeserv.findAll());
            model.addAttribute("reservacion", new Reservacion());

            return "MReservacion";
        }
        return "redirect:/reservacion/cargar";
    }

    public String editarReservacion(@PathVariable String id, Model model){

        Optional<Reservacion> reservacion = reporeserv.findById(id);

        if(reservacion.isPresent()){
            model.addAttribute("listadoCliente", repocliente.findAll());
            model.addAttribute("listadoTipoReserv", repotiporeserv.findAll());
            model.addAttribute("listadoReservacion", reporeserv.findAll());
            model.addAttribute("listadoPeliculas", repopeli.findAll());
            model.addAttribute("listadoComestibles", repocomes.findAll());
            model.addAttribute("reservacion", reservacion);
            return "MReservacion";
        }
        return "redirect:/reservacion/listado";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarReservacion(@PathVariable String id, Model model){
        Reservacion reserva = reporeserv.findById(id).get();

        if(reserva != null){

            reserva.setEstado("I");
            reporeserv.save(reserva);
            model.addAttribute("listadoCliente", repocliente.findAll());
            model.addAttribute("listadoTipoReserv", repotiporeserv.findAll());
            model.addAttribute("listadoReservacion", reporeserv.findAll());
            model.addAttribute("listadoPeliculas", repopeli.findAll());
            model.addAttribute("listadoComestibles", repocomes.findAll());
            model.addAttribute("reservacion", reserva);
            return "MReservacion";
        }

        return "redirect:/reservacion/listado";
    }

}
