package model;

public class Logger {

	public static void log(String msg) {
		try {

			throw new Error(msg);
		} catch (Error error) {
			StackTraceElement [] stack=error.getStackTrace();
			System.out.println( stack[1]+" "+msg);

		}
	}

}
