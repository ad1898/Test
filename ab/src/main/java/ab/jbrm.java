package ab;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;

public class jbrm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					jbrm frame = new jbrm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public jbrm() {
		setTitle("BRM XML Comparator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 541, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel FMWOperationLabel = new JLabel("FMW Operation Name");
		FMWOperationLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		FMWOperationLabel.setForeground(Color.BLACK);
		FMWOperationLabel.setBounds(36, 52, 145, 27);
		contentPane.add(FMWOperationLabel);
		
		JLabel BRMOperationLabel = new JLabel("BRM Operation Name");
		BRMOperationLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		BRMOperationLabel.setBounds(36, 110, 145, 27);
		contentPane.add(BRMOperationLabel);
		
		textField = new JTextField();
		textField.setFont(new Font("Calibri", Font.PLAIN, 12));
		textField.setBackground(Color.LIGHT_GRAY);
		textField.setBounds(217, 54, 255, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Calibri", Font.PLAIN, 12));
		textField_1.setBackground(Color.LIGHT_GRAY);
		textField_1.setColumns(10);
		textField_1.setBounds(217, 112, 255, 24);
		contentPane.add(textField_1);
		
		JButton btnReset = new JButton("RESET");
		btnReset.setBackground(Color.BLACK);
		btnReset.setFont(new Font("Calibri", Font.BOLD, 12));
		btnReset.setBounds(36, 213, 89, 23);
		contentPane.add(btnReset);
		
		JButton btnNext = new JButton("NEXT >>");
		btnNext.setBackground(Color.BLACK);
		btnNext.setFont(new Font("Calibri", Font.BOLD, 12));
		btnNext.setBounds(383, 213, 89, 23);
		contentPane.add(btnNext);
	}
}
