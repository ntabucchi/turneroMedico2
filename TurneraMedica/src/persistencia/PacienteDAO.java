package persistencia;

import java.util.List;

import excepciones.FormatoIncorrectoException;
import excepciones.RegistroNoExistenteException;
import negocio.Paciente;

public interface PacienteDAO {

	Paciente obtener(String documento) throws RegistroNoExistenteException;
	
	void altaPaciente(String direccion, String celular, int idPaciente) throws FormatoIncorrectoException;
	
	void update(String direccion, String celular, Integer idPaciente) throws FormatoIncorrectoException;
	 
	void eliminar(int idPaciente);
	
	List<Paciente> listarPacientes();
}
