package com.example.thaiaddressbatch.batch.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.repository.persistence.StepExecution;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.example.thaiaddressbatch.model.dto.external.DistrictResponseDto;
import com.example.thaiaddressbatch.model.dto.external.ProvinceResponseDto;
import com.example.thaiaddressbatch.model.dto.external.SubDistrictResponseDto;
import com.example.thaiaddressbatch.model.dto.internal.DistrictDto;
import com.example.thaiaddressbatch.model.dto.internal.ProvinceCompositeDto;
import com.example.thaiaddressbatch.model.dto.internal.ProvinceDto;
import com.example.thaiaddressbatch.model.dto.internal.SubDistrictDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BatchProcessor implements ItemProcessor<ProvinceResponseDto , ProvinceCompositeDto> {
	
	
	private final RestClient restClient;
	private final AtomicLong provinceCodeCounter = new AtomicLong(1); 
	private final AtomicLong disCodeCounter = new AtomicLong(1); 
	private final AtomicLong subDisCodeCounter = new AtomicLong(1); 
	private String pathGetDistrictsByProvinceId;
	private String pathGetSubDistrictsByDistrictId;
	
	public BatchProcessor(@Value("${address.thai.from.external.url}") String url
			,@Value("${address.thai.from.external.path.get.districts.by.province.id}") String pathGetDistrictsByProvinceId
			,@Value("${address.thai.from.external.path.get.subdistricts.by.districts.id}") String pathGetSubDistrictsByDistrictId ) {
		this.restClient = RestClient.builder().baseUrl(url).build();
		this.pathGetDistrictsByProvinceId = pathGetDistrictsByProvinceId;
		this.pathGetSubDistrictsByDistrictId = pathGetSubDistrictsByDistrictId;
	}
	
	
	@Override
	public @Nullable ProvinceCompositeDto process(ProvinceResponseDto p) throws Exception {
		
		
		log.info("Process Working !!!");
		
		//Get District of Province.
		DistrictResponseDto resExternalDistrict = restClient.get().uri(pathGetDistrictsByProvinceId.concat(String.valueOf(p.getId()))).retrieve().body(new ParameterizedTypeReference<DistrictResponseDto>(){});

		if(null!=resExternalDistrict) {
			
			List<DistrictDto> disDtoList = new ArrayList<DistrictDto>();
			ProvinceCompositeDto composite = new ProvinceCompositeDto();
			
			//Set Province.
			composite.setProvince(new ProvinceDto(
					String.format("%03d",provinceCodeCounter.getAndIncrement())
					, p.getNameTh()
					, p.getNameEn()
					));
			
			for(DistrictResponseDto.DistrictsInfo resD : resExternalDistrict.getDistricts()) {
				if(null!=resExternalDistrict.getDistricts() && resExternalDistrict.getDistricts().size() > 0) {
					
					
					
					//Set District
					DistrictDto disDto = new DistrictDto( 
							String.format("%06d",disCodeCounter.getAndIncrement())
							, resD.getNameTh()
							, resD.getNameEn() );
					
					//Set SubDistrict.
					List<SubDistrictDto> sdDtoList = new ArrayList<SubDistrictDto>();

					SubDistrictResponseDto resExternalSubDistrict = restClient.get().uri(pathGetSubDistrictsByDistrictId.concat(String.valueOf(resD.getId())) ).retrieve().body(new ParameterizedTypeReference<SubDistrictResponseDto>(){});
					
					for(SubDistrictResponseDto.SubDistrictsInfo resSd : resExternalSubDistrict.getSubdistricts()) {
						
						SubDistrictDto sdDto = new SubDistrictDto(
								String.format("%06d", subDisCodeCounter.getAndIncrement() ) ,
								resSd.getNameTh() , 
								resSd.getNameEn() , 
								resSd.getZipCode()
								);
						sdDto.setNameTh(resSd.getNameTh());
						sdDto.setNameEn(resSd.getNameEn());
						sdDtoList.add(sdDto);
					}
					
					disDto.setSubDistricts(sdDtoList);
					disDtoList.add(disDto);
				}
				
			}
			
			composite.setDistricts(disDtoList);
			
			return composite;
		}
		
		return null;
	}

}
