package com.hame.boot.dao;

import java.util.List;

import com.hame.boot.domain.Departamento;

public interface DepartamentoDao {

    void save(Departamento cargo);

    void update(Departamento cargo);

    void delete(Long id);

    Departamento findById(Long id);

    List<Departamento> findAll();
}

