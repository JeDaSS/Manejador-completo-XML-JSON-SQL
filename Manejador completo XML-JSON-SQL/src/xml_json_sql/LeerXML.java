package xml_json_sql;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LeerXML {
	
	static ArrayList<String> listaEtiquetas = new ArrayList<String>(Arrays.asList("nombre","telefono","fechaEvento","tipo","asistentes","tipoCocina","numeroJornadas","habitaciones","tipoMesa","comensalesMesa"));
	static ArrayList<String> valores = new ArrayList<>();
	
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Que desea Leer: 1-Leer_XML   2-Leer_JSON   3-Leer_SQL");
		
		switch (sc.nextInt()) 
		{
		case 1:
			leerXML();
			break;
		case 2:
			leerJSON();
			break;
		case 3:
			Conexion con = new Conexion("root", "admin");
			con.crearConexion();
			con.leerSQL();
			con.cerrarConexion();
			break;
		
		}
		
		
		System.out.println("Que desea Escribir: 1-Escribir_XML   2-Escribir_JSON  3-Insertar_sql");
		
		switch (sc.nextInt()) 
		{
		case 1:
			escribirNuevoXML();
			break;
		case 2:
			escribirNuevoJSON();
			break;
		case 3:
			Conexion con = new Conexion("root", "admin");
			con.crearConexion();
			con.insertarNuevosValores(listaEtiquetas, valores);
			con.cerrarConexion();
			break;
		}
		
		
		sc.close();
		
		
	}
	
	public static void leerXML() {
		
		File f = new File("xmlModel.txt");
		BufferedReader br = null;
		String linea = "";
		String valor = "";
		
		try 
		{
			br = new BufferedReader(new FileReader(f));
			
			linea = br.readLine();
			
			while (linea != null) 
			{					
					for (String e : listaEtiquetas) 
					{
						if (linea.contains("<"+e+">") && linea.contains("</"+e+">")) 
						{
							valor = linea.substring( linea.indexOf("<"+e+">") + e.length() +2 , linea.indexOf("</"+e+">"));
							valores.add(valor);
						}
						
					}
					
				linea = br.readLine();
			}
			
		}
		catch (Exception e) 
		{
			e.getMessage();
		}
		finally 
		{
			try 
			{
				br.close();
			} 
			catch (IOException e) 
			{

				e.printStackTrace();
			}
		}
		
	}
	
	public static void escribirNuevoXML() 
	{
		BufferedWriter bw = null;
		int i2=0;

		try 
		{
			bw = new BufferedWriter(new FileWriter("nuevoXML.txt",true));
			
			bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n");
			
			bw.write("<reservas>\n");
			
			for (int i = 0; i < valores.size(); i++) 
			{

				
				switch (i2) 
				{
				case 0:
					bw.write("\t<reserva>\n");
					bw.write("\t\t<"+listaEtiquetas.get(i2)+">"+valores.get(i)+"</"+listaEtiquetas.get(i2)+">\n");
					i2++;
					break;
					
				case 9:
					bw.write("\t\t<"+listaEtiquetas.get(i2)+">"+valores.get(i)+"</"+listaEtiquetas.get(i2)+">\n");
					bw.write("\t</reserva>\n");
					i2 = 0;
					break;

				default:
					bw.write("\t\t<"+listaEtiquetas.get(i2)+">"+valores.get(i)+"</"+listaEtiquetas.get(i2)+">\n");
					i2++;
					break;
				}
								
			}
			
			bw.write("</reservas>\n");
			
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());;
		}
		finally 
		{
			try 
			{
				bw.close();
			} 
			catch (IOException e) 
			{

				e.printStackTrace();
			}
		}
	}
	
	public static void leerJSON() {
		
		File f = new File("jsonModel.txt");
		BufferedReader br = null;
		String linea = "";
		String valor = "";
		
		try 
		{
			br = new BufferedReader(new FileReader(f));
			
			linea = br.readLine();
			
			while (linea != null) 
			{					
					for (String e : listaEtiquetas) 
					{
						if (linea.contains("\""+e+"\"") && linea.contains(":")) 
						{
							valor = linea.substring(linea.indexOf(":")+1).replace("\"", "").replace(",", "").trim();
							valores.add(valor);
						}
						
					}
					
				linea = br.readLine();
			}
			
			
		}
		catch (Exception e) 
		{
			e.getMessage();
		}
		finally 
		{
			try 
			{
				br.close();
			} 
			catch (IOException e) 
			{

				e.printStackTrace();
			}
		}
	}
	
	public static void escribirNuevoJSON() {
		
		   BufferedWriter bw = null;
		   int i2 = 0;
		   
		   try 
		   {
			   
			bw = new BufferedWriter(new FileWriter("nuevoJSON.txt",true));
			
			bw.write("{\n");
			bw.write("\t\"reservas\": {\n");
			bw.write("\t\t\"reserva\": [\n");
			
			
			for (int i = 0; i < valores.size(); i++) 
			{
				switch (i2) 
				{
				case 0:
					bw.write("\t\t\t{\n");
					bw.write("\t\t\t\t\""+listaEtiquetas.get(i2)+"\" : \""+valores.get(i)+"\",\n");
					i2++;
					break;
					
				case 9:
					bw.write("\t\t\t\t\""+listaEtiquetas.get(i2)+"\" : \""+valores.get(i)+"\"\n");	
					
		            if (i < valores.size() - listaEtiquetas.size()) {
		                bw.write("\t\t\t},\n");
		            }
		            else {
						bw.write("\t\t\t}\n");
					}
		            
					i2 = 0;
					break;

				default:
					bw.write("\t\t\t\t\""+listaEtiquetas.get(i2)+"\" : \""+valores.get(i)+"\",\n");
					i2++;
					break;
				}
			}
			
			
			bw.write("\t\t]\n");
			bw.write("\t}\n");
			bw.write("}\n");
			
			
		   }
		   catch (IOException e) 
		   {
			e.printStackTrace();
		   }
		   finally 
		    {
				try 
				{
					bw.close();
				} 
				catch (IOException e) 
				{

					e.printStackTrace();
				}
		    }
		   

	}
	
}
