package cl.bice.gestionactas.test;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Date: 23-06-16
 * Created by marcelolopez
 * Neoptera SPA - Chile - Copyright 2016
 * Project: gestionActas
 */
public class DownloadTest {

    @Test
    public void uploadFile() throws Exception {
        File file = new File(".");
        File files[] = file.listFiles();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);


        for(int i=0;i<files.length;i++){
            if (files[i].isFile()) {
                System.out.println("Adding: " + files[i].getName());
                byte[] p = FileUtils.readFileToByteArray(files[i]);
                ZipEntry entry = new ZipEntry(files[i].getName());
                entry.setSize(p.length);
                zos.putNextEntry(entry);
                zos.write(p);
                zos.closeEntry();
            }
        }
        zos.close();
        byte[] p1 = baos.toByteArray();
//        FileUtils.writeByteArrayToFile(new File("/Users/marcelolopez/projects/birchman/bice/trunk/gestionActas/gestionActas/test.zip"), p1);
    }

}
