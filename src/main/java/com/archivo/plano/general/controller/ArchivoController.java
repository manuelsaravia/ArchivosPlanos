package com.archivo.plano.general.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.archivo.plano.general.constantes.ApiConstant;
import com.archivo.plano.general.dto.output.RespuestaGeneralWS;
import com.archivo.plano.general.interfaces.IArchivo;

@RestController
@RequestMapping(ApiConstant.ARCHIVO_CONTROLLER)
public class ArchivoController {

	private Logger log = LoggerFactory.getLogger(ArchivoController.class);
	
	@Autowired
	private IArchivo archivo;
	
	@PostMapping(ApiConstant.ARCHIVO_CONTROLLER_CARGUE)
	public ResponseEntity<RespuestaGeneralWS> cargarArchivo(@RequestParam("file") MultipartFile file, @RequestParam("cliente") String nombreCliente){
		return this.archivo.cargarArchivo(file, nombreCliente);
	}
}
