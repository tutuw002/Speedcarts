package com.gmail.zant95.Speedcarts;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.logging.Logger;

import com.gmail.zant95.Speedcarts.Storage.MemStorage;

public class UpdateChecker {
	public static void check() {
		Logger logger = MemStorage.plugin.getLogger();
		if (MemStorage.plugin.getConfig().getBoolean("speedcarts.check-for-updates")) {
			logger.info("Checking for updates...");
			try {
				String currentVersion = MemStorage.plugin.getDescription().getVersion();
				String lastVersion;
				URLConnection connection = new URL("http://version.znt.se/speedcarts.php").openConnection();
				connection.setRequestProperty("User-Agent", "Speedcarts/" + currentVersion);
				connection.connect();
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
				while ((lastVersion = in.readLine()) != null) {
					if ("error".equals(lastVersion)) {
						logger.warning("\u001B[33m" + "Speedcarts could not check for updates [znt.se respond with 'error']." + "\u001B[m");
					} else if (currentVersion.equals(lastVersion)) {
						logger.info("Speedcarts is up to date.");
					} else {
						logger.warning("\u001B[33m" + "Speedcarts is not up to date. Check the latest version (" + lastVersion + ") on BukkitDev!" + "\u001B[m");
					}
				}
				in.close();
			} catch (Exception e) {
				logger.warning("\u001B[33m" + "Speedcarts could not check for updates [" + e + "]." + "\u001B[m");
			}
		}
	}
}