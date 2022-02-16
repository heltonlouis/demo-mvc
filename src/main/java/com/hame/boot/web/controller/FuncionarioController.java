package com.hame.boot.web.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hame.boot.domain.Cargo;
import com.hame.boot.domain.Funcionario;
import com.hame.boot.domain.UF;
import com.hame.boot.service.CargoService;
import com.hame.boot.service.FuncionarioService;
import com.hame.boot.web.validator.FuncionarioValidator;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {
	
	@Autowired
	private FuncionarioService funcionarioService;
	@Autowired
	private CargoService cargoService;
	
	@InitBinder
	public void initiBinder(WebDataBinder binder) {
		binder.addValidators(new FuncionarioValidator());
	}
	
    @GetMapping("/cadastrar")
    public String cadastrar(Funcionario funcionario) {
        return "funcionario/cadastro";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {
    	model.addAttribute("funcionarios", funcionarioService.buscarTodos());
    	return "funcionario/lista";
    }
    
    @PostMapping("/salvar")
    public String salvar(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attr) {   	
    	
    	if(result.hasErrors()) {
    		return "funcionario/cadastro";
    	}
    	
    	funcionarioService.salvar(funcionario);
    	attr.addFlashAttribute("success", "funcionario inserido com sucesso!");
    	return "redirect:/funcionarios/cadastrar";
    }
    
    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model) {
    	model.addAttribute("funcionario", funcionarioService.buscarPorId(id));
    	return "funcionario/cadastro";
    }
    
    @PostMapping("/editar")
    public String editar(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attr) {   	
    	
    	if(result.hasErrors()) {
    		return "funcionario/cadastro";
    	}
    	
    	funcionarioService.editar(funcionario);
    	attr.addFlashAttribute("success", "funcionario editado com sucesso!");
    	return "redirect:/funcionarios/listar";
    }
    
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {   	
    	funcionarioService.excluir(id);
    	attr.addFlashAttribute("success", "funcionario removido com sucesso!");
    	return "redirect:/funcionarios/listar";
    }
    
    @GetMapping("/buscar/nome")
    public String getPorNome(@RequestParam("nome") String nome, ModelMap model) {
    	model.addAttribute("funcionarios", funcionarioService.buscarPorNome(nome));
    	return "funcionario/lista";
    }
    
    @GetMapping("/buscar/cargo")
    public String getPorCargo(@RequestParam("id") Long id, ModelMap model) {
    	model.addAttribute("funcionarios", funcionarioService.buscarPorCargo(id));
    	return "funcionario/lista";
    }
    
    @GetMapping("/buscar/data")
    public String getPorDatas(@RequestParam("entrada") String entradaStr, 
    						  @RequestParam("saida") String saidaStr, 
    						  ModelMap model) {
    	System.out.println(entradaStr);
    	System.out.println(saidaStr);
    	
    	if (entradaStr != "" && saidaStr != "") {
    		LocalDate entrada = LocalDate.parse(entradaStr);
    		LocalDate saida = LocalDate.parse(saidaStr);
    		model.addAttribute("funcionarios", funcionarioService.buscarPorDatas(entrada, saida));
    	} else if (entradaStr != "") {
    		LocalDate entrada = LocalDate.parse(entradaStr);
    		model.addAttribute("funcionarios", funcionarioService.buscarPorDataEntrada(entrada));
    	} else if (saidaStr != "") {
    		LocalDate saida = LocalDate.parse(saidaStr);
    		model.addAttribute("funcionarios", funcionarioService.buscarPorDataSaida(saida));
    	}
    	
    	return "funcionario/lista";
    }
    
    @ModelAttribute("cargos")
    public List<Cargo> listaDeCargos() {
    	return cargoService.buscarTodos();
    }
    
    @ModelAttribute("ufs")
    public UF[] getUFs() {
    	return UF.values();
    }
    
}
