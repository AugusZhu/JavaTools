# JavaTools
日常Java工具方法集合

## 项目简介

整合的Java工具类，提供加密解密、文件操作、压缩解压缩、网络请求、编码转换等常用功能。所有方法均为静态方法，无需实例化，直接调用即可。

## 目录结构

```
JavaTools/
├── src/
│   └── util/
│       └── Util.java    # 统一工具类（约600行）
├── lib/                 # 依赖库（备用）
└── README.md
```

## 快速开始

```java
import util.Util;

// AES加密
String encrypted = Util.aesEncrypt("Hello", "1234567890123456");
String decrypted = Util.aesDecrypt(encrypted, "1234567890123456");

// MD5加密
String hash = Util.md5("Hello World");

// UUID
String uuid = Util.uuid();

// 文件操作
Util.copyFile("a.txt", "b.txt");
Util.copyDir("dir1", "dir2");
Util.moveFile("old.txt", "new.txt");
Util.delete("path");
String content = Util.readFile("file.txt");
Util.writeFile("file.txt", "content");

// ZIP压缩
Util.zip("folder/", "out.zip");
Util.zipFlat("folder/", "flat.zip");

// HTTP请求
String result = Util.httpGet("https://example.com");
String result = Util.httpPost("https://example.com", "param=value");

// 编码转换
String utf8 = Util.gbkToUtf8("测试");
String gbk = Util.utf8ToGbk("测试");

// 字节转换
String hex = Util.bytesToHex(bytes);
byte[] bytes = Util.hexToBytes("48656c6c6f");
```

## API文档

### 1. AES加密解密

| 方法 | 说明 | 参数 | 返回值 |
|------|------|------|--------|
| `aesEncrypt(data, key)` | AES加密 | data: 明文, key: 16位密钥 | 密文(十六进制) |
| `aesDecrypt(data, key)` | AES解密 | data: 密文, key: 16位密钥 | 明文 |

```java
// 加密
String encrypted = Util.aesEncrypt("Hello World", "1234567890123456");
// 解密
String decrypted = Util.aesDecrypt(encrypted, "1234567890123456");
```

---

### 2. MD5加密

| 方法 | 说明 | 参数 | 返回值 |
|------|------|------|--------|
| `md5(data)` | MD5哈希 | data: 待加密字符串 | 32位小写MD5值 |

```java
String hash = Util.md5("Hello World");
// 输出：b10a8db164e0754105b7a99be72e3fe5
```

<b>注意：</b>MD5为单向哈希，不可解密，仅用于数据完整性校验。

---

### 3. UUID生成

| 方法 | 说明 | 参数 | 返回值 |
|------|------|------|--------|
| `uuid()` | 随机UUID | - | 32位UUID |
| `uuidWithDash()` | 随机UUID | - | 36位UUID(带连字符) |
| `uuidOf(name)` | 名称UUID | name: 名称字符串 | 32位UUID |

```java
Util.uuid();           // 550e8400e29b41d4a716446655440000
Util.uuidWithDash();   // 550e8400-e29b-41d4-a716-446655440000
Util.uuidOf("test");   // c81d4e2e8b2d11ec9f3e0800200c9a66
```

---

### 4. 文件操作

| 方法 | 说明 | 参数 |
|------|------|------|
| `copyFile(source, target)` | 复制文件 | source: 源路径, target: 目标路径 |
| `copyDir(source, target)` | 复制目录 | source: 源目录, target: 目标目录 |
| `moveFile(source, target)` | 移动文件 | source: 源路径, target: 目标路径 |
| `delete(path)` | 删除文件或目录 | path: 文件或目录路径 |
| `readFile(path)` | 读取文件 | path: 文件路径 |
| `writeFile(path, content)` | 写入文件 | path: 文件路径, content: 内容 |

```java
// 文件复制
Util.copyFile("C:\\source.txt", "D:\\backup\\target.txt");

// 目录复制
Util.copyDir("C:\\sourceDir", "D:\\backup\\targetDir");

// 文件移动
Util.moveFile("C:\\old.txt", "D:\\new.txt");

// 删除
Util.delete("C:\\temp");

// 读写
String content = Util.readFile("C:\\test.txt");
Util.writeFile("C:\\test.txt", "Hello World");
```

