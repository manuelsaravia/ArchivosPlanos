package com.archivo.plano.general.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.archivo.plano.general.dto.output.RespuestaGeneralWS;

public interface IArchivo {
	
	public ResponseEntity<RespuestaGeneralWS> cargarArchivo(MultipartFile file, String cliente);

}
