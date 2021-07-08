package encryptdecrypt.encryptors;

public interface Encryptor {

    public String encrypt(String message, int offset);
    public String decrypt(String message, int offset);
}
