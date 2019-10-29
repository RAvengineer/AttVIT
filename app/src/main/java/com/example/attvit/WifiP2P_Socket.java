package com.example.attvit;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.attvit.Classes.WifiDirectBroadcastReceiver;
import com.example.attvit.util.ConnectionUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class WifiP2P_Socket extends AppCompatActivity {
    public Button bDiscover, bSendMessage;
    public ListView lstvDevices;
    public TextView tConnectionStatus, tMessage;
    public ConnectionUtil wifi = new ConnectionUtil();

    WifiP2pManager manager;
    WifiP2pManager.Channel channel;

    BroadcastReceiver receiver;
    IntentFilter intentFilter;

    List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    String[] deviceNameArray;
    WifiP2pDevice[] deviceArray;


    ServerClass serverClass;
    SendReceive sendReceive;
    ClientClass clientClass;
    // ***************

    // Shared Preferences
    SharedPreferences userDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_p2_p__socket);

        userDetails = getSharedPreferences("UserDetails", MODE_PRIVATE);
        initialize();

        wifi.wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifi.enableWifi();
        // <editor-fold default="collapsed" desc="Give him a break of 1500 millis">
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // </editor-fold>

        execListeners();
    }



    // <editor-fold desc="Video No.: 008 Handler">
    // Video No.: 008
    static final int MESSAGE_READ = 1;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_READ:
                    byte[] readBuff = (byte[]) msg.obj;
//                    String tempMsg = new String(readBuff,0,msg.arg1);
                    String tempMsg = null;
                    tempMsg = new String(readBuff, 0, msg.arg1);
//                        tempMsg = new String(readBuff, "UTF-8");

                    Log.d("Received", tempMsg);

                    tMessage.setText(tempMsg);
                    // <editor-fold default="collapsed" desc="Give him a break of 1500 millis">
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // </editor-fold>
                    wifi.disableWifi();
                    break;
            }
            return true;
        }
    });
// </editor-fold>

    // <editor-fold default="collapsed" desc="initialize Components">

    public void initialize() {
        bDiscover = findViewById(R.id.btnDiscover);
        bSendMessage = findViewById(R.id.btnSend);
        lstvDevices = findViewById(R.id.lvDevices);
        tConnectionStatus = findViewById(R.id.tvConnectionStatus);
        tMessage = findViewById(R.id.tvMessage);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);

        receiver = new WifiDirectBroadcastReceiver(manager, channel, this);

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    // </editor-fold>

    // <editor-fold default="collapsed" desc="execute Listeners">

    public void execListeners() {

        bDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess() {
                        Log.d("Connection Status", "Discovering...");
                        tConnectionStatus.setText("Discovering...");
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onFailure(int reason) {
                        Log.d("Connection Status", "Discovery Failed");
                        tConnectionStatus.setText("Discovery Failed!");
                    }
                });
            }
        });

        lstvDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final WifiP2pDevice device = deviceArray[position];
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = device.deviceAddress;

                manager.connect(channel, config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(WifiP2P_Socket.this, "Connected to " + device.deviceName, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int reason) {
                        Toast.makeText(WifiP2P_Socket.this, "Not Connected", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        bSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = userDetails.getString("user_id", "-1");
                sendReceive.write(msg.getBytes());
                wifi.disableWifi();
                finish();
            }
        });
    }

    // </editor-fold>

    public WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {
            if (!peerList.getDeviceList().equals(peers)) {
                peers.clear();
                peers.addAll(peerList.getDeviceList());

                Log.d("Listening for Peers", String.valueOf(peerList.getDeviceList().size()));
                deviceNameArray = new String[peerList.getDeviceList().size()];
                deviceArray = new WifiP2pDevice[peerList.getDeviceList().size()];


                int index = 0;
                for (WifiP2pDevice device : peerList.getDeviceList()) {
                    deviceNameArray[index] = device.deviceName;
                    Log.d("Device", device.deviceName);
                    deviceArray[index] = device;
                    index++;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(WifiP2P_Socket.this, android.R.layout.simple_list_item_1, deviceNameArray);
                lstvDevices.setAdapter(adapter);

            }

            if (peers.size() == 0) {
                Toast.makeText(WifiP2P_Socket.this, "No devices found", Toast.LENGTH_SHORT).show();
            }


        }
    };

    public WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo info) {
            final InetAddress groupOwnerAddress = info.groupOwnerAddress;

            if (info.isGroupOwner && info.groupFormed) {
                tConnectionStatus.setText("Host");
                // Video No.: 008
                serverClass = new ServerClass();
                serverClass.start();
                // *************************
            } else if (info.groupFormed) {
                tConnectionStatus.setText("Client");
                // Video No.: 008
                clientClass = new ClientClass(groupOwnerAddress);
                clientClass.start();
                // *************************
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    // <editor-fold default="collapsed" desc="Sockets">

    // <editor-fold desc="Video No.: 006">
    public class ServerClass extends Thread {
        Socket socket;
        ServerSocket serverSocket;

        @Override
        public void run() {
            int port = 9750;
            try {
                serverSocket = new ServerSocket(port);
                socket = serverSocket.accept();
                // Video No.: 008
                sendReceive = new SendReceive(socket);
                sendReceive.start();
                // **************************
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void closeSocket() {
            try {
                socket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // </editor-fold>

    // <editor-fold desc="Video No.: 008">
    public class SendReceive extends Thread {
        private Socket socket;
        private InputStream inputStream;
        private OutputStream outputStream;

        public SendReceive(Socket skt) {
            socket = skt;
            try {
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;
            while (socket != null) {
                try {
                    bytes = inputStream.read(buffer);
                    if (bytes > 0) {
                        handler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void write(final byte[] bytes) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        outputStream.write(bytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }
    // </editor-fold>

    // <editor-fold desc="Video No.: 007">
    public class ClientClass extends Thread {
        Socket socket;
        String hostAddr;
        int port = 9750;

        public ClientClass(InetAddress hostAddress) { // Constructor
            hostAddr = hostAddress.getHostAddress();
            socket = new Socket();
        }

        @Override
        public void run() {
            try {
                socket.connect(new InetSocketAddress(hostAddr, port), 500);
                // Video No.: 008
                sendReceive = new SendReceive(socket);
                sendReceive.start();
                // **************************
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void closeSocket() {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // </editor-fold>


    // </editor-fold>

}