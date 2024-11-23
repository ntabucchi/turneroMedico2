package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import excepciones.CampoRequeridoException;
import excepciones.FormatoIncorrectoException;
import excepciones.ObjectoDuplicadoException;

public class UsuarioDAOImpl implements UsuarioDAO{
	
	public int altaUsuario(String nombre, String apellido, String documento) throws ObjectoDuplicadoException, CampoRequeridoException, FormatoIncorrectoException {
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    int idUsuario = -1;
	    Connection c = DBManager.connect();

	    if (documento != null && !documento.matches("\\d+")) {
            throw new FormatoIncorrectoException("El campo 'Número de documento' debe contener solo números.");
        }
	    
	    try {
	        String query = "INSERT INTO Usuarios(nombre, apellido, documento) VALUES (?, ?, ?); SELECT @@IDENTITY";

	        stmt = c.prepareStatement(query);
	        stmt.setString(1, nombre);
	        stmt.setString(2, apellido);
	        stmt.setString(3, documento);

	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            idUsuario = rs.getInt(1);
	        }
	        c.commit();  
	    } catch (SQLException e) {
	    	try {
                c.rollback();
                e.printStackTrace();
                if(e.getErrorCode() == 2627) {
                    throw new ObjectoDuplicadoException("El número de documento ya se encuentra registrado");
                }
                if(e.getErrorCode() == 515) {
                    throw new CampoRequeridoException("El número de documento es un campo obligatorio");
                }
            } catch (SQLException e1) {
                e.printStackTrace();
            }
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (c != null) c.close();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }

	    return idUsuario;
	}
	
	public void update(String nombre, String apellido, String documento, int idPaciente) throws CampoRequeridoException, FormatoIncorrectoException {
	    PreparedStatement stmt = null;
	    Connection c = DBManager.connect();

	    if (documento != null && !documento.matches("\\d+")) {
            throw new FormatoIncorrectoException("El campo 'Número de documento' debe contener solo números.");
        }
	    
	    try {
	        String query = "UPDATE Usuarios SET nombre=?, apellido=?, documento=? WHERE id=?";

	        stmt = c.prepareStatement(query);
	        stmt.setString(1, nombre);
	        stmt.setString(2, apellido);
	        stmt.setString(3, documento);
	        stmt.setInt(4, idPaciente);

	        int filasAfectadas = stmt.executeUpdate();
	        
	        if (filasAfectadas > 0) {
	            c.commit();
	        }   
	    } catch (SQLException e) {
	    	try {
                c.rollback();
                e.printStackTrace();
                if(e.getErrorCode() == 515) {
                    throw new CampoRequeridoException("El número de documento es un campo obligatorio");
                }
            } catch (SQLException e1) {
                e.printStackTrace();
            }
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	            if (c != null) c.close();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }
	}
	
	public void eliminar(int id) {
	    PreparedStatement stmt = null;
	    Connection c = DBManager.connect();

	    try {
	        c.setAutoCommit(false);

	        String query = "DELETE FROM Usuarios WHERE id = ?";

	        stmt = c.prepareStatement(query);
	        stmt.setInt(1, id);

	        int filasAfectadas = stmt.executeUpdate();
	        if (filasAfectadas > 0) {
	            c.commit();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            if (c != null) {
	                c.rollback();
	            }
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	            if (c != null) c.close();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }
	}
}
