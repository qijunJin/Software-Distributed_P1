
import java.io.*;
import java.math.BigInteger;

import utils.Endianness;

public class Communication {
    private final int STRSIZE = 40;

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public Communication(InputStream inputStream, OutputStream outputStream) throws IOException {
        dataInputStream = new DataInputStream(inputStream);
        dataOutputStream = new DataOutputStream(outputStream);
    }

    public String reader() throws IOException {
        int opcode = Integer.parseInt(String.valueOf(read_char()));

        char cStr[] = new char[100];
        int pos;

        switch (opcode) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                pos = 0;
                do {
                    char charToPut = read_char();
                    if (charToPut == '0') break;
                    cStr[pos] = charToPut;
                    pos++;
                } while (true);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + opcode);
        }

        return String.valueOf(cStr).trim();
    }

    public byte[] read_hash() throws IOException {
        int opcode = Integer.parseInt(String.valueOf(read_char()));
        byte hashBytes[] = new byte[32];

        if (opcode == 2) {
           hashBytes = read_bytes(32);
        }
        return hashBytes;
    }


    public void write_hello(String str) throws IOException {
        int lenStr = str.length();
        int numBytes = lenStr + 2;

        byte bStr[] = new byte[numBytes];

        bStr[0] = (byte) '1';

        for (int i = 0; i < lenStr; i++)
            bStr[i + 1] = (byte) str.charAt(i);

        bStr[numBytes - 1] = (byte) '0';

        dataOutputStream.write(bStr, 0, numBytes);
    }

    public void write_secret(String str) throws IOException {
        int lenStr = str.length();
        int numBytes = lenStr + 2;

        byte bStr[] = new byte[numBytes];

        bStr[0] = (byte) '3';

        for (int i = 0; i < lenStr; i++)
            bStr[i + 1] = (byte) str.charAt(i);

        bStr[numBytes - 1] = (byte) '0';

        dataOutputStream.write(bStr, 0, numBytes);
    }


    public void write_hash(byte[] bytes) throws IOException {
        byte hashBytes[] = new byte[33];

        hashBytes[0] = (byte) '2';

        for (int i = 0; i < 32; i++) {
            hashBytes[i + 1] = bytes[i];
        }

        dataOutputStream.write(hashBytes, 0, 33);
    }


    private char read_char() throws IOException {
        return (char) read_bytes(1)[0];
    }

    private byte[] read_bytes(int numBytes) throws IOException {
        int len = 0;
        byte bStr[] = new byte[numBytes];
        int bytesread = 0;
        do {
            bytesread = dataInputStream.read(bStr, len, numBytes - len);
            if (bytesread == -1)
                throw new IOException("Broken Pipe");
            len += bytesread;
        } while (len < numBytes);
        return bStr;
    }


}