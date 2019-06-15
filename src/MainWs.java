import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import java.util.*;

import Messages.Message;
import Messages.MessageDecoder;
import Messages.MessageEncoder;

@ServerEndpoint(
        value = "/ws",
        encoders = { MessageEncoder.class },
        decoders = { MessageDecoder.class }
        )


//@ServerEndpoint("/ws")
public class MainWs {


    private static String profil_container = null;
    private static ArrayList<Session> logiciels = new ArrayList <>();

    private static ConnectionSQL rqt = new ConnectionSQL();

    @OnOpen
    public void open(Session session) throws IOException, EncodeException {
        session.getBasicRemote().sendText("Server : connected");

}

    @OnClose
    public void close(Session session) throws IOException, EncodeException {
        //Suppression de la session de l'une des tables
    }

//    @OnMessage
//    public void handleStringmsg(String m, Session s) throws Exception{
//        s.getBasicRemote().sendText(m.split(" ")[1]);
//    }

//
    // {"type":3,"content":"0xFF","from":"client1","to":"scanner1"}

    @OnMessage
    public void handleMessage(Message m, Session session) throws Exception {

        //Logiciel
        if(m.getType() == Message.MESSAGE_PROFIL_TO_WRITE){
            profil_container = m.getContent();
            session.getBasicRemote().sendText("Server : profil retenue, attente d'une carte vide " + profil);
            return;

        }
        //Scan d'une carte
        // 1 de la part du nodemcu
        if(m.getType() == Message.MESSAGE_CHECK_PROFIL){
            // verification avec la bd
            // todo verification

            rqt.insertAcess(m.getUid(), 1, 1);



            // Envoie a tout les client logiciels une notification qu'une nouvelle carte à été scanné.
            // une notification
            for (Session log : logiciels ) {
                log.getBasicRemote().
                        sendText(new MessageEncoder().encode(new Message(m.getContent(),  Message.MESSAGE_REFRESH, "server", "logiciel")));
            }


            //int type, String content, String from, String to, String card_type, String uid
            session.getBasicRemote().sendText(new MessageEncoder().encode(new Message(Message.MESSAGE_ACCESS_ALLOWED, "update_code","server", "ESP8266-01", m.getCard_type(), m.getUid() )));
        }

        if(m.getType() == Message.MESSAGE_BLANK_CARD){
            if(profil_container != null ) {
                //m.getContent();
                session.getBasicRemote()
                        .sendText(
                                new MessageEncoder()
                                        .encode(
                                                new Message(profil, Message.MESSAGE_PROFIL_TO_WRITE, "server", "esp"
                                                )));
            }
            else
                session.getBasicRemote().sendText("Server : "+ Message.MESSAGE_ACCESS_REJECTED);
        }







    }
}

