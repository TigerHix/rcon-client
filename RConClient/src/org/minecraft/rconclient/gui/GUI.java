/**
 * 
 */
package org.minecraft.rconclient.gui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import net.sourceforge.namedlogger.NamedLogger;

import org.minecraft.rconclient.rcon.RCon;

/**
 * This class holds the GUI for the application.
 * 
 * @author vincent
 * 
 */
public class GUI implements Runnable {

	private class CloseListener extends WindowAdapter {

		@Override
		public void windowClosing(final WindowEvent event) {
			LOGGER.entering(event);

			RCon connection = globals.getConnection();
			if (connection != null) {
				try {
					connection.close();
				} catch (final IOException e) {
					// Ignore.
				} finally {
					connection = null;
					globals.setConnection(connection);
				}
			}
			super.windowClosing(event);
			System.exit(0);

			LOGGER.exiting();
		}

	}

	/**
	 * The logger for this class.
	 */
	private static final NamedLogger LOGGER = new NamedLogger();

	/**
	 * The resource bundle of the messages.
	 */
	private static final ResourceBundle MESSAGES = new Messages(GUI.class);
	private static final String MSG_CLASS_NAME = GUI.class.getSimpleName();
	private static final String MSG_TITLE = MSG_CLASS_NAME + ".title";
	private static final String MSG_FILE_MENU_TEXT = MSG_CLASS_NAME + ".fileMenu.text";
	private static final String MSG_FILE_MENU_MNEMONIC = MSG_CLASS_NAME + ".fileMenu.mnemonic";
	private static final String MSG_SERVER_MENU_TEXT = MSG_CLASS_NAME + ".serverMenu.text";
	private static final String MSG_SERVER_MENU_MNEMONIC = MSG_CLASS_NAME + ".serverMenu.mnemonic";
	private static final String MSG_BAN_IP_LIST_TITLE = MSG_CLASS_NAME + ".banIpList.title";
	private static final String MSG_BAN_LIST_TITLE = MSG_CLASS_NAME + ".banList.title";
	private static final String MSG_USER_LIST_TITLE = MSG_CLASS_NAME + ".userList.title";
	private static final String MSG_WHITE_LIST_TITLE = MSG_CLASS_NAME + ".whiteList.title";

	private static final String PROTOTYPE_CELL_VALUE = "XXXXXXXXXXXXXXXX";

	/**
	 * The container with the global objects.
	 */
	private final Globals globals;

	/**
	 * Create the GUI. This will be done in a separate thread.
	 */
	public GUI() {
		super();
		LOGGER.entering();

		globals = new Globals();
		SwingUtilities.invokeLater(this);

		LOGGER.exiting();
	}

	@Override
	public void run() {
		LOGGER.entering();

		final JFrame frame = new JFrame();
		globals.setFrame(frame);
		frame.setTitle(MESSAGES.getString(MSG_TITLE));
		frame.addWindowListener(new CloseListener());

		final JMenuBar menuBar = createMenuBar();
		frame.setJMenuBar(menuBar);

		final Container contentPane = frame.getContentPane();
		contentPane.setLayout(new FlowLayout());

		final JComponent userList = createUserList();
		contentPane.add(userList);

		final JComponent whiteList = createWhiteList();
		contentPane.add(whiteList);

		final JComponent banList = createBanList();
		contentPane.add(banList);

		final JComponent banIpList = createBanIpList();
		contentPane.add(banIpList);

		frame.pack();
		frame.setVisible(true);

		LOGGER.exiting();
	}

	/**
	 * @return
	 */
	private JComponent createBanIpList() {
		LOGGER.entering();

		final ListModel<String> listModel = new BanIpListModel(globals);
		final JList<String> banIpList = new JList<>(listModel);
		banIpList.setPrototypeCellValue(PROTOTYPE_CELL_VALUE);
		banIpList.setVisibleRowCount(10);

		final JPopupMenu popupMenu = new JPopupMenu();
		final JMenuItem pardonItem = new JMenuItem();
		final Action pardonAction = new PardonIpAction(globals, banIpList);
		pardonItem.setAction(pardonAction);
		popupMenu.add(pardonItem);
		banIpList.addMouseListener(new PopupMenuListener(popupMenu));

		final JScrollPane scrollPane = new JScrollPane(banIpList);
		final Border border = BorderFactory.createTitledBorder(MESSAGES.getString(MSG_BAN_IP_LIST_TITLE));
		scrollPane.setBorder(border);

		LOGGER.exiting(scrollPane);
		return scrollPane;
	}

