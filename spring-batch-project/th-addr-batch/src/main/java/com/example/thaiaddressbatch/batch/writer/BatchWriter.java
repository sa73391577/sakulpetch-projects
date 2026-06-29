package com.example.thaiaddressbatch.batch.writer;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.thaiaddressbatch.model.dto.internal.DistrictDto;
import com.example.thaiaddressbatch.model.dto.internal.ProvinceCompositeDto;
import com.example.thaiaddressbatch.model.dto.internal.SubDistrictDto;
import com.example.thaiaddressbatch.model.entity.Districts;
import com.example.thaiaddressbatch.model.entity.Provinces;
import com.example.thaiaddressbatch.model.entity.SubDistricts;
import com.example.thaiaddressbatch.repository.jpa.DistrictsRepository;
import com.example.thaiaddressbatch.repository.jpa.ProvinceRepository;
import com.example.thaiaddressbatch.repository.jpa.SubDistrictsRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class BatchWriter implements ItemWriter<ProvinceCompositeDto> {
	
	
	
	
	@Autowired
	private ProvinceRepository provinceRepo;
	
	@Autowired
	private DistrictsRepository districtsRepo;
	
	@Autowired
	private SubDistrictsRepository subDistrictsRepo;

	@Override
	public void write(Chunk<? extends ProvinceCompositeDto> chunk) throws Exception {
		log.info(">>> write data start processing.");
		
		for(ProvinceCompositeDto pCom : chunk) {
			log.info("pCom : {}",ToStringBuilder.reflectionToString(pCom));
			if(null!=pCom && null!=pCom.getProvince()) {
				Provinces p = new Provinces();
				p.setCode(pCom.getProvince().getCode());
				p.setNameTh(pCom.getProvince().getNameTh());
				p.setNameEn(pCom.getProvince().getNameEn());
				
				p.setCreatedBy("SYSTEM");
				p.setUpdatedBy("SYSTEM");
				p.setCreatedDate(LocalDateTime.now());
				p.setUpdatedDate(LocalDateTime.now());
				
				p = provinceRepo.saveAndFlush(p);
				log.info("provinces saved : {}",ToStringBuilder.reflectionToString(p));
				
				if(null!=pCom.getDistricts() && pCom.getDistricts().size() > 0) {
					for(DistrictDto disDto : pCom.getDistricts()) {
						Districts d = new Districts();
						d.setCode(disDto.getCode());
						d.setNameEn(disDto.getNameEn());
						d.setNameTh(disDto.getNameTh());
						d.setProvinces(p);
						d.setCreatedBy("SYSTEM");
						d.setUpdatedBy("SYSTEM");
						d.setCreatedDate(LocalDateTime.now());
						d.setUpdatedDate(LocalDateTime.now());
						d = districtsRepo.saveAndFlush(d);
						log.info("districts saved : {}",ToStringBuilder.reflectionToString(d));
						
						if( null!=disDto.getSubDistricts() && disDto.getSubDistricts().size() > 0) {
							for(SubDistrictDto subDisDto : disDto.getSubDistricts() ) {
								SubDistricts subDis = new SubDistricts();
								subDis.setCode(subDisDto.getCode());
								subDis.setNameEn(subDisDto.getNameEn());
								subDis.setNameTh(subDisDto.getNameTh());
								subDis.setZipCode(subDisDto.getZipCode());
								subDis.setDistricts(d);
								subDis.setCreatedBy("SYSTEM");
								subDis.setUpdatedBy("SYSTEM");
								subDis.setCreatedDate(LocalDateTime.now());
								subDis.setUpdatedDate(LocalDateTime.now());
								subDis = subDistrictsRepo.saveAndFlush(subDis);
								log.info("subDistricts saved : {}",ToStringBuilder.reflectionToString(subDis));
							}
						}
						
					}
				}
				
			}
			
		}
		log.info(">>> write data end process.");
	}
	
}
