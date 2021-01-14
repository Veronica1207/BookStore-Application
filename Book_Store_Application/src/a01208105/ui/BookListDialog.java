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
import a01208105.book.io.BookReport;
import net.miginfocom.swing.MigLayout;

public class BookListDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Create the dialog.
	 * 
	 * @throws ApplicationException
	 */
	public BookListDialog(JFrame frame, BookDao dao) throws ApplicationException {
		LOG.debug("Calling BookListDialog constructor");
		
		setSize(1100, 750);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setTitle("Book List");
		contentPanel.setLayout(new MigLayout("", "[]", "[]"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		DefaultListModel<String> listModel;
		listModel = new DefaultListModel<String>();
		List<String> bookStrings = null;
		
		LOG.debug("Options: " + MainFrame.booksByAutherOption, MainFrame.descOptionBooks);

		if (MainFrame.booksByAutherOption == true) {
			if (MainFrame.descOptionBooks == true) {
				bookStrings = BookReport.writeBookReport(dao, dao.bookListByAuthor(dao.bookList(), true));
				LOG.debug("Sorted by Author DESC");
			} else {
				bookStrings = BookReport.writeBookReport(dao, dao.bookListByAuthor(dao.bookList(), false));
				LOG.debug("Sorted by Author");
			}
		} else {
			bookStrings = BookReport.writeBookReport(dao, dao.bookList());
			LOG.debug("Sorted");
		}

		for (String aBook : bookStrings) {
			listModel.addElement(aBook);	
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JList list = new JList(listModel);
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
						LOG.debug("Dialog closed - OK button");
						BookListDialog.this.dispose();
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
						LOG.debug("Dialog closed - Cancel button");
						BookListDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton, "cell 31 0,alignx left,aligny top");
			}
		}
	}

}
