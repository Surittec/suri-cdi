/*
 * SURITTEC
 * Copyright 2015, SURITTEC CONSULTORIA LTDA, 
 * and individual contributors as indicated by the @authors tag
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package br.com.surittec.suricdi.faces.facelets.function;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.sun.faces.facelets.tag.AbstractTagLibrary;

/**
 * Classe resonsavel por definir a biblioteca de funcoes do facelets.
 * 
 * @author lucas lins
 *
 */
public class FaceletsLibrary extends AbstractTagLibrary {
	
	// ATTRIBUTES ****************************
	
	public final static String NAMESPACE = "http://www.surittec.com.br/jsf/fn";
	
	public static final FaceletsLibrary INSTANCE = new FaceletsLibrary();

	// CONSTRUCTOR ****************************
	
	public FaceletsLibrary() {
		super(NAMESPACE);
		init(FaceletsFunctions.class);
	}
	
	// PRIVATE METHODS ****************************
	
	/**
	 * Adds all public static methods to facelets library.
	 * @param funtion classes 
	 */
	private void init(Class<?> ... functionClasses){
		for(Class<?> fc : functionClasses){
			for(Method method : fc.getMethods()){
				if(Modifier.isStatic(method.getModifiers())){
					this.addFunction(method.getName(), method);
				}
			}
		}
	}
}
