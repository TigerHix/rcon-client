/**
 * 
 */
package org.minecraft.rconclient.gui;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;

import net.sourceforge.namedlogger.NamedLogger;

/**
 * The action to be used to start the server.
 * 
 * @author vincent
 * 
 */
public class StartServerAction extends AbstractAction {

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
	private static final ResourceBundle MESSAGES = new Messages(StartServerAction.class);

	private static final String MSG_CLASS_NAME = StartServerAction.class.getSimpleName();
	private static final String MSG_NAME = MSG_CLASS_NAME + ".name";
	private static final String MSG_MNEMONIC = MSG_CLASS_NAME + ".mnemonic";

	/**
	 * The container with the global objects.
	 */
	@SuppressWarnings("unused")
	private final Globals globals;

	/**
	 * A new action will be created.
	 * 
	 * @param globals
	 *            The container with the global objects.
	 */
	public StartServerAction(final Globals globals) {
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

		// TODO

		LOGGER.exiting();
	}

}
