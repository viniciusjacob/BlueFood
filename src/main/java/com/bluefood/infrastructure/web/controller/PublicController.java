package com.bluefood.infrastructure.web.controller;

import javax.validation.Valid;

import com.bluefood.domain.cliente.Cliente;
import com.bluefood.service.ClienteService;
import com.bluefood.service.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "public")
public class PublicController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/cliente/new")
    public String novoCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("editMode", false);
        return "cliente-cadastro";
    }

    @GetMapping("/restaurante/new")
    public String novoRestaurante(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("editMode", false);
        return "restaurante-cadastro";
    }

    @PostMapping("/cliente/save")
    public String saveCliente(@ModelAttribute("cliente") @Valid Cliente cliente, Errors errors, Model model) {
        
        if (!errors.hasErrors()){
            try{
                clienteService.saveCliente(cliente);
                model.addAttribute("cliente", new Cliente());
                model.addAttribute("msg", "Cliente gravado com sucesso!");
            }catch(ValidationException e){
                errors.rejectValue("email", null, e.getMessage());
            }
        }
        
        model.addAttribute("editMode", false);
        return "cliente-cadastro";
    }
    
}
