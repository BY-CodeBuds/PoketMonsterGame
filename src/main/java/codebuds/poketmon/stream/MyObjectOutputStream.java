package codebuds.poketmon.stream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MyObjectOutputStream extends ObjectOutputStream {
    public MyObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    // 덮어 싀우기를 위해 Haed 생성 기능 삭제
    @Override
    protected void writeStreamHeader() throws IOException {
//        super.writeStreamHeader();
    }
}
