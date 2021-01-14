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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01208105.book.ApplicationException;
import a01208105.book.data.books.BookDao;
import a01208105.book.data.customers.CustomerDao;
import a01208105.book.data.purchases.Purchase;
import a01208105.book.data.purchases.PurchaseDao;
import a01208105.book.io.PurchaseReport;
import net.miginfocom.swing.MigLayout;

public class PurchaseListDialogExtended extends JDialog {

	private static final Logger LOG = LogManager.getLogger();

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 * 
	 * @throws ApplicationException
	 */
	public PurchaseListDialogExtended(JFrame frame, PurchaseDao dao, CustomerDao customerDao, BookDao bookDao)
			throws ApplicationException {
		
		LOG.debug("Calling PurchaseListDialogExtended constructor");
		setSize(585, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Purchase List - by customer last name");

		DefaultListModel<String> listModel;
		listModel = new DefaultListModel<String>();
		List<Purchase> listTemp = null;
		listTemp = dao.purchaseList(customerDao, bookDao);
		List<String> purchaseStringsSortedReport = null;

		LOG.debug("Options selected: " + MainFrame.purchasesByLastName + " " + MainFrame.purchasesByTitle + " "
				+ MainFrame.descOptionPurchases + " " + MainFrame.individualCustomerId);

		if (MainFrame.purchasesByLastName == true) {
			if (MainFrame.descOptionPurchases == false) {
				listTemp = dao.purchaseListByName(listTemp);
				purchaseStringsSortedReport = PurchaseReport.writePurchaseReportExtended(dao, listTemp);
				LOG.debug("Sorted by Last Name DESC");
			} else {
				listTemp = dao.purchaseListDesc(dao.purchaseListByName(listTemp));
				purchaseStringsSortedReport = PurchaseReport.writePurchaseReportExtended(dao, listTemp);
				LOG.debug("Sorted by Last Name");
			}
		} else if (MainFrame.purchasesByTitle == true) {
			if (MainFrame.descOptionPurchases == true) {
				listTemp = dao.purchaseListDesc(dao.purchaseListByTitle(listTemp));
				purchaseStringsSortedReport = PurchaseReport.writePurchaseReportExtended(dao, listTemp);
				LOG.debug("Sorted by Title DESC");
			} else {
				listTemp = dao.purchaseListByTitle(listTemp);
				purchaseStringsSortedReport = PurchaseReport.writePurchaseReportExtended(dao, listTemp);
				LOG.debug("Sorted By Title");
			}
		}

		if (MainFrame.individualCustomerId != null) {

			listTemp = dao.findPurchasesByCustomerId(MainFrame.individualCustomerId, customerDao, bookDao);

			if (MainFrame.purchasesByTitle) {
				if (MainFrame.descOptionPurchases == true) {
					listTemp = dao.purchaseListDesc(dao.purchaseListByTitle(listTemp));
					purchaseStringsSortedReport = PurchaseReport.writeIndividualReport(listTemp, dao, customerDao,
							bookDao, MainFrame.individualCustomerId);
					LOG.debug("Individual Report Sorted By Title DESC");
				} else {
					listTemp = dao.purchaseListByTitle(listTemp);
					purchaseStringsSortedReport = PurchaseReport.writeIndividualReport(listTemp, dao, customerDao,
							bookDao, MainFrame.individualCustomerId);
					LOG.debug("Individual Report Sorted by Title");
				}
			}
			purchaseStringsSortedReport = PurchaseReport.writeIndividualReport(listTemp, dao, customerDao, bookDao,
					MainFrame.individualCustomerId);
			LOG.debug("Individual Report not sorted");

		}

		for (String aPurchase : purchaseStringsSortedReport) {
			listModel.addElement(aPurchase);
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		JList list = new JList(listModel);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(35);
		contentPanel.add(list, "cell 1 1,grow");

		JScrollPane listScrollPane = new JScrollPane(list);
		contentPanel.add(listScrollPane);

		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[]", "[]"));
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
							PurchaseListDialogExtended.this.dispose();
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton, "cell 30 0,alignx left,aligny top");
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PurchaseListDialogExtended.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton, "cell 31 0,alignx left,aligny top");
			}
		}
	}

}
