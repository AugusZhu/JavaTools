package util;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Java工具类合集
 * 
 * <p>
 * 整合了日常开发中常用的工具方法，包括AES加密解密、MD5加密、UUID生成、
 * 文件操作、ZIP压缩、HTTP请求、编码转换等功能。
 * </p>
 * 
 * <p>
 * <b>使用示例：</b>
 * </p>
 * <pre>
 * import util.Util;
 * 
 * // AES加密
 * String encrypted = Util.aesEncrypt("Hello", "1234567890123456");
 * 
 * // MD5加密
 * String md5 = Util.md5("test");
 * 
 * // UUID生成
 * String uuid = Util.uuid();
 * 
 * // 文件操作
 * Util.copyFile("a.txt", "b.txt");
 * 
 * // ZIP压缩
 * Util.zip("folder/", "out.zip");
 * 
 * // HTTP请求
 * String result = Util.httpGet("https://example.com");
 * </pre>
 * 
 * @author Xianfei Zhu
 * @version 2.0
 * @since 1.0
 */
public final class Util {

    /** 文件操作缓冲区大小（10KB） */
    private static final int BUFFER = 10240;

    /** ZIP压缩缓冲区大小（8KB） */
    private static final int ZIP_BUFFER = 8192;

    /** 十六进制字符表 */
    private static final char[] HEX = "0123456789abcdef".toCharArray();

    /** 私有构造器，禁止实例化 */
    private Util() {
    }

    // ==================== AES加密解密 ====================

    /**
     * AES加密（ECB模式，PKCS5Padding填充）
     *
     * <p>
     * 将明文加密为十六进制字符串格式的密文
     * </p>
     *
     * @param data 明文内容
     * @param key  密钥，必须为16位字符
     * @return 加密后的十六进制字符串，参数无效或加密失败返回null
     * @see #aesDecrypt(String, String)
     * @example
     * <pre>
     * String encrypted = Util.aesEncrypt("Hello World", "1234567890123456");
     * // 返回类似：a5f8d9e2c3b1...
     * </pre>
     */
    public static String aesEncrypt(String data, String key) {
        return aesProcess(data, key, javax.crypto.Cipher.ENCRYPT_MODE);
    }

    /**
     * AES解密（ECB模式，PKCS5Padding填充）
     *
     * <p>
     * 将十六进制字符串格式的密文解密为明文
     * </p>
     *
     * @param data 十六进制格式的密文
     * @param key  密钥，必须为16位字符
     * @return 解密后的明文，参数无效或解密失败返回null
     * @see #aesEncrypt(String, String)
     * @example
     * <pre>
     * String decrypted = Util.aesDecrypt("a5f8d9e2c3b1...", "1234567890123456");
     * // 返回：Hello World
     * </pre>
     */
    public static String aesDecrypt(String data, String key) {
        return aesProcess(data, key, javax.crypto.Cipher.DECRYPT_MODE);
    }

