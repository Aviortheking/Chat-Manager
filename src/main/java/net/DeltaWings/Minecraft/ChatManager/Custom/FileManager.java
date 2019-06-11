package net.DeltaWings.Minecraft.ChatManager.Custom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileManager {


	public static List<String> listFiles(String path) {
		String[] t = new File(path).list();
		if(t == null) return new ArrayList<>();
		else return Arrays.asList(t);
	}

	public static List<String> listFiles(File path) {
		String[] t = path.list();
		if(t == null) return new ArrayList<>();
		else return Arrays.asList(t);
	}

	public static void delete(File path) {
		path.delete();
	}

	public static void delete(String path) {
		File f = new File(path);
		f.delete();
	}

	public static void createFile(String path) {
		try {
			new File(path).createNewFile();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	public static void createFile(File path) {
		try {
			path.createNewFile();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	public static void createFolder(File path) {
		path.mkdirs();
	}

	public static void createFolder(String path) {
		new File(path).mkdirs();
	}

	public static void archive(String srcFolder, String destZipFile) {
		try {
			FileOutputStream f = new FileOutputStream(destZipFile);
			ZipOutputStream z = new ZipOutputStream(f);
			b(srcFolder, z);
			z.close();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private static void a(String p, String s, ZipOutputStream z) throws Exception {
		File f = new File(s);
		if (f.isDirectory()) b(s, z);
		else {
			byte[] b = new byte[1024];
			int l;
			FileInputStream i = new FileInputStream(s);
			z.putNextEntry(new ZipEntry(p + "/" + f.getName()));
			while ((l = i.read(b)) > 0) z.write(b, 0, l);
		}
	}

	private static void b(String s, ZipOutputStream z) throws Exception {
		File f = new File(s);
		String[] l = f.list();
		if(l != null) for (String n : l) a(f.getName(), s + File.separator + n, z);
	}
}