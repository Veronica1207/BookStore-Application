package a01208105.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01208105.book.data.customers.Customer;
import a01208105.book.data.customers.CustomerDao;
import a01208105.book.io.CustomerReport;
import net.miginfocom.swing.MigLayout;

public class CustomerListDialog extends JDialog {


	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Create the dialog.
	 */
	public CustomerListDialog(JFrame frame, CustomerDao dao) {

		LOG.debug("Calling CustomerListDialog cosnstructor");
		setSize(1100, 750);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setTitle("Customer List");
		contentPanel.setLayout(new MigLayout("", "[]", "[]"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		DefaultListModel<String> listModel;
		listModel = new DefaultListModel<String>();
		List<String> customerStrings = null;
		
		LOG.debug("Option: " + MainFrame.customerJoinedDateOption);
		
		if (MainFrame.customerJoinedDateOption == false) {
			customerStrings = CustomerReport.write(dao, dao.customerList());			
		} else {
			customerStrings = CustomerReport.write(dao, dao.customerListByDate(dao.customerList()));
			LOG.debug("Sorted by joined date.");
		}
		for (String aCustomerString : customerStrings) {
			listModel.addElement(aCustomerString);
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		JList list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(35);
		contentPanel.add(list, "cell 0 0,grow");

		JScrollPane listScrollPane = new JScrollPane(list);
		contentPanel.add(listScrollPane);

		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new MigLayout("",
					"[47px][65px][][][][][][][][][][][][][362.00][][][][][-138.00][268.00][][][][][][][][-283.00][93.00][][]",
					"[23px]"));
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (e.getActionCommand().equals("OK")) {							
							String s = (String) list.getSelectedValue();
							LOG.debug("Line selected: " + s);
							if (s == null) {
								LOG.debug("Nothing was selected - closing...");
								CustomerListDialog.this.dispose();
								
							} else {

								String[] values = s.split(" ");
								Customer aCustomer = null;
								try {
									aCustomer = dao.readByCustomerId(values[1]);
									LOG.debug("Customer ID selected: " + values[1]);
								} catch (Exception e1) {
									JOptionPane.showMessageDialog(CustomerListDialog.this, "Error: No Data");
								}
								LOG.debug("Opening the customer details dialog...");
								CustomerDetailsDialog dialogDetails = new CustomerDetailsDialog(CustomerListDialog.this,
										dao);
								dialogDetails.setVisible(true);
								dialogDetails.setCustomer(aCustomer);
							}
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton, "cell 30 0,alignx center,aligny top");
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CustomerListDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton, "cell 31 0,alignx center,aligny top");
			}
		}

	}

}
