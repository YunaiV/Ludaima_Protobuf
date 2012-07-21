package org.ludaima.protobuf;
import java.io.IOException;

import org.junit.Test;
import org.ludaima.protobuf.Config;
import org.ludaima.protobuf.Reader;

public class WriterTest {
	@Test
	public void writeTest() throws IOException {
		Config config = Reader.read("/org/ludaima/protobuf/test.proto");
		Writer.write(config, "/data/Java_Project/Ludaima_Protobuf/src/test/");
		// TODO 暂时未完成生成的消息类所在的包需要org.ludaima.protobuf.base包下的类
	}
}
