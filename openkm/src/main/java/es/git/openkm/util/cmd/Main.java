/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006  GIT Consultors
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package es.git.openkm.util.cmd;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.err.println("Usage: java -cp OKMUtils.jar <utility>");
		System.err.println("Where <utility> may be: ");
		System.err.println("(1)  es.git.openkm.util.cmd.RepositoryUpgrade");
		System.err.println("(2)  es.git.openkm.util.cmd.ViewRepository");
		System.err.println("(3)  es.git.openkm.util.cmd.Traverse");
		System.err.println("(4)  es.git.openkm.util.cmd.UUIDGenerator");
		System.err.println("(5)  es.git.openkm.util.cmd.CheckExtractors");
		System.err.println("-----------");
		System.err.println("In (1) to (3) you need additional parameters like:");
		System.err.println("   -Djava.security.auth.login.config==jaas.config -Xmx512m");
	}
}
