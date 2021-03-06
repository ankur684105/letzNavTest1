package com.letzNav.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.apache.commons.io.FileUtils;

public class letzNavInstaller extends letzNavPropertiesReader {

	public String file = System.getProperty("user.dir") + "//ExtensionResouces//CRX";
	public String directory = System.getProperty("user.dir") + "//ExtensionResouces//";
	File extensionResources = new File(directory);
	public String filePlayer = System.getProperty("user.dir") + "//ExtensionResouces//letznav_player.crx";
	public String editorUrl = letzNavPropertiesReader.getValue("editorurl");
	public String playerUrl = letzNavPropertiesReader.getValue("playerurl");
	public String downloadUrl;
	public String filePath;

	public void downloadExtension(String extensionName) throws IOException {
		if (extensionName.equalsIgnoreCase("player")) {
			downloadUrl = playerUrl;
			filePath = file.replace("CRX", "letznav_player.crx");
		} else if (extensionName.equalsIgnoreCase("editor")) {
			downloadUrl = editorUrl;
			filePath = file.replace("CRX", "letznav_editor.crx");
		}
		
			URL url = new URL(downloadUrl);
			ReadableByteChannel rbc = Channels.newChannel(url.openStream());
			FileOutputStream fos = new FileOutputStream(filePath);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			rbc.close();
		
	}

	public void clearExtensionResources() throws IOException {
		FileUtils.cleanDirectory(extensionResources);
	}

}
