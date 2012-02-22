/**
 * 
 */
package org.minecraft.rconclient.gui;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;

import net.sourceforge.namedlogger.NamedLogger;

/**
 * The action to be used to quit the application.
 * 
 * @author vincent
 * 
 */
public class QuitAction extends AbstractAction {

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
	private static final ResourceBundle MESSAGES = new Messages(QuitAction.class);

	private static final String MSG_CLASS_NAME = QuitAction.class.getSimpleName();
	private static final String MSG_NAME = MSG_CLASS_NAME + ".name";
	private static final String MSG_MNEMONIC = MSG_CLASS_NAME + ".mnemonic";

	/**
	 * A new action will be created.
	 */
	public QuitAction() {
		super();
		LOGGER.entering();

		putValue(NAME, MESSAGES.getString(MSG_NAME));
		putValue(MNEMONIC_KEY, KeyEventUtil.getKeyCode(MESSAGES.getString(MSG_MNEMONIC)));

		LOGGER.exiting();
	}

	@Override
	public void actionPerformed(final ActionEvent event) {
		LOGGER.entering(event);

		System.exit(0);

		LOGGER.exiting();
	}

}
