/**
 * 
 */
package org.minecraft.rconclient.gui;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import net.sourceforge.namedlogger.NamedLogger;

import org.minecraft.rconclient.rcon.RCon;

/**
 * The action to be used when a connection to the RCon needs to be disconnected.
 * 
 * @author vincent
 * 
 */
public class DisconnectAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The logger for this class.
	 */
	private static final NamedLogger LOGGER = new NamedLogger();

	/**
	 * The resource bundle of the messages.
	 */
	private static final ResourceBundle MESSAGES = new Messages(DisconnectAction.class);

	private static final String MSG_CLASS_NAME = DisconnectAction.class.getSimpleName();
	private static final String MSG_NAME = MSG_CLASS_NAME + ".name";
	private static final String MSG_MNEMONIC = MSG_CLASS_NAME + ".mnemonic";
	private static final String MSG_IOEXCEPTION_TITLE = MSG_CLASS_NAME + ".ioException.title";
	private static final String MSG_IOEXCEPTION_MESSAGE = MSG_CLASS_NAME + ".ioException.message";

	/**
	 * The container with the global objects.
	 */
	private final Globals globals;

	/**
	 * A new action will be created.
	 * 
	 * @param globals
	 *            The container with the global objects.
	 */
	public DisconnectAction(final Globals globals) {
		super();
		LOGGER.entering();

		this.globals = globals;
		putValue(NAME, MESSAGES.getString(MSG_NAME));
		putValue(MNEMONIC_KEY, KeyEventUtil.getKeyCode(MESSAGES.getString(MSG_MNEMONIC)));

		LOGGER.exiting();
	}

	@Override
	public void actionPerformed(final ActionEvent event) {
		LOGGER.entering(event);

		try {
			final RCon connection = globals.getConnection();
			if (connection != null) {
				connection.close();
			}
		} catch (final IOException e) {
			LOGGER.log(Level.WARNING, "Closing a connection", e);
			JOptionPane.showMessageDialog((JComponent) event.getSource(), MESSAGES.getString(MSG_IOEXCEPTION_MESSAGE),
					MESSAGES.getString(MSG_IOEXCEPTION_TITLE), JOptionPane.ERROR_MESSAGE);
		} finally {
			globals.setConnection(null);
		}

		LOGGER.exiting();
	}

}
