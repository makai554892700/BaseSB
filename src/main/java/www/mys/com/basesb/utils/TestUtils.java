package www.mys.com.basesb.utils;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

public class TestUtils extends WebSocketClient {

    public TestUtils(String url) throws URISyntaxException {
        super(new URI(url));
    }

    @Override
    public void onOpen(ServerHandshake shake) {
        System.out.println("onOpen...");
        for (Iterator<String> it = shake.iterateHttpFields(); it.hasNext(); ) {
            String key = it.next();
            System.out.println(key + ":" + shake.getFieldValue(key));
        }
    }

    @Override
    public void onMessage(String paramString) {
        System.out.println("onMessage" + paramString);
    }

    @Override
    public void onClose(int paramInt, String paramString, boolean paramBoolean) {
        System.out.println("onClose...paramInt=" + paramInt + ";paramString=" + paramString + ";paramBoolean=" + paramBoolean);
    }

    @Override
    public void onError(Exception e) {
        System.out.println("onError:" + e);
    }

    public static void main(String[] args) throws Exception {
    }

}
