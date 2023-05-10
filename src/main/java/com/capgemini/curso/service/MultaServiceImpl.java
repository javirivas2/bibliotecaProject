package com.capgemini.proyecto.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.proyecto.model.Multa;

@Service("multaServiceImpl")
@Transactional
public class MultaServiceImpl implements MultaService {

	@Override
	public List<Multa> getAllMultas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Multa getMultaById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveMulta(Multa multa) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMultaById(Long id) {
		// TODO Auto-generated method stub

	}

}
