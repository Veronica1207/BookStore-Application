package a01208105.ui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01208105.book.data.customers.Customer;
import a01208105.book.data.customers.CustomerDao;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.awt.event.ActionEvent;

/**
 * This class designs a customer dialog
 * @author Veronica A01208105
 * @version 2020-11-22
 */

@SuppressWarnings("serial")
public class CustomerDetailsDialog extends JDialog {
	
	private static final Logger LOG = LogManager.getLogger();

	private final JPanel contentPanel = new JPanel();
	private JTextField idField;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField streetField;
	private JTextField cityField;
	private JTextField postalCodeField;
	private JTextField phoneField;
	private JTextField emailAddressField;
	private JTextField joinedDateField;

	/**
	 * Create the dialog.
	 */
	public CustomerDetailsDialog(CustomerListDialog customerListDialog, CustomerDao dao) {
		
		LOG.debug("Calling CustomerDetailsDialog constructor");
		
		setTitle("Customer");
		setBounds(800, 430, 450, 346);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[85.00][292.00,grow][33.00]", "[23.00][23.00][23.00][23.00][23.00][23.00][23.00][23.00][23.00]"));
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new MigLayout("", "[47px][65px][][][][][][][][][][][]", "[23px]"));
			
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Customer aCustomer = null;
						Date date1 = null;
						try {
							date1 = new SimpleDateFormat("yyyy-MM-dd").parse(joinedDateField.getText());
						} catch (ParseException e2) {
							e2.printStackTrace();
						} 
						LocalDate date2 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						
						LOG.debug("Create a customer with the dialog fields");
						aCustomer = new Customer.Builder(Long.valueOf(idField.getText()), phoneField.getText())
								.setFirstName(firstNameField.getText()).setLastName(lastNameField.getText())
								.setStreet(streetField.getText()).setCity(cityField.getText()).setPostalCode(postalCodeField.getText())
								.setEmailAddress(emailAddressField.getText()).setJoinedDate(date2).build();
		
						try {
							LOG.debug("Update Customer: " + aCustomer);
							dao.update(aCustomer);
							LOG.debug("Customer is updated");
						} catch (SQLException e1) {
							JOptionPane.showInternalInputDialog(CustomerDetailsDialog.this, "Error: Record cannot be updated");
						}
						CustomerDetailsDialog.this.dispose();
					}
				});
				okButton.setHorizontalAlignment(SwingConstants.RIGHT);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton, "cell 11 0,alignx right,aligny top");
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CustomerDetailsDialog.this.dispose();
					}
				});
				cancelButton.setHorizontalAlignment(SwingConstants.RIGHT);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton, "cell 12 0,alignx right,aligny top");
			}
			
			JLabel labelId = new JLabel("ID");
			contentPanel.add(labelId, "cell 0 0, alignx trailing");
			
			idField = new JTextField();
			idField.setEnabled(false);
			idField.setEditable(false);
			contentPanel.add(idField, "cell 1 0, growx");
			idField.setColumns(4);	
		
			JLabel lblfirstName = new JLabel("First Name");
			contentPanel.add(lblfirstName, "cell 0 1,alignx trailing,growy");
			
			firstNameField = new JTextField();
			contentPanel.add(firstNameField, "cell 1 1,growx");
			firstNameField.setColumns(10);
			
			JLabel lbllastName = new JLabel("Last Name");
			contentPanel.add(lbllastName, "cell 0 2,alignx trailing");
			
			lastNameField = new JTextField();
			contentPanel.add(lastNameField, "cell 1 2,growx");
			lastNameField.setColumns(10);
		
			JLabel lblStreet = new JLabel("Street");
			contentPanel.add(lblStreet, "cell 0 3,alignx trailing");
			
			streetField = new JTextField();
			contentPanel.add(streetField, "cell 1 3,growx");
			streetField.setColumns(10);
		
			JLabel lblCity = new JLabel("City");
			contentPanel.add(lblCity, "cell 0 4,alignx trailing");
			
			cityField = new JTextField();
			contentPanel.add(cityField, "cell 1 4,growx");
			cityField.setColumns(10);
		
			JLabel lblPostal = new JLabel("Postal Code");
			contentPanel.add(lblPostal, "cell 0 5,alignx trailing");
			
			postalCodeField = new JTextField();
			contentPanel.add(postalCodeField, "cell 1 5,growx");
			postalCodeField.setColumns(10);
		
			JLabel lblPhone = new JLabel("Phone");
			contentPanel.add(lblPhone, "cell 0 6,alignx trailing");
			
			phoneField = new JTextField();
			contentPanel.add(phoneField, "cell 1 6,growx");
			phoneField.setColumns(10);
		
			JLabel lblEmail = new JLabel("Email");
			contentPanel.add(lblEmail, "cell 0 7,alignx trailing");
			
			emailAddressField = new JTextField();
			contentPanel.add(emailAddressField, "cell 1 7,growx");
			emailAddressField.setColumns(10);
		
			JLabel lblJoinedDate = new JLabel("Joined Date");
			contentPanel.add(lblJoinedDate, "cell 0 8,alignx trailing");
			
			joinedDateField = new JTextField();
			contentPanel.add(joinedDateField, "cell 1 8,growx");
			joinedDateField.setColumns(10);
		}

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);		
	}
	
	public void setCustomer (Customer customer) {
		idField.setText(Long.toString(customer.getId()));
		firstNameField.setText(customer.getFirstName());
		lastNameField.setText(customer.getLastName());
		streetField.setText(customer.getStreet());
		cityField.setText(customer.getCity());
		postalCodeField.setText(customer.getPostalCode());
		phoneField.setText(customer.getPhone());
		emailAddressField.setText(customer.getEmailAddress());
		joinedDateField.setText(customer.getJoinedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	}	

}
