package servicios;

import excepciones.CampoRequeridoException;
import excepciones.FormatoIncorrectoException;
import excepciones.ObjectoDuplicadoException;
import excepciones.ServiceException;
import persistencia.UsuarioDAO;
import persistencia.UsuarioDAOImpl;

public class UsuarioService {

	public int altaUsuario(String nombre, String apellido, String documento) throws ServiceException, FormatoIncorrectoException {
		UsuarioDAO u = new UsuarioDAOImpl();
        try {
        	return u.altaUsuario(nombre, apellido, documento);
        } catch (ObjectoDuplicadoException e) {
        	throw new ServiceException(e);
        } catch (CampoRequeridoException e) {
        	throw new ServiceException(e);
        }
    }
	
	public void actualizarUsuario(String nombre, String apellido, String documento, int idPaciente) throws ServiceException {
		UsuarioDAO u = new UsuarioDAOImpl();
        try {
        	u.update(nombre, apellido, documento, idPaciente);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
	
	public void eliminarUsuario(int id) throws ServiceException {
		UsuarioDAO u = new UsuarioDAOImpl();
        try {
        	u.eliminar(id);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
