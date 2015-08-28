package library.util;



/**
 * ClassName: FileUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午4:59:16 <br/>
 * 文件工具类，可用于读写文件及对文件进行操作。<br/>
 * 如： readFile(String filePath) 读文件 <br/>
 * writeFile(StringfilePath, String content,boolean append) 写入文件 <br/>
 * getFileSize(String path) 得到文件大小 <br/>
 * deleteFile(String path) 删除文件 <br/>
 * 
 * @author chenhao
 * @version
 * @since JDK 1.6
 */
public class FileUtils {

	public final static String FILE_EXTENSION_SEPARATOR = ".";

	private FileUtils() {
		throw new AssertionError();
	}

	/**
	 * 读取文件
	 * 
	 * @param 文件路径
	 * @param 字符集名称一个受支持的名称
	 * @return 如果文件不存在,返回null,否则返回文件的内容
	 * @throws RuntimeException
	 *             if an error occurs while operator BufferedReader
	 */
	public static StringBuilder readFile(String filePath, String charsetName) {
		java.io.File file = new java.io.File(filePath);
		StringBuilder fileContent = new StringBuilder("");
		if (file == null || !file.isFile()) {
			return null;
		}

		java.io.BufferedReader reader = null;
		try {
			java.io.InputStreamReader is = new java.io.InputStreamReader(new java.io.FileInputStream(file), charsetName);
			reader = new java.io.BufferedReader(is);
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (!fileContent.toString().equals("")) {
					fileContent.append("\r\n");
				}
				fileContent.append(line);
			}
			reader.close();
			return fileContent;
		} catch (java.io.IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (java.io.IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * 写文件
	 * 
	 * @param 文件路径
	 * @param 文件
	 * @param 附加是附加的
	 *            ,如果这是真的,其他写的文件,明确内容的文件和写进去
	 * @return 否则返回假如果内容是空的,真的
	 * @throws RuntimeException
	 *             if an error occurs while operator FileWriter
	 */
	public static boolean writeFile(String filePath, String content, boolean append) {
		if (StringUtils.isEmpty(content)) {
			return false;
		}

		java.io.FileWriter fileWriter = null;
		try {
			makeDirs(filePath);
			fileWriter = new java.io.FileWriter(filePath, append);
			fileWriter.write(content);
			fileWriter.close();
			return true;
		} catch (java.io.IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (java.io.IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * 写文件
	 * 
	 * @param 文件路径
	 * @param 内容列表
	 * @param 附加是附加的
	 *            ,如果这是真的,其他写的文件,明确内容的文件和写进去
	 * @return 否则返回假如果contentList是空的,真的
	 * @throws RuntimeException
	 *             if an error occurs while operator FileWriter
	 */
	public static boolean writeFile(String filePath, java.util.List<String> contentList, boolean append) {
		if (ListUtils.isEmpty(contentList)) {
			return false;
		}

		java.io.FileWriter fileWriter = null;
		try {
			makeDirs(filePath);
			fileWriter = new java.io.FileWriter(filePath, append);
			int i = 0;
			for (String line : contentList) {
				if (i++ > 0) {
					fileWriter.write("\r\n");
				}
				fileWriter.write(line);
			}
			fileWriter.close();
			return true;
		} catch (java.io.IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (java.io.IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * 写文件,字符串将被写入文件的开始
	 * 
	 * @param 文件路径
	 * @param content
	 * @return
	 */
	public static boolean writeFile(String filePath, String content) {
		return writeFile(filePath, content, false);
	}

	/**
	 * 写文件、字符串列表将被写入到文件的开始
	 * 
	 * @param 文件路径
	 * @param contentList
	 * @return
	 */
	public static boolean writeFile(String filePath, java.util.List<String> contentList) {
		return writeFile(filePath, contentList, false);
	}

	/**
	 * 写文件,将字节写入文件的开始
	 * 
	 * @param filePath
	 * @param stream
	 * @return
	 * @see {@link #writeFile(String, InputStream, boolean)}
	 */
	public static boolean writeFile(String filePath, java.io.InputStream stream) {
		return writeFile(filePath, stream, false);
	}

	/**
	 * 写文件
	 * 
	 * @param 文件写的文件被打开
	 * @param 流的输入流
	 * @param 　　附加 如果为 ture,然后将写入的字节数文件,而不是开始
	 * @return 返回 TRUE
	 * @throws RuntimeException
	 *             if an error occurs while operator FileOutputStream
	 */
	public static boolean writeFile(String filePath, java.io.InputStream stream, boolean append) {
		return writeFile(filePath != null ? new java.io.File(filePath) : null, stream, append);
	}

	/**
	 * 写文件,将字节写入文件的开始
	 * 
	 * @param file
	 * @param stream
	 * @return
	 * @see {@link #writeFile(File, InputStream, boolean)}
	 */
	public static boolean writeFile(java.io.File file, java.io.InputStream stream) {
		return writeFile(file, stream, false);
	}

	/**
	 * 写文件
	 * 
	 * @param 文件写的文件被打开
	 *            。
	 * @param 流的输入流
	 * @param 附加
	 *            如果为 ture,然后将写入的字节数文件,而不是开始
	 * @return return true
	 * @throws RuntimeException
	 *             如果运行时FileOutputStream时发生错误
	 */
	public static boolean writeFile(java.io.File file, java.io.InputStream stream, boolean append) {
		java.io.OutputStream o = null;
		try {
			makeDirs(file.getAbsolutePath());
			o = new java.io.FileOutputStream(file, append);
			byte data[] = new byte[1024];
			int length = -1;
			while ((length = stream.read(data)) != -1) {
				o.write(data, 0, length);
			}
			o.flush();
			return true;
		} catch (java.io.FileNotFoundException e) {
			throw new RuntimeException("FileNotFoundException occurred. ", e);
		} catch (java.io.IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (o != null) {
				try {
					o.close();
					stream.close();
				} catch (java.io.IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * 复制文件
	 * 
	 * @param 源文件
	 * @param 目的文件路径
	 * @return
	 * @throws RuntimeException
	 *             如果运行时FileOutputStream时发生错误
	 */
	public static boolean copyFile(String sourceFilePath, String destFilePath) {
		java.io.InputStream inputStream = null;
		try {
			inputStream = new java.io.FileInputStream(sourceFilePath);
		} catch (java.io.FileNotFoundException e) {
			throw new RuntimeException("FileNotFoundException occurred. ", e);
		}
		return writeFile(destFilePath, inputStream);
	}

	/**
	 * 读文件的字符串列表,列表的元素是一条线
	 * 
	 * @param 文件路径
	 * @param 字符集名称一个支持的名字
	 * @return 如果文件不存在,返回null,否则返回文件的内容
	 * @throws RuntimeException
	 *             如果为空责返回异常
	 */
	public static java.util.List<String> readFileToList(String filePath, String charsetName) {
		java.io.File file = new java.io.File(filePath);
		java.util.List<String> fileContent = new java.util.ArrayList<String>();
		if (file == null || !file.isFile()) {
			return null;
		}

		java.io.BufferedReader reader = null;
		try {
			java.io.InputStreamReader is = new java.io.InputStreamReader(new java.io.FileInputStream(file), charsetName);
			reader = new java.io.BufferedReader(is);
			String line = null;
			while ((line = reader.readLine()) != null) {
				fileContent.add(line);
			}
			reader.close();
			return fileContent;
		} catch (java.io.IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (java.io.IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * 从路径获得文件名,不包括后缀
	 * 
	 * <pre>
	 *      getFileNameWithoutExtension(null)               =   null
	 *      getFileNameWithoutExtension("")                 =   ""
	 *      getFileNameWithoutExtension("   ")              =   "   "
	 *      getFileNameWithoutExtension("abc")              =   "abc"
	 *      getFileNameWithoutExtension("a.mp3")            =   "a"
	 *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
	 *      getFileNameWithoutExtension("c:\\")              =   ""
	 *      getFileNameWithoutExtension("c:\\a")             =   "a"
	 *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
	 *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
	 *      getFileNameWithoutExtension("/home/admin")      =   "admin"
	 *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
	 * </pre>
	 * 
	 * @param 文件路径
	 * @return 从路径文件名,不包括后缀
	 * @see
	 */
	public static String getFileNameWithoutExtension(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return filePath;
		}

		int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		int filePosi = filePath.lastIndexOf(java.io.File.separator);
		if (filePosi == -1) {
			return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
		}
		if (extenPosi == -1) {
			return filePath.substring(filePosi + 1);
		}
		return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
	}

	/**
	 * 从路径获得文件名,包括后缀
	 * 
	 * <pre>
	 *      getFileName(null)               =   null
	 *      getFileName("")                 =   ""
	 *      getFileName("   ")              =   "   "
	 *      getFileName("a.mp3")            =   "a.mp3"
	 *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
	 *      getFileName("abc")              =   "abc"
	 *      getFileName("c:\\")              =   ""
	 *      getFileName("c:\\a")             =   "a"
	 *      getFileName("c:\\a.b")           =   "a.b"
	 *      getFileName("c:a.txt\\a")        =   "a"
	 *      getFileName("/home/admin")      =   "admin"
	 *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
	 * </pre>
	 * 
	 * @param 文件路径
	 * @return file name from path, include suffix
	 */
	public static String getFileName(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return filePath;
		}

		int filePosi = filePath.lastIndexOf(java.io.File.separator);
		return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
	}

	/**
	 * 从路径获取文件夹名称
	 * 
	 * <pre>
	 *      getFolderName(null)               =   null
	 *      getFolderName("")                 =   ""
	 *      getFolderName("   ")              =   ""
	 *      getFolderName("a.mp3")            =   ""
	 *      getFolderName("a.b.rmvb")         =   ""
	 *      getFolderName("abc")              =   ""
	 *      getFolderName("c:\\")              =   "c:"
	 *      getFolderName("c:\\a")             =   "c:"
	 *      getFolderName("c:\\a.b")           =   "c:"
	 *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
	 *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
	 *      getFolderName("/home/admin")      =   "/home"
	 *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
	 * </pre>
	 * 
	 * @param 文件路径
	 * @return
	 */
	public static String getFolderName(String filePath) {

		if (StringUtils.isEmpty(filePath)) {
			return filePath;
		}

		int filePosi = filePath.lastIndexOf(java.io.File.separator);
		return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
	}

	/**
	 * 后缀的文件路径
	 * 
	 * <pre>
	 *      getFileExtension(null)               =   ""
	 *      getFileExtension("")                 =   ""
	 *      getFileExtension("   ")              =   "   "
	 *      getFileExtension("a.mp3")            =   "mp3"
	 *      getFileExtension("a.b.rmvb")         =   "rmvb"
	 *      getFileExtension("abc")              =   ""
	 *      getFileExtension("c:\\")              =   ""
	 *      getFileExtension("c:\\a")             =   ""
	 *      getFileExtension("c:\\a.b")           =   "b"
	 *      getFileExtension("c:a.txt\\a")        =   ""
	 *      getFileExtension("/home/admin")      =   ""
	 *      getFileExtension("/home/admin/a.txt/b")  =   ""
	 *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
	 * </pre>
	 * 
	 * @param 文件路径
	 * @return
	 */
	public static String getFileExtension(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			return filePath;
		}

		int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		int filePosi = filePath.lastIndexOf(java.io.File.separator);
		if (extenPosi == -1) {
			return "";
		}
		return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
	}

	/**
	 * 创建的目录命名为落后于这个文件的文件名,包括所需的完整的目录路径创建此目录. <br/>
	 * <br/>
	 * <ul>
	 * <strong>Attentions:</strong>
	 * <li>makeDirs("C:\\Users\\Trinea") can only create users folder</li>
	 * <li>makeFolder("C:\\Users\\Trinea\\") can create Trinea folder</li>
	 * </ul>
	 * 
	 * @param 文件路径
	 * @return 　　真的如果必要的目录被创建或目标目录已经存在,不能假的一个目录创建。 　　
	 *         < ul >
	 *         <li>如果{ @link FileUtils # getFolderName(字符串)}返回null,返回false < / >
	 *         <li>如果目标目录已经存在,返回true< / > 　　
	 *         <li>返回{ @link java.io.File # makeFolder }
	 *         < / ul >
	 */
	public static boolean makeDirs(String filePath) {
		String folderName = getFolderName(filePath);
		if (StringUtils.isEmpty(folderName)) {
			return false;
		}

		java.io.File folder = new java.io.File(folderName);
		return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
	}

	/**
	 * @param 文件路径
	 * @return
	 * @see #makeDirs(String)
	 */
	public static boolean makeFolders(String filePath) {
		return makeDirs(filePath);
	}

	/**
	 * 表明如果这个文件代表底层文件系统上的文件。
	 * 
	 * @param 文件路径
	 * @return
	 */
	public static boolean isFileExist(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			return false;
		}

		java.io.File file = new java.io.File(filePath);
		return (file.exists() && file.isFile());
	}

	/**
	 * 表明如果这个文件代表底层文件上的一个目录系统上的文件.
	 * 
	 * @param 目录路径
	 * @return
	 */
	public static boolean isFolderExist(String directoryPath) {
		if (StringUtils.isBlank(directoryPath)) {
			return false;
		}

		java.io.File dire = new java.io.File(directoryPath);
		return (dire.exists() && dire.isDirectory());
	}

	/**
	 * 删除文件或目录
	 * <ul>
	 * <li>如果路径是null或空,返回true</li>
	 * <li>如果路径不存在,返回true</li>
	 * <li>如果路径存在,删除递归。还真</li>
	 * <ul>
	 * 
	 * @param path
	 * @return
	 */
	public static boolean deleteFile(String path) {
		if (StringUtils.isBlank(path)) {
			return true;
		}

		java.io.File file = new java.io.File(path);
		if (!file.exists()) {
			return true;
		}
		if (file.isFile()) {
			return file.delete();
		}
		if (!file.isDirectory()) {
			return false;
		}
		for (java.io.File f : file.listFiles()) {
			if (f.isFile()) {
				f.delete();
			} else if (f.isDirectory()) {
				deleteFile(f.getAbsolutePath());
			}
		}
		return file.delete();
	}

	/**
	 * 获取文件大小
	 * <ul>
	 * <li>如果路径是null或空,返回- 1</li>
	 * <li>如果路径存在,这是一个文件,返回文件大小,否则返回- 1</li>
	 * <ul>
	 * 
	 * @param path
	 * @return 　　返回该文件的长度字节。如果文件返回1不存在.
	 */
	public static long getFileSize(String path) {
		if (StringUtils.isBlank(path)) {
			return -1;
		}

		java.io.File file = new java.io.File(path);
		return (file.exists() && file.isFile() ? file.length() : -1);
	}

	/**
	 * 检验SDcard状态
	 * 
	 * @return boolean
	 */
	public static boolean checkSDCard() {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 保存文件文件到目录
	 * 
	 * @param context
	 * @return 文件保存的目录
	 */
	public static String setMkdir(android.content.Context context) {
		String filePath;
		if (checkSDCard()) {
			filePath = android.os.Environment.getExternalStorageDirectory() + java.io.File.separator + "myfile";
		} else {
			filePath = context.getCacheDir().getAbsolutePath() + java.io.File.separator + "myfile";
		}
		java.io.File file = new java.io.File(filePath);
		if (!file.exists()) {
			boolean b = file.mkdirs();
			android.util.Log.e("file", "文件不存在  创建文件    " + b);
		} else {
			android.util.Log.e("file", "文件存在");
		}
		return filePath;
	}

	/**
	 * 从文件读取json字符串
	 * 
	 * @param path
	 *            目录
	 * @param name
	 *            文件名称
	 * @return 返回字符串
	 */
	public static String readJsonFromFile(String path, String name) {
		if (StringUtils.isEmpty(path) || StringUtils.isEmpty(name)) {
			return null;
		}
		String content = "";
		java.io.File file = new java.io.File(path + name);
		if (!file.exists()) {
			return null;
		}
		java.io.InputStream in = null;
		try {
			in = new java.io.FileInputStream(file);
			if (in != null) {
				java.io.InputStreamReader inputreader = new java.io.InputStreamReader(in);
				java.io.BufferedReader buffreader = new java.io.BufferedReader(inputreader);
				String line;
				// 分行读取
				while ((line = buffreader.readLine()) != null) {
					content += line;
				}
				in.close();
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
			return null;
		}
		return content;
	}

	/**
	 * json写入文件
	 * 
	 * @param path
	 *            目录
	 * @param name
	 *            文件名称
	 * @param content
	 *            json内容
	 * @return true:写入成功;false:写入失败
	 */
	public static boolean writeJsonIntoFile(String path, String name, String content) {
		if (StringUtils.isEmpty(path) || StringUtils.isEmpty(name) || StringUtils.isEmpty(content)) {
			return false;
		}
		java.io.File file = new java.io.File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		java.io.OutputStreamWriter out = null;
		try {
			out = new java.io.OutputStreamWriter(new java.io.FileOutputStream(new java.io.File(path + name)));
			out.write(content);
			out.flush();
			out.close();
		} catch (java.io.IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 创建文件夹
	 * 
	 * @param dirName
	 */
	public static void MakeDir(String dirName) {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			java.io.File destDir = new java.io.File(dirName);
			if (!destDir.exists()) {
				destDir.mkdirs();
			}
		}
	}

	/**
	 * 删除此路径名表示的文件或目录。 如果此路径名表示一个目录，则会先删除目录下的内容再将目录删除，所以该操作不是原子性的。
	 * 如果目录中还有目录，则会引发递归动作。
	 * 
	 * @param filePath
	 *            要删除文件或目录的路径。
	 * @return 当且仅当成功删除文件或目录时，返回 true；否则返回 false。
	 */
	public static boolean DeleteFile(String filePath) {
		java.io.File file = new java.io.File(filePath);
		if (file.listFiles() == null)
			return true;
		else {
			java.io.File[] files = file.listFiles();
			for (java.io.File deleteFile : files) {
				if (deleteFile.isDirectory())
					DeleteAllFile(deleteFile);
				else
					deleteFile.delete();
			}
		}
		return true;
	}

	/**
	 * 删除全部文件
	 * 
	 * @param file
	 * @return
	 */
	private static boolean DeleteAllFile(java.io.File file) {
		java.io.File[] files = file.listFiles();
		for (java.io.File deleteFile : files) {
			if (deleteFile.isDirectory()) {
				// 如果是文件夹，则递归删除下面的文件后再删除该文件夹
				if (!DeleteAllFile(deleteFile)) {
					// 如果失败则返回
					return false;
				}
			} else {
				if (!deleteFile.delete()) {
					// 如果失败则返回
					return false;
				}
			}
		}
		return file.delete();
	}

	/**
	 * 得到数据库文件路径
	 * 
	 * @return
	 */
	public static String GetDbFileAbsolutePath() {
		// String dbPath="/data/data/" + Config.APP_PACKAGE_NAME + "/databases/"
		// + Config.DB_FILE_NAME;
		// return dbPath;
		return "";
	}

	/**
	 * 读取文件大小
	 * 
	 * @param filePath
	 * @return
	 */
	public static long GetFileLength(String filePath) {
		java.io.File file = new java.io.File(filePath);
		return file.length();
	}

	/**
	 * 读取文件夹大小
	 * 
	 * @param dirPath
	 * @return
	 */
	public static long GetPathLength(String dirPath) {
		java.io.File dir = new java.io.File(dirPath);
		if (dir == null || !dir.exists()) {
			return 0;
		}
		return getDirSize(dir);
	}

	/**
	 * 读取文件夹大小
	 * 
	 * @param dir
	 * @return
	 */
	public static long getDirSize(java.io.File dir) {
		if (dir == null) {
			return 0;
		}
		if (!dir.isDirectory()) {
			return 0;
		}
		long dirSize = 0;
		java.io.File[] files = dir.listFiles();
		for (java.io.File file : files) {
			if (file.isFile()) {
				dirSize += file.length();
			} else if (file.isDirectory()) {
				dirSize += file.length();
				dirSize += getDirSize(file); // 如果遇到目录则通过递归调用继续统计
			}
		}
		return dirSize;
	}

	/**
	 * 将字长长度转换为KB/MB
	 * 
	 * @param size
	 * @return
	 */
	public static String GetFileSize(long size) {
		int kbSize = (int) size / 1024;
		if (kbSize > 1024) {
			float mbSize = kbSize / 1024;
			java.text.DecimalFormat formator = new java.text.DecimalFormat("##,###,###.## ");
			return formator.format(mbSize) + "M";
		}
		return kbSize + "K";
	}

	/**
	 * 复制文件
	 * 
	 * @param path1
	 *            源文件
	 * @param path2
	 *            目标文件
	 * @return
	 */
	public static boolean copyFileTo(String path1, String path2) {
		if (StringUtils.isEmpty(path1) || StringUtils.isEmpty(path2)) {
			return false;
		}
		java.io.File f1 = new java.io.File(path1);
		java.io.File f2 = new java.io.File(path2);
		if (!f1.exists()) {
			return false;
		}
		try {
			copyFileforChannel(f1, f2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 用管道对接的方式复制文件
	 * 
	 * @param f1
	 *            源文件
	 * @param f2
	 *            目标文件
	 * @throws Exception
	 */
	public static void copyFileforChannel(java.io.File f1, java.io.File f2) throws Exception {
		int length = 2097152;
		java.io.FileInputStream in = new java.io.FileInputStream(f1);
		java.io.FileOutputStream out = new java.io.FileOutputStream(f2);
		java.nio.channels.FileChannel inC = in.getChannel();
		java.nio.channels.FileChannel outC = out.getChannel();
		java.nio.ByteBuffer b = null;
		while (true) {
			if (inC.position() == inC.size()) {
				inC.close();
				outC.close();
			}
			if ((inC.size() - inC.position()) < length) {
				length = (int) (inC.size() - inC.position());
			} else
				length = 2097152;
			b = java.nio.ByteBuffer.allocateDirect(length);
			inC.read(b);
			b.flip();
			outC.write(b);
			outC.force(false);
		}
	}

	/**
	 * 
	 * clearBuffer:(清除该应用所有的图片缓存). <br/>
	 * 
	 * @author chenhao
	 * @since JDK 1.6
	 */
	public static void clearBuffer() {
		library.util.FileUtils.DeleteFile(library.controller.FrameConfigure.NORMAL_IMG_DRC);
	}
}
