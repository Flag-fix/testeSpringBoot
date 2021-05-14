package com.cliente.controller;

import com.cliente.models.Apolices;
import com.cliente.models.Cliente;
import com.cliente.repository.ApoliceRepository;
import com.cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClienteController
{

    @Autowired
    private ClienteRepository cr;

    @RequestMapping("/cliente")
    public ModelAndView listarClientes(){
        ModelAndView mv = new ModelAndView("index");
        Iterable<Cliente> clientes = cr.findAll();
        mv.addObject("clientes", clientes);
        return mv;
    }

    @RequestMapping(value = "/cliente", method = RequestMethod.POST)
    public String form (Cliente cliente){
        cr.save(cliente);
        return "redirect:/cliente";
    }

    @RequestMapping("/deletar")
    public String deletarCliente(long id){
        cr.deleteById(id);
        return "redirect:/cliente";
    }

}
