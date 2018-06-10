package ndn.sm.agh.edu.pl.client;


import android.util.Log;

import net.named_data.jndn.Data;
import net.named_data.jndn.Face;
import net.named_data.jndn.Interest;
import net.named_data.jndn.Name;
import net.named_data.jndn.OnData;
import net.named_data.jndn.OnTimeout;


public class Client {
    private String m_retVal;
    private Face m_face;
    private boolean m_shouldStop = false;

    public Client() {
        String data = fetchData();
        System.out.println(">>>RESPONSE: " + data);
        Log.i("UDALOSIE", data);
    }

    String fetchData() {
        try {
            m_face = new Face("");
            m_face.expressInterest(new Name("/ndn/agh/weather/London,us/" + System.currentTimeMillis()),
                    new OnData() {
                        @Override
                        public void
                        onData(Interest interest, Data data) {
                            m_retVal = data.getContent().toString();
                            Log.i("", m_retVal);
                            m_shouldStop = true;
                        }
                    },
                    new OnTimeout() {
                        @Override
                        public void onTimeout(Interest interest) {
                            m_retVal = "ERROR: Timeout";
                            Log.i("", m_retVal);
                            m_shouldStop = true;
                        }
                    });

            while (!m_shouldStop) {
                m_face.processEvents();
                Thread.sleep(500);
            }
            m_face.shutdown();
            m_face = null;
            return m_retVal;
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }
}