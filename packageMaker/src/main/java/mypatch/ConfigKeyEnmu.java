package mypatch;

public enum ConfigKeyEnmu {
    CHARSET,
    // 文件排除
    FILE_EXCLUDING,
    // 文件包含
    FILE_INCLUDE,
    // 用户名
    USERNAME,
    // 描述
    DESCRIPTION,
    // 工作空间位置
    WORK_SPACE,
    // 升级包位置
    PACKAGE_DIR,
    // patch文件类型即版本控制工具类型
    PATCH_FILE_KIND,
    // patch文件路径
    PATCH_FILENAME,
    // CC 时 确定是 CheckOut、Hijack
    TYPE
}