    /**
     * AES加解密核心处理方法
     *
     * @param data 待处理数据
     * @param key  密钥
     * @param mode 处理模式：ENCRYPT_MODE或DECRYPT_MODE
     * @return 处理结果
     */
    private static String aesProcess(String data, String key, int mode) {
        try {
            // 参数校验
            if (data == null || key == null || key.length() != 16) {
                return null;
            }

            // 根据模式转换数据
            byte[] content = mode == javax.crypto.Cipher.ENCRYPT_MODE
                    ? data.getBytes(StandardCharsets.UTF_8)
                    : hexToBytes(data);

            if (content == null) {
                return null;
            }

            // 构建AES密钥规范
            javax.crypto.spec.SecretKeySpec keySpec = new javax.crypto.spec.SecretKeySpec(
                    key.getBytes(StandardCharsets.UTF_8), "AES");

            // 初始化Cipher
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(mode, keySpec);

            // 执行加解密
            byte[] result = cipher.doFinal(content);

            // 返回结果
            return mode == javax.crypto.Cipher.ENCRYPT_MODE
                    ? bytesToHex(result)
                    : new String(result, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    // ==================== MD5加密 ====================

    /**
     * MD5加密（单向哈希）
     *
     * <p>
     * 将输入字符串转换为32位小写MD5哈希值。
     * MD5加密是单向的，不可解密，仅用于生成数据指纹。
     * </p>
     *
     * <p>
     * <b>注意：</b>由于MD5算法存在安全漏洞，仅适用于数据完整性校验，
     * 不适用于安全加密场景。
     * </p>
     *
     * @param data 待加密字符串
     * @return 32位小写十六进制MD5值，输入为null时返回null
     * @example
     * <pre>
     * String hash = Util.md5("Hello World");
     * // 返回：b10a8db164e0754105b7a99be72e3fe5
     * </pre>
     */
    public static String md5(String data) {
        if (data == null) {
            return null;
        }
        try {
            // 获取MD5算法实例并计算哈希值
            byte[] bytes = MessageDigest.getInstance("MD5").digest(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(bytes);
        } catch (Exception e) {
            return null;
        }
    }

    // ==================== UUID ====================

    /**
     * 生成随机UUID（32位，无连字符）
     *
     * <p>
     * 基于随机数生成UUID Version 4，返回不带连字符的32位字符串
     * </p>
     *
     * @return 32位小写UUID字符串
     * @see #uuidWithDash()
     * @see #uuidOf(String)
     * @example
     * <pre>
     * String uuid = Util.uuid();
     * // 返回：550e8400e29b41d4a716446655440000
     * </pre>
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成随机UUID（36位，带连字符）
     *
     * <p>
     * 基于随机数生成UUID Version 4，返回标准格式（36位含连字符）
     * </p>
     *
     * @return 36位小写UUID字符串（带连字符）
     * @see #uuid()
     * @example
     * <pre>
     * String uuid = Util.uuidWithDash();
     * // 返回：550e8400-e29b-41d4-a716-446655440000
     * </pre>
     */
    public static String uuidWithDash() {
        return UUID.randomUUID().toString();
    }

    /**
     * 根据字符串生成UUID（基于名称）
     *
     * <p>
     * 基于输入字符串生成UUID Version 3（MD5）。
     * 相同输入always生成相同的UUID，适用于基于名称生成唯一标识符。
     * </p>
     *
     * @param name 输入字符串
     * @return 32位小写UUID字符串
     * @see #uuid()
     * @example
     * <pre>
     * String uuid = Util.uuidOf("test");
     * // 返回：c81d4e2e8b2d11ec9f3e0800200c9a66
     * </pre>
     */
    public static String uuidOf(String name) {
        return UUID.nameUUIDFromBytes(name.getBytes()).toString().replace("-", "");
    }

    // ==================== 文件操作 ====================

    /**
     * 复制文件
     *
     * <p>
     * 将源文件复制到目标位置。如果目标目录不存在，会自动创建。
     * </p>
     *
     * @param source 源文件路径
     * @param target 目标文件路径
     * @throws RuntimeException 当源文件不存在或复制失败时抛出
     * @see #copyDir(String, String)
     * @example
     * <pre>
     * Util.copyFile("C:\\source.txt", "D:\\backup\\target.txt");
     * </pre>
     */
    public static void copyFile(String source, String target) {
        File src = new File(source);
        if (!src.exists()) {
            throw new RuntimeException("源文件不存在: " + source);
        }
        // 确保目标目录存在
        new File(target).getParentFile().mkdirs();

        try (FileInputStream in = new FileInputStream(src);
             FileOutputStream out = new FileOutputStream(target)) {
            byte[] buf = new byte[BUFFER];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
        } catch (IOException e) {
            throw new RuntimeException("文件复制失败: " + e.getMessage(), e);
        }
    }

    /**
     * 复制目录
     *
     * <p>
     * 递归复制整个目录结构到目标位置
     * </p>
     *
     * @param source 源目录路径
     * @param target 目标目录路径
     * @throws RuntimeException 当源目录不存在或复制失败时抛出
     * @see #copyFile(String, String)
     * @example
     * <pre>
     * Util.copyDir("C:\\sourceDir", "D:\\backup\\targetDir");
     * </pre>
     */
    public static void copyDir(String source, String target) {
        File src = new File(source);
        File dest = new File(target);

        if (!src.exists() || !src.isDirectory()) {
            throw new RuntimeException("源目录不存在: " + source);
        }
        // 创建目标目录
        dest.mkdirs();

        File[] files = src.listFiles();
        if (files == null) return;

        for (File f : files) {
            String path = target + File.separator + f.getName();
            if (f.isDirectory()) {
                // 递归复制子目录
                copyDir(f.getAbsolutePath(), path);
            } else {
                // 复制文件
                copyFile(f.getAbsolutePath(), path);
            }
        }
    }

    /**
     * 移动文件
     *
     * <p>
     * 将文件移动到目标位置（相当于剪切操作）。
     * 如果目标目录不存在会自动创建。
     * </p>
     *
     * @param source 源文件路径
     * @param target 目标文件路径
     * @throws RuntimeException 当源文件不存在或移动失败时抛出
     * @example
     * <pre>
     * Util.moveFile("C:\\old.txt", "D:\\new.txt");
     * </pre>
     */
    public static void moveFile(String source, String target) {
        File src = new File(source);
        if (!src.exists()) {
            throw new RuntimeException("源文件不存在: " + source);
        }
        new File(target).getParentFile().mkdirs();

        // 尝试使用renameTo移动（跨分区可能失败）
        if (!src.renameTo(new File(target))) {
            // 如果失败，先复制再删除
            copyFile(source, target);
            src.delete();
        }
    }

    /**
     * 删除文件或目录
     *
     * <p>
     * 删除指定路径的文件或目录。如果是目录，会递归删除目录下的所有内容。
     * </p>
     *
     * @param path 待删除的文件或目录路径
     * @example
     * <pre>
     * Util.delete("C:\\temp");  // 删除文件或目录
     * </pre>
     */
    public static void delete(String path) {
        File f = new File(path);
        if (!f.exists()) {
            return;
        }

        // 如果是目录，递归删除内容
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null) {
                for (File file : files) {
                    delete(file.getAbsolutePath());
                }
            }
        }
        // 删除文件或空目录
        f.delete();
    }

    /**
     * 读取文件内容
     *
     * <p>
     * 以UTF-8编码读取整个文件内容为字符串
     * </p>
     *
     * @param path 文件路径
     * @return 文件内容字符串
     * @throws RuntimeException 当文件读取失败时抛出
     * @see #writeFile(String, String)
     * @example
     * <pre>
     * String content = Util.readFile("C:\\test.txt");
     * </pre>
     */
    public static String readFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("文件读取失败: " + e.getMessage(), e);
        }
    }