---

### 5. ZIP压缩

| 方法 | 说明 | 参数 | 返回值 |
|------|------|------|--------|
| `zip(source, target)` | 压缩文件或目录 | source: 源路径, target: 目标ZIP路径 | void |
| `zipFlat(sourceDir, targetFile)` | 扁平化压缩 | sourceDir: 源目录, targetFile: 目标ZIP | boolean |

```java
// 保持目录结构压缩
Util.zip("C:\\folder", "D:\\output.zip");

// 扁平化压缩（仅压缩目录下文件）
Util.zipFlat("C:\\folder", "D:\\flat.zip");
```

---

### 6. HTTP请求

| 方法 | 说明 | 参数 | 返回值 |
|------|------|------|--------|
| `httpGet(url)` | GET请求 | url: 请求地址 | 响应内容 |
| `httpGet(url, charset)` | GET请求 | url: 地址, charset: 编码 | 响应内容 |
| `httpPost(url, params)` | POST请求 | url: 地址, params: 参数 | 响应内容 |
| `httpPost(url, params, charset)` | POST请求 | url: 地址, params: 参数, charset: 编码 | 响应内容 |

```java
// GET请求
String result = Util.httpGet("https://api.example.com/data");
String result = Util.httpGet("https://api.example.com/data", "UTF-8");

// POST请求
String result = Util.httpPost("https://api.example.com/login", "username=admin&password=123");
String result = Util.httpPost("https://api.example.com/data", "json={}", "UTF-8");
```

<b>默认配置：</b>
- 连接超时：30秒
- 读取超时：30秒

---

### 7. 编码转换

| 方法 | 说明 | 参数 | 返回值 |
|------|------|------|--------|
| `gbkToUtf8(str)` | GBK转UTF-8 | str: GBK字符串 | UTF-8字符串 |
| `utf8ToGbk(str)` | UTF-8转GBK | str: UTF-8字符串 | GBK字符串 |
| `charsetConvert(str, from, to)` | 编码转换 | str: 字符串, from: 源编码, to: 目标编码 | 转换后字符串 |

```java
String utf8 = Util.gbkToUtf8("测试");
String gbk = Util.utf8ToGbk("测试");
String result = Util.charsetConvert("测试", Charset.forName("GBK"), Charset.forName("UTF-8"));
```

---

### 8. 字节转换

| 方法 | 说明 | 参数 | 返回值 |
|------|------|------|--------|
| `bytesToHex(bytes)` | 字节转十六进制 | bytes: 字节数组 | 十六进制字符串 |
| `hexToBytes(hex)` | 十六进制转字节 | hex: 十六进制字符串 | 字节数组 |

```java
String hex = Util.bytesToHex(new byte[]{0x48, 0x65});  // "4865"
byte[] bytes = Util.hexToBytes("4865");                  // [72, 101]
```

---

## 方法索引

| 分类 | 方法 |
|------|------|
| **AES** | `aesEncrypt(data, key)`<br>`aesDecrypt(data, key)` |
| **MD5** | `md5(data)` |
| **UUID** | `uuid()`<br>`uuidWithDash()`<br>`uuidOf(name)` |
| **文件** | `copyFile(source, target)`<br>`copyDir(source, target)`<br>`moveFile(source, target)`<br>`delete(path)`<br>`readFile(path)`<br>`writeFile(path, content)` |
| **ZIP** | `zip(source, target)`<br>`zipFlat(sourceDir, targetFile)` |
| **HTTP** | `httpGet(url)`<br>`httpGet(url, charset)`<br>`httpPost(url, params)`<br>`httpPost(url, params, charset)` |
| **编码** | `gbkToUtf8(str)`<br>`utf8ToGbk(str)`<br>`charsetConvert(str, from, to)` |
| **字节** | `bytesToHex(bytes)`<br>`hexToBytes(hex)` |

---

## 版本信息

- 作者：Xianfei Zhu
- 版本：2.0
- 更新日期：2024

## 许可证

MIT License
