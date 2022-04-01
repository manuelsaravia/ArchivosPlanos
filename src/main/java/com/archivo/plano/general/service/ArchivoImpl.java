package com.archivo.plano.general.service;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.naming.java.javaURLContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.archivo.plano.general.constantes.ApiAccionesEnum;
import com.archivo.plano.general.constantes.CaracteresEnum;
import com.archivo.plano.general.controller.ArchivoController;
import com.archivo.plano.general.dto.output.ArchivoWsOutput;
import com.archivo.plano.general.dto.output.RespuestaGeneralWS;
import com.archivo.plano.general.entity.ArchivoEntity;
import com.archivo.plano.general.entity.ClienteEntity;
import com.archivo.plano.general.entity.PedidoEntity;
import com.archivo.plano.general.interfaces.IArchivo;
import com.archivo.plano.general.repository.ArchivoRepository;
import com.archivo.plano.general.repository.ClienteRepository;
import com.archivo.plano.general.repository.PedidoRepository;

@Service
public class ArchivoImpl implements IArchivo {

	private Logger log = LoggerFactory.getLogger(ArchivoImpl.class);

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ArchivoRepository archivoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Override
	public ResponseEntity<RespuestaGeneralWS> cargarArchivo(MultipartFile file, String nombreCliente) {
		log.info("Entra a cargar Archivo");
		RespuestaGeneralWS respuesta = new RespuestaGeneralWS("Inicio de Proceso",
				ApiAccionesEnum.CARGAR_ARCHIVO.toString(), HttpStatus.OK, null);
		try {
			if (file.getOriginalFilename().isEmpty()) {
				log.info("Archivo nulo");
				respuesta = new RespuestaGeneralWS("Archivo nulo", ApiAccionesEnum.CARGAR_ARCHIVO.toString(),
						HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<RespuestaGeneralWS>(respuesta, HttpStatus.BAD_REQUEST);
			}

			log.info("Consultando Cliente");
			Optional<ClienteEntity> cliente = this.clienteRepository.findByNombreCliente(nombreCliente.toUpperCase());
			if (!cliente.isPresent()) {
				log.info("Cliente no existente");
				respuesta = new RespuestaGeneralWS("Cliente no existente", ApiAccionesEnum.CARGAR_ARCHIVO.toString(),
						HttpStatus.NOT_FOUND, null);
				return new ResponseEntity<RespuestaGeneralWS>(respuesta, HttpStatus.NOT_FOUND);
			}

			log.info("Cargando Archivo");
			String uuid = UUID.randomUUID().toString();
			String nombreArchivo = file.getOriginalFilename();
			nombreArchivo = nombreArchivo.replace(CaracteresEnum.ESPACIO_BLANCO.getCaracter(),
					CaracteresEnum.GUION_BAJO.getCaracter());
			Path filePath = Paths.get(cliente.get().getPath(), uuid.concat(nombreArchivo));
			OutputStream os = Files.newOutputStream(filePath);
			os.write(file.getBytes());
			os.close();
			log.info("Archivo cargado en la ruta: " + filePath.toString());

			String reemplazo = "";
			if (cliente.get().getCaracterReemplazo() != null) {
				reemplazo = CaracteresEnum.getCaracter(cliente.get().getCaracterReemplazo()).toString();
			}
			
			String delimitador = CaracteresEnum.getCaracter(cliente.get().getDelimitadorCliente()).toString();
			delimitador = Pattern.quote(delimitador);
			File doc = new File(filePath.toString());
			Scanner obj = new Scanner(doc);

			while (obj.hasNextLine()) {
				String linea = obj.nextLine();
				linea = linea.replace(reemplazo, CaracteresEnum.NADA.getCaracter());
				
				String vector[] = linea.split(delimitador);
				PedidoEntity pedido = new PedidoEntity();
				pedido.setCliente(cliente.get());
				pedido.setNombreProducto(vector[Integer.parseInt(
						cliente.get().getOrdenColumnas().split(CaracteresEnum.GUION_MEDIO.getCaracter())[0])]);
				pedido.setCantidadProducto(Integer.parseInt(vector[Integer.parseInt(
						cliente.get().getOrdenColumnas().split(CaracteresEnum.GUION_MEDIO.getCaracter())[1])]));
				pedido.setFechaRequerida(this.convertirFecha(vector[Integer.parseInt(
						cliente.get().getOrdenColumnas().split(CaracteresEnum.GUION_MEDIO.getCaracter())[2])], cliente.get().getOrdenFecha(), cliente.get().getDelimitadorFecha()));

				do {
					this.pedidoRepository.save(pedido);
				} while (pedido.getIdPedido() == null);
			}

			ArchivoEntity archivo = new ArchivoEntity();
			archivo.setCliente(cliente.get());
			archivo.setNombreArchivo(nombreArchivo);
			archivo.setNombreArchivoUUID(uuid.concat(nombreArchivo));

			do {
				this.archivoRepository.save(archivo);
			} while (archivo.getIdArchivo() == null);

			ArchivoWsOutput output = new ArchivoWsOutput();
			output.setEstadoCargue(true);
			output.setNombreArchivo(nombreArchivo);
			output.setRutaCargue(cliente.get().getPath());

			respuesta = new RespuestaGeneralWS("Carga de Archivo Existosa", ApiAccionesEnum.CARGAR_ARCHIVO.toString(),
					HttpStatus.OK, output);
		} catch (Exception e) {
			log.info("ERROR " + e.getMessage());
			respuesta = new RespuestaGeneralWS("Error del Sistema", ApiAccionesEnum.CARGAR_ARCHIVO.toString(),
					HttpStatus.INTERNAL_SERVER_ERROR, null);
			return new ResponseEntity<RespuestaGeneralWS>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<RespuestaGeneralWS>(respuesta, HttpStatus.OK);
	}
	
	private java.sql.Date convertirFecha(String fechaString, String ordenFecha, String delimitadorFecha){
		if(delimitadorFecha != null) {
			fechaString = fechaString.replace(CaracteresEnum.getCaracter(delimitadorFecha).toString(), CaracteresEnum.NADA.getCaracter());
		}
		String[] vectorFecha = ordenFecha.split(CaracteresEnum.GUION_MEDIO.getCaracter());
		String dia = "";
		String mes = "";
		String anio = "";
		int posicion = 0;
		switch(vectorFecha[0]) {
		case "A":
			anio = fechaString.substring(posicion, posicion+4);
			posicion += 4;
			break;
		case "D":
			dia = fechaString.substring(posicion,posicion+2);
			posicion += 2;
			break;
		case "M":
			mes = fechaString.substring(posicion, posicion+2);
			posicion += 2;
			break;
		}
		
		switch(vectorFecha[1]) {
		case "A":
			anio = fechaString.substring(posicion, posicion+4);
			posicion +=4;
			break;
		case "D":
			dia = fechaString.substring(posicion, posicion+2);
			posicion += 2;
			break;
		case "M":
			mes = fechaString.substring(posicion, posicion+2);
			posicion += 2;
			break;
		}
		
		switch(vectorFecha[2]) {
		case "A":
			anio = fechaString.substring(posicion, posicion+4);
			posicion += 4;
			break;
		case "D":
			dia = fechaString.substring(posicion, posicion+2);
			posicion += 2;
			break;
		case "M":
			mes = fechaString.substring(posicion, posicion+2);
			posicion += 2;
			break;
		}
		
		return new java.sql.Date(Integer.parseInt(anio)-1900, Integer.parseInt(mes)-1, Integer.parseInt(dia));
		
	}

}
