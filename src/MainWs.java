import Messages.Message;
import Messages.MessageDecoder;
import Messages.MessageEncoder;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;

@ServerEndpoint(
        value = "/ws",
        encoders = { MessageEncoder.class },
        decoders = { MessageDecoder.class }
        )


//@ServerEndpoint("/ws")
public class MainWs {


    private static String profil_container = null;

    private static ArrayList<Session> logiciels = new ArrayList <>();


    private Session waiting_card_creation = null;

    private static ConnectionSQL rqt = new ConnectionSQL();

    @OnOpen
    public void open(Session session) throws IOException, EncodeException {
        session.getBasicRemote().sendText("Server : connected");
        logiciels.add(session);

}

    @OnClose
    public void close(Session session) throws IOException, EncodeException {
        logiciels.remove(session);
    }

//
    // {"type":3,"content":"0xFF","from":"client1","to":"scanner1"}

    @OnMessage
    public void handleMessage(Message m, Session session) throws Exception {

        // Logiciel
        if(m.getType() == Message.MESSAGE_PROFIL_TO_WRITE){
            profil_container = m.getContent();
            session.getBasicRemote().sendText("Server : profil retenue, attente d'une carte vide " + profil_container);
            waiting_card_creation = session;
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

        if (m.getType() == Message.MESSAGE_SUCESS_WRITE_CARD) {
//            for (Session s : logiciels) {
//                s.getBasicRemote().sendText(new MessageEncoder().encode(new Message(profil_container, Message.MESSAGE_SUCESS_WRITE_CARD, "server", "asadmin")));
//
//            }

        }
        if(m.getType() == Message.MESSAGE_BLANK_CARD){
            if(profil_container != null ) {
                //m.getContent();
                session.getBasicRemote().sendText(
                        new MessageEncoder().encode(
                                new Message(profil_container, Message.MESSAGE_PROFIL_TO_WRITE, "server", "esp"
                                                )));


                for (Session s : logiciels)
                    s.getBasicRemote().sendText(new MessageEncoder().encode(new Message(profil_container, Message.MESSAGE_SUCESS_WRITE_CARD, "server", "asadmin")));


            }
            else
                session.getBasicRemote().sendText("Server : "+ Message.MESSAGE_ACCESS_REJECTED);
        }







    }
}

