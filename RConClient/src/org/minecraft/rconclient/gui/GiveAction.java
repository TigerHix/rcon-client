/**
 * 
 */
package org.minecraft.rconclient.gui;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JList;

import net.sourceforge.namedlogger.NamedLogger;

import org.minecraft.rconclient.rcon.AuthenticationException;
import org.minecraft.rconclient.rcon.RCon;

/**
 * The action to be used to give a player some items.
 * 
 * @author vincent
 */
public class GiveAction extends AbstractAction {

	/**
	 * The serial version id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The logger for this class.
	 */
	private static final NamedLogger LOGGER = new NamedLogger();

	/**
	 * The resource bundle of the messages.
	 */
	private static final ResourceBundle MESSAGES = new Messages(GiveAction.class);

	private static final String MSG_CLASS_NAME = GiveAction.class.getSimpleName();
	private static final String MSG_NAME = MSG_CLASS_NAME + ".name";
	private static final String MSG_MNEMONIC = MSG_CLASS_NAME + ".mnemonic";

	/**
	 * The container with the global objects.
	 */
	private final Globals globals;

	/**
	 * The list with the selected items.
	 */
	private final JList<String> list;

	/**
	 * A new action will be created.
	 * 
	 * @param globals
	 *            The container with the global objects.
	 * @param list
	 *            The list with the selected items.
	 */
	public GiveAction(final Globals globals, final JList<String> list) {
		super();
		LOGGER.entering(globals, list);

		this.globals = globals;
		this.list = list;

		putValue(NAME, MESSAGES.getString(MSG_NAME));
		putValue(MNEMONIC_KEY, KeyEventUtil.getKeyCode(MESSAGES.getString(MSG_MNEMONIC)));

		LOGGER.exiting();
	}

	@Override
	public void actionPerformed(final ActionEvent event) {
		LOGGER.entering(event);

		final RCon connection = globals.getConnection();
		final List<String> selectedPlayers = list.getSelectedValuesList();
		list.clearSelection();
		try {
			// TODO Ask for the values by a dialog.
			final int dataValue = 0;
			final int amount = 1;
			final int damage = 0;
			for (final String player : selectedPlayers) {
				if (connection != null) {
					connection.give(player, dataValue, amount, damage);
				}
			}
			globals.refreshConnection(connection);
		} catch (AuthenticationException | IOException e) {
			globals.setConnection(null);
		}

		LOGGER.exiting();
	}

}
