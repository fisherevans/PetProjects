package com.fisherevans.miblio_theca;

import com.fisherevans.miblio_theca.worker.AudioWorker;
import com.fisherevans.miblio_theca.worker.Worker;
import com.fisherevans.miblio_theca.media.MediaManager;
import com.fisherevans.miblio_theca.media.file.MediaFileWrapper;
import com.fisherevans.miblio_theca.util.FileUtil;
import com.fisherevans.miblio_theca.util.InputUtil;
import com.fisherevans.miblio_theca.util.StringUtil;

import java.io.File;
import java.util.*;

/**
 * Created by immortal on 5/10/2015.
 */
public class Miblio<T1 extends MediaFileWrapper> {
    private Worker _worker;
    private Map<String, Set<T1>> _fileMap;

    public static void main(String[] args) throws Exception {
        new Miblio(new AudioWorker()).run();
    }

    public Miblio(Worker worker) {
        _worker = worker;
    }

    public void run() {
        verifyRun();
        readFiles();
        copySingles();
        copyMultiples();
        cleanEmptyFolders(Config.getInstance().INPUT_DIR);
        System.out.println();
        System.out.println("Process finished.");
    }

    private void verifyRun() {
        System.out.println("Please confirm the following configuration.");
        System.out.println("This tool will automatically move/delete files inside them.");
        System.out.println("    Input Directory: " + Config.getInstance().INPUT_DIR.getAbsolutePath());
        System.out.println("   Output Directory: " + _worker.getOutputDirectory().getAbsolutePath());
        System.out.println("        File Format: " + _worker.getFormat());
        System.out.println("   Valid Extensions: " + StringUtil.join(_worker.getValidExtensions(), ", "));
        System.out.print("To continue, please enter [Y]: ");
        String input = InputUtil.getString();
        if(!input.equalsIgnoreCase("Y"))
            System.exit(0);
    }

    private void readFiles() {
        System.out.print("Scanning files...");
        try {
            _fileMap = MediaManager.readFiles(_worker.getMediaFileWrapperClass(),
                    _worker.getValidExtensions(), _worker.getOutputDirectory(), _worker.getFormat());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println(" Done.");
    }

    private void copySingles() {
        System.out.print("Moving the obvious around...");
        Iterator<Map.Entry<String, Set<T1>>> iterator = _fileMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Set<T1>> entry = iterator.next();
            if(entry.getValue().size() == 1) {
                MediaFileWrapper file = (MediaFileWrapper) entry.getValue().toArray()[0];
                if(!file.containWithin(_worker.getOutputDirectory())) {
                    moveFile(file, entry.getKey());
                }
                iterator.remove();
            }
        }
        System.out.println(" Done.");
    }

    private void copyMultiples() {
        if(_fileMap.size() == 0) {
            System.out.println("No files to organize.");
            return;
        }
        System.out.println("Please select which you'd like to keep:");
        List<String> keys = new ArrayList<>(_fileMap.keySet());
        Collections.sort(keys);
        for(String formattedOutput:keys) {
            System.out.println();
            System.out.println(formattedOutput);
            List<T1> files = new ArrayList<>(_fileMap.get(formattedOutput));
            Collections.sort(files);
            for(int i = 0;i < files.size();i++) {
                MediaFileWrapper file = files.get(i);
                boolean current = file.containWithin(_worker.getOutputDirectory());
                System.out.printf(" %2s%2s) File:    %s\n", current ? "**" : "  ", i+1, file.getFile().getAbsolutePath());
                System.out.printf("       Details: %s\n", file.getDetails());
                System.out.printf("       MD5:     %s\n", file.getMD5());
            }
            System.out.print("What's your pick: ");
            int input = 0;
            while(true) {
                input = InputUtil.getInt();
                if(input < 1 || input > files.size())
                    System.out.print("Not a valid file, try again: ");
                else
                    break;
            }
            for(int i = 0;i < files.size();i++) {
                if(input-1 == i)
                    continue;
                MediaFileWrapper file = files.get(i);
                file.getFile().delete();
            }
            MediaFileWrapper file = files.get(input-1);
            moveFile(file, formattedOutput);
        }
    }

    private void cleanEmptyFolders(File folder) {
        for(File file:folder.listFiles()) {
            if(file.exists() && file.canWrite() && file.isDirectory()) {
                cleanEmptyFolders(file);
                if(file.listFiles().length == 0)
                    file.delete();
            }
        }
    }

    private void moveFile(MediaFileWrapper fromWrapper, String formattedOutput) {
        File to = new File(_worker.getOutputDirectory().getAbsolutePath() + "\\" + formattedOutput + "." + FileUtil.getFileExtension(fromWrapper.getFile()));
        FileUtil.move(fromWrapper.getFile(), to);
    }
}