	/**
	 * @return
	 */
	private JComponent createBanList() {
		LOGGER.entering();

		final ListModel<String> listModel = new BanListModel(globals);
		final JList<String> banList = new JList<>(listModel);
		banList.setPrototypeCellValue(PROTOTYPE_CELL_VALUE);
		banList.setVisibleRowCount(10);

		final JPopupMenu popupMenu = new JPopupMenu();
		final JMenuItem pardonItem = new JMenuItem();
		final Action pardonAction = new PardonAction(globals, banList);
		pardonItem.setAction(pardonAction);
		popupMenu.add(pardonItem);
		banList.addMouseListener(new PopupMenuListener(popupMenu));

		final JScrollPane scrollPane = new JScrollPane(banList);
		final Border border = BorderFactory.createTitledBorder(MESSAGES.getString(MSG_BAN_LIST_TITLE));
		scrollPane.setBorder(border);

		LOGGER.exiting(scrollPane);
		return scrollPane;
	}

	/**
	 * Create the menu bar.
	 * 
	 * @return The menu bar.
	 */
	private JMenuBar createMenuBar() {
		LOGGER.entering();

		final JMenuBar menuBar = new JMenuBar();

		final JMenu fileMenu = new JMenu();
		fileMenu.setText(MESSAGES.getString(MSG_FILE_MENU_TEXT));
		fileMenu.setMnemonic(KeyEventUtil.getKeyCode(MESSAGES.getString(MSG_FILE_MENU_MNEMONIC)));

		final JMenuItem connectItem = new JMenuItem();
		final Action connectAction = new ConnectAction(globals);
		connectItem.setAction(connectAction);
		fileMenu.add(connectItem);

		final JMenuItem disconnectItem = new JMenuItem();
		final Action disconnectAction = new DisconnectAction(globals);
		disconnectItem.setAction(disconnectAction);
		fileMenu.add(disconnectItem);

		final JMenuItem quitItem = new JMenuItem();
		final Action quitAction = new QuitAction();
		quitItem.setAction(quitAction);
		fileMenu.add(quitItem);

		menuBar.add(fileMenu);

		final JMenu serverMenu = new JMenu();
		serverMenu.setText(MESSAGES.getString(MSG_SERVER_MENU_TEXT));
		serverMenu.setMnemonic(KeyEventUtil.getKeyCode(MESSAGES.getString(MSG_SERVER_MENU_MNEMONIC)));
		final JMenuItem startServerItem = new JMenuItem();
		final Action startServerAction = new StartServerAction(globals);
		startServerItem.setAction(startServerAction);
		serverMenu.add(startServerItem);

		final JMenuItem stopServerItem = new JMenuItem();
		final Action stopServerAction = new StopServerAction(globals);
		stopServerItem.setAction(stopServerAction);
		serverMenu.add(stopServerItem);

		menuBar.add(serverMenu);

		LOGGER.exiting(menuBar);
		return menuBar;
	}

	/**
	 * @return
	 */
	private JComponent createUserList() {
		LOGGER.entering();

		final ListModel<String> listModel = new UserListModel(globals);
		final JList<String> userList = new JList<>(listModel);
		userList.setPrototypeCellValue(PROTOTYPE_CELL_VALUE);
		userList.setVisibleRowCount(10);

		final JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.add(new JMenuItem("Ban"));
		popupMenu.add(new JMenuItem("Op"));
		popupMenu.add(new JMenuItem("de-Op"));
		popupMenu.add(new JMenuItem("Game mode"));
		popupMenu.add(new JMenuItem("Give"));
		popupMenu.add(new JMenuItem("Kick"));
		popupMenu.add(new JMenuItem("Tell"));
		popupMenu.add(new JMenuItem("Teleport"));
		popupMenu.add(new JMenuItem("Whitelist"));
		popupMenu.add(new JMenuItem("XP"));
		userList.addMouseListener(new PopupMenuListener(popupMenu));

		final JScrollPane scrollPane = new JScrollPane(userList);
		final Border border = BorderFactory.createTitledBorder(MESSAGES.getString(MSG_USER_LIST_TITLE));
		scrollPane.setBorder(border);

		LOGGER.exiting(scrollPane);
		return scrollPane;
	}

	/**
	 * @return
	 */
	private JComponent createWhiteList() {
		LOGGER.entering();

		final ListModel<String> listModel = new WhiteListModel(globals);
		final JList<String> whiteList = new JList<>(listModel);
		whiteList.setPrototypeCellValue(PROTOTYPE_CELL_VALUE);
		whiteList.setVisibleRowCount(10);

		final JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.add(new JMenuItem("Remove"));
		whiteList.addMouseListener(new PopupMenuListener(popupMenu));

		final JScrollPane scrollPane = new JScrollPane(whiteList);
		final Border border = BorderFactory.createTitledBorder(MESSAGES.getString(MSG_WHITE_LIST_TITLE));
		scrollPane.setBorder(border);

		LOGGER.exiting(scrollPane);
		return scrollPane;
	}

}
