package com.devsuperior.dslearnbds.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dslearnbds.dtos.DeliverCorrectionDTO;
import com.devsuperior.dslearnbds.entities.Deliver;
import com.devsuperior.dslearnbds.repositories.DeliverRepository;
import com.devsuperior.dslearnbds.services.exceptions.ResourceNotFoundException;

@Service
public class DeliverService {

	@Autowired
	private DeliverRepository repository;
	
	@PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
	@Transactional
	public void saveCorrection(Long id, DeliverCorrectionDTO dto) {
		try {
			Deliver deliver = repository.getOne(id);
			deliver.setStatus(dto.getStatus());
			deliver.setFeedback(dto.getFeedback());
			deliver.setCorrectCount(dto.getCorrectCount());
			repository.save(deliver);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado");
		}
	}
}
