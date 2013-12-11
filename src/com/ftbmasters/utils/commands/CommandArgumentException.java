package com.ftbmasters.utils.commands;

/**
 * Describes a wrong number of arguments or
 * wrong spelling error
 */
public class CommandArgumentException extends RuntimeException {
	public CommandArgumentException(String s) {
		super(s);
	}
}
