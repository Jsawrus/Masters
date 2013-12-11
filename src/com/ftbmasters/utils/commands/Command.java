package com.ftbmasters.utils.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention (RetentionPolicy.RUNTIME)
public @interface Command {
	/**
	 * Command name
	 */
	String name();

	/**
	 * Command aliases
	 */
	String[] aliases() default {};

	/**
	 * Command must be issued by a Player
	 */
	boolean player() default true;

	/**
	 * Command permission
	 * Multiple permission nodes can be specified splitting nodes with ";"
	 */
	String permission() default "system.admin";

	/**
	 * Command description
	 * long description of what the command does
	 */
	String description() default "\u00A7cNo description provided for this command.";

	/**
	 * Command usage with parameters
	 */
	String usage() default "\u00A7cNo usage provided for command";
}
