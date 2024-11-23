package persistencia;

import excepciones.CampoRequeridoException;
import excepciones.FormatoIncorrectoException;
import excepciones.ObjectoDuplicadoException;

public interface UsuarioDAO {

	int altaUsuario(String nombre, String apellido, String documento) throws ObjectoDuplicadoException, CampoRequeridoException, FormatoIncorrectoException;
	
	void update(String nombre, String apellido, String documento, int idPaciente) throws CampoRequeridoException, FormatoIncorrectoException;
	
	void eliminar(int id);
}
