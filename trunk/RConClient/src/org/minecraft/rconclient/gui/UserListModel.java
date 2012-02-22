/**
 * 
 */
package org.minecraft.rconclient.gui;

import java.io.IOException;

import net.sourceforge.namedlogger.NamedLogger;

import org.minecraft.rconclient.rcon.AuthenticationException;
import org.minecraft.rconclient.rcon.RCon;

/**
 * @author vincent
 * 
 */
public class UserListModel extends AbstractUserListModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The logger for this class.
	 */
	private static final NamedLogger LOGGER = new NamedLogger();

	/**
	 * @param globals
	 */
	public UserListModel(final Globals globals) {
		super(globals);
	}

	@Override
	protected String[] getList(final RCon connection) throws IOException, AuthenticationException {
		LOGGER.entering(connection);

		final String[] users = connection != null ? connection.list() : new String[0];

		LOGGER.exiting(users);
		return users;
	}

}
