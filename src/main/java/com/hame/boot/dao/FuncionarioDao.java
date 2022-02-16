package com.hame.boot.dao;

import java.time.LocalDate;
import java.util.List;

import com.hame.boot.domain.Funcionario;

public interface FuncionarioDao {

    void save(Funcionario cargo);

    void update(Funcionario cargo);

    void delete(Long id);

    Funcionario findById(Long id);

    List<Funcionario> findAll();

	List<Funcionario> findByNome(String nome);

	List<Funcionario> findByCargo(Long id);

	List<Funcionario> findByDatas(LocalDate entrada, LocalDate saida);

	List<Funcionario> findByDataEntrada(LocalDate entrada);

	List<Funcionario> findByDataSaida(LocalDate saida);
}
