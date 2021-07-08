package encryptdecrypt.services;

import encryptdecrypt.encryptors.Encryptor;
import encryptdecrypt.encryptors.EncryptorFactory;
import encryptdecrypt.enums.AlgorithmType;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EncryptionService {

    private static final String MODE = "-mode";
    private static final String DATA = "-data";
    private static final String KEY = "-key";
    private static final String IN = "-in";
    private static final String OUT = "-out";
    private static final String ALG = "-alg";

    private static final String ENCRYPT = "enc";
    private static final String DECRYPT = "dec";

    public void invoke(String[] args) {
        String message = null;

        Map<String,String> map = mapArgs(args);
        String mode = map.getOrDefault(MODE, ENCRYPT);
        int offset = Integer.parseInt(map.getOrDefault(KEY, "0"));
        String data = map.getOrDefault(DATA, map.getOrDefault(IN, ""));
        String alg = map.getOrDefault(ALG, "shift");

        if (map.containsKey(IN)) {
            try {
                message = new String(Files.readAllBytes(Paths.get(map.get(IN))));
            } catch (IOException e) {
                System.out.printf("Error : %s", e.getMessage());
            }
        } else message = data;

        Encryptor encryptor = map.getOrDefault(ALG, "shift").equals("shift")
                ? EncryptorFactory.create(AlgorithmType.SHIFT)
                : EncryptorFactory.create(AlgorithmType.UNICODE);

        String output = mode.equals(ENCRYPT) ?
                encryptor.encrypt(message, offset) :
                encryptor.decrypt(message, offset);

        if (map.containsKey(OUT)) {
            File outputFile = new File(map.get(OUT));
            try (PrintWriter writer = new PrintWriter(outputFile)){
                writer.print(output);
            } catch (IOException e) {
                System.out.printf("Error : %s", e.getMessage());
            }
        } else System.out.println(output);
    }

    private HashMap<String,String> mapArgs(String[] args) {
        return IntStream
                .range(0, args.length)
                .filter(i -> i % 2 == 0)
                .boxed()
                .collect(Collectors.toMap(i -> args[i], i -> args[i + 1], (a, b) -> b, HashMap::new));
    }
}
