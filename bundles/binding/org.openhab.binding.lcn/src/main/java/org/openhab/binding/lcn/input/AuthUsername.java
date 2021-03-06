/**
 * Copyright (c) 2010-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.lcn.input;

import java.util.Collection;
import java.util.LinkedList;

import org.openhab.binding.lcn.common.PckParser;
import org.openhab.binding.lcn.connection.Connection;
import org.openhab.core.events.EventPublisher;
import org.openhab.core.items.Item;
import org.openhab.core.types.Command;

/**
 * User name request for LCN-PCHK authentication.
 * 
 * @author Tobias J�ttner
 */
public class AuthUsername extends Input {
	
	/**
	 * Tries to parse the given input received from LCN-PCHK.
	 * 
	 * @param input the input
	 * @return list of {@link AuthUsername} (might be empty, but not null}
	 */
	static Collection<Input> tryParseInput(String input) {
		LinkedList<Input> ret = new LinkedList<Input>();
		if (input.equals(PckParser.AUTH_USERNAME)) {
			ret.add(new AuthUsername());
		}
		return ret;
	}
	
	/**
	 * Sends the user name.
	 * {@inheritDoc}
	 */
	@Override
	public void process(Connection conn) {
		conn.queue(conn.getSets().getUsername());
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean tryVisualization(Input.VisualizationVisitor handler, Connection conn, Command cmd, Item item, EventPublisher eventPublisher) {
		// Not used for visualization
		return false;
	}
	
}
