package com.giuliano.curso.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {
	
	/**
	 * Decodifica String que possa possuir espaços para o formato UTF-8.
	 * @param s
	 * @return
	 */
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	
	/**
	 * Converte uma string que possui uma lista de números ("1,2,3") para
	 * uma lista de inteiros [1,2,3].
	 * @param s
	 * @return
	 */
	// Não precisa instanciar.
	public static List<Integer> decodeIntList(String s){
		return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
		/*String[] vet = s.split(",");
		List<Integer> list = new ArrayList<>();
		for (int i=0; i<vet.length; i++)
			list.add(Integer.parseInt(vet[i]));
		return list;*/
	}

}
