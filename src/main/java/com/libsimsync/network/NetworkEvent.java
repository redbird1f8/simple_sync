package com.libsimsync.network;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.UUID;

//newFileTransaction
//fileTransactionComplete
//fileTransactionError
//incomingObject

/**
 * Информация о файле
 */
class FileInfo{
    UUID ShareId;
    String  name;
}

/**
 * ВСЕ события
 */
abstract class NetworkEvent extends EventObject
{
    NetworkEvent(Object src){
        super(src);
    }

}

/**
 * Все события, возникающие во время передачи файлов
 */

class NetworkFileEvent extends NetworkEvent{
    FileInfo file;
    NetworkFileEvent(Object src,FileInfo file){
        super(src);
        this.file = file;
    }
    public FileInfo getFileInfo(){
        return file;
    }
}

/**
 * Внутреенее событие для передачи информации из RemotePeer в Peer
 */
class RemotePeerEvent extends NetworkEvent{
    boolean connected;
    RemotePeerEvent(RemotePeer src, boolean connected){
        super(src);
        this.connected = connected;
    }

    @Override
    public RemotePeer getSource() {
        return (RemotePeer)super.getSource();
    }
}
/**
 * Создатель событий
 */
class NetworkEventProducer{
    private ArrayList<NetworkEventListener> listeners = new ArrayList<>();

    public void addListener(NetworkEventListener listener){
        listeners.add(listener);
    }
    public void actionPerformedFire(NetworkEvent e){
        for(int i = 0; i < listeners.size(); i++){
            listeners.get(i).actionPerformed(e);
        }
    }

}

/**
 * Слушатель событий
 */
interface NetworkEventListener {
    public void actionPerformed(NetworkEvent e);
}