package Messages;

import com.google.gson.Gson;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;



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
    @Override
    public String encode(Message message) throws EncodeException {
        return convertor.toJson(message);
    }


    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
