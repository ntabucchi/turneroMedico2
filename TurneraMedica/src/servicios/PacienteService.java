package servicios;

import java.util.List;

import excepciones.FormatoIncorrectoException;
import excepciones.ServiceException;
import negocio.Paciente;
import persistencia.PacienteDAO;
import persistencia.PacienteDAOImpl;

public class PacienteService {
	
	public Paciente obtener(String documento) throws ServiceException {
		PacienteDAO p = new PacienteDAOImpl();
        try {
        	return p.obtener(documento);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
	
	public void altaPaciente(String direccion, String celular, int idPaciente) throws ServiceException {
		PacienteDAO p = new PacienteDAOImpl();
        try {
        	p.altaPaciente(direccion, celular, idPaciente);
        } catch (FormatoIncorrectoException e) {
        	UsuarioService usuarioService = new UsuarioService();
        	usuarioService.eliminarUsuario(idPaciente);
            throw new ServiceException(e);
        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }
	
	public void actualizarPaciente(String direccion, String celular, int idPaciente) throws ServiceException {
		PacienteDAO p = new PacienteDAOImpl();
        try {
        	p.update(direccion, celular, idPaciente);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
	
	public void eliminarPaciente(int idPaciente) throws ServiceException {
		PacienteDAO p = new PacienteDAOImpl();
        try {
        	p.eliminar(idPaciente);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
	
	public List<Paciente> listarPacientes() {
		PacienteDAO p = new PacienteDAOImpl();
        List<Paciente> usuarios = p.listarPacientes();
        return usuarios;
    }

}
