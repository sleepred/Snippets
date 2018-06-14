package snippets;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Pattern;

public class FileUtil {
	private static final String SUCCESS = "SUCCESS";
	private static final String FAIL = "FAIL";
	
	public static String fileWrite(String filePath, String text){
		
		String result = FAIL;
		
		FileOutputStream fos = null;
		try{
			File file = new File(filePath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			
			fos = new FileOutputStream(file);
			
			byte[] bt = text.getBytes();
			fos.write(bt);
			fos.flush();
			
			result = SUCCESS;
		}
		catch(Exception e){
			result = FAIL;
		}
		finally{
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					result = FAIL;
				}
			}
		}
		
		return result;
	}
	
	
	public static String fileWrite(String filePath, String fileName, String text){
		return fileWrite(makeFilePath(filePath, fileName), text);
	}
	
	public static StringBuffer readString(String filePath, String fileName){
		StringBuffer data = new StringBuffer();
		
		BufferedReader in = null;
        try{
        	in = new BufferedReader(new FileReader(filePath + "/" + fileName));
        	int c = 0;
            while ((c = in.read()) != -1) {
                data.append((char)c);
            }
            
        }catch(Exception e){
        	data = null;
        }finally{
        	if(in != null){
        		try {
					in.close();
				} catch (IOException e) {
					data = null;
				}
        	}
        }
        
        return data;
	}
	
	public static File[] getDirList(String dirPath){
		return getDirList(dirPath, "FILE");
	}
	public static File[] getDirList(String dirPath, String type){
		File[] fileList = null;
		
		File directory = new File(dirPath);
		if (!directory.exists()) {
			return null;
		}

		FileFilter FileFilter = null;
		if(type.equals("ALL")){
			FileFilter = new DefaultFileFilter();
		}
		else{
			FileFilter = new ContentFileFilter();
		}
		fileList = directory.listFiles(FileFilter);
		
		return fileList;
	}
	
	public static String deleteFile(String filePath){
		return deleteFile(filePath, null);
	}
	public static String deleteFile(String filePath, String fileName){
		File file = new File(filePath + (fileName == null? "": "/" + fileName));
		if(!file.exists()){
			return FAIL;
		}
		if(file.isDirectory()){
			try {
				File[] files = getDirList(file.getCanonicalPath(), "ALL");
				for(File f : files){
					if(deleteFile(f.getCanonicalPath(), null).equals(FAIL)){
						return FAIL;
					}
				}
			} catch (IOException e) {
				return FAIL;
			}
		}
		if(file.delete())
		{
			return SUCCESS;
		}
		else{
			return FAIL;
		}
	}
	
	public static class ContentFileFilter implements FileFilter {
        public boolean accept(File file){
            return file.isFile() || !file.getName().startsWith(".");
        }
    }
	
	public static class DefaultFileFilter implements FileFilter {
        public boolean accept(File file){
            return file.isFile() || !file.getName().startsWith(".");
        }
    }

	public static InputStream close(InputStream in) {
		try {
			if (in != null) {
				in.close();
			}
		} catch (Throwable e) {
			// Ignore e
		}
		return null;
	}

	public static OutputStream close(OutputStream out) {
		try {
			if (out != null) {
				out.close();
			}
		} catch (Throwable e) {
			// Ignore e
		}
		return null;
	}

	public static Reader close(Reader in) {
		try {
			if (in != null) {
				in.close();
			}
		} catch (Throwable e) {
			// Ignore e
		}
		return null;
	}

	public static Writer close(Writer out) {
		try {
			if (out != null) {
				out.close();
			}
		} catch (Throwable e) {
			// Ignore e
		}
		return null;
	}

	public static byte[] readAll(InputStream fin) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buff = new byte[4096];
		int n = fin.read(buff);
		while (n >= 0) {
			out.write(buff, 0, n);
			n = fin.read(buff);
		}
		return out.toByteArray();
	}

	public static RandomAccessFile close(RandomAccessFile raf) {
		try {
			if (raf != null) {
				raf.close();
			}
		} catch (Throwable e) {
			// Ignore e
		}
		return null;
	}

	public static Socket close(Socket socket) {
		try {
			if (socket != null) {
				socket.close();
			}
		} catch (Throwable e) {
			// Ignore e
		}
		return null;
	}

	public static ServerSocket close(ServerSocket socket) {
		try {
			if (socket != null) {
				socket.close();
			}
		} catch (Throwable e) {
			// Ignore e
		}
		return null;
	}

	public static void save(String file, byte[] b) {
		save(new File(file), b);

	}

	public static void save(File file, byte[] byteArray) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(byteArray);
		} catch (Exception e) {
			// Ignore e
		}
		close(out);
	}

	public static byte[] readAll(File file) {
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			return readAll(in);
		} catch (Exception e) {
			// Ignore e
		} finally {
			close(in);
		}
		return null;
	}

	public static void copy(File src, File dest) {
		try {
			copy(src, dest, true);
		} catch (Exception e) {
			// Ignore e
		}
	}

	public static boolean copy(File src, File dst, boolean overwrite) throws IOException, FileNotFoundException {
		File dest = dst;

		if (!src.isFile() || !src.exists())
			return false;

		if (dest.exists()) {
			if (dest.isDirectory()) // Directory이면 src파일명을 사용한다.
				dest = new File(dest, src.getName());
			else if (dest.isFile()) {
				if (!overwrite)
					throw new IOException(dest.getAbsolutePath() + "' already exists!");
			} else
				throw new IOException("Invalid  file '" + dest.getAbsolutePath() + "'");
		}

		File destDir = dest.getParentFile();
		if (!destDir.exists())
			if (!destDir.mkdirs())
				throw new IOException("Failed to create " + destDir.getAbsolutePath());
		long fileSize = src.length();
		if (fileSize > 20 * 1024 * 1024) {
			FileInputStream in = null;
			FileOutputStream out = null;
			try {
				in = new FileInputStream(src);
				out = new FileOutputStream(dest);
				int done = 0;
				int buffLen = 32768;
				byte buf[] = new byte[buffLen];
				while ((done = in.read(buf, 0, buffLen)) >= 0) {
					if (done == 0)
						Thread.yield();
					else
						out.write(buf, 0, done);
				}
			} finally {
				close(in);
				close(out);
			}
		} else {
			FileInputStream in = null;
			FileOutputStream out = null;
			FileChannel fin = null;
			FileChannel fout = null;
			try {
				in = new FileInputStream(src);
				out = new FileOutputStream(dest);
				fin = in.getChannel();
				fout = out.getChannel();

				long position = 0;
				long done = 0;
				long count = Math.min(65536, fileSize);
				do {
					done = fin.transferTo(position, count, fout);
					position += done;
					fileSize -= done;
				} while (fileSize > 0);
			} finally {
				close(fin);
				close(fout);
				close(in);
				close(out);
			}
		}
		return true;
	}

	public static FileChannel close(FileChannel fc) {
		if (fc != null) {
			try {
				fc.close();
			} catch (IOException e) {
				// Ignore e
			}
		}
		return null;
	}
	
	public static String makeFilePath(String path, String path2){
		String path1 = path;
		path1 = path1.trim();
		if(path1.lastIndexOf('\\') == path1.length() || path1.lastIndexOf('/') == path1.length()){
			return path1 + path2;
		}
		else{
			if(System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
				return path1 + "\\" + path2;
			} else {
				return path1 + "/" + path2;	
			}
		}
	}

	public static String getJarLocation(Class class1) {
		try {
			String path = "" + class1.getResource("/" + class1.getName().replace('.', '/') + ".class");
			if (path.indexOf('!') < 0)
				return null;
			path = path.substring("jar:file:".length(), path.indexOf('!'));
			path = path.substring(0, path.lastIndexOf('/'));
			return new File(path).getAbsolutePath();
		} catch (Exception e) {
			// Ignore e
		}
		return null;
	}


	public static String getFileName(String filePathName){
		File file = new File(filePathName);
		return file.getName();
	}

	public static String load(File file, String enc) {
		if (file == null || !file.canRead())
			return null;
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			return new String(readAll(in), enc);
		} catch (IOException e) {
			// Ignore e
		} finally {
			close(in);
		}
		return null;
	}

	public static void append(String file, String line) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileWriter(file, true));
			out.println(line);
		} catch (Exception e) {
			// Ignore e
		}
		close(out);
	}

	public static boolean mkdirs(String path) {
		File f = new File(path);
		if (!f.exists())
			return f.mkdirs();
		else
			return true;
	}

	public static Properties readProperties(File f) {
		BufferedInputStream reader = null;
		Properties p = new Properties();
		try {
			reader = new BufferedInputStream(new FileInputStream(f));
			p.load(reader);
		} catch (Exception e) {
			// Ignore e
		} finally {
			close(reader);
		}
		return p;
	}

	public static void writeProperties(File f, Properties p) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(f);
			p.list(pw);
		} catch (Exception e) {
			// Ignore e
		} finally {
			close(pw);
		}

	}
	
	/**
	 * path를 포함한 하위의 모든 파일의 권한을 775로 변경한다.
	 * @param path 변경할 경로
	 * @deprecated
	 */
	public static void chmod775(String path){
		// check path traversal
		checkPathTraversal(path);
		
		chmod775(new File(path));
	}

	/**
     * 파일경로가 상대경로를 지정하여 보안상에 문제가 있는지를 판단한다.
     * @param path 파일경로
     */
    public static void checkPathTraversal(String path){
    	if(isRelativePath(path)){
    		
    	}
    }
    
    /**
     * 해당 파일경로가 상대경로인지 확인한다.
     * @param path 파일경로
     * @return 상대경로여부
     */
    private static boolean isRelativePath(String path){
    	File file = new File(path);
    	try {
			if(file.getAbsolutePath().equals(file.getCanonicalPath())){
				return false;
			}
		} catch (IOException e) {
			
		}
    	
		return true;
    }
    
    /**
	 * file을 포함한 하위의 모든 파일의 권한을 775로 변경한다.
	 * @param file 변경할 파일객체
	 * @deprecated
	 */
	public static void chmod775(File file){
		if(!file.exists()){
			return;
		}
		
		if(file.isDirectory()){
			for(File child : file.listFiles()){
				chmod775(child);
			}
		}
		file.setReadable(true, false);
		file.setWritable(true, true);
		file.setExecutable(true, false);
	}
	
	/**
	 * path를 포함한 하위의 모든 파일의 권한을 755로 변경한다.
	 * @param path 변경할 경로
	 */
	public static void chmod755(String path){
		// check path traversal
		checkPathTraversal(path);
		
		chmod755(new File(path));
	}

	/**
	 * file을 포함한 하위의 모든 파일의 권한을 755로 변경한다.
	 * @param file 변경할 파일객체
	 */
	public static void chmod755(File file){
		if(!file.exists()){
			return;
		}
		
		if(file.isDirectory()){
			for(File child : file.listFiles()){
				chmod755(child);
			}
		}
		
		file.setReadable(true, false);
		file.setWritable(false, false);
		file.setWritable(true, true);
		file.setExecutable(true, false);
	}
	
	/**
	 * @param filePath
	 * @return
	 */
	public static String loadText(String filePath){
		File file = new File(filePath);
		InputStream fin = null;
		try {
			fin = new FileInputStream(file);
			byte[] buff = FileUtil.readAll(fin);
			return new String(buff);
		} catch (Exception e) {
			// Ignore e
		} finally {
			FileUtil.close(fin);
		}
		return null;
	}
	
	/**
	 * @param filePath
	 * @param text
	 * @return
	 */
	public static boolean saveText(String filePath, String text) {
		File file = new File(filePath);
		OutputStream out = null;
		try {
			File parentDir = file.getParentFile();
			parentDir.mkdirs();
			out = new FileOutputStream(file);
			out.write(text.getBytes());
			return true;
		} catch (Exception e) {
			// Ignore e
		} finally {
			FileUtil.close(out);
		}
		return false;
	}
	 /**
     * shell에 있는 변수의 값을 가져온다.
     * @param shellFilePath shell파일 경로
     * @param name 변수명
     * @return 변수값
     */
	public static String getShellVariable(String shellFilePath, String name){
		checkPathTraversal(shellFilePath);
		
		String variableString = getShellVariableString(shellFilePath, name);
		String nameAndValueArray[] = variableString.split("=");
		
		if(nameAndValueArray.length != 2){
			return null;
		}
		
		String curValue = nameAndValueArray[1].trim();
		
		return curValue;
	}
	
	/**
	 * shell에서 변수가 있는 line의 name=value의 스트링을 가져온다.
	 * @param shellFilePath shell파일경로
	 * @param name 변수명
	 * @return line스트링
	 */
	private static String getShellVariableString(String shellFilePath, String name){
		BufferedReader br = null;
		String variableString = null;
		
		checkPathTraversal(shellFilePath);
		
		String declare = "set";
		if(shellFilePath.endsWith(".sh")){
			declare = "export";
		}
		
		try{
			br = new BufferedReader(new FileReader(shellFilePath));
			
			do{
				String line = br.readLine();
				if(line == null){
					break;
				}
				
				if(line.startsWith(declare)){
					String nameAndValue = line.substring(declare.length(), line.length());
					//윈도우일경우, /a로 계산하는 변수셋팅 처리
					if(shellFilePath.endsWith(".bat")){
						nameAndValue = nameAndValue.trim();
						if(nameAndValue.startsWith("/a")) {
							nameAndValue=nameAndValue.replace("/a", "").trim();
						}
						
					}
					nameAndValue = nameAndValue.trim();
					if(nameAndValue.startsWith(name)){
						variableString = nameAndValue;
						break;
					}
				}
			}
			while(true);
		}
		catch(Throwable e){
			
		}
		finally{
			close(br);
		}
		
		return variableString;
	}
	
	


    /**
     * 파일사이즈 1K
     */
    private final static long BUFFER_SIZE = 1024L;

    private final static String NEW_LINE = System.getProperty("line.separator");

    /**
     * 도스 파일시스템의 seperator(\)를 Java Style (/)로 변경하기 위해 사용되는 정규식 패턴
     */
    public static final Pattern DOS_SEPERATOR = Pattern.compile("\\\\");

    /**
     * 파일 시스템의 Full Path에서 마지막이 /로 끝나는지를 검사하기 위해 사용되는 정규식 패턴
     */
    public static final Pattern LAST_SEPERATOR = Pattern.compile("/$");

    /**
     * 파일구분자
     */
    static final char FILE_SEPARATOR = File.separatorChar;

    /**
     * 최대 문자길이
     */
    static final int MAX_STR_LEN = 1024;

    /**
     * File 배열의 총 파일 size 를 리턴한다.
     *
     * @param files File 배열
     * @return File 배열에 담긴 파일들의 총 size
     */
    public static long getFileSize(File[] files) {
        long size = 0;

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.exists()) {
                size += file.length();
            }
        }
        return size;
    }

    /**
     * 확장자를 제외한 파일명을 리턴한다.
     *
     * @param filename 파일명
     * @return 확장자를 제외한 파일명
     */
    public static String getFileNameWithoutExtension(String filename) {
        int index = filename.lastIndexOf(".");
        String name = index == -1 ? filename : filename.substring(0, index);
        return name;
    }

    /**
     * 확장자를 제외한 파일경로를 리턴한다.
     *
     * @param filePath 파일경로
     * @param extension 확장자
     * @return 확장자를 제외한 파일경로
     */
    public static String getFilePathWithoutExtension(String filePath, String extension) {
        int index = filePath.indexOf("." + extension);
        String name = index == -1 ? filePath : filePath.substring(0, index);
        return name;
    }

    /**
     * 주어진 파일의 fullpath중 filname part를 제외한 path 부분만 분리하여 리턴한다.<br>
     * (new File(fullpath)).getParent()와 동일하나 File 객체를 사용하지 않고 문자열 패턴만으로 분석한다.<br>
     * 만약 fullpath에 / 혹은 \가 존재하지 않는 경우라면 "./" 을 리턴할 것이다.<br>
     * 만약 \이 fullpath에 존재한다면 모두 /로 변경할 것이다.
     *
     * @param fullpath Path와 filename으로 이루어진 파일의 fullpath
     * @return fullpath중 path
     */
    public static String getFilePath(String fullpath) {
        String fullFilePath = fullpath;
        if (null == fullFilePath) return null;
        fullFilePath = DOS_SEPERATOR.matcher(fullFilePath).replaceAll("/");
        int pos = fullFilePath.lastIndexOf("/");
        if (pos > -1) {
            return fullFilePath.substring(0, pos + 1);
        } else {
            return "./";
        }
    }

    /**
     * 주어진 파일의 fullpath의 맨 마지막에 /가 붙어 있는지를 검사하고 없는경우 /를 붙여서 리턴한다.<br>
     * 만약 \이 fullpath에 존재한다면 모두 /로 변경될 것이다.
     *
     * @param fullpath Path와 filename으로 이루어진 파일의 fullpath
     * @return fullpath의 맨 마지막에 /가 붙어 있는 fullpath
     */
    public static String getCompleteLeadingSeperator(String fullpath) {
        if (fullpath == null) return null;
        String completeFullPath = DOS_SEPERATOR.matcher(fullpath).replaceAll("/");
        		
        if (!completeFullPath.endsWith(File.separator) && !completeFullPath.endsWith("/")) { 
        	return completeFullPath + "/";
        } else {
        	return completeFullPath;
        }
    }

    /**
     * 주어진 파일의 fullpath의 맨 마지막에 /가 붙어 있는지를 검사하고 있는 경우 맨 마지막의 /를 제거하여 리턴한다.<br>
     * 만약 \이 fullpath에 존재한다면 모두 /로 변경될 것이다.
     *
     * @param fileName Path와 filename으로 이루어진 파일의 fullpath
     * @return fullpath의 맨 마지막에 /가 제거된 fullpath
     */
    public static String getRemoveLeadingSeperator(String fileName) {
        String removeFileName = fileName;
        if (null == removeFileName) return null;
        removeFileName = DOS_SEPERATOR.matcher(removeFileName).replaceAll("/");
        removeFileName = LAST_SEPERATOR.matcher(removeFileName).replaceAll("");
        return removeFileName;
    }

    /**
     * 주어진 filesize를 크기에 따라 byte, KB, MB, GB, TB 형태의 읽기 좋은 문자열로 변경하여 리턴한다.<br>
     * 변경 시 <code>"#,###.00 "</code> 포맷을 사용하며, 변경되는 형태는 아래와 같다:
     * <ul>
     * <li>0 이상 ~ 1024 미만 : #,###.00 byte</li>
     * <li>1024 이상 ~ 1048576 미만 : #,###.00 KB</li>
     * <li>1048576 이상 ~ 1073741824 미만 : #,###.00 MB</li>
     * <li>1073741824 이상 ~ 1099511627776L 미만 : #,###.00 GB</li>
     * <li>1099511627776L 이상 ~ : #,###.00 TB</li>
     * </ul>
     *
     * @param filesize 변경할 filesize.
     * @return 변경된 문자열.
     * @see #converterSizeFormat(long, String)
     */
    public static String converterSizeFormat(long filesize) {
        return converterSizeFormat(filesize, "#,###.00 ");
    }

    /**
     * 주어진 filesize를 크기에 따라 byte, KB, MB, GB, TB 형태의 읽기 좋은 문자열로 변경하여 리턴한다.
     *
     * @param filesize 변경할 filesize.
     * @param format 변경시 사용할 <code>DecimalFormat</code>.
     * @return 변경된 문자열.
     * @see #converterSizeFormat(long)
     * @see DecimalFormat
     */
    public static String converterSizeFormat(long filesize, String format) {
        double size = (double)filesize;
        String tail = "byte";

        if (1048576 > filesize && filesize >= 1024) {
            size = size / 1024;
            tail = "KB";
        } else if (1073741824 > filesize && filesize >= 1048576) {
            size = size / 1048576;
            tail = "MB";
        } else if (1099511627776L > filesize && filesize >= 1073741824) {
            size = size / 1073741824;
            tail = "GB";
        } else if (filesize >= 1099511627776L) {
            size = size / 1099511627776L;
            tail = "TB";
        }

        return new DecimalFormat(format).format(size) + tail;
    }

    /**
     * 두 파일의 바이너리값을 비교한다.
     *
     * @param source 파일명
     * @param target 파일명
     * @return boolean 일치 여부
     * @throws FileNotFoundException 해당 파일이 존재하지 않을 때 발생
     */
    public static boolean compareBinary(File source, File target) throws FileNotFoundException {
        boolean result = true;
        if (source.length() != target.length()) { return false; }
        InputStream in1 = new BufferedInputStream(new FileInputStream(source));
        InputStream in2 = new BufferedInputStream(new FileInputStream(target));
        try {
            byte[] buffer1 = new byte[(int)BUFFER_SIZE * 2];
            byte[] buffer2 = new byte[(int)BUFFER_SIZE * 2];
            int read1 = -1;
            int read2 = -1;
            while ((read1 = in1.read(buffer1)) >= 0 && (read2 = in2.read(buffer2)) >= 0) {
                if (read1 != read2 || !Arrays.equals(buffer1, buffer2)) {
                    result = false;
                    break;
                }
            }
        } catch (IOException e) {
            return false;
        } finally {
            try {
                in1.close();
                in2.close();
            } catch (IOException ex) {
            	
            }
        }
        return result;
    }

    /**
     * 빈 파일 여부를 체크한다.
     *
     * @param file 파일명
     * @return 빈 파일 여부
     */
    public static boolean isEmpty(File file) {
        if (file.isDirectory()) {
            String[] files = file.list();
            return files == null || files.length == 0;
        } else {
            return file.length() == 0;
        }
    }

    /**
     * 빈 폴더 여부를 체크한다.
     *
     * @param file 파일명
     * @return 빈 폴더 여부
     */
    public static boolean isEmptyDirectory(File file) {
        if (!file.isDirectory()) return false;
        String[] files = file.list();
        return files == null || files.length == 0;
    }

    /**
     * 파일의 내용을 String으로 리턴한다.
     *
     * @param fileName 파일명
     * @return 파일 내용
     */
    public static String readFile(String fileName) {
        String retStr = "";
        File file = new File(fileName);
        FileReader freader = null;
        BufferedReader breader = null;
        try {
            freader = new FileReader(file);
            breader = new BufferedReader(freader);

            StringBuilder buff = new StringBuilder();
            String line = breader.readLine();
            while (line != null) {
                buff.append(line + NEW_LINE);
                line = breader.readLine();
            }
            retStr = buff.toString();
        } catch (Exception e1) {
        	
        } finally {
            if (breader != null) {
                try {
                    breader.close();
                } catch (IOException e) {
                	
                }
            }
            if (freader != null) {
                try {
                    freader.close();
                } catch (IOException e) {
                	
                }
            }
        }
        return retStr;
    }

    /**
     * 파일의 내용을 String으로 리턴한다.
     *
     * @param fileName 파일명
     * @param charsetName charset명
     * @return 파일 내용
     */
    public static String readFile(String fileName, String charsetName) {
        String retStr = "";
        File file = new File(fileName);
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader breader = null;

        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis, charsetName);
            breader = new BufferedReader(isr);

            StringBuilder buff = new StringBuilder();
            String line = breader.readLine();
            while (line != null) {
                buff.append(line + NEW_LINE);
                line = breader.readLine();
            }
            retStr = buff.toString();
        } catch (Exception e1) {
        	
        } finally {
            if (breader != null) {
                try {
                    breader.close();
                } catch (IOException e) {
                	
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                	
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                	
                }
            }
        }
        return retStr;
    }

    /**
     * 파일의 내용을 저장한다.
     *
     * @param fileName 파일명
     * @param content 파일의 내용
     */
    public static void saveFile(String fileName, String content) {
        File file = new File(fileName);
        FileWriter fwriter = null;
        BufferedWriter bwriter = null;
        try {
            fwriter = new FileWriter(file);
            bwriter = new BufferedWriter(fwriter);
            bwriter.write(content);
        } catch (Exception e1) {
        	
        } finally {
            if (bwriter != null) {
                try {
                    bwriter.close();
                } catch (IOException e) {
                	
                }
            }
            if (fwriter != null) {
                try {
                    fwriter.close();
                } catch (IOException e) {
                	
                }
            }
        }
    }

    /**
     * rootPath와 그 하위 디렉토리의 파일 중 확장자가 extension와 일치하는 파일을 HashMap에 담아서 리턴한다.
     *
     * @param rootPath String scan을 시작할 directory path
     * @param extension String scan할 대상 파일 확장자
     * @return 확장자가 extension와 일치하는 파일을 HashMap에 담아서 리턴
     */
    public static HashMap<String, File> getFilesWithExtension(String rootPath, String extension) {
        File rootDirectory = getRootDirectory(rootPath);
        HashMap<String, File> fileMap = new HashMap<String, File>();

        setFileMapByExtension(fileMap, rootDirectory, "", extension);
        return fileMap;
    }

    /**
     * rootPath를 File 객체로 생성하여 존재여부와 디렉토리인지 체크하고 File 객체를 리턴한다.
     *
     * @param rootPath String scan을 시작할 root directory path
     * @return scan을 시작할 root directory object
     */
    private static File getRootDirectory(String rootPath) {
        File rootDirectory = new File(rootPath);

        if (rootDirectory == null || !rootDirectory.exists()) { throw new IllegalArgumentException("The file(" + rootPath + ") does not exist."); }

        if (!rootDirectory.isDirectory()) { throw new IllegalArgumentException("The file(" + rootPath + ") isn't a directory."); }

        return rootDirectory;
    }

    /**
     * parent 디렉토리의 확장자가 extension와 일치하는 모든 파일을 fileMap에 저장한다.
     *
     * @param fileMap file map
     * @param parent file이 존재하는 directory
     * @param keyHeader arent의 full path
     * @param extension String scan할 대상 파일 확장자
     */
    private static void setFileMapByExtension(HashMap<String, File> fileMap, File parent, String keyHeader, String extension) {
        if (!parent.canRead()) { throw new IllegalStateException("You don't have permission to read the directory(" + parent.getAbsolutePath() + ")."); }

        File[] list = parent.listFiles();
        String newHeader = "";
        for (int inx = 0, count = list.length; inx < count; inx++) {
            File subFile = list[inx];
            if (subFile.isDirectory()) {
                newHeader = keyHeader + "/" + subFile.getName();
                setFileMapByExtension(fileMap, subFile, newHeader, extension);
            } else {
                if (subFile.getName().toLowerCase().endsWith(extension)) {
                    String fName = subFile.getName();
                    fName = fName.substring(0, fName.toLowerCase().lastIndexOf(extension));
                    newHeader = keyHeader + "/" + fName;
                    fileMap.put(newHeader, subFile);
                }
            }
        }
    }

    /**
     * 부모 파일로부터 자식 파일까지의 상대경로를 리턴한다.
     *
     * @param parent the parent
     * @param child the child
     * @return the relative path
     */
    public static String getRelativePath(File parent, File child) {
        File parentPath = parent;
        File childPath = child;
        try {
            if (parentPath == null || childPath == null) return null;
            parentPath = parentPath.getCanonicalFile();
            childPath = childPath.getCanonicalFile();

            String pstr = parentPath.getAbsolutePath();
            if (pstr.endsWith(File.separator) || pstr.endsWith("/")) pstr = pstr.substring(0, pstr.length() - 1);
            String cstr = childPath.getAbsolutePath();
            if (parentPath.equals(childPath)) { return cstr; }
            if (isChild(parentPath, childPath)) { return cstr.substring(pstr.length()); }
        } catch (IOException e) {
        	
        }

        return childPath.getAbsolutePath();
    }

    /**
     * parent 디렉토리의 자녀 파일 중에 파라미터로 지정된 child 파일이 포함되는지 여부를 반환한다.
     *
     * @param parent 부모 디렉토리
     * @param child 자녀 File
     * @return 자녀 파일이면 true, 아니면 false
     */
    public static boolean isChild(File parent, File child) {
        File parentFile = parent;
        File childFile = child;
        try {
            if (parentFile == null || childFile == null) return false;
            parentFile = parentFile.getCanonicalFile();
            childFile = childFile.getCanonicalFile();
            if (parentFile.equals(childFile)) return false;
            File file = childFile.getParentFile();
            while (file != null) {
                if (file.equals(parentFile)) return true;
                file = file.getParentFile();
            }
        } catch (Exception e) {
        	
        }
        return false;
    }

    /**
     * 파일의 확장자를 바꾸어 준다.
     *
     * @param file 바꿀 파일
     * @param newExtension 새로운 확장자
     * @return 확장자가 바뀌어진 파일
     * @throws IOException
     */
    public static File replaceExtension(File file, String newExtension) throws IOException {
        if (!file.exists()) throw new FileNotFoundException("file not found : " + file.getAbsolutePath());
        return new File(getFileNameWithoutExtension(file.getAbsolutePath()) + "." + newExtension);
    }

    /**
     * 두 파일의 사이즈를 비교하여 결과를 반환한다. (KB 단위 비교)
     *
     * @param source 파일1
     * @param target 파일2
     * @return result(source > target : 1, source == target : 0, source < target : -1)
     */
    public static int compareSize(File source, File target) {

        int result = 0;

        if (source.exists() && target.exists() && source.isFile() && target.isFile()) {

            long size1 = source.length();
            long size2 = target.length();

            if (size1 > size2) {
                result = 1;
            } else if (size1 < size2) {
                result = -1;
            }
        } else {
            throw new IllegalArgumentException("Input parameters(" + source.getAbsolutePath() + " or " + target.getAbsolutePath() + ") are not files or does not exist.");

        }

        return result;
    }

    /**
     * 두 파일의 수정일자를 비교하여 결과를 반환한다.
     *
     * @param source 파일1
     * @param target 파일2
     * @return result(source > target : 1, source == target : 0, source < target : -1)
     */
    public static int compareLastModifiedDate(File source, File target) {

        int result = 0;

        if (source.exists() && target.exists() && source.isFile() && target.isFile()) {

            long date1 = source.lastModified();
            long date2 = target.lastModified();

            if (date1 > date2) {
                result = 1;
            } else if (date1 < date2) {
                result = -1;
            }
        } else {
            throw new IllegalArgumentException("Input parameters(" + source.getAbsolutePath() + " or " + target.getAbsolutePath() + ") are not files or does not exist.");

        }

        return result;
    }

    /**
     * 파일의 최종수정일자별 파일목록 조회한다.
     *
     * @param directory 디렉토리
     * @param modifiedDate 최종수정일자(YYYYMMDD 형태로 입력)
     * @return 파일목록
     */
    public static List<String> getFileListByLastModifiedDate(File directory, String modifiedDate) {
        List<String> list = new ArrayList<String>();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            list = getFileListByLastModifiedDate(directory, dateFormat.parse(modifiedDate));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Format of date(" + modifiedDate + ") is not valid. Valid date format is yyyyMMdd.");
        }
        return list;
    }

    /**
     * 파일의 최종수정일자별 파일목록 조회한다.
     *
     * @param directory 디렉토리
     * @param modifiedDate 최종수정일자(java.util.Date)
     * @return 파일목록
     */
    public static List<String> getFileListByLastModifiedDate(File directory, Date modifiedDate) {
        List<String> list = new ArrayList<String>();

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            list = getSubFilesByModifiedDate(files, modifiedDate);
        } else {
            throw new IllegalArgumentException("The input parameter(" + directory.getAbsolutePath() + ") is not a directory or does not exist.");

        }

        return list;
    }

    /**
     * 하위디렉토리 포함 최종수정일자가 같은 파일목록을 조회한다.
     *
     * @param fileArray 파일목록
     * @param modifiedDate 최종수정일자(java.util.Date)
     * @return 파일목록
     */
    private static List<String> getSubFilesByModifiedDate(File[] fileArray, Date modifiedDate) {

        List<String> list = new ArrayList<String>();

        for (int inx = 0; inx < fileArray.length; inx++) {
            if (fileArray[inx].isDirectory()) {
                File[] files = fileArray[inx].listFiles();
                list.addAll(getSubFilesByModifiedDate(files, modifiedDate));
            } else {
                long date = fileArray[inx].lastModified();

                GregorianCalendar updtDateCal = new GregorianCalendar();
                updtDateCal.setTime(modifiedDate);
                GregorianCalendar lastUpdtDateCal = new GregorianCalendar();
                lastUpdtDateCal.setTimeInMillis(date);

                // 그냥 Date 포맷으로 비교하면 시분초까지 비교가 되기 때문에 년/월/일이 같은지를 비교한다.
                if (updtDateCal.get(Calendar.YEAR) == lastUpdtDateCal.get(Calendar.YEAR) && updtDateCal.get(Calendar.MONTH) == lastUpdtDateCal.get(Calendar.MONTH) && updtDateCal.get(Calendar.DAY_OF_MONTH) == lastUpdtDateCal.get(Calendar.DAY_OF_MONTH)) {
                    list.add(fileArray[inx].getAbsolutePath());
                }
            }
        }

        return list;
    }

    /**
     * 파일의 최종수정기간내 파일목록을 조회한다.
     *
     * @param directory 디렉토리
     * @param modifiedDateFrom 최종수정일자From(YYYYMMDD 형태로 입력)
     * @param modifiedDateTo 최종수정일자To(YYYYMMDD 형태로 입력)
     * @return 파일목록
     */
    public static List<String> getFileListByLastModifiedDate(File directory, String modifiedDateFrom, String modifiedDateTo) {
        List<String> list = new ArrayList<String>();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            list = getFileListByLastModifiedDate(directory, dateFormat.parse(modifiedDateFrom), dateFormat.parse(modifiedDateTo));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Format of fromDate(" + modifiedDateFrom + ") or updated toDate(" + modifiedDateTo + ") is not valid. Valid date format is yyyyMMdd.");

        }
        return list;
    }

    /**
     * 파일의 최종수정기간내 파일목록 조회한다.
     *
     * @param directory 디렉토리
     * @param modifiedDateFrom 최종수정일자From(java.util.Date)
     * @param modifiedDateTo 최종수정일자To(java.util.Date)
     * @return 파일목록
     */
    public static List<String> getFileListByLastModifiedDate(File directory, Date modifiedDateFrom, Date modifiedDateTo) {
        List<String> list = new ArrayList<String>();

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            list = getSubFilesByModifiedDate(files, modifiedDateFrom, modifiedDateTo);
        } else {
            throw new IllegalArgumentException("The input parameter(" + directory.getAbsolutePath() + ") is not a directory or does not exist.");

        }

        return list;
    }

    /**
     * 디렉토리 내부 하위목록들 중에서 파일을 조회한다.(최종수정기간별)
     *
     * @param fileArray 파일목록
     * @param modifiedDateFrom 수정일자From(java.util.Date)
     * @param modifiedDateTo 수정일자To(java.util.Date)
     * @return 파일목록(절대경로)
     */
    private static List<String> getSubFilesByModifiedDate(File[] fileArray, Date modifiedDateFrom, Date modifiedDateTo) {

        List<String> list = new ArrayList<String>();

        for (int inx = 0; inx < fileArray.length; inx++) {
            if (fileArray[inx].isDirectory()) {
                File[] files = fileArray[inx].listFiles();
                list.addAll(getSubFilesByModifiedDate(files, modifiedDateFrom, modifiedDateTo));
            } else {
                long date = fileArray[inx].lastModified();
                GregorianCalendar lastUpdtDateCal = new GregorianCalendar();
                lastUpdtDateCal.setTimeInMillis(date);

                // from date 는 시분초를 모두 0으로 세팅해서 그보다 크거나 같은 날짜를 찾는다.
                GregorianCalendar updtFromCal = new GregorianCalendar();
                updtFromCal.setTime(modifiedDateFrom);
                updtFromCal.set(updtFromCal.get(Calendar.YEAR), updtFromCal.get(Calendar.MONTH), updtFromCal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

                // to date 는 바로 다음날 0시 0분 0초로 세팅한 뒤 그보다 작은 날짜를 찾는다.
                GregorianCalendar updtToCal = new GregorianCalendar();
                updtToCal.setTime(modifiedDateTo);
                updtToCal.set(updtToCal.get(Calendar.YEAR), updtToCal.get(Calendar.MONTH), updtToCal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
                updtToCal.add(Calendar.DAY_OF_MONTH, 1);

                if (lastUpdtDateCal.equals(updtFromCal) || lastUpdtDateCal.after(updtFromCal) && (lastUpdtDateCal.before(updtToCal))) {
                    list.add(fileArray[inx].getAbsolutePath());
                }
            }
        }

        return list;
    }

    /**
     * 디렉토리에 해당 파일이 존재하는지 체크한다.
     *
     * @param directory 디렉토리
     * @param fileName 파일명
     * @return 존재여부 true / false
     */
    public static boolean isExistingFileByName(File directory, String fileName) {

        boolean result = false;

        if (directory.exists() && directory.isDirectory()) {

            File[] fileArray = directory.listFiles();

            for (int inx = 0; inx < fileArray.length; inx++) {
                if (fileArray[inx].isDirectory()) {
                    result = isExistingFileByName(fileArray[inx], fileName);
                } else {
                    if (fileArray[inx].getName().equals(fileName)) {
                        result = true;
                    }
                }
                if (result) {
                    break;
                }
            }
        } else {
            throw new IllegalArgumentException("The input parameter(" + directory.getAbsolutePath() + ") is not a directory or does not exist.");
        }

        return result;
    }

    /**
     * 확장자별로 디렉토리에 파일이 존재하는지 체크한다.
     *
     * @param directory 디렉토리
     * @param extension 확장자
     * @return 존재여부 true / false
     */
    public static boolean isExistingFileByExtension(File directory, String extension) {

        boolean result = false;

        if (directory.exists() && directory.isDirectory()) {

            File[] fileArray = directory.listFiles();

            for (int inx = 0; inx < fileArray.length; inx++) {
                if (fileArray[inx].isDirectory()) {
                    result = isExistingFileByExtension(fileArray[inx], extension);
                } else {
                    if (extension.equals(getExtension(fileArray[inx].getName()))) {
                        result = true;
                    }
                }
                if (result) {
                    break;
                }
            }
        } else {
            throw new IllegalArgumentException("The input parameter(" + directory.getAbsolutePath() + ") is not a directory or does not exist.");
        }

        return result;
    }

    /**
     * 수정기간별로 디렉토리에 파일이 존재하는지 체크한다.
     *
     * @param directory 디렉토리
     * @param modifiedDateFrom 수정일자From(YYYYMMDD 형태로 입력)
     * @param modifiedDateTo 수정일자To(YYYYMMDD 형태로 입력)
     * @return 존재여부 true / false
     */
    public static boolean isExistingFileByModifiedDate(File directory, String modifiedDateFrom, String modifiedDateTo) {
        boolean result = false;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            result = isExistingFileByModifiedDate(directory, dateFormat.parse(modifiedDateFrom), dateFormat.parse(modifiedDateTo));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Format of fromDate(" + modifiedDateFrom + ") or updated toDate(" + modifiedDateTo + ") is not valid. Valid date format is yyyyMMdd.");
        }

        return result;
    }

    /**
     * 수정기간별로 디렉토리에 파일이 존재하는지 체크한다.
     *
     *
     * @param directory 디렉토리
     * @param modifiedDateFrom 수정일자From(java.util.Date)
     * @param modifiedDateTo 수정일자To(java.util.Date)
     * @return 존재여부 true / false
     */
    public static boolean isExistingFileByModifiedDate(File directory, Date modifiedDateFrom, Date modifiedDateTo) {

        boolean result = false;

        if (directory.exists() && directory.isDirectory()) {
            File[] fileArray = directory.listFiles();
            for (int inx = 0; inx < fileArray.length; inx++) {
                if (fileArray[inx].isDirectory()) {
                    result = isExistingFileByModifiedDate(fileArray[inx], modifiedDateFrom, modifiedDateTo);
                } else {
                    long date = fileArray[inx].lastModified();
                    GregorianCalendar lastUpdtDateCal = new GregorianCalendar();
                    lastUpdtDateCal.setTimeInMillis(date);

                    // from date 는 시분초를 모두 0으로 세팅해서 그보다 크거나 같은 날짜를 찾는다.
                    GregorianCalendar updtFromCal = new GregorianCalendar();
                    updtFromCal.setTime(modifiedDateFrom);
                    updtFromCal.set(updtFromCal.get(Calendar.YEAR), updtFromCal.get(Calendar.MONTH), updtFromCal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

                    // to date 는 바로 다음날 0시 0분 0초로 세팅한 뒤 그보다 작은 날짜를 찾는다.
                    GregorianCalendar updtToCal = new GregorianCalendar();
                    updtToCal.setTime(modifiedDateTo);
                    updtToCal.set(updtToCal.get(Calendar.YEAR), updtToCal.get(Calendar.MONTH), updtToCal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
                    updtToCal.add(Calendar.DAY_OF_MONTH, 1);

                    if (lastUpdtDateCal.equals(updtFromCal) || lastUpdtDateCal.after(updtFromCal) && (lastUpdtDateCal.before(updtToCal))) {
                        result = true;
                    }
                }
                if (result) {
                    break;
                }
            }
        } else {
            throw new IllegalArgumentException("The input parameter(" + directory.getAbsolutePath() + ") is not a directory or does not exist.");
        }

        return result;
    }

    /**
     * 사이즈별로 디렉토리에 파일이 존재하는지 체크한다.
     *
     * @param directory 디렉토리
     * @param sizeFrom 사이즈From (KB)
     * @param sizeTo 사이즈To (KB)
     * @return 존재여부 true / false
     */
    public static boolean isExistingFileBySize(File directory, long sizeFrom, long sizeTo) {

        boolean result = false;

        if (directory.exists() && directory.isDirectory()) {
            File[] fileArray = directory.listFiles();
            for (int inx = 0; inx < fileArray.length; inx++) {
                if (fileArray[inx].isDirectory()) {
                    result = isExistingFileBySize(fileArray[inx], sizeFrom, sizeTo);
                } else {
                    long size = fileArray[inx].length();
                    if (size >= (sizeFrom * BUFFER_SIZE) && size <= (sizeTo * BUFFER_SIZE)) {
                        result = true;
                    }
                }
                if (result) {
                    break;
                }
            }
        } else {
            throw new IllegalArgumentException("The input parameter(" + directory.getAbsolutePath() + ") is not a directory or does not exist.");
        }

        return result;
    }

    /**
     * 디렉토리(파일)의 최종 수정일자를 확인한다.
     *
     * @param filePath 수정일자를 확인할 대상파일경로
     * @return 최종수정일자를 문자열로 리턴한다.
     */
    public static String getLastModifiedDate(String filePath) {
        File file = new File(filePath);
        String result = getLastModifiedDate(file);
        return result;
    }

    /**
     * 디렉토리(파일)의 최종 수정일자를 확인한다.
     *
     * @param file 수정일자를 확인할 대상파일
     * @return 최종수정일자를 문자열로 리턴한다.
     */
    public static String getLastModifiedDate(File file) {
        return getLastModifiedDate(file, "yyyyMMdd");
    }

    /**
     * 디렉토리(파일)의 최종 수정일자를 확인한다.
     *
     * @param file 수정일자를 확인할 대상파일
     * @param format
     * @return 최종수정일자를 문자열로 리턴한다.
     */
    public static String getLastModifiedDate(File file, String format) {
        String result = "";

        if (file.exists()) {
            long date = file.lastModified();
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            result = dateFormat.format(new java.util.Date(date));
        } else {
            throw new IllegalArgumentException("The input parameter(" + file.getAbsolutePath() + ") does not exist.");

        }

        return result;
    }

    /**
     * 디렉토리 존재여부를 확인한다. (단일디렉토리 확인용)
     *
     * @param targetDirectoryPath 존재여부를 확인할 디렉토리의 절대경로
     * @return 존재하는 디렉토리 경로를 리턴한다.
     */
    public static boolean isExistingDirectory(String targetDirectoryPath) {

        // 인자값 유효하지 않은 경우 공백 리턴
        if (targetDirectoryPath == null || targetDirectoryPath.equals("")) { return false; }

        boolean result = false;
        File dir = null;

        dir = new File(targetDirectoryPath);
        if (dir.exists() && dir.isDirectory()) {
            result = true;
        }

        return result;
    }

    /**
     * 조건구간내에 생성된 디렉토리 목록을 조회한다.
     *
     * @param baseDirectoryPath 하위디렉토리를 확인할 경로
     * @param fromDate 조건시작일
     * @param toDate 조건 종료일
     * @return 조건구간내에 생성된 디렉토리 목록을 리턴한다.
     */
    public static List<String> getDirectoryList(String baseDirectoryPath, String fromDate, String toDate) {

        File dir = null;
        File childFile = null;
        String[] subDirList;
        String subDirPath = "";
        List<String> childResult = null;
        List<String> result = new ArrayList<String>();

        dir = new File(baseDirectoryPath);

        subDirList = dir.list();

        if (subDirList != null) {
            for (int i = 0; i < subDirList.length; i++) {

                subDirPath = baseDirectoryPath + "/" + subDirList[i];
                childFile = new File(subDirPath);
                if (childFile.isDirectory()) {
                    String lastModifyedDate = getLastModifiedDate(childFile);
                    if (Integer.parseInt(lastModifyedDate) >= Integer.parseInt(fromDate) && Integer.parseInt(lastModifyedDate) <= Integer.parseInt(toDate)) {
                        result.add(baseDirectoryPath + "/" + subDirList[i]);
                    }
                    childResult = getDirectoryList(baseDirectoryPath + "/" + subDirList[i], fromDate, toDate);
                    // 하위디렉토리의 결과를 추가한다.
                    for (int j = 0; j < childResult.size(); j++) {
                        result.add((String)childResult.get(j));
                    }
                }
            }
        }

        return result;
    }

    /**
     * 디렉토리(파일)의 읽기권한을 확인한다.
     *
     * @param filePath 읽기권한을 확인할 대상파일경로
     * @return 읽기가능하면 true를 리턴한다. 권한이 없어가 파일이 없는 경우는 false를 리턴한다.
     */
    public static boolean isAuthorityRead(String filePath) {

        // 인자값 유효하지 않은 경우 빈 false 리턴
        if (filePath == null || filePath.equals("")) { return false; }

        File file = null;
        boolean result = false;

        file = new File(filePath);
        if (file.exists()) {
            result = file.canRead();
        }

        return result;
    }

    /**
     * 디렉토리(파일)의 쓰기권한을 확인한다.(대상경로가 파일인 경우만 정보가 유효함)
     *
     * @param filePath 쓰기권한을 확인할 대상파일경로
     * @return 쓰기가능하면 true를 리턴한다. 권한이 없어가 파일이 없는 경우는 false를 리턴한다.
     */
    public static boolean isAuthorityWrite(String filePath) {

        // 인자값 유효하지 않은 경우 빈 false 리턴
        if (filePath == null || filePath.equals("")) { return false; }

        File file = new File(filePath);
        boolean result = false;

        if (file.exists()) {
            result = file.canWrite();
        }

        return result;
    }

    /**
     * 디렉토리(파일)의 이름을 확인한다.
     *
     * @param filePath 이름을 확인할 대상경로
     * @return 이름을 리턴한다. 존재하지 않는 경우는 블랭크를 리턴한다.
     */
    public static String getName(String filePath) {

        // 인자값 유효하지 않은 경우 빈 false 리턴
        if (filePath == null || filePath.equals("")) { return ""; }

        File file = new File(filePath);
        String result = "";

        if (file.exists()) {
            result = file.getName();
        }

        return result;
    }

    /**
     * 디렉토리에 파일이 존재하는지 체크한다.
     *
     * @param directory 디렉토리
     * @param file 파일
     * @return result 존재여부 True / False
     */
    public static boolean isExistingFileByName(String directory, String file) {

        // 파일 존재 여부
        boolean result = false;

        // 디렉토리 오픈
        String drctry = directory.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
        File srcDrctry = new File(drctry);

        // 디렉토리이면서, 존재하면
        if (srcDrctry.exists() && srcDrctry.isDirectory()) {

            // 디렉토리 안 목록을 조회한다. (파일명)
            File[] fileArray = srcDrctry.listFiles();
            List<String> list = getSubFilesByName(fileArray, file);
            if (list != null && list.size() > 0) {
                result = true;
            }
        }

        return result;
    }

    /**
     * 확장자별로 디렉토리에 파일이 존재하는지 체크한다.
     *
     * @param directory 디렉토리
     * @param extension 확장자명(txt 형태 입력)
     * @return result 존재여부 True / False
     */
    public static boolean isExistingFileByExtension(String directory, String extension) {

        // 파일 존재 여부
        boolean result = false;

        // 디렉토리 오픈
        String drctry = directory.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
        File srcDrctry = new File(drctry);

        // 디렉토리이면서, 존재하면
        if (srcDrctry.exists() && srcDrctry.isDirectory()) {

            // 디렉토리 안 목록을 조회한다. (확장자별)
            File[] fileArray = srcDrctry.listFiles();
            List<String> list = getSubFilesByExtension(fileArray, extension);
            if (list != null && list.size() > 0) {
                result = true;
            }
        }

        return result;
    }

    /**
     * 수정기간별로 디렉토리에 파일이 존재하는지 체크한다.
     *
     * @param directory 디렉토리
     * @param modifiedDateFrom 수정일자From(YYYYMMDD 형태로 입력)
     * @param modifiedDateTo 수정일자To(YYYYMMDD 형태로 입력)
     * @return boolean result 존재여부 True / False
     */
    public static boolean isExistingFileByModifiedDate(String directory, String modifiedDateFrom, String modifiedDateTo) {

        // 파일 존재 여부
        boolean result = false;

        // 디렉토리 오픈
        String drctry = directory.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
        File srcDrctry = new File(drctry);

        // 디렉토리이면서, 존재하면
        if (srcDrctry.exists() && srcDrctry.isDirectory()) {

            // 디렉토리 안 목록을 조회한다. (수정기간별)
            File[] fileArray = srcDrctry.listFiles();
            List<String> list = getSubFilesByModifiedDate(fileArray, modifiedDateFrom, modifiedDateTo);
            if (list != null && list.size() > 0) {
                result = true;
            }
        }

        return result;
    }

    /**
     * 사이즈별로 디렉토리에 파일이 존재하는지 체크한다.
     *
     * @param directory 디렉토리
     * @param sizeFrom 사이즈From (KB)
     * @param sizeTo 사이즈To (KB)
     * @return boolean result 존재여부 True / False
     */
    public static boolean isExistingFileBySize(String directory, long sizeFrom, long sizeTo) {

        // 파일 존재 여부
        boolean result = false;

        // 디렉토리 오픈
        String drctry = directory.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
        File srcDrctry = new File(drctry);

        // 디렉토리이면서, 존재하면
        if (srcDrctry.exists() && srcDrctry.isDirectory()) {

            // 디렉토리 안 목록을 조회한다. (사이즈별)
            File[] fileArray = srcDrctry.listFiles();
            List<String> list = getSubFilesBySize(fileArray, sizeFrom, sizeTo);
            if (list != null && list.size() > 0) {
                result = true;
            }
        }

        return result;
    }

    /**
     * 디렉토리 내부 하위목록들 중에서 파일을 찾는 기능(모든 목록 조회)
     *
     * @param fileArray 파일목록
     * @return ArrayList list 파일목록(절대경로)
     */
    public static List<String> getSubFilesByAll(File[] fileArray) {

        List<String> list = new ArrayList<String>();

        for (int i = 0; i < fileArray.length; i++) {
            // 디렉토리 안에 디렉토리면 그 안의 파일목록에서 찾도록 재귀호출한다.
            if (fileArray[i].isDirectory()) {
                File[] tmpArray = fileArray[i].listFiles();
                list.addAll(getSubFilesByAll(tmpArray));
                // 파일이면 담는다.
            } else {
                list.add(fileArray[i].getAbsolutePath());
            }
        }

        return list;
    }

    /**
     * 디렉토리 내부 하위목록들 중에서 파일을 찾는 기능(파일명)
     *
     * @param fileArray 파일목록
     * @param file 파일명
     * @return ArrayList list 파일목록(절대경로)
     */
    public static List<String> getSubFilesByName(File[] fileArray, String file) {

        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < fileArray.length; i++) {
            // 디렉토리 안에 디렉토리면 그 안의 파일목록에서 찾도록 재귀호출한다.
            if (fileArray[i].isDirectory()) {
                File[] tmpArray = fileArray[i].listFiles();
                list.addAll(getSubFilesByName(tmpArray, file));
                // 파일이면 파일명이 같은지 비교한다.
            } else {
                if (fileArray[i].getName().equals(file)) {
                    list.add(fileArray[i].getAbsolutePath());
                }
            }
        }

        return list;
    }

    /**
     * 디렉토리 내부 하위목록들 중에서 파일을 찾는 기능(확장자별)
     *
     * @param fileArray 파일목록
     * @param extensio 확장자
     * @return ArrayList list 파일목록(절대경로)
     */
    public static List<String> getSubFilesByExtension(File[] fileArray, String extensio) {

        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < fileArray.length; i++) {
            // 디렉토리 안에 디렉토리면 그 안의 파일목록에서 찾도록 재귀호출한다.
            if (fileArray[i].isDirectory()) {
                File[] tmpArray = fileArray[i].listFiles();
                list.addAll(getSubFilesByExtension(tmpArray, extensio));
                // 파일이면 확장자명이 들어있는지 비교한다.
            } else {
                if (fileArray[i].getName().indexOf(extensio) != -1) {
                    list.add(fileArray[i].getAbsolutePath());
                }
            }
        }

        return list;
    }

    /**
     * 디렉토리 내부 하위목록들 중에서 파일을 찾는 기능(최종수정기간별)
     *
     * @param fileArray 파일목록
     * @param modifiedDateFrom 수정일자From(YYYYMMDD 형태로 입력)
     * @param modifiedDateTo 수정일자To(YYYYMMDD 형태로 입력)
     * @return ArrayList list 파일목록(절대경로)
     */
    public static List<String> getSubFilesByModifiedDate(File[] fileArray, String modifiedDateFrom, String modifiedDateTo) {

        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < fileArray.length; i++) {
            // 디렉토리 안에 디렉토리면 그 안의 파일목록에서 찾도록 재귀호출한다.
            if (fileArray[i].isDirectory()) {
                File[] tmpArray = fileArray[i].listFiles();
                list.addAll(getSubFilesByModifiedDate(tmpArray, modifiedDateFrom, modifiedDateTo));
                // 파일이면 수정기간내에 존재하는지 비교한다.
            } else {
                // 파일의 최종수정일자 조회
                long date = fileArray[i].lastModified();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                String lastUpdtDate = dateFormat.format(new Date(date));
                // 수정기간 내에 존재하는지 확인
                if (Integer.parseInt(lastUpdtDate) >= Integer.parseInt(modifiedDateFrom) && Integer.parseInt(lastUpdtDate) <= Integer.parseInt(modifiedDateTo)) {
                    list.add(fileArray[i].getAbsolutePath());
                }
            }
        }

        return list;
    }

    /**
     * 디렉토리 내부 하위목록들 중에서 파일을 찾는 기능(사이즈별)
     *
     * @param fileArray 파일목록
     * @param sizeFrom 사이즈From(KB)
     * @param sizeTo 사이즈To(KB)
     * @return ArrayList list 파일목록(절대경로)
     */
    public static List<String> getSubFilesBySize(File[] fileArray, long sizeFrom, long sizeTo) {

        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < fileArray.length; i++) {
            // 디렉토리 안에 디렉토리면 그 안의 파일목록에서 찾도록 재귀호출한다.
            if (fileArray[i].isDirectory()) {
                File[] tmpArray = fileArray[i].listFiles();
                list.addAll(getSubFilesBySize(tmpArray, sizeFrom, sizeTo));
                // 파일이면, 사이즈내에 존재하는지 비교한다.
            } else {
                // 파일의 사이즈 조회
                long size = fileArray[i].length();
                // 사이즈 내에 존재하는지 확인
                if (size >= (sizeFrom * BUFFER_SIZE) && size <= (sizeTo * BUFFER_SIZE)) {
                    list.add(fileArray[i].getAbsolutePath());
                }
            }
        }

        return list;
    }

    /**
     * 디렉토리를 생성한다.
     *
     * @param directoryPath 생성하고자 하는 절대경로
     * @return 성공하면 생성된 절대경로, 아니면 블랭크
     */
    public static String createDirectory(String directoryPath) {
        File file = new File(directoryPath);
        String result = "";
        if (!file.exists()) {
            file.mkdirs();
            result = file.getAbsolutePath();
        }
        return result;
    }

    /**
     * 파일을 생성한다.
     *
     * @param filePath 파일의 절대경로 +파일명
     * @return 저장할 문자열입니다. (filePath 값 또는 File의 절대경로)
     *
     */
    public static String createNewFile(String filePath) {

        // 인자값 유효하지 않은 경우 블랭크 리턴
        if (filePath == null || filePath.equals("")) { return ""; }

        File file = new File(filePath);
        String result = "";
        try {
            if (file.exists()) {
                result = filePath;
            } else {
                // 존재하지 않으면 생성함
                new File(file.getParent()).mkdirs();
                if (file.createNewFile()) {
                    result = file.getAbsolutePath();
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return result;
    }

    /**
     * 디렉토리(파일)를 삭제한다. (파일,디렉토리 구분없이 존재하는 경우 무조건 삭제한다)
     *
     * @param filePath 삭제하고자 하는 파일의 절대경로 + 파일명
     * @return 성공하면 삭제된 절대경로, 아니면블랭크
     */
    private static String deletePath(String filePath) {
        File file = new File(filePath);
        String result = "";

        if (file.exists()) {
            result = file.getAbsolutePath();
            if (!file.delete()) {
                result = "";
            }
        }
        return result;
    }

    /**
     * 디렉토리를 삭제한다.
     *
     * @param directoryDeletePath 삭제하고자 하는디렉토리의 절대경로(파일의 경로가 들어오는 경우 삭제하지 않음)
     * @return 성공하면 삭제된 절대경로, 아니면블랭크
     */
    public static String deleteDirectory(String directoryDeletePath) {

        // 인자값 유효하지 않은 경우 블랭크 리턴
        if (directoryDeletePath == null || directoryDeletePath.equals("")) { return ""; }
        String result = "";
        File dir = new File(directoryDeletePath);
        if (dir.isDirectory()) {
            String[] files = dir.list();
            // 소속된 파일을 모두 삭제
            for (int i = 0; i < files.length; i++) {
                File file = new File(directoryDeletePath + FILE_SEPARATOR + files[i]);
                if (file.isFile()) {
                    // 디렉토리에 속한 파일들을 모두 삭제한다.
                    file.delete();
                } else {
                    // 디렉토리에 속한 하위 디렉토리들에 대한 삭제 명령을 재귀적으로 호출시킨다.
                    deleteDirectory(directoryDeletePath + FILE_SEPARATOR + files[i]);
                }
            }
            // 디렉토리에 속한 파일들과 하위 디렉토리가 삭제되었으면 디렉토리 자신을 삭제한다.
            result = deletePath(directoryDeletePath);
        } else {
            result = "";
        }

        return result;
    }

//    /**
//     * 파일을 삭제한다.
//     *
//     * @param fileDeletePath 삭제하고자 하는파일의 절대경로
//     * @return 성공하면 삭제된 파일의 절대경로, 아니면블랭크
//     */
//    public static String deleteFile(String fileDeletePath) {
//
//        // 인자값 유효하지 않은 경우 블랭크 리턴
//        if (fileDeletePath == null || fileDeletePath.equals("")) { return ""; }
//        String result = "";
//        File file = new File(fileDeletePath);
//        if (file.isFile()) {
//            result = deletePath(fileDeletePath);
//        } else {
//            result = "";
//        }
//
//        return result;
//    }

    /**
     * 파일을 특정 구분자(',', '|', 'TAB')로 파싱하여 파일 내용을 반환한다.
     *
     * @param parFile 파일
     * @param parChar 구분자(',', '|', 'TAB')
     * @param parField 필드수
     * @return 파싱결과 구조체
     * @throws IOException IOException
     */
    public static List<List<String>> getFileListSplitByChars(String parFile, String parChar, int parField) throws IOException {
        // 파싱결과 구조체
        List<List<String>> parResult = new ArrayList<List<String>>();

        // 파일 오픈
        String parFile1 = parFile.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
        File file = new File(parFile1);
        BufferedReader br = null;
        try {
            // 파일이며, 존재하면 파싱 시작
            if (file.exists() && file.isFile()) {

                // 1. 파일 텍스트 내용을 읽어서 StringBuilder에 쌓는다.
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                StringBuilder strBuff = new StringBuilder();
                String line = br.readLine();
                while (line != null) {
                    if (line.length() < MAX_STR_LEN) {
                        strBuff.append(line);
                    }
                    line = br.readLine();
                }

                // 2. 쌓은 내용을 특정 구분자로 파싱하여 String 배열로 얻는다.
                String[] strArr = StringUtil.split(strBuff.toString(), parChar);

                // 3. 필드 수 만큼 돌아가며 Vector<ArrayList> 형태로 만든다.
                int filedCnt = 1;
                ArrayList<String> arr = null;
                for (int i = 0; i < strArr.length; i++) {

                    if (parField != 1) {
                        if ((filedCnt % parField) == 1) {
                            arr = new ArrayList<String>();
                            if (strArr[i] != null) {
                                arr.add(strArr[i]);
                            }
                            if (i == (strArr.length - 1)) {
                                parResult.add(arr);
                            }
                        } else if ((filedCnt % parField) == 0) {
                            if (strArr[i] != null) {
                                arr.add(strArr[i]);
                            }
                            parResult.add(arr);
                        } else {
                            if (strArr[i] != null) {
                                arr.add(strArr[i]);
                            }
                            if (i == (strArr.length - 1)) {
                                parResult.add(arr);
                            }
                        }
                    } else {
                        arr = new ArrayList<String>();
                        if (strArr[i] != null) {
                            arr.add(strArr[i]);
                        }
                        parResult.add(arr);
                    }
                    filedCnt++;
                }
            }
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        } finally {
            if (br != null) {
                br.close();
            }
        }

        return parResult;
    }

    /**
     * 파일을 일정 길이로 파싱한다.
     *
     * @param parFile 파일
     * @param parLen 각 필드의 길이
     * @param parLine 읽어낼 라인수
     * @return 파싱결과 구조체
     * @exception IOException IOException
     */
    public static List<List<String>> getFileListSplitBySize(String parFile, int[] parLen, int parLine) throws IOException {

        // 파싱결과 구조체
        List<List<String>> parResult = new ArrayList<List<String>>();

        // 파일 오픈
        String parFile1 = parFile.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
        File file = new File(parFile1);
        BufferedReader br = null;
        try {
            // 파일이며, 존재하면 파싱 시작
            if (file.exists() && file.isFile()) {

                // 1. 입력된 라인수만큼 파일 텍스트 내용을 읽어서 String[]에 쌓는다.
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String[] strArr = new String[parLine];
                String line = br.readLine();
                int readCnt = 0;
                while (line != null && readCnt < parLine) {
                    if (line.length() <= MAX_STR_LEN) {
                        strArr[readCnt++] = line;
                    }
                    line = br.readLine();
                }

                // 2. Vector<ArrayList> 형태로 만든다.
                for (int i = 0; i < strArr.length; i++) {
                    String text = strArr[i];
                    ArrayList<String> arr = new ArrayList<String>();
                    int idx = 0;
                    boolean result = false;
                    for (int j = 0; j < parLen.length; j++) {
                        if (!result) {
                            String split = "";
                            if (text.length() < (idx + parLen[j])) {
                                split = text.substring(idx, text.length());
                                result = true;
                            } else {
                                split = text.substring(idx, idx + parLen[j]);
                            }
                            arr.add(split);
                            idx = idx + parLen[j];
                        }
                    }
                    parResult.add(arr);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            if (br != null) {
                br.close();
            }
        }

        return parResult;
    }

    /**
     * 두 파일의 사이즈를 비교한다. (KB 단위 비교)
     *
     * @param compareFile1 파일1
     * @param compareFile2 파일2
     * @return 동일여부 True / False
     */
    public static boolean compareSize(String compareFile1, String compareFile2) {

        // 파일 동일 여부
        boolean result = false;

        // 파일 오픈
        String cmprFile11 = compareFile1.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
        String cmprFile22 = compareFile2.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
        File file1 = new File(cmprFile11);
        File file2 = new File(cmprFile22);

        // 파일이며, 존재하면 파일 사이즈 비교
        if (file1.exists() && file2.exists() && file1.isFile() && file2.isFile()) {

            // 파일1 사이즈
            long size1 = file1.length();

            // 파일2 사이즈
            long size2 = file2.length();

            // 사이즈 비교
            if (size1 == size2) {
                result = true;
            }
        }

        return result;
    }

    /**
     * 두 파일의 수정일자를 비교한다.
     *
     * @param cmprFile1 파일1
     * @param cmprFile2 파일2
     * @return 동일여부 True / False
     */
    public static boolean compareLastModifiedDate(String compareFile1, String compareFile2) {

        // 파일 동일 여부
        boolean result = false;

        // 파일 오픈
        String cmprFile11 = compareFile1.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
        String cmprFile22 = compareFile2.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
        File file1 = new File(cmprFile11);
        File file2 = new File(cmprFile22);

        // 파일이며, 존재하면 파일 수정일자 비교
        if (file1.exists() && file2.exists() && file1.isFile() && file2.isFile()) {

            // 파일1 수정일자
            long date1 = file1.lastModified();
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            String lastUpdtDate1 = dateFormat1.format(new java.util.Date(date1));

            // 파일2 수정일자
            long date2 = file2.lastModified();
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            String lastUpdtDate2 = dateFormat2.format(new java.util.Date(date2));

            // 수정일자 비교
            if (lastUpdtDate1.equals(lastUpdtDate2)) {
                result = true;
            }
        }

        return result;
    }

    /**
     * 두 파일의 내용을 비교한다. (TEXT파일만 가능)
     *
     * @param cmprFile1 파일1
     * @param cmprFile2 파일2
     * @return 동일여부 True / False
     * @exception IOException IOException
     */
    public static boolean compareContent(String compareFile1, String compareFile2) throws IOException {
        // 파일 오픈
        String cmprFile11 = compareFile1.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
        String cmprFile22 = compareFile2.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
        File file1 = new File(cmprFile11);
        File file2 = new File(cmprFile22);

        return compareContent(file1, file2);
    }

    /**
     * 두 파일의 내용을 비교한다. (TEXT파일만 가능)
     *
     * @param cmprFile1 파일1
     * @param cmprFile2 파일2
     * @return 동일여부 True / False
     * @exception IOException IOException
     */
    private static boolean compareContent(File compareFile1, File compareFile2) throws IOException {
        // 파일 동일 여부
        boolean result = false;

        BufferedReader br1 = null;
        BufferedReader br2 = null;
        try {
            // 파일이며, 존재하면 파일 내용 비교
            if (compareFile1.exists() && compareFile2.exists() && compareFile1.isFile() && compareFile2.isFile()) {

                ArrayList<String> cmprText1 = new ArrayList<String>();
                ArrayList<String> cmprText2 = new ArrayList<String>();

                // 파일1 텍스트 내용
                br1 = new BufferedReader(new InputStreamReader(new FileInputStream(compareFile1)));
                String line1 = br1.readLine();
                while (line1 != null) {
                    if (line1.length() < MAX_STR_LEN) {
                        cmprText1.add(line1);
                    }
                    line1 = br1.readLine();
                }

                // 파일2 텍스트 내용
                br2 = new BufferedReader(new InputStreamReader(new FileInputStream(compareFile2)));
                String line2 = br2.readLine();
                while (line2 != null) {
                    if (line2.length() <= MAX_STR_LEN) {
                        cmprText2.add(line2);
                    }
                    line2 = br2.readLine();
                }

                // 내용 비교
                boolean isWrong = false;
                for (int i = 0; i < cmprText1.size(); i++) {
                    if (!isWrong) {
                        String text1 = (String)cmprText1.get(i);
                        String text2 = (String)cmprText2.get(i);

                        if (!text1.equals(text2)) {
                            isWrong = true;
                        }
                    }
                }

                if (!isWrong) {
                    result = true;
                }
            }
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        } finally {

            if (br1 != null) {
                br1.close();
            }
            if (br2 != null) {
                br2.close();
            }
        }

        return result;
    }

    /**
     * 단일 파일을 다른 파일에 복사(Copy)한다.
     *
     * @param source 원본파일 경로
     * @param target 타겟파일 경로
     * @return 복사여부 True / False
     */
    public static boolean copyFile(String source, String target) {

        // 복사여부
        boolean result = false;

        // 원본 파일
        String src = source.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
        File srcFile = new File(src);

        // 타켓 파일
        String tar = target.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);

        try {
            // 원본 파일이 존재하는지 확인한다.
            if (srcFile.exists()) {

                // 복사될 target 파일 생성
                tar = createNewFile(tar);
                File tarFile = new File(tar);
                // 복사
                result = copyFile(srcFile, tarFile);
            }
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }

        return result;
    }

    /**
     * 여러 파일을 다른 디렉토리에 복사(Copy)한다.
     *
     * @param source 원본파일들
     * @param target 타겟디렉토리
     * @return 복사여부 True / False
     */
    public static boolean copyFiles(String[] source, String target) {

        // 복사여부
        boolean result = true;

        // 복사 이전에 복사할 파일들의 경로가 올바른지 확인한다.
        for (int i = 0; i < source.length; i++) {
            String src = source[i].replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
            File chkFile = new File(src);
            if (!chkFile.exists()) {
                return result;
            }
        }

        String tar = target.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);

        // 복사를 시작한다.
        for (int j = 0; j < source.length; j++) {

            if (result) {

                // 타겟파일이름 명명
                File chkFile = new File(source[j]);
                String tarTemp = tar + FILE_SEPARATOR + chkFile.getName();

                try {
                    // 복사될 target 파일 생성
                    tarTemp = createNewFile(tarTemp);
                    File tarFile = new File(tarTemp);

                    // 복사
                    result = copyFile(chkFile, tarFile);

                } catch (IOException ex) {
                    throw new IllegalStateException(ex);
                }

            }

        } // end for

        return result;
    }

    /**
     * 확장자별 파일들을 다른 디렉토리에 복사(Copy)한다.
     *
     * @param source 원본디렉토리
     * @param extension 확장자(.txt 형태 입력)
     * @param target 타겟디렉토리
     * @return 복사여부 True / False
     */
    public static boolean copyFileByExtension(String source, String extension, String target) {

        // 복사여부
        boolean result = true;

        // 원본 파일
        String src = source.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
        File srcFile = new File(src);

        // 원본 디렉토리가 존재하는지 확인한다.
        if (srcFile.exists() && srcFile.isDirectory()) {

            String tar = target.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);

            // 원본 디렉토리 안에서 확장자가 일치하는 파일목록을 가져온다.
            File[] fileArray = srcFile.listFiles();
            List<String> list = getSubFilesByExtension(fileArray, extension);

            // 복사를 시작한다.
            for (int i = 0; i < list.size(); i++) {
                if (result) {
                    // 원본파일 절대경로
                    String abspath = (String)list.get(i);

                    // 타겟파일이름 명명
                    File chkFile = new File(abspath);
                    String tarTemp = tar + FILE_SEPARATOR + chkFile.getName();

                    try {
                        // 복사될 target 파일 생성
                        tarTemp = createNewFile(tarTemp);
                        File tarFile = new File(tarTemp);

                        // 복사
                        result = copyFile(chkFile, tarFile);

                    } catch (IOException ex) {
                        throw new IllegalStateException(ex);
                    }
                }
            } // end for
        }

        return result;
    }

    /**
     * 수정기간내 파일들을 다른 디렉토리에 복사(Copy)한다.
     *
     * @param source 원본디렉토리
     * @param modifiedDateFrom 수정시작일자(YYYYMMDD 형태로 입력)
     * @param modifiedDateTo 수정종료일자(YYYYMMDD 형태로 입력)
     * @param target 타겟디렉토리
     * @return 복사여부 True / False
     */
    public static boolean copyFilesByModifiedDate(String source, String modifiedDateFrom, String modifiedDateTo, String target) {

        // 복사여부
        boolean result = true;

        // 원본 파일
        String src = source.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
        File srcFile = new File(src);

        // 원본 디렉토리가 존재하는지 확인한다.
        if (srcFile.exists() && srcFile.isDirectory()) {

            String tar = target.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);

            // 원본 디렉토리 안에서 수정기간내 존재하는 파일목록을 가져온다.
            File[] fileArray = srcFile.listFiles();
            List<String> list = getSubFilesByModifiedDate(fileArray, modifiedDateFrom, modifiedDateTo);

            // 복사를 시작한다.
            for (int i = 0; i < list.size(); i++) {

                if (result) {

                    // 원본파일 절대경로
                    String abspath = (String)list.get(i);

                    // 타겟파일이름 명명
                    File chkFile = new File(abspath);
                    String tarTemp = tar + FILE_SEPARATOR + chkFile.getName();

                    try {
                        // 복사될 target 파일 생성
                        tarTemp = createNewFile(tarTemp);
                        File tarFile = new File(tarTemp);

                        // 복사
                        result = copyFile(chkFile, tarFile);

                    } catch (IOException ex) {
                        throw new IllegalStateException(ex);
                    }
                }
            } // end for
        }

        return result;
    }

    /**
     * 사이즈내 파일들을 다른 디렉토리에 복사(Copy)한다.
     *
     * @param source 원본디렉토리
     * @param sizeFrom 최소사이즈(KB)
     * @param sizeTo 최대사이즈(KB)
     * @param target 타겟디렉토리
     * @return 복사여부 True / False
     */
    public static boolean copyFilesBySize(String source, long sizeFrom, long sizeTo, String target) {

        // 복사여부
        boolean result = true;

        // 원본 파일
        String src = source.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
        File srcFile = new File(src);

        // 원본 디렉토리가 존재하는지 확인한다.
        if (srcFile.exists() && srcFile.isDirectory()) {

            String tar = target.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);

            // 원본 디렉토리 안에서 사이즈내 존재하는 파일목록을 가져온다.
            File[] fileArray = srcFile.listFiles();
            List<String> list = getSubFilesBySize(fileArray, sizeFrom, sizeTo);
            // 복사를 시작한다.
            for (int i = 0; i < list.size(); i++) {

                if (result) {
                    // 원본파일 절대경로
                    String abspath = (String)list.get(i);

                    // 타겟파일이름 명명
                    File chkFile = new File(abspath);

                    String tarTemp = tar + FILE_SEPARATOR + chkFile.getName();

                    try {
                        // 복사될 target 파일 생성
                        tarTemp = createNewFile(tarTemp);
                        File tarFile = new File(tarTemp);
                        // 복사
                        result = copyFile(chkFile, tarFile);
                        if (!result) {
                            break;
                        }

                    } catch (IOException ex) {
                        throw new IllegalStateException(ex);
                    }
                }
            } // end for
        }

        return result;
    }

    /**
     * 복사를 수행한다.
     *
     * @param source 원본파일
     * @param target 타겟파일
     * @return 복사여부 True / False
     * @exception IOException
     */
    public static boolean copyFile(File source, File target) throws IOException {

        // 결과
        boolean result = false;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            // 복사
            fis = new FileInputStream(source);

            // 예외상황에 따른 처리 추가함. -> 만약 tarFile 이 디렉토리명인 경우 디렉토리 밑으로 새로 파일을 생성해서 복사한다.. like DOS
            File targetFile = target;
            if (targetFile.isDirectory()) {
                targetFile = new File(targetFile.getAbsolutePath() + "/" + source.getName());
            }
            fos = new FileOutputStream(targetFile);
            byte[] buffer = new byte[(int)BUFFER_SIZE];
            if (fis != null && fos != null) {
                int i = fis.read(buffer);
                while (i != -1) {
                    fos.write(buffer, 0, i);
                    i = fis.read(buffer);
                }
            }
            result = true;
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }

        return result;
    }

    /**
     * String 값을 파일에 복사한다.
     *
     * @param source 원본 String
     * @param target 타겟파일
     * @return 복사여부 True / False
     * @exception IOException
     */
    public static boolean copyFile(String source, File target) throws IOException {
        boolean result = false;

        FileWriter fileWriter = null;
        try {
            if(!target.exists()) {
                target.createNewFile();
            }
            fileWriter = new FileWriter(target);
            fileWriter.write(source);
            result = true;
        } finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }

        return result;
    }

    /**
     * 파일(디렉토리)가 존재하는 디렉토리(Parent)를 조회한다.
     *
     * @param file 파일(디렉토리)
     * @return parentDirectoryName 디렉토리
     */
    public static String getDirectoryParent(String file) {

        String parentDirectoryName = "";
        String src = file.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);

        File srcFile = new File(src);
        if (srcFile.exists()) {
            parentDirectoryName = srcFile.getParent();
        }

        return parentDirectoryName;
    }

