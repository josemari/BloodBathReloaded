package org.jomaveger.tiger.util.functional.io;

import org.jomaveger.tiger.util.functional.control.Result;
import org.jomaveger.tiger.util.functional.tuples.Tuple2;

public interface Input {

	Result<Tuple2<String, Input>> readString();

	Result<Tuple2<Integer, Input>> readInt();

	default Result<Tuple2<String, Input>> readString(String message) {
		return readString();
	}

	default Result<Tuple2<Integer, Input>> readInt(String message) {
		return readInt();
	}
}
