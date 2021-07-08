package encryptdecrypt.encryptors;

import encryptdecrypt.enums.ModeType;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class UnicodeEncryptor implements Encryptor {
    @Override
    public String encrypt(String message, int offset) {
        return process(message, offset, ModeType.ENCRYPT);
    }

    @Override
    public String decrypt(String message, int offset) {
        return process(message, offset, ModeType.DECRYPT);
    }

    private String process(String message, int offset, ModeType mode) {
        ArrayList<Character> list = new ArrayList<>();
        char[] charArray = message.toCharArray();
        for (char ch : charArray) {
            int res;
            if (mode == ModeType.ENCRYPT) {
                res = ch + offset;
            } else if (mode == ModeType.DECRYPT) {
                res = ch - offset;
            } else {
                res = ch;
            }
            list.add((char) res);
        }
        return list.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}
