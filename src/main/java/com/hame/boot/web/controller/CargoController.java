package com.hame.boot.web.controller;

import com.hame.boot.domain.Cargo;
import com.hame.boot.domain.Departamento;
import com.hame.boot.service.CargoService;
import com.hame.boot.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import javax.validation.Valid;

@Controller
@RequestMapping("/cargos")
public class CargoController {
    @Autowired
    private CargoService cargoService;
    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping("/cadastrar")
    public String cadastrar(Cargo cargo) {
        return "cargo/cadastro";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {
    	model.addAttribute("cargos", cargoService.buscarTodos());
        return "cargo/lista";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Cargo cargo, BindingResult result, RedirectAttributes attr) {
        
    	if (result.hasErrors()) {
    		return "cargo/cadastro";
    	}
    	
    	cargoService.salvar(cargo);
        attr.addFlashAttribute("success", "Cargo inserido com sucesso.");
        return "redirect:/cargos/cadastrar";
    }
    
    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model) {
    	model.addAttribute("cargo", cargoService.buscarPorId(id));
    	return "cargo/cadastro";
    }
    
    @PostMapping("/editar")
    public String editar(@Valid Cargo cargo, BindingResult result, RedirectAttributes attr) {
    	
    	if (result.hasErrors()) {
    		return "cargo/cadastro";
    	}
    	
    	cargoService.editar(cargo);
    	attr.addFlashAttribute("success", "Registro atualizado com sucesso");
    	return "redirect:/cargos/listar";
    }
    
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
    	if (cargoService.cargoTemFuncionarios(id)) {
    		attr.addFlashAttribute("fail", "Cargo não excluído. Tem funcionário(s) vinculados(s).");
    	} else {
    		cargoService.excluir(id);
    		attr.addFlashAttribute("success", "Cargo excluído com sucesso.");
    	}
    	return "redirect:/cargos/listar";
    }

    @ModelAttribute("departamentos")
    public List<Departamento> listaDeDepartamentos() {
        return departamentoService.buscarTodos();
    }
}
