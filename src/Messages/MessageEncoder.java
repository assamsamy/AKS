package Messages;

import com.google.gson.Gson;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.math.BigInteger;


public class MessageEncoder implements Encoder.Text<Message>{

    private static Gson convertor = new Gson();

    /*
    Message m = new Message("161631104710-X0ICKOE-IPO?PICIOJ", 4, "ID_ADMIN_CLIENT", "scanner1");
* {
     "type": 4,
     "content": "161631104710-X0ICKOE-IPO?PICIOJ",
     "from": "ID_ADMIN_CLIENT",
     "to": "scanner1",

* }
*
*
* */
    public String binary(String expression){
        return new BigInteger(expression.getBytes()).toString(2);
    }
    public String stringify(String binary){
        return new String(new BigInteger(binary, 2).toByteArray());
    }
    public int [] contentToCipher(String content){
        String bin = binary(content);
        int [] cipher = new int[bin.length()];
        for(int i = 0; i< bin.length(); i++){
            cipher[i] = Integer.parseInt(String.valueOf(bin.charAt(i)));
        }
        return cipher;
    }
    public static void printCipher(int [] cipher){
        for(int i = 0; i < cipher.length; i++)
            System.out.print(cipher[i]);
    }

    public String intArrayToString(int [] cipher){
        String result = "";
        for(int i = 0; i< cipher.length; i ++){
            result += String.valueOf(cipher[i]);
        }
        return result;
    }

    private static LFSR lfsr=new LFSR();
    @Override
    public String encode(Message message) throws EncodeException {
        int [] cipher;
        if(message.getType()==message.MESSAGE_PROFIL_TO_WRITE || message.getType()==message.MESSAGE_HCE) {
            cipher=lfsr.encrypt(lfsr.genratearray(contentToCipher(message.getContent()).length),contentToCipher(message.getContent()));
            String chiffrement=intArrayToString(cipher);

            message.setContent(chiffrement);
            System.out.println("le chiffrement "+chiffrement);
        }



        return convertor.toJson(message);
    }



    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
