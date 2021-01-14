package a01208105.ui;

import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01208105.book.ApplicationException;
import a01208105.book.data.books.BookDao;
import a01208105.book.data.customers.CustomerDao;
import a01208105.book.data.purchases.PurchaseDao;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private JPanel contentPane;
	public static boolean customerJoinedDateOption;
	public static boolean booksByAutherOption;
	public static boolean descOptionBooks;
	public static boolean descOptionPurchases;
	public static boolean purchasesByTitle;
	public static boolean purchasesByLastName;
	public static String individualCustomerId;

	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Create the frame.
	 */
	public MainFrame(CustomerDao customerDao, BookDao bookDao, PurchaseDao purchaseDao) {

		LOG.debug("Calling MainFrame Constructor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(583, 450);
		setLocationRelativeTo(null);
		setTitle("My Book Store Database");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[56.00][][10.00][-20.00]", "[][][][][45.00][][][][54.00]"));

		JLabel lblNewLabel = new JLabel("Welcome");
		lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Georgia", Font.ITALIC, 55));
		contentPane.add(lblNewLabel, "cell 1 6,alignx center");

		JLabel lblNewLabel_1 = new JLabel("to the Book Store Database!");
		lblNewLabel_1.setFont(new Font("Georgia", Font.ITALIC, 35));
		contentPane.add(lblNewLabel_1, "cell 1 7");

		// Menu Bar with Menus
		JMenuBar mainMenuBar = new JMenuBar();
		setJMenuBar(mainMenuBar);

		// File Menu -> Drop, Quit
		JMenu fileMenu = new JMenu("File");
		mainMenuBar.add(fileMenu);
		fileMenu.setMnemonic('f');

		JMenuItem drop = new JMenuItem("Drop", KeyEvent.VK_D);
		drop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(MainFrame.this,
						"Are you sure you want to delete all input data from the database and exit?");
				LOG.debug("Prompt to drop: " + option);
				if (option == 0) {
					try {
						purchaseDao.drop();
						customerDao.drop();
						bookDao.drop();
						LOG.debug("All tables are dropped");
						System.exit(0);
					} catch (SQLException e1) {
						LOG.error(e1.getMessage());
						JOptionPane.showMessageDialog(MainFrame.this, "Error: The table cannot be deleted");
					}
				}

			}
		});
		fileMenu.add(drop);
		fileMenu.addSeparator();
		JMenuItem quit = new JMenuItem("Quit", KeyEvent.VK_Q);
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(quit);

		// Books Menu -> Count, By Author, Descending, List
		JMenu bookMenu = new JMenu("Books");
		mainMenuBar.add(bookMenu);
		fileMenu.setMnemonic('b');

		JMenuItem bookCount = new JMenuItem("Count");
		bookCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JOptionPane.showMessageDialog(MainFrame.this,
							String.format("We currently offer %d different books!", bookDao.countAllBooks()),
							"All Books Count", JOptionPane.INFORMATION_MESSAGE);
				} catch (HeadlessException | SQLException e1) {
					LOG.error(e1.getMessage());
					JOptionPane.showMessageDialog(MainFrame.this, "Error: No Data");
				}

			}
		});
		bookMenu.add(bookCount);

		JCheckBoxMenuItem checkByAuthor = new JCheckBoxMenuItem("By Author");
		checkByAuthor.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					booksByAutherOption = true;
				} else {
					booksByAutherOption = false;
				}
			}
		});
		bookMenu.add(checkByAuthor);

		JCheckBoxMenuItem checkDesc = new JCheckBoxMenuItem("Descending");
		checkDesc.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					descOptionBooks = true;
				} else {
					descOptionBooks = false;
				}
			}
		});
		bookMenu.add(checkDesc);

		bookMenu.addSeparator();

		JMenuItem bookList = new JMenuItem("List");
		bookList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookListDialog bookListDialog = null;
				try {
					bookListDialog = new BookListDialog(MainFrame.this, bookDao);
				} catch (ApplicationException e1) {
					LOG.error(e1.getMessage());
					JOptionPane.showMessageDialog(MainFrame.this, "Something went wrong... Please try again");
				}
				bookListDialog.setVisible(true);
			}
		});
		bookMenu.add(bookList);

		// Customers Menu -> Count, By Join Date, List
		JMenu customerMenu = new JMenu("Customers");
		customerMenu.setMnemonic('c');
		mainMenuBar.add(customerMenu);

		JMenuItem count = new JMenuItem("Count");
		count.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					JOptionPane.showMessageDialog(MainFrame.this,
							String.format("We currently have %d customers!", customerDao.countAllCustomers()),
							"All Customers Count", JOptionPane.INFORMATION_MESSAGE);
				} catch (HeadlessException | SQLException e1) {
					LOG.error(e1.getMessage());
					JOptionPane.showMessageDialog(MainFrame.this, "Error: No Data");
				}
			}
		});
		customerMenu.add(count);

		JCheckBoxMenuItem checkJoinedDate = new JCheckBoxMenuItem("By Joined Date");
		checkJoinedDate.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					customerJoinedDateOption = true;
				} else {
					customerJoinedDateOption = false;
				}
			}
		});
		customerMenu.add(checkJoinedDate);

		customerMenu.addSeparator();

		JMenuItem customerList = new JMenuItem("List");
		customerList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerListDialog listDialog = new CustomerListDialog(MainFrame.this, customerDao);
				listDialog.setVisible(true);
			}
		});
		customerMenu.add(customerList);

		// Purchases Menu -> Total, By Last Name, By Title, Descending, Filter by
		// Customer, List

		JMenu purchasesMenu = new JMenu("Purchases");
		mainMenuBar.add(purchasesMenu);
		purchasesMenu.setMnemonic('p');

		JMenuItem total = new JMenuItem("Total");
		total.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (individualCustomerId != null) {
						JOptionPane.showMessageDialog(MainFrame.this,
								String.format("The total for the customer %s: $%.2f CAD", individualCustomerId,
										purchaseDao.countTotalForACustomer(individualCustomerId)),
								"All Purchases Count - Total", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(MainFrame.this,
								String.format("We have made $%.2f CAD in total so far!",
										purchaseDao.countAllPurchases()),
								"All Purchases Count - Total", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (HeadlessException | SQLException e1) {
					LOG.error(e1.getMessage());
					JOptionPane.showMessageDialog(MainFrame.this, "Error: No Data");
				}
			}
		});
		purchasesMenu.add(total);

		JCheckBoxMenuItem checkByLastName = new JCheckBoxMenuItem("By LastName");
		checkByLastName.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					purchasesByLastName = true;
				} else {
					purchasesByLastName = false;
				}
			}
		});
		purchasesMenu.add(checkByLastName);

		JCheckBoxMenuItem checkByTitle = new JCheckBoxMenuItem("By Title");
		checkByTitle.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					purchasesByTitle = true;
				} else {
					purchasesByTitle = false;
				}
			}
		});
		purchasesMenu.add(checkByTitle);

		JCheckBoxMenuItem descPurchases = new JCheckBoxMenuItem("Descending");
		descPurchases.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					descOptionPurchases = true;
				} else {
					descOptionPurchases = false;
				}
			}
		});
		purchasesMenu.add(descPurchases);

		JMenuItem filterById = new JMenuItem("Filter by Customer ID");
		filterById.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = JOptionPane.showInputDialog(MainFrame.this, "Enter Customer Id: ");
				if (input != null) {
					try {
						if (customerDao.readByCustomerId(input) != null) {
							individualCustomerId = input;
							LOG.debug(individualCustomerId);
							JOptionPane.showMessageDialog(MainFrame.this, String.format(
									"Confirmation: Customer %s is selected%nTo see the individual report, please select Purchases > List",
									individualCustomerId));
						} else {
							individualCustomerId = null;
							JOptionPane.showMessageDialog(MainFrame.this, "There is no Customer that matches this ID." + "\n" + "The filter is removed.");
						}
					} catch (Exception e1) {
						LOG.error(e1.getMessage());
						JOptionPane.showMessageDialog(MainFrame.this, "Something went wrong... Please try again");
					}
				} else {
					individualCustomerId = null;
					JOptionPane.showMessageDialog(MainFrame.this,
							"No Customer Id entered." + "\n" + "The filter is removed.");
				}

			}
		});
		purchasesMenu.add(filterById);

		purchasesMenu.addSeparator();

		JMenuItem listPurchases = new JMenuItem("List");
		listPurchases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (purchasesByLastName == true || purchasesByTitle == true || individualCustomerId != null) {
						PurchaseListDialogExtended purchaseDialogExtended = new PurchaseListDialogExtended(
								MainFrame.this, purchaseDao, customerDao, bookDao);
						purchaseDialogExtended.setVisible(true);
					} else {
						PurchaseListDialog purchaseListDialog = new PurchaseListDialog(MainFrame.this, purchaseDao,
								customerDao, bookDao);
						purchaseListDialog.setVisible(true);
					}
				} catch (ApplicationException e1) {
					LOG.error(e1.getMessage());
					JOptionPane.showMessageDialog(MainFrame.this, "Something went wrong... Please try again");
				}
			}
		});
		purchasesMenu.add(listPurchases);

		// Help -> About

		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('h');
		mainMenuBar.add(helpMenu);

		JMenuItem about = new JMenuItem("About");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainFrame.this, String.format("This is a Database for the Book Store"),
						"About", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		KeyStroke keyStrokeToAbout = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);
		about.setAccelerator(keyStrokeToAbout);
		helpMenu.add(about);

	}

}
