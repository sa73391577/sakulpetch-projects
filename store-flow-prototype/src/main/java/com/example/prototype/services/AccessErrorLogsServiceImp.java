package com.example.prototype.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prototype.entity.log.AccessErrorLogs;
import com.example.prototype.repository.jpa.AccessErrorLogsRepository;


@Service("AccessErrorLogsService_V1")
public class AccessErrorLogsServiceImp implements AccessErrorLogsService {
	
	@Autowired  AccessErrorLogsRepository accErrLogRepo;
	
	@Override
	public void addAccessErrorLog(AccessErrorLogs accErr) {
		accErrLogRepo.saveAndFlush(accErr);
	}
	
}
