package es.uma.informatica.sii.jsf.demo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Calculos {
	
	private Integer numero;

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	public List<Integer> getDivisores() {
		
		if (numero == null) {
			return Collections.EMPTY_LIST;
		} else {
			return IntStream.rangeClosed(1,numero)
					.filter(i->(numero%i)==0)
					.boxed()
					.collect(Collectors.toList());
		}
	}

}
