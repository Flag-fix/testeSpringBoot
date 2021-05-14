package com.cliente.controller;

import com.cliente.models.Apolices;
import com.cliente.repository.ApoliceRepository;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ApoliceController
{
    @Autowired
    private ApoliceRepository ar;

    @RequestMapping("/apolice")
    public ModelAndView listarApolices(){
        ModelAndView mv = new ModelAndView("apolices");
        Iterable<Apolices> apolices = ar.findAll();
        mv.addObject("apolices",apolices);
        if(apolices != null){
            List<Apolices> val = (List<Apolices>) apolices;
            for (int i = 0; i < val.size(); i++) {
                SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy");
                Date dataFinal = val.get(i).getFimVigencia();
                Date dataInicial = val.get(i).getInicioVigencia();
                if (dataInicial.before(dataFinal)){
                    System.out.println("A data inicial é menor q a data final");
                }else {
                    System.out.println("A data inicial é maior q a data final");
                }
            }
        }
        return mv;
    }


    @RequestMapping(value = "/apolice", method = RequestMethod.POST)
    public String form (Apolices apolices){
        Double numApo = Math.random()*(5-2)+5;
        apolices.setNumeroApolice(numApo);
        ar.save(apolices);
        return "redirect:/apolice";
    }

    @RequestMapping("/deletarApolice")
    public String deletarApolice(long id){
        ar.deleteById(id);
        return "redirect:/apolice";
    }

    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(  "yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }


}
