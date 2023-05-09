package com.capgemini.curso.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.capgemini.curso.model.Lector;
import com.capgemini.curso.repository.LectorRepository;

@Service("lectorServiceImpl")
@Transactional
public class LectorServiceImp implements LectorService {

	
	private static final Logger logger = LoggerFactory.getLogger(LectorServiceImpl.class);
	@Autowired
	private LectorRepository lectorRepository;


	
	@Override
	@Transactional(readOnly=true)
	public List<Lector> getAllLectores() {
		logger.info("LectorServiceIml getAllLectores");
		List<Lector> lectores = new ArrayList<>();
		lectorRepository.findAll().forEach(lectores::add);
		return lectores;
	}

	@Override
	@Transactional(readOnly=true)
	public Lector getLectorById(long id) {
	    logger.info("LectorServiceIml getLectorById");
	    Optional<Lector> optLector = lectorRepository.findById(id);
	    return optLector.isPresent() ? optLector.get() : null;
	}

	@Override
	public void saveLector(Lector lector) {
		lectorRepository.save(lector);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Page<Lector> findAllPage(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
