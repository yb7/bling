package uti
/*
 * Slightly modified version of the com.ibatis.common.jdbc.ScriptRunner class
 * from the iBATIS Apache project. Only removed dependency on Resource class
 * and a constructor 
 */
/*
 *  Copyright 2004 Clinton Begin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import java.io.IOException
import java.io.LineNumberReader
import java.io.PrintWriter
import java.io.Reader
import java.sql._

/**
 * Tool to run database scripts
 */
class ScriptRunner(connection: Connection, autoCommit: Boolean,
                   stopOnError: Boolean) {

	private final val DEFAULT_DELIMITER: String = ";"

//	private var connection: Connection = _

//	private var stopOnError: Boolean = _
//	private var autoCommit: Boolean = _

	private var logWriter = new PrintWriter(System.out)
	private var errorLogWriter = new PrintWriter(System.err)

	private var delimiter = DEFAULT_DELIMITER
	private var fullLineDelimiter = false

	/**
	 * Default constructor
	 */
//	public ScriptRunner() {
//		this.connection = connection
//		this.autoCommit = autoCommit
//		this.stopOnError = stopOnError
//	}

	def setDelimiter(delimiter: String, fullLineDelimiter: Boolean) {
		this.delimiter = delimiter
		this.fullLineDelimiter = fullLineDelimiter
	}

	/**
	 * Setter for logWriter property
	 * 
	 * @param logWriter
	 *            - the new value of the logWriter property
	 */
	def setLogWriter(logWriter: PrintWriter) {
		this.logWriter = logWriter
	}

	/**
	 * Setter for errorLogWriter property
	 * 
	 * @param errorLogWriter
	 *            - the new value of the errorLogWriter property
	 */
	def setErrorLogWriter(errorLogWriter: PrintWriter) {
		this.errorLogWriter = errorLogWriter
	}

	/**
	 * Runs an SQL script (read in using the Reader parameter)
	 * 
	 * @param reader
	 *            - the source of the script
	 */
	def runScript(reader: Reader)  {
		try {
			val originalAutoCommit = connection.getAutoCommit()
			try {
				if (originalAutoCommit != this.autoCommit) {
					connection.setAutoCommit(this.autoCommit)
				}
				runScript(connection, reader)
			} finally {
				connection.setAutoCommit(originalAutoCommit)
			}
		} catch {
            case e: IOException => throw e
            case e: SQLException => throw e
            case e: Exception => throw new RuntimeException("Error running script.  Cause: " + e, e)
		}
	}

	/**
	 * Runs an SQL script (read in using the Reader parameter) using the
	 * connection passed in
	 * 
	 * @param conn
	 *            - the connection to use for the script
	 * @param reader
	 *            - the source of the script
	 * @throws SQLException
	 *             if any SQL errors occur
	 * @throws IOException
	 *             if there is an error reading from the Reader
	 */
	def runScript(conn: Connection, reader: Reader) {
		var command: StringBuffer = null
		try {
			val lineReader = new LineNumberReader(reader)
			var line: String = null
			while ((line = lineReader.readLine()) != null) {
				if (command == null) {
					command = new StringBuffer()
				}
				val trimmedLine = line.trim()
				if (trimmedLine.startsWith("--")) {
					println(trimmedLine)
				} else if (trimmedLine.length() < 1
						|| trimmedLine.startsWith("//")) {
					// Do nothing
				} else if (trimmedLine.length() < 1
						|| trimmedLine.startsWith("--")) {
					// Do nothing
				} else if (!fullLineDelimiter
						&& trimmedLine.endsWith(getDelimiter())
						|| fullLineDelimiter
						&& trimmedLine.equals(getDelimiter())) {
					command.append(line.substring(0, line
							.lastIndexOf(getDelimiter())))
					command.append(" ")
					val statement = conn.createStatement()

					println(command)

					var hasResults = false
					if (stopOnError) {
						hasResults = statement.execute(command.toString())
					} else {
						try {
							statement.execute(command.toString())
						} catch {
                            case e: SQLException =>
                                e.fillInStackTrace()
                                printlnError("Error executing: " + command)
                                printlnError(e)
                        }
					}

					if (autoCommit && !conn.getAutoCommit()) {
						conn.commit()
					}

				    val rs = statement.getResultSet()
					if (hasResults && rs != null) {
						val md = rs.getMetaData()
						val cols = md.getColumnCount()
						for (i <- 0 to cols) {
							val name = md.getColumnLabel(i)
							print(name + "\t")
						}
						println("")
						while (rs.next()) {
							for (i <- 0 to cols) {
								val value = rs.getString(i)
								print(value + "\t")
							}
							println("")
						}
					}

					command = null
					try {
						statement.close()
					} catch {
                        case e: Exception => // Ignore to workaround a bug in Jakarta DBCP
                    }
					Thread.`yield`()
				} else {
					command.append(line)
					command.append(" ")
				}
			}
			if (!autoCommit) {
				conn.commit()
			}
		} catch {
            case e: SQLException =>
                e.fillInStackTrace()
                printlnError("Error executing: " + command)
                printlnError(e)
                throw e
		    case e1:IOException=>
                e1.fillInStackTrace()
                printlnError("Error executing: " + command)
                printlnError(e1)
                throw e1
        } finally {
			conn.rollback()
			flush()
		}
	}

	private def getDelimiter() = delimiter

	private def print(o:Object) {
		if (logWriter != null) {
			System.out.print(o)
		}
	}

	private def println(o:Object) {
		if (logWriter != null) {
			logWriter.println(o)
		}
	}

	private def printlnError(o:Object) {
		if (errorLogWriter != null) {
			errorLogWriter.println(o)
		}
	}

	private def flush() {
		if (logWriter != null) {
			logWriter.flush()
		}
		if (errorLogWriter != null) {
			errorLogWriter.flush()
		}
	}
}
