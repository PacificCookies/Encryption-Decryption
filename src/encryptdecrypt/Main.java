package encryptdecrypt;

import encryptdecrypt.services.EncryptionService;


public class Main {

    public static void main(String[] args) {
        EncryptionService service = new EncryptionService();
        service.invoke(args);
    }
}
