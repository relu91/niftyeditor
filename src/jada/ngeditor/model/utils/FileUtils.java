/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 *
 * @author cris
 */
public class FileUtils {
    
    public static void copyFile(File source, File dest)
		throws IOException {
	FileChannel inputChannel = null;
	FileChannel outputChannel = null;
	try {
		inputChannel = new FileInputStream(source).getChannel();
		outputChannel = new FileOutputStream(dest).getChannel();
		outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
	} finally {
		inputChannel.close();
		outputChannel.close();
	}
}
}
