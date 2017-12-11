package com.libsimsync.network;

import com.libsimsync.config.FileEntry;
import com.libsimsync.config.FileInfo;

import javax.swing.event.PopupMenuListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class exampleForNikita implements PeerManagerHandler{
    PeerManager peerManager = new PeerManager();
    String root;
    HashMap<String,FileInfo> lastFileInfo = new HashMap<>();
    public exampleForNikita(String root){
        this.root = root;
        peerManager.setRootDirectory(root);
        peerManager.setPeerManagerHandler(this);//Теперь методы этого класса будут вызываться пир мэнэджером
    }
    //Тебе предстоит реализовать например изменение корневой дирректории извне.
    //Для этого есть сеттер у peerManager

    /**
     *
     * Этот метод вызывается, когда удаленный пир запросил у нас файл инфо.
     * Надо их ему послать. Для этого в пир-мэнеджере предусмотрен метод
     * sendFileInfo
     *
     * @param remotePeer пир, куда нам надо послать файлинфы
     */
    @Override
    public void FileInfoRequested(RemotePeer remotePeer) {
        try {
            FileInfoUpdate(new File(root), "");
        }catch(java.io.IOException e) {

        }
        LinkedList<FileInfo> toSend = new LinkedList<>(lastFileInfo.values());

        peerManager.sendFileInfo(toSend,remotePeer);

    }
    private void FileInfoUpdate(File folder, String path) throws java.io.IOException{
        //lastFileInfo.clear();
        for (FileInfo i : lastFileInfo.values()){
            i.isDeleted = true;
        }
            File[] folders = folder.listFiles();
            for (File entry : folders)
            {
                if (entry.isDirectory())
                {
                    FileInfoUpdate(entry, path + '/' + entry.getName());
                    continue;
                }
                System.err.println(path + '/' + entry.getName());
                if(lastFileInfo.containsKey(path + '/' + entry.getName()) ) {
                    lastFileInfo.get(path + '/' + entry.getName()).isDeleted = false;
                }else{
                    lastFileInfo.put(path + '/' + entry.getName(), new FileInfo(root + path + '/' + entry.getName(), path + '/' + entry.getName()));
                }
            }


    }
    /**
     *
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
    public void FileInfoArrived(LinkedList<FileInfo> fileInfos, RemotePeer remotePeer) { // Why LinkedList???

        try {
            FileInfoUpdate(new File(root),"");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.err.println("FileInfoArrived");
        FileInfo f;
       for(FileInfo i:fileInfos){
           i.origin = remotePeer;
           if(lastFileInfo.containsKey(i.relPath)) {
               f = lastFileInfo.get(i.relPath);
               System.err.println(f.lastModifiedDate - i.lastModifiedDate);
               if(i.isDeleted || f.lastModifiedDate < i.lastModifiedDate - 10000) {
                   f.origin = i.origin;
                   f.lastModifiedDate = i.lastModifiedDate;
                   f.isDeleted = i.isDeleted;
               }
           }else{
                lastFileInfo.put(i.relPath, i);
           }
       }
        System.err.println(lastFileInfo.size());
       for(FileInfo i : lastFileInfo.values()){
           try {
               if(i.origin != null) {
                   if(!i.isDeleted)peerManager.requestFile(i, null, remotePeer);
                   else peerManager.deleteFile(i,null);
               }


           }catch(java.io.IOException e ){

           }

       }

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
    public void sync() throws FileNotFoundException, java.io.IOException{
        FileInfoUpdate(new File(root),"");
        peerManager.requestFileInfo();
        //peerManager.requestFileInfo();                                                                 //пока что проэмиттируем запрос инфы
        //peerManager.requestFile(new FileEntry(Paths.get("/test.bmp"),null),null); //и запрос якобы нужного нам файла

    }

    /**
     *
     * Функция чисто для тестирования на одном компе,
     * в релизе это будет автоматически, скорее всего
     *
     */
    public void listen(){
        peerManager.listen();
    }

    /**
     * Пока что коннект делается извне. Вообще коннект
     * пир менеджера будешь вызывать ты, например в
     * обработчике какой-нибудь кнопички
     * @param host
     */
    public void connect(String host){
        peerManager.connect(host);
    }
}
