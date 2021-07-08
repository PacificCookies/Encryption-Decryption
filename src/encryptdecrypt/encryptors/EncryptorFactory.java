package encryptdecrypt.encryptors;

import encryptdecrypt.enums.AlgorithmType;

public class EncryptorFactory {
    public static Encryptor create(AlgorithmType type) {
        switch (type) {
            case UNICODE:
                return new UnicodeEncryptor();
            case SHIFT:
            default:
                return new CaesarEncryptor();
        }
    }
}
