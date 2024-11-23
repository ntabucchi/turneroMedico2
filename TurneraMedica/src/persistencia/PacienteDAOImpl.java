package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import excepciones.FormatoIncorrectoException;
import excepciones.RegistroNoExistenteException;
import negocio.Paciente;

public class PacienteDAOImpl implements PacienteDAO {

	public Paciente obtener(String documento) throws RegistroNoExistenteException {
		PreparedStatement ps = null;
	    ResultSet rs = null;
	    Paciente paciente = null;
	    Connection c = DBManager.connect();

	    try {
	        String query = "SELECT u.Nombre, u.Apellido, u.Documento, p.idPaciente, p.direccion, p.celular " +
	                       "FROM Usuarios u " +
	                       "JOIN Pacientes p ON u.Id = p.idPaciente " +
	                       "WHERE u.Documento = ?";

	        ps = c.prepareStatement(query);
	        ps.setString(1, documento);

	        rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            String nombre = rs.getString("Nombre");
	            String apellido = rs.getString("Apellido");
	            Integer idPaciente = rs.getInt("idPaciente");
	            String direccion = rs.getString("direccion");
	            String celular = rs.getString("celular");
	        
	            paciente = new Paciente(nombre, apellido, documento, idPaciente, direccion, celular);
	            paciente.setIdPaciente(idPaciente);
	        }else {
	            throw new RegistroNoExistenteException("No se encontró el paciente con el documento: " + documento);
	        }
	    } catch (SQLException e) {
		    	try {
	                c.rollback();
	                e.printStackTrace();
	            } catch (SQLException e1) {
	                e.printStackTrace();
	            }
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (c != null) c.close();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }

	    return paciente;
	}
	
	public void altaPaciente(String direccion, String celular, int idPaciente) throws FormatoIncorrectoException {
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    Connection c = DBManager.connect();

	    if (!celular.matches("\\d+")) {
            throw new FormatoIncorrectoException("El campo 'Celular' debe contener solo números.");
        }
	    
	    try {
	        String query = "INSERT INTO Pacientes(idPaciente, direccion, celular) VALUES (?, ?, ?); SELECT @@IDENTITY";
	        
	        stmt = c.prepareStatement(query);
	        stmt.setInt(1, idPaciente);
	        stmt.setString(2, direccion);
	        stmt.setString(3, celular);

	        rs = stmt.executeQuery();
	        c.commit();
	    } catch (SQLException e) {
	    	try {
                c.rollback();
                e.printStackTrace();
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
	}
	
	public void update(String direccion, String celular, Integer idPaciente) throws FormatoIncorrectoException {
		PreparedStatement stmt = null;
	    Connection c = DBManager.connect();

	    if (!celular.matches("\\d+")) {
            throw new FormatoIncorrectoException("El campo 'Celular' debe contener solo números.");
        }

	    try {
	        String query = "UPDATE Pacientes SET direccion=?, celular=? WHERE idPaciente=?";
	        stmt = c.prepareStatement(query);
	        stmt.setString(1, direccion);
	        stmt.setString(2, celular);
	        stmt.setInt(3, idPaciente);

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
	        } catch (SQLException ex) {
	            ex.printStackTrace();
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
	
	public void eliminar(int idPaciente) {
		
	    PreparedStatement stmt = null;
	    Connection c = DBManager.connect();

	    try {
	        c.setAutoCommit(false);

	        String query = "DELETE FROM Pacientes WHERE idPaciente = ?";

	        stmt = c.prepareStatement(query);
	        stmt.setInt(1, idPaciente);

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

	public List<Paciente> listarPacientes() {
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    List<Paciente> pacientes = new ArrayList<>();
	    Connection c = DBManager.connect();

	    try {
	        String query = "SELECT u.nombre, u.apellido, u.documento, p.idPaciente, p.direccion, p.celular " +
	                       "FROM Pacientes p " +
	                       "JOIN Usuarios u ON u.Id = p.idPaciente";

	        stmt = c.prepareStatement(query);
	        rs = stmt.executeQuery();

	        while (rs.next()) {
	            String nombre = rs.getString("nombre");
	            String apellido = rs.getString("apellido");
	            String documento = rs.getString("documento");
	            int idPaciente = rs.getInt("idPaciente");
	            String direccion = rs.getString("direccion");
	            String celular = rs.getString("celular");

	            Paciente paciente = new Paciente(nombre, apellido, documento, idPaciente, direccion, celular);
	            pacientes.add(paciente);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (c != null) c.close();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }

	    return pacientes;
	}
}