    /**
     * 写入文件内容
     *
     * <p>
     * 以UTF-8编码将内容写入指定文件。如果目标目录不存在会自动创建。
     * </p>
     *
     * @param path    文件路径
     * @param content 待写入内容
     * @throws RuntimeException 当文件写入失败时抛出
     * @see #readFile(String)
     * @example
     * <pre>
     * Util.writeFile("C:\\test.txt", "Hello World");
     * </pre>
     */
    public static void writeFile(String path, String content) {
        File f = new File(path);
        f.getParentFile().mkdirs();
        try {
            Files.write(Paths.get(path), content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("文件写入失败: " + e.getMessage(), e);
        }
    }

    // ==================== ZIP压缩 ====================

    /**
     * 压缩文件或目录
     *
     * <p>
     * 将源文件或目录压缩为ZIP文件，保持原始目录结构
     * </p>
     *
     * @param source 源文件或目录路径
     * @param target 目标ZIP文件路径
     * @throws RuntimeException 当源文件不存在或压缩失败时抛出
     * @see #zipFlat(String, String)
     * @example
     * <pre>
     * Util.zip("C:\\folder", "D:\\output.zip");
     * </pre>
     */
    public static void zip(String source, String target) {
        File src = new File(source);
        if (!src.exists()) {
            throw new RuntimeException("源文件不存在: " + source);
        }

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(new File(target)))) {
            zipToZip(src, zos, "");
        } catch (Exception e) {
            throw new RuntimeException("压缩失败: " + e.getMessage(), e);
        }
    }

    /**
     * 扁平化压缩
     *
     * <p>
     * 仅压缩目录下的所有文件，不保留子目录结构。
     * 所有文件将直接放在ZIP根目录。
     * </p>
     *
     * @param sourceDir 源目录路径
     * @param targetFile 目标ZIP文件路径
     * @return 压缩是否成功，源目录无效或为空时返回false
     * @see #zip(String, String)
     * @example
     * <pre>
     * boolean success = Util.zipFlat("C:\\folder", "D:\\output.zip");
     * </pre>
     */
    public static boolean zipFlat(String sourceDir, String targetFile) {
        File src = new File(sourceDir);
        if (!src.exists() || !src.isDirectory()) return false;

        File[] files = src.listFiles();
        if (files == null || files.length == 0) return false;

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(new File(targetFile)))) {
            for (File f : files) {
                if (f.isFile()) {
                    zipFile(f, zos, "");
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 递归添加文件到ZIP
     */
    private static void zipToZip(File file, ZipOutputStream zos, String base) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) return;
            for (File f : files) {
                zipToZip(f, zos, base + file.getName() + "/");
            }
        } else {
            zipFile(file, zos, base);
        }
    }

    /**
     * 添加单个文件到ZIP
     */
    private static void zipFile(File file, ZipOutputStream zos, String base) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            ZipEntry entry = new ZipEntry(base + file.getName());
            zos.putNextEntry(entry);

            byte[] buf = new byte[ZIP_BUFFER];
            int len;
            while ((len = bis.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
        }
    }

    // ==================== HTTP请求 ====================

    /**
     * 发送GET请求
     *
     * <p>
     * 使用默认UTF-8编码发送GET请求
     * </p>
     *
     * @param url 请求URL
     * @return 响应内容字符串
     * @throws RuntimeException 当请求失败时抛出
     * @see #httpGet(String, String)
     * @see #httpPost(String, String)
     * @example
     * <pre>
     * String result = Util.httpGet("https://api.example.com/data");
     * </pre>
     */
    public static String httpGet(String url) {
        return httpGet(url, "UTF-8");
    }

    /**
     * 发送GET请求
     *
     * <p>
     * 使用指定字符编码发送GET请求
     * </p>
     *
     * @param url     请求URL
     * @param charset 响应字符编码，如UTF-8、GBK等
     * @return 响应内容字符串
     * @throws RuntimeException 当请求失败时抛出
     * @example
     * <pre>
     * String result = Util.httpGet("https://api.example.com/data", "UTF-8");
     * </pre>
     */
    public static String httpGet(String url, String charset) {
        charset = charset == null ? "UTF-8" : charset;
        java.net.HttpURLConnection conn = null;
        try {
            conn = (java.net.HttpURLConnection) new java.net.URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setRequestProperty("Charset", charset);
            return httpRead(conn, charset);
        } catch (Exception e) {
            throw new RuntimeException("GET请求失败: " + e.getMessage(), e);
        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    /**
     * 发送POST请求
     *
     * <p>
     * 使用默认UTF-8编码提交表单数据
     * </p>
     *
     * @param url    请求URL
     * @param params 表单参数，格式如：key1=value1&key2=value2
     * @return 响应内容字符串
     * @throws RuntimeException 当请求失败时抛出
     * @see #httpPost(String, String, String)
     * @see #httpGet(String)
     * @example
     * <pre>
     * String result = Util.httpPost("https://api.example.com/login", "username=admin&password=123");
     * </pre>
     */
    public static String httpPost(String url, String params) {
        return httpPost(url, params, "UTF-8");
    }

    /**
     * 发送POST请求
     *
     * <p>
     * 使用指定字符编码提交数据
     * </p>
     *
     * @param url     请求URL
     * @param params  请求参数
     * @param charset 字符编码
     * @return 响应内容字符串
     * @throws RuntimeException 当请求失败时抛出
     * @example
     * <pre>
     * String result = Util.httpPost("https://api.example.com/data", "json={}", "UTF-8");
     * </pre>
     */
    public static String httpPost(String url, String params, String charset) {
        charset = charset == null ? "UTF-8" : charset;
        java.net.HttpURLConnection conn = null;
        try {
            conn = (java.net.HttpURLConnection) new java.net.URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // 写入请求参数
            if (params != null && !params.isEmpty()) {
                try (BufferedWriter bw = new BufferedWriter(
                        new OutputStreamWriter(conn.getOutputStream(), charset))) {
                    bw.write(params);
                    bw.flush();
                }
            }
            return httpRead(conn, charset);
        } catch (Exception e) {
            throw new RuntimeException("POST请求失败: " + e.getMessage(), e);
        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    /**
     * 读取HTTP响应内容
     */
    private static String httpRead(java.net.HttpURLConnection conn, String charset) throws Exception {
        int code = conn.getResponseCode();
        try (InputStream is = code == 200 ? conn.getInputStream() : conn.getErrorStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is, charset))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        }
    }

    // ==================== 编码转换 ====================

    /**
     * GBK编码转换为UTF-8编码
     *
     * @param str GBK编码的字符串
     * @return UTF-8编码的字符串
     * @see #utf8ToGbk(String)
     * @example
     * <pre>
     * String utf8 = Util.gbkToUtf8("测试");
     * </pre>
     */
    public static String gbkToUtf8(String str) {
        return charsetConvert(str, Charset.forName("GBK"), StandardCharsets.UTF_8);
    }

    /**
     * UTF-8编码转换为GBK编码
     *
     * @param str UTF-8编码的字符串
     * @return GBK编码的字符串
     * @see #gbkToUtf8(String)
     * @example
     * <pre>
     * String gbk = Util.utf8ToGbk("测试");
     * </pre>
     */
    public static String utf8ToGbk(String str) {
        return charsetConvert(str, StandardCharsets.UTF_8, Charset.forName("GBK"));
    }

    /**
     * 字符串编码转换
     *
     * <p>
     * 将字符串从一种字符集转换为另一种字符集
     * </p>
     *
     * @param str   待转换字符串
     * @param from  源字符集
     * @param to    目标字符集
     * @return 转换后的字符串，输入为null时返回null
     * @example
     * <pre>
     * String result = Util.charsetConvert("测试", 
     *     Charset.forName("GBK"), 
     *     Charset.forName("UTF-8"));
     * </pre>
     */
    public static String charsetConvert(String str, Charset from, Charset to) {
        if (str == null) return null;
        return new String(str.getBytes(from), to);
    }

    // ==================== 字节转换 ====================

    /**
     * 字节数组转十六进制字符串
     *
     * <p>
     * 将二进制数据转换为可读的十六进制字符串
     * </p>
     *
     * @param bytes 字节数组
     * @return 十六进制字符串，输入为null或空返回空字符串
     * @see #hexToBytes(String)
     * @example
     * <pre>
     * String hex = Util.bytesToHex(new byte[]{0x48, 0x65, 0x6c, 0x6c, 0x6f});
     * // 返回：48656c6c6f
     * </pre>
     */
    public static String bytesToHex(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return "";
        char[] chars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            chars[i * 2] = HEX[v >>> 4];
            chars[i * 2 + 1] = HEX[v & 0x0F];
        }
        return new String(chars);
    }

    /**
     * 十六进制字符串转字节数组
     *
     * <p>
     * 将十六进制字符串还原为二进制数据
     * </p>
     *
     * @param hex 十六进制字符串
     * @return 字节数组，输入为null或长度不为偶数返回null
     * @see #bytesToHex(byte[])
     * @example
     * <pre>
     * byte[] bytes = Util.hexToBytes("48656c6c6f");
     * // 返回：[72, 101, 108, 108, 111]
     * </pre>
     */
    public static byte[] hexToBytes(String hex) {
        if (hex == null || hex.length() % 2 != 0) return null;
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
        }
        return bytes;
    }
}