//    /**
//     * 파일(디렉토리)가 존재하는 파일명을 조회한다.
//     *
//     * @param file 파일(디렉토리)
//     * @return 파일명
//     */
//    public static String getFileName(String file) {
//
//        String fileName = "";
//        String src = file.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
//
//        File srcFile = new File(src);
//        if (srcFile.exists()) {
//            fileName = srcFile.getName();
//        }
//
//        return fileName;
//    }

    /**
     * 파일(디렉토리)의 사이즈를 조회한다.
     *
     * @param file 파일(디렉토리)
     * @return 사이즈(Byte)
     */
    public static long getSize(String file) {

        long size = 0L;
        String src = file.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);

        File srcFile = new File(src);
        if (srcFile.exists()) {
            size = srcFile.length();
        }

        return size;
    }

    /**
     * 디렉토리를 복사한다.
     *
     * @param originalDirectoryPath 원본 디렉토리 의 절대경로
     * @param targetDirectoryPath 타겟 디렉토리 의 절대경로
     * @return 복사가 성공하면 true, 실패하면 false를 리턴한다.
     */
    public static boolean copyDirectory(String originalDirectoryPath, String targetDirectoryPath) {

        // 인자값 유효하지 않은 경우 공백 리턴
        if (originalDirectoryPath == null || originalDirectoryPath.equals("") || targetDirectoryPath == null || targetDirectoryPath.equals("")) { return false; }
        boolean result = false;
        File dir = new File(originalDirectoryPath);
        // 원본이 유효해야 진행한다.
        if (dir.exists() && dir.isDirectory()) {

            // 타겟으로 설정한 경로가 유효한지 확인(중간경로에 파일명 이 포함되어있으면 유효하지 못하므로 진행안함.
            String targetDirPath1 = createDirectory(targetDirectoryPath);
            if (targetDirPath1.equals("")) {
                result = false;
            } else {
                File targetDir = new File(targetDirPath1);
                targetDir.mkdirs();
                // 디렉토리에 속한 파일들을 복사한다.
                String[] originalFileList = dir.list();

                if (originalFileList.length > 0) {
                    for (int i = 0; i < originalFileList.length; i++) {
                        File subF = new File(originalDirectoryPath + FILE_SEPARATOR + originalFileList[i]);
                        if (subF.isFile()) {
                            // 하위목록이 파일이면 파일복사실행 -> 실패 발생하는 경우 복사를 중단한다.
                            result = copyFile(originalDirectoryPath + FILE_SEPARATOR + originalFileList[i], targetDir.getAbsolutePath() + FILE_SEPARATOR + originalFileList[i]);
                        } else {
                            // 하위목록이 디렉토리이면 복사를 재귀적으로 호출한다.
                            result = copyDirectory(originalDirectoryPath + "/" + originalFileList[i], targetDirPath1 + "/" + originalFileList[i]);
                        }
                    }
                } else {
                    result = true;
                }
            }
        }

        return result;
    }

    /**
     * 디렉토리를 복사한다. (생성일자 조건으로 복사)
     *
     * @param originalDirectoryPath 원본 디렉토리 의 절대경로
     * @param targetDirPath 타겟 디렉토리 의 절대경로
     * @param fromDate 디렉토리의 복사조건 시작일자
     * @param toDate 디렉토리의 복사조건 종료일자
     * @return 복사가 성공함변 true, 실패하면 false를 리턴한다.
     */
    public static boolean copyDirectory(String originalDirectoryPath, String targetDirectoryPath, String fromDate, String toDate) {

        // 인자값 유효하지 않은 경우 공백 리턴
        if (originalDirectoryPath == null || originalDirectoryPath.equals("")) { return false; }
        if (targetDirectoryPath == null || targetDirectoryPath.equals("")) { return false; }
        if (fromDate == null || fromDate.equals("")) { return false; }
        if (toDate == null || toDate.equals("")) { return false; }
        boolean result = false;
        File dir = null;

        dir = new File(originalDirectoryPath);
        boolean isInCondition = false;
        String lastModifyedDate = getLastModifiedDate(dir);
        if (Integer.parseInt(lastModifyedDate) >= Integer.parseInt(fromDate) && Integer.parseInt(lastModifyedDate) <= Integer.parseInt(toDate)) {
            isInCondition = true;
        }

        // 원본이 유효하고 조건에 부합되야 진행한다.
        if (dir.exists() && dir.isDirectory() && isInCondition) {

            // 타겟으로 설정한 경로가 유효한지 확인(중간경로에 파일명 이 포함되어있으면 유효하지 못하므로 진행안함.
            String targetDirPath1 = createDirectory(targetDirectoryPath);
            if (targetDirPath1.equals("")) {
                result = false;
            } else {
                File targetDir = new File(targetDirPath1);
                targetDir.mkdirs();
                // 디렉토리에 속한 파일들을 복사한다.
                String[] originalFileList = dir.list();

                if (originalFileList.length > 0) {
                    for (int i = 0; i < originalFileList.length; i++) {
                        File subF = new File(originalDirectoryPath + FILE_SEPARATOR + originalFileList[i]);
                        if (subF.isFile()) {
                            // 하위목록이 파일이면 파일복사실행 -> 실패 발생하는 경우 복사를 중단한다.
                            result = copyFile(originalDirectoryPath + FILE_SEPARATOR + originalFileList[i], targetDir.getAbsolutePath() + FILE_SEPARATOR + originalFileList[i]);
                        } else {
                            // 하위목록이 디렉토리이면 복사를 재귀적으로 호출한다.
                            result = copyDirectory(originalDirectoryPath + "/" + originalFileList[i], targetDirPath1 + "/" + originalFileList[i]);
                        }
                    }
                } else {
                    result = true;
                }
            }

        } else {
            // 원본자체가 유효하지 않은 경우는 false 리턴하고 종료
            result = false;
        }

        return result;
    }

    /**
     * 디렉토리의 사이즈를 조회한다.
     *
     * @param targetDirectoryPath 디렉토리
     * @return 디렉토리사이즈
     */
    public static long getDirectorySize(String targetDirectoryPath) {

        File file = new File(targetDirectoryPath);
        long size = 0;
        size = getDirectorySize(file);
        return size;
    }

    /**
     * 파일명에서 파일확장자를 추출한다.
     *
     * @param fileName 파일명
     * @return 파일확장자
     */
    public static String getExtension(String fileName) {
        if (fileName == null) { return null; }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 파일 존재여부를 확인한다.
     *
     * @param filePath 파일경로
     * @return if exists, return true
     */
    public static boolean isExistingFile(String filePath) {
        if (filePath == null) { return false; }
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 파일이 존재하고 파일이 맞는지 확인한다.
     *
     * @param filePath 파일경로
     * @return if exists and is file, return true
     */
    public static boolean existValidFile(String filePath) {
        if (filePath == null) { return false; }
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    /**
     * 파일의 내용을 byte[]으로 return 한다.
     *
     * @param file File객체
     * @return byte[] 타입의 파일 내용
     */
    public static byte[] readFile(File file) {
        InputStream is = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            is = new FileInputStream(file);

            byte[] buff = new byte[512];
            int size = -1;
            while (true) {
                size = is.read(buff);
                if (size == -1) {
                    break;
                }
                baos.write(buff, 0, size);
            }
            return baos.toByteArray();

        } catch (IOException e) {
            throw new IllegalStateException(e);

        } finally {
            if (is != null) try {
                is.close();
            } catch (Exception ex) {
            	
            }
        }
    }

    /**
     * 파일의 내용을 저장한다.
     *
     * @param file File객체
     * @param content byte[] 타입의 파일의 내용
     */
    public static void saveFile(File file, byte[] contents) {
        OutputStream os = null;
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            os = new FileOutputStream(file);
            os.write(contents);

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            if (os != null) try {
                os.close();
            } catch (Exception ex) {
            	
            }
        }
    }

    /**
     * 디렉토리를 복사한다.
     *
     * @param originalDirectory 원본 디렉토리
     * @param targetDirectory 타겟 디렉토리
     * @return 복사가 성공하면 true, 실패하면 false를 리턴한다.
     * @throws IOException
     *             @
     */
    public static boolean copyDirectory(File originalDirectory, File targetDirectory) throws IOException {

        boolean result = false;

        if (originalDirectory.exists() && originalDirectory.isDirectory()) {

            targetDirectory.mkdirs();

            String[] originalFileList = originalDirectory.list();
            if (originalFileList.length > 0) {
                for (int inx = 0; inx < originalFileList.length; inx++) {
                    File subF = new File(originalDirectory.getAbsolutePath() + FILE_SEPARATOR + originalFileList[inx]);
                    File out = new File(targetDirectory.getAbsolutePath() + FILE_SEPARATOR + originalFileList[inx]);

                    if (subF.isFile()) {
                        copyFile(subF, out);
                        result = true;
                    } else {
                        result = copyDirectory(subF, out);
                    }

                    if (!result) { return false; }
                }
            } else {
                result = true;
            }
        } else {
            throw new IllegalArgumentException(FileUtil.class.getName() + "-copyDirectory(File originalDirectory, File targetDirectory) ▶ " + "The input parameter(" + originalDirectory.getAbsolutePath() + ") does not exist.");
        }

        return result;
    }

    /**
     * 디렉토리를 복사한다. (생성일자 조건으로 복사)
     *
     * @param originalDirectory 원본 디렉토리
     * @param targetDirectory 타겟 디렉토리
     * @param fromDate 디렉토리의 복사조건 시작일자 (yyyymmdd)
     * @param toDate 디렉토리의 복사조건 종료일자 (yyyymmdd)
     * @return 복사가 성공하면 true, 실패하면 false를 리턴한다.
     * @throws IOException
     */
    public static boolean copyDirectory(File originalDirectory, File targetDirectory, String fromDate, String toDate) throws IOException {

        boolean inCondition = false;

        String lastModifyedDate = FileUtil.getLastModifiedDate(originalDirectory);
        if (Integer.parseInt(lastModifyedDate) >= Integer.parseInt(fromDate) && Integer.parseInt(lastModifyedDate) <= Integer.parseInt(toDate)) {
            inCondition = true;
        }

        boolean result = false;

        if (originalDirectory.exists() && originalDirectory.isDirectory()) {

            String[] originalFileList = originalDirectory.list();
            if (originalFileList.length > 0) {
                for (int inx = 0; inx < originalFileList.length; inx++) {
                    File subF = new File(originalDirectory.getAbsolutePath() + FILE_SEPARATOR + originalFileList[inx]);
                    File out = new File(targetDirectory.getAbsolutePath() + FILE_SEPARATOR + originalFileList[inx]);

                    if (subF.isFile()) {
                        if (inCondition) {
                            targetDirectory.mkdirs();

                            copyFile(subF, out);
                            result = true;
                        }
                    } else {
                        result = copyDirectory(subF, out, fromDate, toDate);
                    }
                }

                if (!result) { return false; }
            } else {
                targetDirectory.mkdirs();
                result = true;
            }
        } else {
            throw new IllegalArgumentException(FileUtil.class.getName() + "-copyDirectory(File originalDirectory, File targetDirectory, String fromDate, String toDate) ▶ " + "The input parameter(" + originalDirectory.getAbsolutePath()
                    + ") does not exist.");
        }

        return result;
    }

    /**
     * 디렉토리를 삭제한다.
     *
     * @param file 삭제하고자 하는디렉토리(파일의 경로가 들어오는 경우 삭제하지 않음)
     * @return 삭제가 성공하면 true, 실패하면 false를 리턴한다.
     */

    public static boolean deleteDirectory(File file) {

        boolean result = false;

        if (file.exists() && file.isDirectory()) {
            String[] files = file.list();

            for (int inx = 0; inx < files.length; inx++) {

                File f = new File(file.getAbsolutePath() + FILE_SEPARATOR + files[inx]);
                if (f.isFile()) {
                    result = f.delete();
                } else {
                    deleteDirectory(f);
                }
            }
            result = file.delete();
        } else {
            throw new IllegalArgumentException(FileUtil.class.getName() + "-deleteDirectory(File file) ▶ " + "The input parameter(" + file.getAbsolutePath() + ") is not a directory or does not exist.");
        }

        return result;
    }

    /**
     * 디렉토리의 사이즈를 조회한다.
     *
     * @param targetDirectory 디렉토리
     * @return 디렉토리사이즈
     */
    public static long getDirectorySize(File targetDirectory) {

        if (!targetDirectory.exists()) { return 0; }
        if (targetDirectory.isFile()) { return targetDirectory.length(); }

        File[] list = targetDirectory.listFiles();
        long size = 0;
        long fileSize = 0;

        for (int i = 0; i < list.length; i++) {

            if (list[i].isDirectory()) {
                fileSize = getDirectorySize(list[i]);
            } else {
                fileSize = list[i].length();
            }
            size = size + fileSize;
        }
        return size;
    }

    /**
     * 입력된 input byte array를 Contents를 Output File로 Copy한다.
     *
     * @param in Copy할 byte array
     * @param out targt File
     */
    public static void copyByteToFile(byte[] in, File out) {
        copyIoStream(new ByteArrayInputStream(in), getBufferedOutputStream(out));
    }

    /**
     * InputStream으로 넘어온 Content를 OutputStream에 Copy한다.
     *
     * @param in source의 InputStream
     * @param out target의 OutputStream
     */
    public static void copyIoStream(InputStream in, OutputStream out) {
        try {
            byte[] buffer = new byte[(int)BUFFER_SIZE];
            int nrOfBytes = -1;
            while ((nrOfBytes = in.read(buffer)) != -1) {
                out.write(buffer, 0, nrOfBytes);
            }
            out.flush();
        } catch (IOException ie) {
            throw new IllegalStateException("IO Exception has occurred.", ie);
        } finally {
            close(in);
            close(out);
        }
    }

    /**
     * InputStream으로 넘어온 Content를 OutputStream에 Copy한다.(단, copy 후 Stream을 close하지는 않는다.)
     *
     * @param in source의 InputStream
     * @param out target의 OutputStream
     */
    public static void copyIoStreamWithoutClose(InputStream in, OutputStream out) {
        try {
            byte[] buffer = new byte[(int)BUFFER_SIZE];
            int nrOfBytes = -1;
            while ((nrOfBytes = in.read(buffer)) != -1) {
                out.write(buffer, 0, nrOfBytes);
            }
            out.flush();
        } catch (IOException ie) {
            throw new IllegalStateException("IO Exception has occurred.", ie);
        }
    }

    /**
     * byte array의 Content를 OutputStream에 Copy한다.
     *
     * @param in source byte array
     * @param out target의 OutputStream
     */
    public static void copyByteToIoStream(byte[] in, OutputStream out) {
        copyIoStream(new ByteArrayInputStream(in), out);
    }

    /**
     * 입력된 파일의 Content를 byte array로 반환한다.
     *
     * @param in Copy할 File
     * @return byte[] 복사된 byte array값
     */
    public static byte[] converterByteArray(File in) {
        return converterByteArray(getBufferedInputStream(in));
    }

    /**
     * 입력된 InputStream의 Content를 byte array로 반환한다.
     *
     * @param in source InputStream
     * @return byte[] target byte array
     */
    public static byte[] converterByteArray(InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copyIoStream(in, out);
        return out.toByteArray();
    }

    /**
     * 파일을 무조건 덮어쓰게 하지 않기 위해, BackUp 파일을 생성한다.
     * <p> 날짜 패턴으로 File 객체를 생성해준다
     *
     * @param originalFile 원본 파일
     * @param pattern 새로 만들 파일명에 해당하는 패턴
     * @return 백업 File 객체
     */
    public static File getBackupFile(File originalFile, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        String timestamp = dateFormat.format(new java.util.Date());
        String newFile = originalFile.getAbsolutePath();
        File resultFile = new File(getFileNameWithoutExtension(newFile) + "[" + timestamp + "]." + getExtension(newFile));
        while (resultFile.exists()) {
            newFile = resultFile.getAbsolutePath();
            resultFile = new File(getFileNameWithoutExtension(newFile) + "[" + timestamp + "]." + getExtension(newFile));
        }
        return resultFile;
    }

    /**
     * 파일명(경로)를 URLEncoder의 encode 처리해서 반환한다.
     * <p> '\\+' 은' _' 로 , '%' 은 '=' 로 변경한다
     *
     * @param fileName 파일명
     * @param encoding 인코딩
     * @return String
     * @throws UnsupportedEncodingException
     */
    public static String encodeQuotedPrintable(String fileName, String encoding) throws UnsupportedEncodingException {
        String urlEncodingFileName = URLEncoder.encode(fileName, encoding);
        urlEncodingFileName = urlEncodingFileName.replaceAll("\\+", "_");
        urlEncodingFileName = urlEncodingFileName.replaceAll("%", "=");

        return urlEncodingFileName;
    }

    /**
     * 파일명(경로)를 URLEncoder의 encode 처리해서 반환한다.
     * <p> '\\+' 은 '%20' 로 변경한다.
     *
     * @param fileName 파일명
     * @param encoding 인코딩
     * @return String
     * @throws UnsupportedEncodingException
     */
    public static String encodeURLEncoding(String fileName, String encoding) throws UnsupportedEncodingException {
        String urlEncodingFileName = fileName;
        urlEncodingFileName = URLEncoder.encode(urlEncodingFileName, encoding);
        urlEncodingFileName = urlEncodingFileName.replaceAll("\\+", "%20");
        return urlEncodingFileName;
    }

    /**
     * String 포맷으로 지정된 file size를 long type의 숫자값으로 반환한다.
     * (접미사 포맷은 대소문자를 구별하지 않음)
     * <p>
     * parseStringFormatFileSize("512") -> 512
     * parseStringFormatFileSize("512B") -> 512
     * parseStringFormatFileSize("512byte") -> 512
     * parseStringFormatFileSize("128KB") -> 154112 [128 * 1024]
     * parseStringFormatFileSize("10MB") -> 10485760 [10 * 1024 * 1024]
     * parseStringFormatFileSize("1GB") -> 1073741824 [1 * 1024 * 1024 * 1024]
     *
     * @param fileSize
     * @return
     */
    public static long parseStringFormatFileSize(String fileSize) {
        try {
            if(fileSize.toUpperCase().endsWith("KB")){
                return Long.parseLong(fileSize.substring(0, fileSize.length()-2).trim()) * 1024;

            }else if(fileSize.toUpperCase().endsWith("MB")){
                return Long.parseLong(fileSize.substring(0, fileSize.length()-2).trim()) * 1024 * 1024;

            }else if(fileSize.toUpperCase().endsWith("GB")){
                return Long.parseLong(fileSize.substring(0, fileSize.length()-2).trim()) * 1024 * 1024 * 1024;

            }else if(fileSize.toUpperCase().endsWith("B")){
                return Long.parseLong(fileSize.substring(0, fileSize.length()-1).trim());

            }else if(fileSize.toUpperCase().endsWith("BYTE")){
                return Long.parseLong(fileSize.substring(0, fileSize.length()-4).trim());
            }

            return Long.parseLong(fileSize);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Unsupported file size suffix : " + fileSize);
        }
    }

//    /**
//     * InputStream을 close한다.
//     *
//     * @param is close할 InputStream객체
//     */
//    public static void close(InputStream is) {
//        if(is != null) {
//            try {
//                is.close();
//            } catch (Exception e) {
//            	
//            }
//        }
//    }

//    /**
//     * OutputStream을 close한다.
//     *
//     * @param os close할 OutputStream객체
//     */
//    public static void close(OutputStream os) {
//        if(os != null) {
//            try {
//                os.close();
//            } catch (Exception e) {
//            	
//            }
//        }
//    }

//    /**
//     * Reader를 close한다.
//     *
//     * @param reader close할 Reader객체
//     */
//    public static void close(Reader reader) {
//        if(reader != null) {
//            try {
//                reader.close();
//            } catch (Exception e) {
//            	
//            }
//        }
//    }

//    /**
//     * Writer를 close한다.
//     *
//     * @param writer close할 Writer객체
//     */
//    public static void close(Writer writer) {
//        if(writer != null) {
//            try {
//                writer.close();
//            } catch (Exception e) {
//            	
//            }
//        }
//    }

    /**
     * 파일 경로에 대한 String 배열을 File 배열로 변환하여 반환한다.
     *
     * @param filenames 파일 경로에 대한 String 배열
     * @return 변환된 File 배열
     */
    public static File[] convertToFileArray(String[] filenames) {
        File[] files = new File[filenames.length];
        for (int i = 0; i < filenames.length; i++) {
            files[i] = new File(filenames[i]);
        }
        return files;
    }

    /**
     * 입력된 File 객체를 OutputStream 으로 변환한다.
     *
     * @param file File명
     * @return OutputStream
     */
    private static OutputStream getBufferedOutputStream(File file) {
        try {
            return new BufferedOutputStream(new FileOutputStream(file));
        } catch (IOException ie) {
            throw new IllegalStateException("IO Exception has occurred.", ie);
        }
    }

    /**
     * 입력된 File 객체를 InputStream 으로 변환한다.
     *
     * @param file File명
     * @return InputStream
     */
    private static InputStream getBufferedInputStream(File file) {
        try {
            return new BufferedInputStream(new FileInputStream(file));
        } catch (IOException ie) {
            throw new IllegalStateException("IO Exception has occurred.", ie);
        }
    }
	
}
