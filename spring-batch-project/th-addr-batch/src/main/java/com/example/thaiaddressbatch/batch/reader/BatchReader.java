package com.example.thaiaddressbatch.batch.reader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.example.thaiaddressbatch.model.dto.external.ProvinceResponseDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BatchReader implements ItemReader<ProvinceResponseDto>  {
	
	private final RestClient restClient;
	private Iterator<ProvinceResponseDto> dataIterator;
	private String pathGetAllProvince;
	
	
	public BatchReader(@Value("${address.thai.from.external.url}") String url , 
			@Value("${address.thai.from.external.path.all.province}") String pathGetAllProvince) {
		this.restClient = RestClient.builder().baseUrl(url).build();
		this.pathGetAllProvince = pathGetAllProvince;
	}
	
	@Override
	public @Nullable ProvinceResponseDto read() throws Exception {
		if(null == dataIterator) {
			fetchDataFromApi();
		}
		if(null!=dataIterator && dataIterator.hasNext()) {
			return dataIterator.next();
		}
		return null;
	}
	
	private void fetchDataFromApi() {
		//Fetch Provinces from api.
		List<ProvinceResponseDto> resExternalProvinceList = new ArrayList<ProvinceResponseDto>();
		resExternalProvinceList = restClient.get().uri(pathGetAllProvince).retrieve().body(new ParameterizedTypeReference<List<ProvinceResponseDto>>() {});
		log.info(">>> Size result from external api path : {} , size : {} ","",resExternalProvinceList.size());
		
		if(null!=resExternalProvinceList) {
			this.dataIterator = resExternalProvinceList.iterator();
		}
	
	}

}
