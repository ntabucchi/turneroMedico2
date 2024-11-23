package vistas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import negocio.Paciente;
import servicios.PacienteService;
import servicios.UsuarioService;

import javax.swing.JTextField;

public class VentanaDatosModificarPaciente extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textAreaNombre;
	private JTextField textAreaApellido;
	private JTextField textAreaDocumento;
	private JTextField textAreaDireccion;
	private JTextField textAreaCelular;

	/**
	 * Create the frame.
	 */
	public VentanaDatosModificarPaciente(Paciente paciente) {
		setTitle("Modificar Paciente");
	    setSize(506, 320);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	    getContentPane().setLayout(new BorderLayout()); 
		
		JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
		panel.setBorder(new EmptyBorder(20, 20, 20, 20));

		setContentPane(panel);
		
		JPanel pnlNombre = new JPanel(new GridLayout(1, 2, 10, 10));
		JLabel lblNombre = new JLabel("Nombre");
		pnlNombre.add(lblNombre);
		panel.add(pnlNombre);
		
		textAreaNombre = new JTextField(paciente.getNombre());
		pnlNombre.add(textAreaNombre);
		textAreaNombre.setColumns(10);
		
		JPanel pnlApellido = new JPanel(new GridLayout(1, 2, 10, 10));
		JLabel lblApellido = new JLabel("Apellido");
		pnlApellido.add(lblApellido);
		panel.add(pnlApellido);
		
		textAreaApellido = new JTextField(paciente.getApellido());
		pnlApellido.add(textAreaApellido);
		textAreaApellido.setColumns(10);
		
		JPanel pnlDocumento = new JPanel(new GridLayout(1, 2, 10, 10));
		JLabel lblDocumento = new JLabel("Documento");
		pnlDocumento.add(lblDocumento);
		panel.add(pnlDocumento);
		
		textAreaDocumento = new JTextField(paciente.getDocumento());
		pnlDocumento.add(textAreaDocumento);
		textAreaDocumento.setColumns(10);
		
		JPanel pnlDireccion = new JPanel(new GridLayout(1, 2, 10, 10));
		JLabel lblDireccion = new JLabel("Dirección");
		pnlDireccion.add(lblDireccion);
		panel.add(pnlDireccion);
		
		textAreaDireccion = new JTextField(paciente.getDireccion());
		pnlDireccion.add(textAreaDireccion);
		textAreaDireccion.setColumns(10);
		
		JPanel pnlCelular = new JPanel(new GridLayout(1, 2, 10, 10));
		JLabel lblCelular = new JLabel("Celular");
		pnlCelular.add(lblCelular);
		panel.add(pnlCelular);
		
		textAreaCelular = new JTextField(paciente.getCelular());
		pnlCelular.add(textAreaCelular);
		textAreaCelular.setColumns(10);
		
		JPanel pnlBotones = new JPanel();
		JButton btnIngresar = new JButton("Actualizar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					UsuarioService usuarioService = new UsuarioService();
					PacienteService pacienteService = new PacienteService();
										
					String documento = textAreaDocumento.getText().isEmpty() ? null : textAreaDocumento.getText();
					pacienteService.actualizarPaciente(textAreaDireccion.getText(), textAreaCelular.getText(), paciente.getIdPaciente());					
					usuarioService.actualizarUsuario(textAreaNombre.getText(), textAreaApellido.getText(), documento, paciente.getIdPaciente());
				
					dispose();
					JOptionPane.showMessageDialog(null, "Se actualizó el paciente: " + paciente.getNombre() + " " + paciente.getApellido() , "", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
			        JOptionPane.showMessageDialog(null, ex.getMessage().toString(), "Error", JOptionPane.ERROR_MESSAGE);
			    }
			}
		});
		pnlBotones.add(btnIngresar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		pnlBotones.add(btnVolver);
		panel.add(pnlBotones);
		setVisible(true);
	}

}
