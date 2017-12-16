package com.libsimsync.network;

import com.libsimsync.config.FileEntry;
import com.libsimsync.config.FileInfo;

import javax.swing.event.PopupMenuListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

public class Synchronizer implements PeerManagerHandler {
    private PeerManager peerManager = new PeerManager();

    public void setRoot(String root) {
        this.root = root;
        fileManger.setRoot(root);
    }

    private String root;
    private HashMap<String, FileInfo> lastFileInfo = new HashMap<>();
    private FileManager fileManger;

    public Synchronizer(String root) {
        this.root = root;
        fileManger = new FileManager(root);
        peerManager.setPathRouter(fileManger);
        peerManager.setPeerManagerHandler(this);//Теперь методы этого класса будут вызываться пир мэнэджером
    }

    public void LoadFileInfo(String path) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
            lastFileInfo = (HashMap<String, FileInfo>) ois.readObject();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void SaveFileInfo(String path) {

        for (FileInfo i : lastFileInfo.values()) {
            i.origin = null;
        }
        try {
            ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream(path));
            ois.writeObject(lastFileInfo);
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Тебе предстоит реализовать например изменение корневой дирректории извне.
    //Для этого есть сеттер у peerManager

    /**
     * Этот метод вызывается, когда удаленный пир запросил у нас файл инфо.
     * Надо их ему послать. Для этого в пир-мэнеджере предусмотрен метод
     * sendFileInfo
     *
     * @param remotePeer пир, куда нам надо послать файлинфы
     */
    @Override

    public void FileInfoRequested(RemotePeer remotePeer) {
        System.err.println(" FileInfoArrived");
         try {
            StartInfoUpdate(new File(root), "", null);
        } catch (java.io.IOException e) {

        }
        LinkedList<FileInfo> toSend = new LinkedList<>(lastFileInfo.values());
        for(FileInfo i:toSend){
            i.origin = null;
        }
        peerManager.sendFileInfo(toSend, remotePeer);


    }

    private void StartInfoUpdate(File folder, String path, UUID shareID) throws IOException {
        for (FileInfo i : lastFileInfo.values()) {
            i.isDeleted = true;
        }

            FileInfoUpdate(folder, path, shareID);


    }

    private void FileInfoUpdate(File folder, String path, UUID shareID) throws java.io.IOException {
        //lastFileInfo.clear();

        File[] folders = folder.listFiles();


        for (File entry : folders) {
            if (entry.isDirectory()) {
                FileInfoUpdate(entry, path + '/' + entry.getName(), shareID);
                continue;
            }
            //System.err.println(path + '/' + entry.getName());
            if (lastFileInfo.containsKey(path + '/' + entry.getName())) {
                lastFileInfo.get(path + '/' + entry.getName()).isDeleted = false;
                BasicFileAttributes attr = Files.readAttributes(Paths.get(entry.getPath()),BasicFileAttributes.class);
                lastFileInfo.get(path + '/' + entry.getName()).lastModifiedDate = attr.lastModifiedTime().toMillis();
            } else {
                lastFileInfo.put(path + '/' + entry.getName(), fileManger.getFileInfo(path + '/' + entry.getName(), shareID));
            }
        }


    }

    /**
     * Этот метод вызывается, когда нам прислали массив файлИнфо.
     * В нем надо сравнить полученнве файл инфо с тем, что есть
     * на компе. Вместе с листом файл инфо приходит RemotePeer,
     * откуда пришло. Потом, в будующем, наверное надо сделать
     * так, чтоб каждому девайсу соответствовал ID но это долго
     * Пока - что можешь в FileEntry сделать поле RemotePeer,
     * которое изначально null - это означает, что скачивать
     * не надо ни от куда. Потом в каком - то списке ФайлЭнтри,
     * или где ты там их будешь хранить, ищешь нужный ФайлЭнтри,
     * (Если будешь по пути искать, то убедись, что слэши там
     * в одну сторону и все такое) Далее как-то сравниваешь,
     * если новоприбывшее инфо лучше заменяешь это поле на
     * новоприбывший ремоут пир.
     *
     * @param fileInfos
     * @param remotePeer
     */
    @Override
    public void FileInfoArrived(LinkedList<FileInfo> fileInfos, RemotePeer remotePeer) {
        System.err.println(" FileInfoArrived");
        try {
            StartInfoUpdate(new File(root), "", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastFileInfo = FileInfoMerge(lastFileInfo,fileInfos,remotePeer);
        /*try {
            StartInfoUpdate(new File(root), "", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileInfo f;
        for (FileInfo i : fileInfos) {
            i.origin = remotePeer;
            if (lastFileInfo.containsKey(i.relPath)) {
                f = lastFileInfo.get(i.relPath);
                if (i.isDeleted || f.isDeleted || f.lastModifiedDate < i.lastModifiedDate) {
                    f.origin = i.origin;
                    f.lastModifiedDate = i.lastModifiedDate;
                    f.isDeleted = i.isDeleted;
                }
            } else {
                lastFileInfo.put(i.relPath, i);
            }
        }*/
        for (FileInfo i : lastFileInfo.values()) {
            try {
                if (i.origin != null) {
                    if (!i.isDeleted) peerManager.requestFile(i, null, remotePeer);
                    else fileManger.deleteFile(i, null);
                }


            } catch (java.io.IOException e) {

            }


        }


    }
    public HashMap<String,FileInfo> FileInfoMerge(HashMap<String,FileInfo> currentState, LinkedList<FileInfo> newInfo, RemotePeer origin){
        HashMap<String,FileInfo> ret = new HashMap<>(currentState);

        FileInfo f;
        for (FileInfo i : newInfo) {
            i.origin = origin;
            if (ret.containsKey(i.relPath)) {
                f = ret.get(i.relPath);
                if (i.isDeleted || f.isDeleted || f.lastModifiedDate < i.lastModifiedDate) {
                    f.origin = i.origin;
                    f.lastModifiedDate = i.lastModifiedDate;
                    f.isDeleted = i.isDeleted;
                }
            } else {
                ret.put(i.relPath, i);
            }
        }
        return ret;
    }

    /**
     * Возможно, звучит слишком пафосно, но все таки, походу уже
     * в этом классе можно её писсать. Её самую. Нашу прекрасную
     * функцию синк, которая:
     * 1.Запрашивает файл инфо.
     * 2.ЖДЕТ какое-то время пока придут файлИнфо(Видимо какой-то таймер нужен, погугли там)
     * 3.когда таймер дошел, проходится по списку файлоав(неважно где и как он хранится) и запрашивает реквест,
     * для тех, которые надо скачать. Все.
     */
    public void sync() throws FileNotFoundException, java.io.IOException {

        StartInfoUpdate(new File(root), "", null);
        peerManager.requestFileInfo();
        //peerManager.requestFileInfo();                                                                 //пока что проэмиттируем запрос инфы
        //peerManager.requestFile(new FileEntry(Paths.get("/test.bmp"),null),null); //и запрос якобы нужного нам файла

    }

    /**
     * Функция чисто для тестирования на одном компе,
     * в релизе это будет автоматически, скорее всего
     */
    public void listen() {
        peerManager.listen();
    }

    /**
     * Пока что коннект делается извне. Вообще коннект
     * пир менеджера будешь вызывать ты, например в
     * обработчике какой-нибудь кнопички
     *
     * @param host
     */
    public void connect(String host) {
        peerManager.connect(host);
    }


}
