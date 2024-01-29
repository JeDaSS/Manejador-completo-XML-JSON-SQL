package xml_json_sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Conexion {
	
	private String usuario;
	private String contraseña;
	private String urlCon;
	private Connection CON;
	
	
	
	public Conexion(String user, String pass) {
		usuario = user;
		contraseña = pass;
		urlCon = "jdbc:mysql://localhost:3306/habana";
		
		try 
		{
			CON = DriverManager.getConnection(urlCon, usuario, contraseña);
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	
	
	public String getUsuario() {
		return usuario;
	}



	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}



	public String getContraseña() {
		return contraseña;
	}



	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}



	public String getUrlCon() {
		return urlCon;
	}



	public void setUrlCon(String urlCon) {
		this.urlCon = urlCon;
	}



	public Connection getCON() {
		return CON;
	}



	public void setCON(Connection cON) {
		CON = cON;
	}



	public void crearConexion() {
		
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public void insertarNuevosValores(ArrayList<String> etiquetas, ArrayList<String> valores) {
		
		int control1 = 9;
		int control2 = 10;
				
		try 
		{
			
			StringBuilder consulta = new StringBuilder("INSERT INTO eventos (");
		    

		    for (int i = 0; i < etiquetas.size(); i++) {
		        consulta.append(etiquetas.get(i));
		        if (i < etiquetas.size() - 1) {
		            consulta.append(",");
		        }
		    }
		    
		    consulta.append(") VALUES (");
		    
		    for (int i = 0; i < valores.size(); i++) {
		    	

		    	if (i== control1) 
		    	{
			        if (i == valores.size() -1) 
			        {
				        consulta.append("'");
				        consulta.append(valores.get(i));
				        consulta.append("')");
						control1 += 10;
			        }
			        else {
				        consulta.append("'");
				        consulta.append(valores.get(i));
				        consulta.append("'),\n");
						control1 += 10;
					}
				}
		    	else if (i==control2) {
			        consulta.append("('");
			        consulta.append(valores.get(i));
			        consulta.append("',");
		    		control2 += 10;
				}
		    	else {
			        consulta.append("'");
			        consulta.append(valores.get(i));
			        consulta.append("',");

		    	}


		    }
		    
		    consulta.append(";");

			Statement st = CON.createStatement();
			st.executeUpdate(consulta.toString());
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void leerSQL() {
		
		try 
		{
			Statement st = CON.createStatement();		
			ResultSet result = st.executeQuery("SELECT * FROM eventos;");
			
			while (result.next()) 
			{
				for (int i = 1; i <= 10; i++) 
				{
					LeerXML.valores.add(result.getString(i));
				}

			}

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public void cerrarConexion() {
		
		try 
		{
			CON.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	

}

