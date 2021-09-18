package utils;

import model.BodyParser;

public class BasicReturn {
	public static String make(boolean success) {
		BodyParser bp = new BodyParser();
		bp.newBlock();
		bp.put("success", Boolean.toString(success));
		
		return bp.make();
	}
	
	public static String make(boolean success, String reason) {
		BodyParser bp = new BodyParser();
		bp.newBlock();
		bp.put("success", Boolean.toString(success));
		bp.put("reason", reason);
		
		return bp.make();
	}
}
