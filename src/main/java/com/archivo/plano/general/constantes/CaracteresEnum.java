package com.archivo.plano.general.constantes;

public enum CaracteresEnum {

	ESPACIO_BLANCO(" "),
	GUION_BAJO("_"),
	GUION_MEDIO("-"),
	COMA(","),
	COMILLA_SIMPLE("'"),
	PAI("|"),
	NADA("");
	
	private String caracter;
	
	private CaracteresEnum(String caracter) {
		this.caracter = caracter;
	}
	
	public String getCaracter() {
		return this.caracter;
	}
	
	public static String getCaracter(String caracter) {
		CaracteresEnum [] caracteres = CaracteresEnum.values();
		for(CaracteresEnum car: caracteres) {
			if(car.name().equals(caracter)) {
				return car.caracter;
			}
		}
		return null;
	}
}
