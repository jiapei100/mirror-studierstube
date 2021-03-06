/* ========================================================================
 * Copyright (C) 2004-2005  Graz University of Technology
 *
 * This framework is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This framework is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this framework; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * For further information please contact Dieter Schmalstieg under
 * <schmalstieg@icg.tu-graz.ac.at> or write to Dieter Schmalstieg,
 * Graz University of Technology, Institut f�r Maschinelles Sehen und Darstellen,
 * Inffeldgasse 16a, 8010 Graz, Austria.
 * ========================================================================
 * PROJECT: Muddleware
 * ======================================================================== */
/**
 * 
 */
package org.studierstube.net;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.studierstube.net.protocol.ErrorMessageHandler;
//import org.studierstube.net.protocol.muddleware.Message;



/**
 * Open connection to server sending byte messages and receiving bate messages.
 * 
 * Note: JAVA only
 * 
 * @author Michael Kalkusch
 *
 */
public abstract class AbstractClientByteStreamHandler 
 implements ErrorMessageHandler  {
	
	/**
	 * Socket connection to server
	 */
	private Socket client = null;
	
	/**
	 * Server name
	 */
	private String sServerName = "localhost";
	
	/**
	 * Server port
	 */
	private int iServerPort = 20000;
	
	
	/**
	 * Output stream
	 */
	protected OutputStream outStream = null;
	
	/**
	 * Input stream
	 */
	protected InputStream inStream = null;
	
	
//	private int iMessageInfoStyle = Message.MESSAGE_STYLE_BRIEF;
	

	
	/**
	 * Default constructor.
	 */
	protected AbstractClientByteStreamHandler( String nServerName,
			int nServerPort ) {
		
		this.sServerName = nServerName;		
		this.setServerPortByNumber( nServerPort );
	}



	public abstract void removeAllMessages();
		//TODO: to make thread safe use explicit locking!

	
	/**
	 * Set port number in the range of [1025..65535]
	 * 
	 * @see org.studierstube.net.AbstractClientByteStreamHandler#setServerPort(String)
	 */
	public final boolean setServerPortByNumber( int nSetServerPort ) {
		
		if (( nSetServerPort > 1024 )&&(nSetServerPort <= 65535)) {
			this.iServerPort = nSetServerPort;
			
			return true;
		} else {
			logMsg("server port [" + nSetServerPort + 
					"] invalid, because range is exceeded [1025..65535]",true);
			
			return false;
		}
	}
	
	/**
	 * Get current server port.
	 * 
	 * @see org.studierstube.net.AbstractClientByteStreamHandler#setServerPortByNumber(int)
	 * @see org.studierstube.net.AbstractClientByteStreamHandler#setServerPort(String)
	 * 
	 * @return server port
	 */
	public final int getServerPort() {
		return this.iServerPort;
	}
	
	/**
	 * Set server port pasing a String
	 * 
	 * @see org.studierstube.net.AbstractClientByteStreamHandler#setServerPortByNumber(int)
	 * 
	 * 
	 * @param nSetServerPort server port as String
	 * 
	 * @return TRUE if nSetServerPort is in the range of [1025...65535]
	 */
	public final boolean setServerPort( String nSetServerPort ) {
		
		Integer portNumber = Integer.getInteger( nSetServerPort, this.iServerPort );
		
		return (portNumber != null) ? setServerPortByNumber( portNumber ) : false;		
	}
	
	/**
	 * Test is connection was established.
	 * 
	 * @return TRUE if connection is established.
	 */
	public boolean isConnected() {
		return (client != null) ? client.isConnected() : false;
	}
	
	
	
	/**
	 * Set server name.
	 * 
	 * Note: Does not perform check, if server name is valid.
	 * 
	 * @see org.studierstube.net.AbstractClientByteStreamHandler#isServerValid()
	 * 
	 * @param nServerName
	 */
	public final void setServerName( String nServerName ) {
		sServerName = nServerName;
	}
	
	/**
	 * Test if server address is valid.
	 * 
	 * @see org.studierstube.net.AbstractClientByteStreamHandler#setServerName(String)
	 * 
	 * @return TRUE if server address is valid, does not check port number
	 */
	public final boolean isServerValid() {		
		try {
			//InetAddress serverAddress = 
			
			InetAddress.getByName(sServerName);
			return true;
		} catch ( UnknownHostException uhe ) {
			return false;
		}
	}

	
	
	/**
	 * Calls setServerName(String) and setServerPort(String)
	 * Note: serverName is checked while connect()
	 * 
	 * @see org.studierstube.net.AbstractClientByteStreamHandler#setServerName(String)
	 * @see org.studierstube.net.AbstractClientByteStreamHandler#setServerPort(String)
	 * @see org.studierstube.net.AbstractClientByteStreamHandler#connect()
	 * 
	 * @param nServerName
	 * @param sPortNumber
	 * @return TRUE if port was valid and both are set, FLASE if port was not valid and nothing was set
	 */
	public final boolean setServerNameAndPort( String nServerName,
			String sPortNumber ) {
		if ( setServerPort( sPortNumber ) ) {
			sServerName = nServerName;
			return true;
		}		
		return false;
	}
	
	
	/**
	 * Calls setServerName(String) and setServerPortByNumber(int)
	 * Note: serverName is checked while connect()
	 * 
	 * @see org.studierstube.net.AbstractClientByteStreamHandler#setServerName(String)
	 * @see org.studierstube.net.AbstractClientByteStreamHandler#setServerPortByNumber(int)
	 * @see org.studierstube.net.AbstractClientByteStreamHandler#connect()
	 * 
	 * @param nServerName server name
	 * @param sPortNumber port number [1025..65535]
	 * 
	 * @return TRUE if port number was valid
	 */
	public final boolean setServerNameAndPort( String nServerName,
			int sPortNumber ) {
		if ( setServerPortByNumber( sPortNumber ) ) {
			sServerName = nServerName;
		}
		
		return false;
	}
	
	/**
	 * Get current server name.
	 * 
	 * @see org.studierstube.net.AbstractClientByteStreamHandler#setServerName(String)
	 * 
	 * @return current server name
	 */
	public final String getServerName( ) {
		return this.sServerName;
	}
	
	/**
	 * Connect to server.
	 * Note: Overload this method is needed. 
	 * It calls connectToServer(String, int) internally.
	 * 
	 * @see org.studierstube.net.AbstractClientByteStreamHandler#isConnected()
	 * @see org.studierstube.net.AbstractClientByteStreamHandler#disconnect()
	 * 
	 * @see org.studierstube.net.AbstractClientByteStreamHandler#connectToServer(String, int)
	 * 
	 * @return TRUE if connection to server was established.	
	 */
	public boolean connect() {
		return connectToServer( sServerName, iServerPort );
	}
	
	/**
	 * Connect to the server and assigns input stream and output stream.
	 * 
	 * @see org.studierstube.net.AbstractClientByteStreamHandler#getInputStream()
	 * @see org.studierstube.net.AbstractClientByteStreamHandler#getOutputStream()
	 * 
	 * @param nServerName server name
	 * @param nServerPort server port
	 * @return TRUE if connection is established
	 */
	protected final boolean connectToServer( final String nServerName, final int nServerPort ) {
		
		try {
			InetAddress serverAddress = 
				InetAddress.getByName(nServerName);
			
			client = new Socket( serverAddress , nServerPort );
			
			logMsg( "connect..("+nServerName+":"+nServerPort+")",
					false );
			
			try {
				inStream = client.getInputStream();
				logMsg( ".. input-stream ..", false );
			} catch ( IOException ioe ) {
				logMsg( "..input-stream [FAILED]", false );	
				inStream = null;
			}	
			
			try {
				outStream = client.getOutputStream();
				logMsg( ".. output-stream ..", false );	
				
			} catch ( IOException ioe ) {
				logMsg( "..output-stream [FAILED]", false );	
				outStream = null;
			}
			
			logMsg( "..[DONE]", true );
			return true;
			
		} catch ( UnknownHostException uhe ) {
			return false;		
		} catch ( IOException ioe ) {
			logMsg( "..[FAILED]", true );	
			return false;
		}
	}
	
	
//	
//	/**
//	 * @see org.studierstube.net.ErrorMessageHandler
//	 */
//	public abstract void logMsg( String text, boolean newLine );
//	
	
	
	/**
	 * Get the current input stream, or null if no connection is established.
	 * 
	 * @return current input stream
	 */
	public final InputStream getInputStream() {
		return this.inStream;
	}
	
	/**
	 * Get the current output stream, or null if no connection is established.
	 *
	 * @return current output stream
	 */
	public final OutputStream getOutputStream() {
		return this.outStream;
	}
	
	/**
	 * Disconnect from server, if a connection has been established.
	 * Note calls disconnectFromServer() internally.
	 * 
	 * @return TRUE if connection is closed and FALSE in case of en error during closing the connection
	 */
	public boolean disconnect() {
		if ( client.isClosed() ) {
			return true;
		}
		
		return disconnectFromServer();
	}
	
	/**
	 * Close connection.
	 * Note: All derived classes should use this methode to disconnect form server.
	 * 
	 * @return TRUE if connection is closed and FALSE in case of en error during closing the ocnnection
	 */
	protected final boolean disconnectFromServer() {
		
		try {
			logMsg( "disconnect...", false );
			
			if ( inStream != null ) {
				inStream.close();
				inStream = null;
			} else {
				
			}
			
			if ( outStream != null ) {
				outStream.close();	
				outStream = null;
			} else {
				
			}
			
			if ( client != null) {
				client.close();
				client = null;
				
				logMsg( "..[DONE]", true );
			} else {
				logMsg( "is closed..[DONE]", true );
			}
			
			return true;
			
		} catch ( IOException ioe ) {
			
			logMsg( "..[FAILED]", true );
			
			client = null;
			
			return false;
		}
		
	}
		

}
